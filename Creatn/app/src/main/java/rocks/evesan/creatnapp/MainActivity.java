package rocks.evesan.creatnapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rocks.evesan.creatnapp.Adapter.HistoryAdapter;
import rocks.evesan.creatnapp.Constants.LocationConstants;
import rocks.evesan.creatnapp.Constants.RecordConstants;
import rocks.evesan.creatnapp.domain.AudioMultipart;
import rocks.evesan.creatnapp.domain.History;
import rocks.evesan.creatnapp.domain.searchData;
import rocks.evesan.creatnapp.io.apiAdapter;
import rocks.evesan.creatnapp.ui.RecordButton;
import rocks.evesan.creatnapp.ui.RecordButtonAdapter;
import rocks.evesan.creatnapp.ui.SnackBarError;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer notification;
    private RecordButton mRecordButton;

    private LocationManager locationManager;
    private Location currentLocation;
    private String lProvider;

    private ViewGroup rootScene;
    private Scene ready_buttonScene, listen_recordedScene, setTagNameScene;
    private TransitionManager mTransitionManager;

    private String NO_HISTORIES, NO_NAME, NO_TAG, UPLOADING, PLEASE_WAIT, INTERNET_ERROR, UPLOADED;
    private String Tag = null;

    private ProgressDialog progress;
    private Context ctx;
    private RelativeLayout wrapper;

    private HistoryAdapter hA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        wrapper = (RelativeLayout) findViewById(R.id.mainWrapper);
        SnackBarError.init( findViewById(R.id.wrapper) );
        ctx = this;
        hA = new HistoryAdapter(ctx);

        setListeners();
        setAnimations();
        getStrings();

        notification = MediaPlayer.create(this, R.raw.the_calling);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        lProvider = LocationManager.GPS_PROVIDER;
        getLastLocation();

    }

    private void setListeners() {
        mRecordButton = RecordButtonAdapter.setmRecordButton( (ImageButton) findViewById(R.id.record) );
        mRecordButton.getImageButton().setOnTouchListener(recordListener);

    }

    private void getStrings() {
        NO_HISTORIES = getResources().getString(R.string.no_location);
        NO_NAME = getResources().getString(R.string.no_name);
        NO_TAG = getResources().getString(R.string.no_tag);
        UPLOADING = getResources().getString(R.string.uploading);
        PLEASE_WAIT = getResources().getString(R.string.please_wait);
        INTERNET_ERROR = getResources().getString(R.string.internet_error);
        UPLOADED = getResources().getString(R.string.uploaded);
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
            mRecordButton.enable_sound();
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mRecordButton.startRecord();
                    break;
                case MotionEvent.ACTION_UP:
                    mRecordButton.stopRecord();
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
            locationManager.requestLocationUpdates(lProvider, LocationConstants.UPDATE_LOCATION_TIME, LocationConstants.UPDATE_LOCATION_DISCTANCE, locationListener);
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
                if( response.isSuccessful() ) {
                    if (response.body().size() > 0) {
                        notification.start();

                        hA.addHistories( response.body() );
                        hA.addToScreen(wrapper);

                    } else {
                        SnackBarError.getSnackBar(NO_HISTORIES).show();
                    }
                } else {
                    SnackBarError.getSnackBar("Some random error :C").show();
                }

            }

            @Override
            public void onFailure(Call<List<History>> call, Throwable t) {
                SnackBarError.getSnackBar( getResources().getString(R.string.network_error) ).show();
            }
        } );
    }

    public void playRecord(View v) {
        MediaPlayer mp = new MediaPlayer();
        final ImageButton playButton = (ImageButton) v;
        try {
            mp.setDataSource(RecordConstants.AUDIO_NAME);
            mp.prepare();
            mp.start();
            SnackBarError.getSnackBar( Integer.toString( mp.getDuration() ) ).show();

            playButton.setImageDrawable( getResources().getDrawable(R.drawable.play_button_disabled) );
            playButton.setEnabled(false);

            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    playButton.setEnabled(true);
                    playButton.setImageDrawable( getResources().getDrawable(R.drawable.play_button) );

                }
            });

        } catch (IOException e) {
            Log.e("TAG", "prepare() failed");
        }

    }

    public void deleteRecord(View v) {
        mTransitionManager.transitionTo(ready_buttonScene);
        setListeners();
    }

    public void tagRecord(View v) {
        mTransitionManager.transitionTo(setTagNameScene);
    }

    public void setTag(View v) {
        switch (v.getId()){
            case R.id.myth:
                Tag = "1";
                break;
            case R.id.love:
                Tag = "2";
                break;
            case R.id.crime:
                Tag = "3";
                break;
            case R.id.history:
                Tag = "4";
                break;
        }
    }

    public void uploadRecord (View v){
        String name = ( (EditText) findViewById(R.id.audio_name)).getText().toString();
        if ( !name.isEmpty() ) {
            if (Tag != null ) {
                progress = ProgressDialog.show( ctx, UPLOADING, PLEASE_WAIT);

                AudioMultipart form = new AudioMultipart( new String[] {
                        RecordConstants.AUDIO_NAME,
                        name,
                        Double.toString(currentLocation.getLatitude()),
                        Double.toString(currentLocation.getLongitude()),
                        Tag
                        } );

                Call <History> call = apiAdapter.getApiService().postHistory(
                        form.getAudio(),
                        form.getName(),
                        form.getAudio_lat(),
                        form.getAudio_lon(),
                        form.getAudio_tag()
                );

                call.enqueue(new Callback<History>() {
                    @Override
                    public void onResponse(Call<History> call, Response<History> response) {
                        progress.dismiss();
                        if (response.isSuccessful()) {
                            SnackBarError.getSnackBar(UPLOADED).show();
                            mTransitionManager.transitionTo(ready_buttonScene);
                        } else {
                            SnackBarError.getSnackBar("error").show();
                        }
                    }

                    @Override
                    public void onFailure(Call<History> call, Throwable t) {
                        progress.dismiss();
                        SnackBarError.getSnackBar(INTERNET_ERROR).show();
                    }
                });

            } else {
                SnackBarError.getSnackBar(NO_TAG).show();
            }
        } else {
            SnackBarError.getSnackBar(NO_NAME).show();
        }
    }

    @Override
    public void onBackPressed() {
        mTransitionManager.transitionTo(ready_buttonScene);
        setListeners();
    }
}
