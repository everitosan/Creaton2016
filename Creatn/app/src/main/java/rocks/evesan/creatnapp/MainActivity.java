package rocks.evesan.creatnapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rocks.evesan.creatnapp.domain.History;
import rocks.evesan.creatnapp.domain.searchData;
import rocks.evesan.creatnapp.io.apiAdapter;
import rocks.evesan.creatnapp.ui.RecordButton;
import rocks.evesan.creatnapp.ui.SnackBarError;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private MediaPlayer notification;
    private RecordButton mRecordButton;
    private Location currentLocation;
    private String lProvider;

    private ViewGroup rootScene;
    private Scene ready_buttonScene, listen_recordedScene, setTagNameScene;
    private TransitionManager mTransitionManager;

    private String NOHISTORIES;

    private static final int UPDATE_LOCATION_TIME = 1000*3*60;
    private static final int UPDATE_LOCATION_DISCTANCE = 200;


    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        SnackBarError.init( findViewById(R.id.wrapper) );

        setListeners();
        setAnimations();
        getStrings();

        notification = MediaPlayer.create(this, R.raw.the_calling);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        lProvider = LocationManager.GPS_PROVIDER;
        getLastLocation();

    }

    private void setListeners() {
        mRecordButton = (RecordButton) findViewById(R.id.record);
        mRecordButton.setOnTouchListener(recordListener);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipper);
        //swipe action
        swipeContainer.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchHistories(currentLocation);
            }
        });
    }

    private void getStrings() {
        NOHISTORIES = getResources().getString(R.string.no_location);
    }

    private void setAnimations() {
        rootScene = (ViewGroup) findViewById(R.id.wrapper);
        ready_buttonScene = Scene.getSceneForLayout(rootScene, R.layout.ready_button, this);
        listen_recordedScene = Scene.getSceneForLayout(rootScene, R.layout.listen_recorded, this);
        setTagNameScene = Scene.getSceneForLayout(rootScene, R.layout.set_tag_name_scene, this);

        mTransitionManager = TransitionInflater.from(this).inflateTransitionManager(R.transition.transition_manager, rootScene);
    }

    public View.OnTouchListener recordListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mRecordButton.enableSound();
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mRecordButton.startRecord();
                    break;
                case MotionEvent.ACTION_UP:
                    mRecordButton.stop();
                    mRecordButton.release();
                    mRecordButton.setImageDrawable(getResources().getDrawable(R.drawable.record_button));
                    mTransitionManager.transitionTo(listen_recordedScene);
                    break;
            }
            return false;
        }
    };


    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            searchHistories(location);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {}

        public void onProviderEnabled(String provider) {}

        public void onProviderDisabled(String provider) {}
    };


    public void getLastLocation() {
        boolean permission = (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        if (permission) {
            String locationProvider = lProvider;
            Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
            currentLocation = lastKnownLocation;
            searchHistories(lastKnownLocation);
            //enable repetitive scan of location
            locationManager.requestLocationUpdates(lProvider, UPDATE_LOCATION_TIME, UPDATE_LOCATION_DISCTANCE, locationListener);
        } else {
            SnackBarError.getSnackBar( getResources().getString(R.string.permission_denied) ).show();
        }
    }

    private void searchHistories(Location location) {
        SnackBarError.getSnackBar(Double.toString(currentLocation.getLatitude()) + "," + Double.toString(currentLocation.getLongitude()) ).show();

        searchData data = new searchData(Double.toString(location.getLatitude()),  Double.toString(location.getLongitude()) , "0");

        Call <List<History>> call = apiAdapter.getApiService().seacrhHistories(data);
        call.enqueue( new Callback<List<History>>() {

            @Override
            public void onResponse(Call<List<History>> call, Response<List<History>> response) {
                swipeContainer.setRefreshing(false);
                if( response.isSuccessful() ) {
                    if (response.body().size() > 0) {
                        notification.start();
                        Intent i = new Intent(MainActivity.this, NearbyHistory.class);
                        i.putExtra("list", (Serializable) response.body() );
                        startActivity(i);
                    } else {
                        SnackBarError.getSnackBar(NOHISTORIES).show();
                    }
                } else {
                    SnackBarError.getSnackBar("Some random error :C").show();
                }

            }

            @Override
            public void onFailure(Call<List<History>> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                SnackBarError.getSnackBar( getResources().getString(R.string.network_error) ).show();
            }
        } );
    }


    public void playRecord(View v) {
        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(mRecordButton.getFileName());
            mp.prepare();
            mp.start();
            SnackBarError.getSnackBar( Integer.toString( mp.getDuration() ) ).show();
        } catch (IOException e) {
            Log.e("TAG", "prepare() failed");
        }
    }

    public void deleteRecord(View v) {
        mTransitionManager.transitionTo(ready_buttonScene);
        setListeners();
    }

    public void updateRecord(View v) {
        mTransitionManager.transitionTo(setTagNameScene);

    }
    @Override
    public void onBackPressed() {
        mTransitionManager.transitionTo(ready_buttonScene);
        setListeners();
    }
}
