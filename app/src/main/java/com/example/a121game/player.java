package com.example.a121game;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class player implements Parcelable{
    private String PlayerName;
    private String CharacterName;
    private int[] PlayerStats;
    private int Health;

    private JSONObject jS;

    public int GetHealth(){
        return Health;
    }
    public void modifyHealth(int change){
        this.Health += change;
    }

    public player(String PlayName, int stats[], String CharName, int HP){
        this.PlayerName = PlayName;
        this.PlayerStats = stats;
        this.CharacterName = CharName;
        this.Health = HP;
    }

    public JSONObject getJson(){
        try {
            jS.put("player", this.PlayerName);
            jS.put("character", this.CharacterName);
            jS.put("intellect", this.PlayerStats[0]);
            jS.put("wisdom", this.PlayerStats[1]);
            jS.put("charisma", this.PlayerStats[2]);
            jS.put("strength", this.PlayerStats[3]);
            jS.put("dexterity", this.PlayerStats[4]);
            jS.put("constitution", this.PlayerStats[5]);

            jS.put("health", this.Health);

        }catch(JSONException jX){

        }
        return jS;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(this.PlayerName);
        dest.writeString(this.CharacterName);
        dest.writeIntArray(this.PlayerStats);
        dest.writeInt(this.Health);
    }

    protected player(Parcel in){
        this.PlayerName = in.readString();
        this.CharacterName = in.readString();
        this.PlayerStats = in.createIntArray();
        this.Health = in.readInt();
    }

    public static final Parcelable.Creator<player> CREATOR = new Parcelable.Creator<player>(){
        public player createFromParcel(Parcel source){
            return new player(source);
        }

        public player[] newArray(int size){
            return new player[size];
        }
    };

}
