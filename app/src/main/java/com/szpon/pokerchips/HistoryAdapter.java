package com.szpon.pokerchips;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szpon.pokerchips.pojo.Game;

import java.util.ArrayList;

/**
 * Created by KS on 2017-01-22.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {

    Context c;
    ArrayList<Integer> games;

    public HistoryAdapter(Context c, ArrayList<Integer> games) {
        this.c = c;
        this.games = games;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.history_item,parent,false);
        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        String string1 = Integer.toString((games.get(position)));
        holder.nameTxt.setText(string1);

    }

    @Override
    public int getItemCount() {
        return games.size();
    }
}

