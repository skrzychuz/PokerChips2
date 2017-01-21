package com.szpon.pokerchips.pojo;

import java.util.ArrayList;

import io.realm.RealmObject;

/**
 * Created by KS on 2017-01-09.
 */
public class Hand extends RealmObject {

    static int uniqHandId = 0;
    private int handId;
    private float handPot;

    ArrayList<Players> handPlayers;





}
