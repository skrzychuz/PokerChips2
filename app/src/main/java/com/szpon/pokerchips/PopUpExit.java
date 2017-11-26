package com.szpon.pokerchips;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

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

        Players nowy = this.getIntent().getParcelableExtra("asdf");

        TextView playerLeave = (TextView) findViewById(R.id.line1);
        String s = nowy.getName();
        playerLeave.setText(s + " Leaves The Table");

        TextView yourStack = (TextView) findViewById(R.id.line2);
        String s1 = Float.toString(nowy.getStack());
        yourStack.setText("Your Stack:" + s1);

    }


//    public void exit(View view) {
//// to wyjebac
//        Intent intent = getIntent();
//        intent.putExtra("backPOP", test2);
//        setResult(RESULT_OK, intent);
//        finish();
//    }

    public void okButton(View view) {
        finish();
    }
}
