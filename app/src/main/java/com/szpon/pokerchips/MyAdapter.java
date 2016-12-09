package com.szpon.pokerchips;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

    public MyAdapter(ArrayList<Players> playerslist, Context c) {
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
    public void onBindViewHolder(final MyAdapter.MyHolder holder, int position) {
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
        String river = Float.toString(mPlayersList.get(holder.getAdapterPosition()).getRiver());
        holder.river.setText(river);


        holder.Check.setOnCheckedChangeListener(null);
        holder.Check.setChecked(mPlayersList.get(position).isSelected()); //if true, checkbox will be selected, else unselected

        //CONTEX MENU
       /* holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(contex, holder.name);   //holder is accessed from inner class must  be final ??
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
*/


    holder.setItemClickListenerr(new ItemClickListenerr() {
        @Override
        public void onItemClickk(View v, int pos) {
            CheckBox chk = (CheckBox) v;

            if(chk.isChecked()) {
                mPlayersListChecked.add(mPlayersList.get(pos));
                mPlayersList.get(pos).setSelected(true);

            } else if(!chk.isChecked()){
                mPlayersList.get(pos).setSelected(false);
                mPlayersListChecked.remove(mPlayersList.get(pos));
            }
        }
    }); 

    }

    @Override
    public int getItemCount() {
       return mPlayersList.size();
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

            Check = (CheckBox) itemView.findViewById(R.id.checkboxID);
            Check.setOnClickListener(this);

            name = (TextView) itemView.findViewById(R.id.nameID);
            stack = (TextView) itemView.findViewById(R.id.stackID);

            preFlop = (EditText) itemView.findViewById(R.id.preflopID);
            myCustomEditTextListenert1 = new MyCustomEditTextListener(preFlop);
            preFlop.addTextChangedListener(this.myCustomEditTextListenert1);

            flop = (EditText) itemView.findViewById(R.id.flopID);
            myCustomEditTextListenert2 = new MyCustomEditTextListener(flop);
            this.flop.addTextChangedListener(this.myCustomEditTextListenert2);

            turn = (EditText) itemView.findViewById(R.id.turnID);
            myCustomEditTextListenert3 = new MyCustomEditTextListener(turn);
            this.turn.addTextChangedListener(this.myCustomEditTextListenert3);

            river = (EditText) itemView.findViewById(R.id.riverID);
            myCustomEditTextListenert4 = new MyCustomEditTextListener(river);
            this.river.addTextChangedListener(this.myCustomEditTextListenert4);

        }

        public void setItemClickListenerr(ItemClickListenerr ic) {
            this.itemClickListenerr = ic;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListenerr.onItemClickk(v, getLayoutPosition());
        }
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;
        private View view;


        private MyCustomEditTextListener(View view) {
            this.view = view;
        }


        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i2, int i3) {

            String text = s.toString();
            Float in = Float.parseFloat(text);
            switch (view.getId()) {
                case R.id.preflopID:
                    if (text.isEmpty())
                        mPlayersList.get(position).setPreFlop((float) 0.0);
                    else
                        mPlayersList.get(position).setPreFlop(in);

                    break;
                case R.id.flopID:
                    if (text.isEmpty())
                        mPlayersList.get(position).setFlop((float) 0.0);
                    else
                        mPlayersList.get(position).setFlop(in);
                    break;
                case R.id.turnID:
                    if (text.isEmpty())
                        mPlayersList.get(position).setTurn((float) 0.0);
                    else
                        mPlayersList.get(position).setTurn(in);

                break;
                
                case R.id.riverID:
                    if (text.isEmpty())
                        mPlayersList.get(position).setRiver((float) 0.0);
                    else
                        mPlayersList.get(position).setRiver(in);
                    break;
            }
        }


        @Override
        public void afterTextChanged(Editable e) {

          /*  String string2 = lista2.get(position).getName2();
            float fstring2 = Float.parseFloat(string2);

            String string3 = lista2.get(position).getName3();
            float fstring3 = Float.parseFloat(string3);

            String string4 = lista2.get(position).getName4();
            float fstring4 = Float.parseFloat(string4);

            String wins = lista2.get(position).getWins();
            float fwins = Float.parseFloat(wins);

            float fstring1 = fstring4 - fstring2 - fstring3 + fwins;

            String stringSuma = String.valueOf(fstring1);

            lista2.get(position).setName1(stringSuma);

*/
        }
    }
}









