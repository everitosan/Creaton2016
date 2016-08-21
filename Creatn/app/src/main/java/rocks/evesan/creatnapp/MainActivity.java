package rocks.evesan.creatnapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        SnackBarError.init( findViewById(R.id.wrapper) );

        mRecordButton = (RecordButton) findViewById(R.id.record);
        mRecordButton.setOnCheckedChangeListener(recordListener);

        notification = MediaPlayer.create(this, R.raw.the_calling);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        getLastLocation();


    }

    public CompoundButton.OnCheckedChangeListener recordListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) { //Record audio
                mRecordButton.prepare();
                mRecordButton.start();
            } else { //stop recording and show preview
                mRecordButton.stop();
                mRecordButton.release();
                Intent i = new Intent(MainActivity.this, UploadHistory.class);

                startActivity(i);
            }
        }
    };


    public void getLastLocation() {
        boolean permission = (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        if (permission) {
            String locationProvider = LocationManager.GPS_PROVIDER;

            Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

            searchData data = new searchData(Double.toString(lastKnownLocation.getLatitude()),  Double.toString(lastKnownLocation.getLongitude()) , "0");


            Call <List<History>> call = apiAdapter.getApiService().seacrhHistories(data);
            call.enqueue( new Callback<List<History>>() {

                @Override
                public void onResponse(Call<List<History>> call, Response<List<History>> response) {
                    notification.start();
                    Intent i = new Intent(MainActivity.this, NearbyHistory.class);
                    i.putExtra("list", (Serializable) response.body() );
                    startActivity(i);
                }

                @Override
                public void onFailure(Call<List<History>> call, Throwable t) {
                    SnackBarError.getSnackBar( getResources().getString(R.string.network_error) ).show();
                }
            } );

        } else {
            SnackBarError.getSnackBar( getResources().getString(R.string.permission_denied) ).show();
        }

    }
}
