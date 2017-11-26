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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Refresh {

    RecyclerView myRecycler;
    MyAdapter myAdapter;

    ArrayList<Players> playersList = new ArrayList<>();
    public static final int ADD_PLAYER_CODE = 1;
    public static final int WINNERS_CODE = 2;

    public ArrayList<Players> playersListCopy;
    public TextView pot;

    @Override
    public void refresh() {
        float pot = 0;
        myAdapter.notifyDataSetChanged();
        for (int i = 0; i < playersList.size(); i++) {
            float pothelper = playersList.get(i).bets();
            pot += pothelper;
        }
        String s = Float.toString(pot);
        this.pot.setText("POT: " + s);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pot = (TextView) findViewById(R.id.MainPotID);

        myRecycler = (RecyclerView) findViewById(R.id.recyclerview);
        myRecycler.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(playersList, this, this);
        myRecycler.setAdapter(myAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(myRecycler);


        float potFromAdapter = this.getIntent().getFloatExtra("pot", 0.0f);
        String s = Float.toString(potFromAdapter);
        pot.setText("POT: " + s);
    }

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP, ItemTouchHelper.DOWN) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                        return true;
                    }

                    @Override
                    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {

                    }
                };
        return simpleItemTouchCallback;
    }

    private void moveItem(int oldPos, int newPos) {

        Players item = playersList.get(oldPos);
        playersList.remove(oldPos);
        playersList.add(newPos, item);
        myAdapter.notifyItemMoved(oldPos, newPos);
    }

    public void refresh(View view) {
        float potHelper = 0;
        myAdapter.notifyDataSetChanged();
        for (int i = 0; i < playersList.size(); i++) {
            float pothelper = playersList.get(i).bets();
            potHelper += pothelper;
        }
        String s = Float.toString(potHelper);
        pot.setText("POT: " + s);

    }

    public void plus2(View view) {
        Intent intent = new Intent(this, AddPlayer.class);
        startActivityForResult(intent, ADD_PLAYER_CODE);
    }

    public void finish(View view) {

        playersListCopy = new ArrayList<>(myAdapter.mPlayersListChecked);
        float fpot = 0;

        for (int i = 0; i < myAdapter.mPlayersList.size(); i++) {
            fpot = fpot + myAdapter.mPlayersList.get(i).bets();
        }

        if (playersListCopy.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please select winners", Toast.LENGTH_SHORT).show();
        }
        if (playersListCopy.size() == 1) {

            int id = playersListCopy.get(0).getID();

            for (Players x : playersList) {

                if (x.getID() == id) {
                    x.setWins(fpot);
                    x.setStack();
                }
                x.clean();
                x.setStackHelper(x.getStack());
                myAdapter.notifyDataSetChanged();
            }
        }
        if (playersListCopy.size() > 1) {

            Intent i = new Intent(this, PopWinners.class);
            i.putParcelableArrayListExtra("extra", playersListCopy);
            i.putExtra("pot", fpot);
            startActivityForResult(i, WINNERS_CODE);
        }

        myAdapter.mPlayersListChecked.clear();
        refresh();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case ADD_PLAYER_CODE:
                if (resultCode == RESULT_OK) {
                    String name = data.getStringExtra("name");
                    Float stack = Float.parseFloat(data.getStringExtra("stack"));
                    Players item = new Players(name, stack, 0, false);
                    item.setStack();
                    playersList.add(item);
                    myAdapter.notifyItemInserted(playersList.indexOf(item));
                }
                break;
            case WINNERS_CODE:
                if (resultCode == RESULT_OK) {
                    playersListCopy = data.getParcelableArrayListExtra("backPOP");

                    for (int i = 0; i < playersList.size(); i++) {
                        for (int j = 0; j < playersListCopy.size(); j++) {
                            if (playersList.get(i).getID() == playersListCopy.get(j).getID()) {
                                playersList.get(i).setWins(playersListCopy.get(j).getWins());
                                playersList.get(i).setStack();
                            }

                        }
                        playersList.get(i).clean();
                        playersList.get(i).setStackHelper(playersList.get(i).getStack());
                    }
                    myAdapter.notifyDataSetChanged();
                }

                break;
        }
    }

    public void plus(View view) {
        Players item = new Players("Jacek", 100, 0, false);
        playersList.add(item);
        myAdapter.notifyItemInserted(playersList.indexOf(item));

        Players item2 = new Players("Placek", 100, 0, false);
        playersList.add(item2);
        myAdapter.notifyItemInserted(playersList.indexOf(item2));

        Players item3 = new Players("Bolek", 100, 0, false);
        playersList.add(item3);
        myAdapter.notifyItemInserted(playersList.indexOf(item3));

        myAdapter.notifyDataSetChanged();
    }
}





