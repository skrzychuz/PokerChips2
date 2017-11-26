package com.szpon.pokerchips;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by KS on 2016-11-27.
 */
public class AdapterForWinners extends RecyclerView.Adapter<AdapterForWinners.MyHolder> {

    private LayoutInflater myInflater;
    public ArrayList<Players> winnersList = new ArrayList<>();

    public AdapterForWinners(ArrayList<Players> winList, Context c) {
        this.winnersList.clear();
        this.myInflater = LayoutInflater.from(c);
        this.winnersList = winList;
    }

    @Override
    public AdapterForWinners.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = myInflater.inflate(R.layout.winners_item, parent, false);
        MyHolder vha = new MyHolder(view, new MCEditTextListener(view));
        return vha;
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        Players item = winnersList.get(position);
        holder.name.setText(item.getName());

        String string = Float.toString(item.bets());
        holder.bets.setText(string);

        holder.mcEditTextListener.updatePosition(holder.getAdapterPosition());
        String wins = Float.toString(winnersList.get(holder.getAdapterPosition()).getWins());
        holder.wins.setText(wins);

    }

    @Override
    public int getItemCount() {
        return winnersList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;
        public TextView bets;
        public EditText wins;
        public MCEditTextListener mcEditTextListener;

        public MyHolder(View itemView, MCEditTextListener mcEditTextListener) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.winner_name_ID);
            bets = (TextView) itemView.findViewById(R.id.winner_bets_ID);

            wins = (EditText) itemView.findViewById(R.id.winner_wins_ID);
            this.mcEditTextListener = new MCEditTextListener(wins);
            this.wins.addTextChangedListener(this.mcEditTextListener);

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
            if (text.isEmpty())
                winnersList.get(position).setWins(0.0f);
            else {
                Float in = Float.parseFloat(text);
                winnersList.get(position).setWins(in);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}