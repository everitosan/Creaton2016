package rocks.evesan.creatnapp.ui;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import rocks.evesan.creatnapp.R;
import rocks.evesan.creatnapp.domain.History;
import rocks.evesan.creatnapp.domain.Tag;
import rocks.evesan.creatnapp.io.apiConstants;

/**
 * Created by evesan on 8/26/16.
 */
public class HistoryBubble  {
    private Button mButton = null;
    private Context ctx = null;
    private History history;

    public HistoryBubble(Context ctx, History h) {
        this.ctx = ctx;
        history = h;
        initButton();
        setBackground();
        setListener();
    }

    private void initButton() {
        mButton = new Button(this.ctx);
        mButton.setWidth( 50 );
        mButton.setHeight( 50 );
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
                String realUrl = apiConstants.URLBASE + history.getUrl();
              try {
                    Log.i("Error",realUrl);
                    mp.setDataSource(realUrl);

                    mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                        }
                    });

                    mp.prepareAsync();

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
