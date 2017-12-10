package com.szpon.pokerchips.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by KS on 2016-12-09.
 */
public class Players extends RealmObject implements Parcelable {

    private int ID;
    static int uniqID = 0;
    private String name;
    private float buyin;
    private float stack;
    private float stackHelper;
    private float preFlop, flop, turn, river;
    private float wins;
    private boolean isSelected;

    public Players() {
    }

    public Players(String name, float buyin, float stackHelper, boolean isSelected) {
        this.name = name;
        this.buyin = 0.0f;
        this.stack = buyin;
        this.stackHelper = buyin;
        this.isSelected = isSelected;
        this.wins = 0;

        uniqID++;
        ID = uniqID;

    }

    protected Players(Parcel in) {
        ID = in.readInt();
        name = in.readString();
        buyin = in.readFloat();
        stack = in.readFloat();
        stackHelper = in.readFloat();
        preFlop = in.readFloat();
        flop = in.readFloat();
        turn = in.readFloat();
        river = in.readFloat();
        wins = in.readFloat();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<Players> CREATOR = new Creator<Players>() {
        @Override
        public Players createFromParcel(Parcel in) {
            return new Players(in);
        }

        @Override
        public Players[] newArray(int size) {
            return new Players[size];
        }
    };

    public void clean () {
        setPreFlop(0);
        setFlop(0);
        setTurn(0);
        setRiver(0);
        setWins(0);
        setBuyin(0);
        setSelected(false);

    }


    public float bets () {
        float bets = preFlop + flop + turn + river;
        return bets;
    }
    public void reBuys (float rebuy) {

        this.buyin = rebuy;
    }

    public float getRebuy() {
        return buyin;
    }

    public float getBuyin() {
        return buyin;
    }

    public void setBuyin(float buyin) {
        this.buyin = buyin;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getStack() {
        return stack;
    }

    public void setStack() {
       this.stack = this.stackHelper - this.bets() + getWins() + getRebuy();
    }
    public void setStackk(float stack) {
        this.stack = stack;
    }

    public float getStackHelper() {
        return stackHelper;
    }

    public void setStackHelper(float stackHelper) {
        this.stackHelper = stackHelper;
    }

    public float getPreFlop() {
        return preFlop;
    }

    public void setPreFlop(float preFlop) {
        this.preFlop = preFlop;
    }

    public float getFlop() {
        return flop;
    }

    public void setFlop(float flop) {
        this.flop = flop;
    }

    public float getTurn() {
        return turn;
    }

    public void setTurn(float turn) {
        this.turn = turn;
    }

    public float getRiver() {
        return river;
    }

    public void setRiver(float river) {
        this.river = river;
    }

    public float getWins() {
        return wins;
    }

    public void setWins(float wins) {
        this.wins = wins;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(name);
        dest.writeFloat(buyin);
        dest.writeFloat(stack);
        dest.writeFloat(stackHelper);
        dest.writeFloat(preFlop);
        dest.writeFloat(flop);
        dest.writeFloat(turn);
        dest.writeFloat(river);
        dest.writeFloat(wins);
        dest.writeInt(isSelected ? 1 : 0);
    }
}
