package rocks.evesan.creatnapp.ui;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by evesan on 8/21/16.
 */
public class SnackBarError {

    static Snackbar snak;
    static View v;

    public static void init(View view) {
        v = view;
    }

    public static Snackbar getSnackBar(String msg) {
        return snak.make(v, msg, Snackbar.LENGTH_LONG);
    }
}
