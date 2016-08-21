package rocks.evesan.creatnapp.ui;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ToggleButton;

import java.io.IOException;

/**
 * Created by evesan on 8/21/16.
 */
public class RecordButton extends ToggleButton {

    private MediaRecorder mRecorder = null;
    private String mFileName;

    public RecordButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecordButton(Context context) {
        super(context);
    }


    public void init() {

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
        mRecorder.stop();
    }

    public void release() {
        mRecorder.release();
        mRecorder = null;
    }

}
