package rocks.evesan.creatnapp.Adapter;

import android.content.Context;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import rocks.evesan.creatnapp.domain.History;
import rocks.evesan.creatnapp.ui.HistoryBubble;

/**
 * Created by evesan on 8/26/16.
 */
public class HistoryAdapter {

    private ArrayList<HistoryBubble> listBubbles;
    private Context ctx;

    public HistoryAdapter(Context ctx) {
        listBubbles = new ArrayList<>();
        this.ctx = ctx;
    }
    public void addHistories(List<History> list) {
        for (int i = 0; i < list.size(); i++) {
            HistoryBubble hB = new HistoryBubble(ctx, list.get(i));
            listBubbles.add(hB);
        }
    }

    public void addToScreen(RelativeLayout layout) {
        for (int i = 0; i < listBubbles.size(); i++) {
            layout.addView( listBubbles.get(i).getButton() );
        }
    }
}
