package rocks.evesan.creatnapp.Constants;

import android.media.MediaRecorder;
import android.os.Environment;

/**
 * Created by evesan on 8/26/16.
 */
public class RecordConstants {
    public static final String AUDIO_NAME =  Environment.getExternalStorageDirectory().getAbsolutePath() + "/caminos_parlantes.3gp";
    public static final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
    public static final int OUTPUT_FORMAT = MediaRecorder.OutputFormat.THREE_GPP;
    public static final int AUDIO_ENCODER = MediaRecorder.AudioEncoder.AMR_NB;
}
