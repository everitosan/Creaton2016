package rocks.evesan.creatnapp;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import rocks.evesan.creatnapp.ui.RecordButton;

public class UploadHistory extends AppCompatActivity {

    private RecordButton mRecordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_history);

        mRecordButton = new RecordButton(this);
        playAudio();
    }

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
