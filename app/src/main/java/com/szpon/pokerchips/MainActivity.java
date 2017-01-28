package com.szpon.pokerchips;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.szpon.pokerchips.Activities.History;
import com.szpon.pokerchips.pojo.Game;
import com.szpon.pokerchips.pojo.Hand;
import com.szpon.pokerchips.pojo.Players;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity implements refreshInter {


    RecyclerView myRecycler;
    MyAdapter myAdapter, myAdapter2;
    Realm realm;
    RealmHelper realmHelper;
    Game game124;


    ArrayList<Players> PlayersList = new ArrayList<>();
    public static final int ADD_PLAYER_CODE = 1;
    public static final int WINNERS_CODE = 2;

    ArrayList<Players> zapas;
    public ArrayList<Players> kopialisty;
    public TextView pot;


    @Override
    public void refresz123() {
        float pula = 0;
        myAdapter.notifyDataSetChanged();
        for (int i=0; i<PlayersList.size(); i++) {
            float pothelper = PlayersList.get(i).bets();
            pula+=pothelper;
        }
        String s = Float.toString(pula);
        pot.setText("POT: " + s);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .build();




        Realm.setDefaultConfiguration(config);

        realm = Realm.getInstance(config);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        setContentView(R.layout.activity_main);
/*
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
*/

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Game game1 = realm.createObject(Game.class);
                Number number = realm.where(Game.class).max("gameID");
                int nextID;

                if(number == null)
                    nextID = 1;
                else {
                    nextID = number.intValue() + 1;
                }


                game1.setID2(nextID);

                realm.copyToRealm(game1);


                Hand hand1 = realm.createObject(Hand.class);
                game1.setHandList(hand1);
                Hand hand2 = realm.createObject(Hand.class);
                game1.setHandList(hand2);

                Players player1 = realm.createObject(Players.class);
                hand1.sethandplayers(player1);
                player1.setName("Tomek");

                Players player2 = realm.createObject(Players.class);
                hand1.sethandplayers(player2);
                player2.setName("Radek");

                Players player3 = realm.createObject(Players.class);
                hand1.sethandplayers(player3);
                player3.setName("Wojtek");
            }
        });


        pot = (TextView) findViewById(R.id.MainPotID);

        myRecycler = (RecyclerView) findViewById(R.id.recyclerview);
        myRecycler.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(PlayersList, this, this);


        realmHelper = new RealmHelper(realm);
        game124 = realmHelper.loadGame();


        myAdapter2 = new MyAdapter(game124, this, this);
     //   myRecycler.setAdapter(myAdapter); -------------------------------------------------------<

        myRecycler.setAdapter(myAdapter2);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(myRecycler);


        float potFromAdapter = this.getIntent().getFloatExtra("pot", 0.0f);
        String s = Float.toString(potFromAdapter);
        pot.setText("POT: " +s);
/*
        Players item = new Players("jacek", 100 ,0, false);
        PlayersList.add(item);
        myAdapter.notifyItemInserted(PlayersList.indexOf(item));

        Players item2 = new Players("placek", 100 ,0, false);
        PlayersList.add(item2);
        myAdapter.notifyItemInserted(PlayersList.indexOf(item2));

*/



}


    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP , ItemTouchHelper.DOWN)
                         {

                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                        return true;
                    }

                    @Override
                    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                     //   deleteItem(viewHolder.getAdapterPosition());
                    }
                };
        return simpleItemTouchCallback;
    }

    private void moveItem(int oldPos, int newPos) {

        Players item = PlayersList.get(oldPos);
        PlayersList.remove(oldPos);
        PlayersList.add(newPos, item);
        myAdapter.notifyItemMoved(oldPos, newPos);
    }

    public void refresh(View view) {
        float pula = 0;
        myAdapter.notifyDataSetChanged();
        for (int i=0; i<PlayersList.size(); i++) {
            float pothelper = PlayersList.get(i).bets();
            pula+=pothelper;
        }
        String s = Float.toString(pula);
        pot.setText("POT: " + s);

    }

    public void refreshPot () {
        float pula = 0;
        myAdapter.notifyDataSetChanged();
        for (int i=0; i<PlayersList.size(); i++) {
            float pothelper = PlayersList.get(i).bets();
            pula+=pothelper;
        }
        String s = Float.toString(pula);
        pot.setText(s);

    }
    public void plus2(View view) {
        Intent intent = new Intent(this, AddPlayer.class);
        startActivityForResult(intent, ADD_PLAYER_CODE);
    }

    public void finish(View view) {
        //  startActivity(new Intent(MainActivity.this, Pop.class));
        kopialisty = new ArrayList<>(myAdapter.mPlayersListChecked);
        float fpot = 0;
  //      myAdapter.mPlayersListChecked.clear();

        for (int i = 0; i < myAdapter.mPlayersList.size(); i++) {
            fpot = fpot + myAdapter.mPlayersList.get(i).bets();
        }

        if (kopialisty.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please select winners", Toast.LENGTH_SHORT).show();
        }
        if (kopialisty.size()==1) {

            int id = kopialisty.get(0).getID();

            for (Players x : PlayersList) {

                if (x.getID() == id) {
                    x.setWins(fpot);
                    x.setStack();
                }
                x.clean();
                x.setStackHelper(x.getStack());
                myAdapter.notifyDataSetChanged();
            }
        }
        if (kopialisty.size()>1) {

            Intent i = new Intent(this, PopWinners.class);
            i.putParcelableArrayListExtra("extra", kopialisty);
            i.putExtra("pot",fpot);
            startActivityForResult(i, WINNERS_CODE);

        }

       myAdapter.mPlayersListChecked.clear();
        refresz123();

}



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case ADD_PLAYER_CODE:
                if (resultCode == RESULT_OK) {
                    String name = data.getStringExtra("name");
                    Float stack = Float.parseFloat(data.getStringExtra("stack"));
                    Players item = new Players(name, stack ,0, false);
                    item.setStack();
                    PlayersList.add(item);
                    myAdapter.notifyItemInserted(PlayersList.indexOf(item));


                }
                break;
            case WINNERS_CODE:
                if (resultCode == RESULT_OK) {
                    kopialisty = data.getParcelableArrayListExtra("backPOP");

                    for (int i = 0; i<PlayersList.size(); i++) {
                        for(int j = 0; j < kopialisty.size(); j++) {
                            if (PlayersList.get(i).getID() == kopialisty.get(j).getID()) {
                                PlayersList.get(i).setWins(kopialisty.get(j).getWins());
                                PlayersList.get(i).setStack();
                          //      myAdapter.notifyItemChanged(PlayersList.indexOf(PlayersList.get(i)));
                           //     myAdapter.notifyDataSetChanged();
                            }

                        }
                        PlayersList.get(i).clean();
                        PlayersList.get(i).setStackHelper(PlayersList.get(i).getStack());
                    }
                    myAdapter.notifyDataSetChanged();
                }

                break;
        }
    }

    public void plus(View view) {
        Players item = new Players("Jacek", 100 ,0, false);
        PlayersList.add(item);
        myAdapter.notifyItemInserted(PlayersList.indexOf(item));

        Players item2 = new Players("Placek", 100 ,0, false);
        PlayersList.add(item2);
        myAdapter.notifyItemInserted(PlayersList.indexOf(item2));

        Players item3 = new Players("Bolek", 100 ,0, false);
        PlayersList.add(item3);
        myAdapter.notifyItemInserted(PlayersList.indexOf(item3));




        //debuger
        myAdapter.notifyDataSetChanged();
    }


    public void history(View view) {

        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }
}





