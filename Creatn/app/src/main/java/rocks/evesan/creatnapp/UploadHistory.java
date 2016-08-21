package rocks.evesan.creatnapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rocks.evesan.creatnapp.domain.History;
import rocks.evesan.creatnapp.io.apiAdapter;
import rocks.evesan.creatnapp.ui.RecordButton;
import rocks.evesan.creatnapp.ui.SnackBarError;

public class UploadHistory extends AppCompatActivity {

    private RecordButton mRecordButton;
    private ImageButton rerecord;
    private ImageButton play;
    private Button up;
    private Location lastKnownLocation;
    private String lat;
    private String lon;
    private EditText name;
    private ProgressDialog progress;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_history);

        SnackBarError.init( findViewById(R.id.wrapper) );

        lat =  getIntent().getStringExtra("latitude");
        lon =  getIntent().getStringExtra("longitude");

        play = (ImageButton) findViewById(R.id.play);
        rerecord = (ImageButton) findViewById(R.id.rerecord);

        play.setOnClickListener(playAudioListener);
        rerecord.setOnTouchListener(rerecordListener);

        name = (EditText) findViewById(R.id.editText);

        up = (Button) findViewById(R.id.upload);
        up.setOnClickListener(uploadListener);

        mRecordButton = new RecordButton(this);
        ctx = this;

        getSupportActionBar().hide();
    }

    public View.OnClickListener uploadListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            if(!name.getText().toString().isEmpty() ) {


                File file = new File(mRecordButton.getFileName());
                RequestBody fbody =  RequestBody.create(MediaType.parse("video/3gpp"), file);
                MultipartBody.Part audio = MultipartBody.Part
                                .createFormData("history[audio]", file.getName(), fbody);
                MultipartBody.Part m_name = MultipartBody.Part.createFormData("history[name]", name.getText().toString());
                MultipartBody.Part m_lat = MultipartBody.Part.createFormData("history[latitude]", lat);
                MultipartBody.Part m_lon = MultipartBody.Part.createFormData("history[longitude]", lon);


                Call<History> call = apiAdapter.getApiService().postHistory(m_name, m_lat, m_lon, audio);
                progress = ProgressDialog.show( ctx, "Subiendo...", "Espere por favor");


                call.enqueue(new Callback<History>() {
                    @Override
                    public void onResponse(Call<History> call, Response<History> response) {
                        progress.dismiss();
                        if (response.isSuccessful()) {
                            SnackBarError.getSnackBar("La calle agradece tu historia").show();

                            finish();
                        } else {
                            SnackBarError.getSnackBar(":( Errors místicos").show();
                        }
                    }

                    @Override
                    public void onFailure(Call<History> call, Throwable t) {
                        progress.dismiss();
                        SnackBarError.getSnackBar(":( Errors místicos").show();

                    }
                });
            } else {
                SnackBarError.getSnackBar("Dále un nombre a tu historia.").show();
            }
        }
    };

    public View.OnClickListener playAudioListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            playAudio();
        }
    };

    public View.OnTouchListener rerecordListener = new View.OnTouchListener(){

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mRecordButton.prepare();
                    mRecordButton.start();
                    break;
                case MotionEvent.ACTION_UP:
                    mRecordButton.stop();
                    mRecordButton.release();
                    break;
            }
            return false;
        }
    };

    public void playAudio() {
        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(mRecordButton.getFileName());
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            Log.e("TAG", "prepare() failed");
        }
    }
}
