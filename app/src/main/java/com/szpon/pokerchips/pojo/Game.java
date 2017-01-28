package com.szpon.pokerchips.pojo;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by KS on 2017-01-21.
 */
public class Game extends RealmObject {

    private int gameID;

    static int uniqGameId = 0;



    RealmList<Hand> handList;

    public RealmList<Hand> getHandList() {
        return handList;
    }

    public void setHandList(Hand hand) {
        this.handList.add(hand);
    }

    public Game() {
    }

    public void setID () {
        gameID = uniqGameId;
        uniqGameId++;
    }

    public void setID2 (int i) {
        gameID = i;
    }

    public int getGameID() {
        return gameID;
    }
}
