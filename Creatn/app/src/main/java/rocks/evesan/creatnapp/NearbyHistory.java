package rocks.evesan.creatnapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import rocks.evesan.creatnapp.R;
import rocks.evesan.creatnapp.domain.History;

public class NearbyHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_history);

        Intent i = getIntent();
        List<History> list =(List<History>) i.getSerializableExtra("list");

    }
}
