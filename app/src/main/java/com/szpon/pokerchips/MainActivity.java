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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRecycler = (RecyclerView) findViewById(R.id.recyclerview);
        myRecycler.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(PlayersList, this);
        myRecycler.setAdapter(myAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(myRecycler);

    }

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

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

        myAdapter.notifyDataSetChanged();

    }
    public void plus2(View view) {
        Intent intent = new Intent(this, AddPlayer.class);
        startActivityForResult(intent, ADD_PLAYER_CODE);
    }

    public void finish(View view) {
        //  startActivity(new Intent(MainActivity.this, Pop.class));
        kopialisty = new ArrayList<>(myAdapter.mPlayersListChecked);
        float fpot = 0;

        for (int i = 0; i < myAdapter.mPlayersList.size(); i++) {
            fpot = fpot + myAdapter.mPlayersList.get(i).bets();
        }

        if (kopialisty.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please select winners", Toast.LENGTH_SHORT).show();
        } else {

            Intent i = new Intent(this, PopWinners.class);
            i.putParcelableArrayListExtra("extra", kopialisty);
            i.putExtra("pot",fpot);
            startActivityForResult(i, WINNERS_CODE);

        }

}



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case ADD_PLAYER_CODE:
                if (resultCode == RESULT_OK) {
                    String name = data.getStringExtra("name");
                    Float stack = Float.parseFloat(data.getStringExtra("stack"));
                    Players item = new Players(name, stack ,0, false);
                    PlayersList.add(item);
                    myAdapter.notifyItemInserted(PlayersList.indexOf(item));
                }
                break;
          /*  case WINNERS_CODE:
                if (resultCode == RESULT_OK) {
                    kopialisty = data.getParcelableArrayListExtra("backPOP");

                    for (int i = 0; i<listData.size(); i++) {
                        for(int j = 0; j < kopialisty.size(); j++) {
                            if (listData.get(i).getID() == kopialisty.get(j).getID()) {

                                //  listData.remove(i);
                                //  listData.add(i, kopialisty.get(j));
                                listData.get(i).setWins(kopialisty.get(j).getWins());
                                myAdapter.notifyItemChanged(listData.indexOf(listData.get(i)));
                                myAdapter.notifyDataSetChanged();

                            }

                            // GDZIE NAJLEPIEJ TO UMIESCIC??
                            String string2 = listData.get(i).getName2();
                            float fstring2 = Float.parseFloat(string2);

                            String string3 = listData.get(i).getName3();
                            float fstring3 = Float.parseFloat(string3);

                            String string4 = listData.get(i).getName4();
                            float fstring4 = Float.parseFloat(string4);

                            String wins = listData.get(i).getWins();
                            float fwins = Float.parseFloat(wins);

                            float fstring1 = fstring4 - fstring2 - fstring3 + fwins;

                            String stringSuma = String.valueOf(fstring1);

                            listData.get(i).setName1(stringSuma);

                            myAdapter.notifyDataSetChanged();

                        }
                    }

                    myAdapter.notifyDataSetChanged();
                }

                break;    */
        }
    }
}





