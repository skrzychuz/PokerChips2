package com.szpon.pokerchips;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.szpon.pokerchips.AdapterForWinners;
import com.szpon.pokerchips.Players;
import com.szpon.pokerchips.R;

import java.util.ArrayList;

/**
 * Created by KS on 2016-11-20.
 */
public class PopUpExit extends AppCompatActivity {

    AdapterForWinners winnersAdapter;
    RecyclerView winnersRecycler;
    ArrayList<Players> test2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_exit);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width * 0.40), (int) (height * 0.30));


        //  test2 = this.getIntent().getParcelableArrayListExtra("winner");
        Players nowy = this.getIntent().getParcelableExtra("asdf");
        float pot = this.getIntent().getFloatExtra("pot", 0.0f);

        TextView opis1 = (TextView) findViewById(R.id.narka1);
        String s = nowy.getName();
        opis1.setText(s + " Leaves The Table");

        TextView opis2 = (TextView) findViewById(R.id.narka2);
        String s1 = Float.toString(nowy.getStack());
        opis2.setText("Your Stack:" + s1);








    }



    public void powrotpop(View view) {
// to wyjebac
        Intent intent = getIntent();
        intent.putExtra("backPOP", test2);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void methOK(View view) {
        finish();
    }
}
