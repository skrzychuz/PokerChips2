package com.szpon.pokerchips;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by KS on 2017-01-22.
 */
public class HistoryViewHolder extends RecyclerView.ViewHolder {

    TextView nameTxt;

    public HistoryViewHolder(View itemView) {
        super(itemView);

        nameTxt= (TextView) itemView.findViewById(R.id.hisotryID1);
    }
}

