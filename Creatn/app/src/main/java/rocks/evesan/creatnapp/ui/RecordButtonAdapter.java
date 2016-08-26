package rocks.evesan.creatnapp.ui;

import android.widget.ImageButton;

/**
 * Created by evesan on 8/25/16.
 */
public class RecordButtonAdapter {
    private static RecordButton mRecordButton = null;

    public static RecordButton setmRecordButton(ImageButton imageButton) {
        mRecordButton = new RecordButton(imageButton);
        return mRecordButton;
    }

    public static RecordButton getmRecordButton() {
        return mRecordButton;
    }

}
