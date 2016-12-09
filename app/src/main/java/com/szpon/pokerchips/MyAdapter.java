package com.szpon.pokerchips;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by KS on 2016-12-09.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    public ArrayList<Players> mPlayersList;
    public ArrayList<Players> mPlayersListChecked = new ArrayList<>();
    private LayoutInflater MyInflater;
    private Context contex;

    public MyAdapter (ArrayList<Players> playerslist, Context c) {
        this.MyInflater = LayoutInflater.from(c);
        this.mPlayersList = playerslist;
        this.contex = c;
    }

    @Override
    public MyAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = MyInflater.inflate(R.layout.players_item, parent, false);
        MyHolder vh = new MyHolder(view, new MyCustomEditTextListener(view));
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyHolder holder, int position) {
        Players player = mPlayersList.get(position);

        holder.name.setText(player.getName());

        String stack = Float.toString(player.getStack());
        holder.stack.setText(stack);

        holder.myCustomEditTextListenert1.updatePosition(holder.getAdapterPosition());
        String preFlop = Float.toString(mPlayersList.get(holder.getAdapterPosition()).getPreFlop());
        holder.preFlop.setText(preFlop);

        holder.myCustomEditTextListenert2.updatePosition(holder.getAdapterPosition());
        String flop = Float.toString(mPlayersList.get(holder.getAdapterPosition()).getFlop());
        holder.flop.setText(flop);

        holder.myCustomEditTextListenert3.updatePosition(holder.getAdapterPosition());
        String turn = Float.toString(mPlayersList.get(holder.getAdapterPosition()).getTurn());
        holder.turn.setText(turn);

        holder.myCustomEditTextListenert4.updatePosition(holder.getAdapterPosition());
        String river = Float.toString(mPlayersList.get(holder.getAdapterPosition()).getPreFlop());
        holder.river.setText(river);


        holder.Check.setOnCheckedChangeListener(null);
        holder.Check.setChecked(mPlayersList.get(position).isSelected()); //if true, checkbox will be selected, else unselected

        //CONTEX MENU
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(contex, holder.text1);
                //inflating menu from xml resource
                popup.inflate(R.menu.pop_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.Rebuy:
                                //handle menu1 click
                                break;
                            case R.id.Delete:
                                ListItem nowy = lista2.get(holder.getAdapterPosition());

                                Intent i = new Intent(contex, PopupExit.class);
                                //  i.putParcelableArrayListExtra("winner", lista2);
                                i.putExtra("asdf", nowy);
                                contex.startActivity(i);


                                lista2.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                //handle menu2 click
                                break;
                            case R.id.menu3:
                                //handle menu3 click
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;
        public TextView stack;

        public EditText preFlop;
        public MyCustomEditTextListener myCustomEditTextListenert1;

        public EditText flop;
        public MyCustomEditTextListener myCustomEditTextListenert2;

        public EditText turn;
        public MyCustomEditTextListener myCustomEditTextListenert3;

        public EditText river;
        public MyCustomEditTextListener myCustomEditTextListenert4;

        CheckBox Check;
        ItemClickListenerr itemClickListenerr;


        public MyHolder(View itemView, MyCustomEditTextListener po_co_mi_ten_szajs) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
