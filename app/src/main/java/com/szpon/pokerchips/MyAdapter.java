package com.szpon.pokerchips;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by KS on 2016-12-09.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    public ArrayList<Players> mPlayersList;
    public ArrayList<Players> mPlayersListChecked = new ArrayList<>();
    private LayoutInflater MyInflater;
    private Context contex;
    private Refresh refresher;
    private boolean flag;
    private Timer timer;
    private Timer timerr;
    final Handler mHandler = new Handler();


    public MyAdapter(ArrayList<Players> playerslist, Context c, Refresh refresher) {

        this.MyInflater = LayoutInflater.from(c);
        this.mPlayersList = playerslist;
        this.contex = c;
        this.refresher = refresher;
    }

    @Override
    public MyAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = MyInflater.inflate(R.layout.players_item, parent, false);
        MyHolder vh = new MyHolder(view, new MyCustomEditTextListener(view));
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyAdapter.MyHolder holder, int position) {


        flag = true;
        Players player = mPlayersList.get(position);

        holder.name.setText(player.getName());

        final String stack = Float.toString(player.getStack());
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

        flag = false;

        //CONTEX MENU
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(contex, holder.name);   //holder is accessed from inner class must  be final ??
                //inflating menu from xml resource
                popup.inflate(R.menu.menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.Rebuy:
                                int position;

                                position = holder.getAdapterPosition();
                                Players player = mPlayersList.get(position);
                                player.reBuys(100);
                                player.setStack();
                                notifyDataSetChanged();

                                break;
                            case R.id.Delete:
                                Players newPlayer = mPlayersList.get(holder.getAdapterPosition());

                                Intent i = new Intent(contex, PopUpExit.class);
                                i.putExtra("asdf", newPlayer);
                                contex.startActivity(i);
                                mPlayersList.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                break;
                        }
                        return false;
                    }
                });

                popup.show();
            }
        });


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                CheckBox chk = (CheckBox) v;

                if (chk.isChecked()) {
                    mPlayersListChecked.add(mPlayersList.get(pos));
                    mPlayersList.get(pos).setSelected(true);

                } else if (!chk.isChecked()) {
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
        ItemClickListener itemClickListener;


        public MyHolder(View itemView, MyCustomEditTextListener myCustomEditTextListener) {
            super(itemView);

            Check = (CheckBox) itemView.findViewById(R.id.checkboxID);
            Check.setOnClickListener(this);

            name = (TextView) itemView.findViewById(R.id.nameID);
            stack = (TextView) itemView.findViewById(R.id.stackID);

            preFlop = (EditText) itemView.findViewById(R.id.a1_preflopID);
            preFlop.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        refresher.refresh();
                    }
                    return false;
                }
            });
            myCustomEditTextListenert1 = new MyCustomEditTextListener(preFlop);
            preFlop.addTextChangedListener(this.myCustomEditTextListenert1);

            flop = (EditText) itemView.findViewById(R.id.a2_flopID);
            flop.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        refresher.refresh();
                    }
                    return false;
                }
            });

            myCustomEditTextListenert2 = new MyCustomEditTextListener(flop);
            this.flop.addTextChangedListener(this.myCustomEditTextListenert2);

            turn = (EditText) itemView.findViewById(R.id.turnID);
            turn.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        refresher.refresh();
                    }
                    return false;
                }
            });
            myCustomEditTextListenert3 = new MyCustomEditTextListener(turn);
            this.turn.addTextChangedListener(this.myCustomEditTextListenert3);

            river = (EditText) itemView.findViewById(R.id.riverID);
            river.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        refresher.refresh();
                    }
                    return false;
                }
            });
            myCustomEditTextListenert4 = new MyCustomEditTextListener(river);
            this.river.addTextChangedListener(this.myCustomEditTextListenert4);
        }

        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v, getLayoutPosition());
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

        }

        @Override
        public void onTextChanged(final CharSequence s, int i, int i2, int i3) {

            if (timerr != null) {
                timerr.cancel();
            }
        }

        @Override
        public void afterTextChanged(final Editable e) {

            String text = e.toString();
            switch (view.getId()) {

                case R.id.a1_preflopID:

                    if (text.isEmpty())
                        mPlayersList.get(position).setPreFlop(0.0f);
                    else {
                        Float in = Float.parseFloat(text);
                        mPlayersList.get(position).setPreFlop(in);
                        mPlayersList.get(position).setStack();
                    }
                    break;
                case R.id.a2_flopID:
                    if (text.isEmpty())
                        mPlayersList.get(position).setFlop(0.0f);
                    else {
                        Float in = Float.parseFloat(text);
                        mPlayersList.get(position).setFlop(in);
                        mPlayersList.get(position).setStack();
                    }
                    break;
                case R.id.turnID:
                    if (text.isEmpty())
                        mPlayersList.get(position).setTurn(0.0f);
                    else {
                        Float in = Float.parseFloat(text);
                        mPlayersList.get(position).setTurn(in);
                        mPlayersList.get(position).setStack();
                    }
                    break;

                case R.id.riverID:
                    if (text.isEmpty())
                        mPlayersList.get(position).setRiver((float) 0.0);
                    else {
                        Float in = Float.parseFloat(text);
                        mPlayersList.get(position).setRiver(in);
                        mPlayersList.get(position).setStack();
                    }
                    break;
            }
        }
    }
}

