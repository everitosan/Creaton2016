package rocks.evesan.creatnapp.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;
import android.widget.ImageButton;

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
        init();
    }

    public void enable_sound() {
        this.sound_enabled = true;
    }

    public ImageButton getImageButton () {
        return this.mImageButton;
    }

    public void prepare() {
        try {
            mRecorder.prepare();
            mRecorder.start();
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
        mRecorder.setAudioEncodingBitRate(RecordConstants.SAMPLING_RATE);
        mRecorder.setAudioSamplingRate(RecordConstants.SAMPLING_RATE);
        mRecorder.setAudioChannels(RecordConstants.AUDIO_CHANNELS);
        mRecorder.setMaxDuration(RecordConstants.AUDIO_DURATION);
    }


    public void stop() {
        is_recording = false;
        mRecorder.stop();
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
        this.mImageButton.setImageDrawable(this.context.getResources().getDrawable(R.drawable.record_button_pressed));
        is_recording = true;
    }

    public void stopRecord () {
        mRecorder.stop();
        mRecorder.reset();
        mRecorder.release();
        this.mImageButton.setImageDrawable(this.context.getResources().getDrawable(R.drawable.record_button));
    }

    public void disable() {
        this.mImageButton.setColorFilter(this.context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
    }

    public void enable(){
        this.mImageButton.clearColorFilter();
    }

}
