package rocks.evesan.creatnapp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.util.Random;

import rocks.evesan.creatnapp.R;
import rocks.evesan.creatnapp.domain.History;
import rocks.evesan.creatnapp.domain.Tag;

/**
 * Created by evesan on 8/26/16.
 */
public class HistoryBubble  implements View.OnClickListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener{
    private Button mButton = null;
    private Context ctx = null;
    private History history = null;
    private DisplayMetrics metrics = null;
    private ProgressDialog progress;
    private MediaPlayer mp;

    private static final int w = 60;
    private static final int h = 60;

    public HistoryBubble(Context ctx, History h) {
        this.ctx = ctx;
        history = h;
        metrics = new DisplayMetrics();
        initButton();
        setBackground();
        setListener();
    }

    private void initButton() {
        mButton = new Button(this.ctx);
        RelativeLayout.LayoutParams params =  new RelativeLayout.LayoutParams(w, h);
        params.setMargins(
                getRandom("left"),
                getRandom("top"),
                0, 0);
        mButton.setLayoutParams(params);
    }

    private int getRandom(String side) {
        int min = 0;
        int max;

        ((Activity) this.ctx).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        if (side.equals("left") ) {
            max = 500;
        } else {
            max = 700;
        }

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;



        return randomNum;
    }


    private void setBackground() {
        Tag tag = (this.history.getTags() ).get(0);
        switch (tag.getId()) {
            case 1:
                mButton.setBackground( ctx.getResources().getDrawable(R.drawable.mitos_urbanos)  );
                break;
            case 2:
                mButton.setBackground( ctx.getResources().getDrawable(R.drawable.love)  );
                break;
            case 3:
                mButton.setBackground( ctx.getResources().getDrawable(R.drawable.crimen)  );
                break;
            case 4:
                mButton.setBackground( ctx.getResources().getDrawable(R.drawable.historico)  );
                break;
        }
    }

    private void setListener() {
        mButton.setOnClickListener(this);
    }

    public Button getButton () {
        return this.mButton;
    }

    @Override
    public void onClick(View v) {
        mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            Log.i(" -- URL --",history.getUrl().replaceFirst("https", "http") );
            mp.setDataSource(history.getUrl().replaceFirst("https", "http"));
            mp.setOnPreparedListener(this);
            mp.prepareAsync();
            progress = ProgressDialog.show( ctx, "cargando", history.getName());
            mp.setOnErrorListener(this);


        } catch(IOException e){
            Log.i("-- ERROR --", e.getMessage());
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        progress.dismiss();
    }
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.i("ERROR WHAT", Integer.toString(what) );
        Log.i("ERROR EXTRA", Integer.toString(extra) );
        progress.dismiss();
        return false;
    }
}
