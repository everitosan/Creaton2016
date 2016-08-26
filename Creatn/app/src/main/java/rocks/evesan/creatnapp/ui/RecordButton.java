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

import rocks.evesan.creatnapp.Constants.RecordConstants;
import rocks.evesan.creatnapp.R;

/**
 * Created by evesan on 8/21/16.
 */
public class RecordButton {

    private ImageButton mImageButton = null;
    private MediaRecorder mRecorder = null;
    private MediaPlayer button_sound = null;
    private boolean sound_enabled = false;
    private Context context;
    private boolean is_recording = false;


    public RecordButton(ImageButton imageButton) {
        this.mImageButton = imageButton;
        this.context = mImageButton.getContext();
        this.button_sound = MediaPlayer.create(this.context, R.raw.served);
    }

    public void enable_sound() {
        this.sound_enabled = true;
    }

    public ImageButton getImageButton () {
        return this.mImageButton;
    }

    public void prepare() {
        init();
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e("TAG", "prepare() failed" + e.getMessage());
        }
    }

    private void init() {
        //Prepare media recorder
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(RecordConstants.AUDIO_SOURCE) ;
        mRecorder.setOutputFormat(RecordConstants.OUTPUT_FORMAT);
        mRecorder.setOutputFile(RecordConstants.AUDIO_NAME);
        mRecorder.setAudioEncoder(RecordConstants.AUDIO_ENCODER);
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
        this.mImageButton.setImageDrawable(this.context.getResources().getDrawable(R.drawable.record_button_pressed));
        is_recording = true;
    }

    public void stopRecord () {
        mRecorder.stop();
        mRecorder.release();
        this.mImageButton.setImageDrawable(this.context.getResources().getDrawable(R.drawable.record_button));
    }

}
