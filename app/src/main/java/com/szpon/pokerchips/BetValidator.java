package com.szpon.pokerchips;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

/**
 * Created by KS on 2017-01-07.
 */
public class BetValidator implements TextView.OnEditorActionListener {

    private refreshInter refresher;
    public BetValidator(refreshInter i) {
        refresher = i;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
            refresher.refresz123();

        }
            return false;

    }

}


