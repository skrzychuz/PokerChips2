package com.szpon.pokerchips.pojo;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by KS on 2017-01-09.
 */
public class Hand extends RealmObject {

    static int uniqHandId = 0;
    private int handId;
    private float handPot;

    RealmList<Players> handPlayers;

    public Hand() {
    }
    public void sethandplayers(Players player) {
        this.handPlayers.add(player);
    }


}
