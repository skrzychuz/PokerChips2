package com.szpon.pokerchips;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by KS on 2016-12-09.
 */
public class Players implements Parcelable {

    private int ID;
    static int uniqID = 0;
    private String name;
    private float stack;
    private float stackHelper;
    private float preFlop, flop, turn, river;
    private float wins;
    private boolean isSelected;

    protected Players(Parcel in) {
        ID = in.readInt();
        name = in.readString();
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

    public float bets (float preFlop, float flop, float turn, float river) {
        float bets = preFlop + flop + turn + river;
        return bets;
    }
    public void reBuys (float rebuy) {
        this.stack = this.stack + rebuy;
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

    public void setStack(float stack) {
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
        dest.writeFloat(preFlop);
        dest.writeFloat(flop);
        dest.writeFloat(turn);
        dest.writeFloat(river);
        dest.writeFloat(wins);
        dest.writeInt(isSelected ? 1 : 0);
        dest.writeInt(ID);
        dest.writeString(name);
        dest.writeFloat(stack);
        dest.writeFloat(stackHelper);

    }
}