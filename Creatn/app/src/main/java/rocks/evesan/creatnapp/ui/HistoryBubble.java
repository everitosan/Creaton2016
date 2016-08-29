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
public class HistoryBubble  {
    private Button mButton = null;
    private Context ctx = null;
    private History history = null;
    private DisplayMetrics metrics = null;
    private ProgressDialog progress;

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
                0,
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
            max = 900;
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
        mButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MediaPlayer mp = new MediaPlayer();
                mp.setAudioStreamType(AudioManager.STREAM_MUSIC);

              try {
                    Log.i(" -- URL --",history.getUrl());
                    mp.setDataSource(history.getUrl());

                    mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                            progress.dismiss();
                        }
                    });

                    mp.prepareAsync();
                    progress = ProgressDialog.show( ctx, "cargando", history.getName());
                    mp.setOnErrorListener( new MediaPlayer.OnErrorListener() {

                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            Log.i("ERROR", "ERROR");
                            progress.dismiss();
                            return false;
                        }
                    } );


                } catch(IOException e){
                    Log.i("Error", e.getMessage());
                }
            }
        } );
    }

    public Button getButton () {
        return this.mButton;
    }

}
