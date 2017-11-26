package com.szpon.pokerchips;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by KS on 2016-12-09.
 */
public class PopWinners extends AppCompatActivity {

    AdapterForWinners winnersAdapter;
    RecyclerView winnersRecycler;
    ArrayList<Players> playersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_winners);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        playersList.clear();
        playersList = this.getIntent().getParcelableArrayListExtra("extra");
        float pot = this.getIntent().getFloatExtra("pot",0.0f);

        getWindow().setLayout((int) (width*0.50), (int) (height*0.85));

        TextView textView = (TextView) findViewById(R.id.textpop1id);
        textView.setText("POT  " + String.valueOf(pot));

        winnersRecycler = (RecyclerView) findViewById(R.id.recyclerwinner);
        winnersRecycler.setHasFixedSize(true);
        winnersRecycler.setLayoutManager(new LinearLayoutManager(this));

        winnersAdapter = new AdapterForWinners(playersList, this);
        winnersRecycler.setAdapter(winnersAdapter);
    }


    public void exit(View view) {

        Intent intent = getIntent();
        intent.putExtra("backPOP", playersList);
        setResult(RESULT_OK, intent);
        finish();
    }

}

