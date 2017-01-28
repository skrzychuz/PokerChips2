package com.szpon.pokerchips;

import com.szpon.pokerchips.pojo.Game;
import com.szpon.pokerchips.pojo.Players;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Oclemy on 6/14/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class RealmHelper {

    Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }


    /*
    //WRITE
    public void saveHand(final Spacecraft spacecraft)
    {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Spacecraft s=realm.copyToRealm(spacecraft);

            }
        });

    }

    //READ
    public ArrayList<String> retrieveHand()
    {
        ArrayList<String> spacecraftNames=new ArrayList<>();
        RealmResults<Spacecraft> spacecrafts=realm.where(Spacecraft.class).findAll();

        for(Spacecraft s:spacecrafts)
        {
            spacecraftNames.add(s.getName());
        }

        return spacecraftNames;
    }       */

    //WRITE
    public void savePlayer(final Players player)
    {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Players s=realm.copyToRealm(player);

            }
        });

    }

    //READ
    public ArrayList<Players> retrievePlayer()
    {
        ArrayList<Players> playerslist=new ArrayList<>();
        RealmResults<Players> playerslistR=realm.where(Players.class).findAll();

        for(Players s:playerslistR)
        {
            playerslist.add(s);
        }

        return playerslist;
    }

    public Game loadGame()
    {
        Game game = realm.where(Game.class).findFirst();


        return game;
    }

    //READ
    public ArrayList<Integer> loadGames() {

        ArrayList<Integer> loadedGames = new ArrayList<>();
        RealmResults<Game> gamesfromrealm = realm.where(Game.class).findAll();

        for (Game s : gamesfromrealm) {
            loadedGames.add(s.getGameID());
        }

        return loadedGames;
    }



}







