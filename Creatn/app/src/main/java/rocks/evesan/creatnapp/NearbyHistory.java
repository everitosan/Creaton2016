package rocks.evesan.creatnapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import rocks.evesan.creatnapp.R;
import rocks.evesan.creatnapp.domain.History;
import rocks.evesan.creatnapp.ui.SnackBarError;

public class NearbyHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_history);

        getSupportActionBar().hide();

        SnackBarError.init(findViewById(R.id.wrapper));

        Intent i = getIntent();
        List<History> list =(List<History>) i.getSerializableExtra("list");
        SnackBarError.getSnackBar( Integer.toString( list.size() ) ).show();
    }
}
