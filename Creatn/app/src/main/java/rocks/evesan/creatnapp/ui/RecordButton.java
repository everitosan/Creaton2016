package rocks.evesan.creatnapp.ui;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import java.io.IOException;

import rocks.evesan.creatnapp.R;

/**
 * Created by evesan on 8/21/16.
 */
public class RecordButton extends ImageButton {

    private MediaRecorder mRecorder = null;
    private MediaPlayer button_sound = null;
    private boolean sound_enabled = false;
    private String mFileName;
    private Context context;
    private boolean is_recording = false;

    public RecordButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        postConstructor(context);
    }

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        postConstructor(context);
    }

    public RecordButton(Context context) {
        super(context);
        postConstructor(context);
    }

    public void postConstructor(Context ctx) {
        this.context = ctx;
        //Set audio for button
        button_sound = MediaPlayer.create(this.context, R.raw.served);
    }

    public void enableSound() {
        sound_enabled = true;
    }


    public void init() {
        postConstructor(context);
        //Set file name and route
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/caminos_parlantes.3gp";

        //Prepare media recorder
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);


    }

    public String getFileName() {
        init();
        return mFileName;
    }

    public void prepare() {
        init();
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e("TAG", "prepare() failed" + e.getMessage());
        }
    }

    public void start() {
        mRecorder.start();
    }

    public void stop() {
        is_recording = false;
        mRecorder.stop();
    }

    public void release() {
        mRecorder.release();
        mRecorder = null;
    }

    public void startRecord() {
        if(sound_enabled) {
            button_sound.start();
        }

        if (is_recording) {
            stop();
            is_recording = false;
        }

        prepare();
        start();
        setImageDrawable(getResources().getDrawable(R.drawable.record_button_pressed));
        is_recording = true;
    }

}
