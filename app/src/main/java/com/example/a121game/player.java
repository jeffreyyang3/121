package com.example.a121game;
public class player {
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
}
