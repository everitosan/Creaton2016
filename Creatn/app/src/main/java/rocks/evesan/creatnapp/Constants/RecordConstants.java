package rocks.evesan.creatnapp.Constants;

import android.media.MediaRecorder;
import android.os.Environment;

/**
 * Created by evesan on 8/26/16.
 */
public class RecordConstants {
    public static final String AUDIO_NAME =  Environment.getExternalStorageDirectory().getAbsolutePath() + "/caminos_parlantes.mp3";
    public static final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
    public static final int OUTPUT_FORMAT = MediaRecorder.OutputFormat.MPEG_4;
    public static final int AUDIO_ENCODER = MediaRecorder.AudioEncoder.AAC;
}
