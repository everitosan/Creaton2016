package rocks.evesan.creatnapp.Constants;

import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.os.Environment;

/**
 * Created by evesan on 8/26/16.
 */
public class RecordConstants {
    public static final String AUDIO_NAME =  Environment.getExternalStorageDirectory().getAbsolutePath() + "/cp.m4a";
    public static final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
    public static final int OUTPUT_FORMAT = MediaRecorder.OutputFormat.MPEG_4;
    public static final int AUDIO_ENCODER = MediaRecorder.AudioEncoder.AAC;
    public static final int AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    public static final int SAMPLING_RATE = 48000;
    public static final int AUDIO_DURATION = 10000;
    public static final int AUDIO_CHANNELS = 1;
}
