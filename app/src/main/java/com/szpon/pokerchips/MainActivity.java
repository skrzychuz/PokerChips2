package com.szpon.pokerchips;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    RecyclerView myRecycler;
    MyAdapter myAdapter;

    ArrayList<Players> PlayersList = new ArrayList<>();
    public static final int ADD_PLAYER_CODE = 1;
    public static final int WINNERS_CODE = 2;

    ArrayList<Players> zapas;
    public ArrayList<Players> kopialisty;
    public TextView pot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        pot = (TextView) findViewById(R.id.MainPotID);

        myRecycler = (RecyclerView) findViewById(R.id.recyclerview);
        myRecycler.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(PlayersList, this);
        myRecycler.setAdapter(myAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(myRecycler);

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
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {

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
        else
        {

            Intent i = new Intent(this, PopWinners.class);
            i.putParcelableArrayListExtra("extra", kopialisty);
            i.putExtra("pot",fpot);
            startActivityForResult(i, WINNERS_CODE);

        }

       myAdapter.mPlayersListChecked.clear();

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
}





