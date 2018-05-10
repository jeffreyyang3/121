package com.example.a121game;

import android.os.Parcel;
import android.os.Parcelable;

public class player implements Parcelable{
    public String PlayerName;
    public String CharacterName;

    private int Health;

    public int GetHealth(){
        return Health;
    }
    public void modifyHealth(int change){
        this.Health += change;
    }

    public player(String PlayName, String CharName, int HP){
        this.PlayerName = PlayName;
        this.CharacterName = CharName;
        this.Health = HP;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(this.PlayerName);
        dest.writeString(this.CharacterName);
        dest.writeInt(this.Health);
    }

    protected player(Parcel in){
        this.PlayerName = in.readString();
        this.CharacterName = in.readString();
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
