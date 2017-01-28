package com.szpon.pokerchips.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.szpon.pokerchips.HistoryAdapter;
import com.szpon.pokerchips.R;
import com.szpon.pokerchips.RealmHelper;
import com.szpon.pokerchips.pojo.Game;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by KS on 2017-01-22.
 */
public class History extends AppCompatActivity {

    RecyclerView rv;
    Realm realm;
    HistoryAdapter historyAdapter;
    ArrayList<Integer> historygames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_game);

        //SETUP RECYCLERVIEW
        rv= (RecyclerView) findViewById(R.id.recyclerhistory);
        rv.setLayoutManager(new LinearLayoutManager(this));

        //SETUP REEALM
       // RealmConfiguration config=new RealmConfiguration.Builder(this).build();
      //  realm= Realm.getInstance(config);


    /*    Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);

        realm = Realm.getInstance(config);
*/
         realm = Realm.getDefaultInstance();
        //RETRIEVE
        RealmHelper helper=new RealmHelper(realm);
         historygames = helper.loadGames();

        //BIND
        historyAdapter = new HistoryAdapter(this,historygames);
        rv.setAdapter(historyAdapter);






    }
}
