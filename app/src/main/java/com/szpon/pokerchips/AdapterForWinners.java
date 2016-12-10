package com.szpon.pokerchips;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by KS on 2016-11-27.
 */
public class AdapterForWinners extends RecyclerView.Adapter<AdapterForWinners.MyHolder> {


    private LayoutInflater MyInflater;
    public ArrayList<Players> mWinners;


  /*      private class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView name;
            public TextView stack;
            public MyViewHolder(View pItem) {
                super(pItem);
                name = (TextView) pItem.findViewById(R.id.text_view_winner);
                stack = (TextView) pItem.findViewById(R.id.winner_paid);
            }
        }
  */


    public AdapterForWinners(ArrayList<Players> lista, Context c) {
        this.MyInflater = LayoutInflater.from(c);
        this.mWinners = lista;
    }


    @Override
    public AdapterForWinners.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = MyInflater.inflate(R.layout.winners_item, parent, false);

        MyHolder vha = new MyHolder(view, new MCEditTextListener(view));  //nowy konstruktor!!

        return vha;
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        Players item = mWinners.get(position);
        holder.name.setText(item.getName());

        String string = Float.toString(item.bets());
        holder.bets.setText(string);
        
        holder.listener1.updatePosition(holder.getAdapterPosition());
        String wins = Float.toString(mWinners.get(holder.getAdapterPosition()).getWins());
        holder.wins.setText(wins);


    }

    @Override
    public int getItemCount() {
        return mWinners.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;
        public TextView bets;
        public EditText wins;
        public MCEditTextListener listener1;


        public MyHolder(View itemView, MCEditTextListener po_co_mi_ten_szajs) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.winner_name_ID);
            bets = (TextView) itemView.findViewById(R.id.winner_bets_ID);

            wins = (EditText) itemView.findViewById(R.id.winner_wins_ID);
            listener1 = new MCEditTextListener(wins);
            this.wins.addTextChangedListener(this.listener1);

        }

        @Override
        public void onClick(View v) {

        }


    }


    private class MCEditTextListener implements TextWatcher {
        private int position;
        private View view;


        public MCEditTextListener(View view) {
            this.view = view;
        }
        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String text = s.toString();
            if(text.isEmpty())
                mWinners.get(position).setWins(0.0f);
            else {
                Float in = Float.parseFloat(text);
                mWinners.get(position).setWins(in);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}