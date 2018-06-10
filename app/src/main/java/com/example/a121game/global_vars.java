
package com.example.a121game;
import android.app.Application;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;

public class global_vars extends Application {

    File f;
    JSONArray j = new JSONArray();
    ArrayList<String> a = new ArrayList<String>();
    public void setFile(File f){
        this.f = f;
    }
    public File getFile(){
        return f;
    }
    public JSONArray getJSONArray(){
        return j;
    }
    public void setJSONArray(JSONArray jA){
        this.j = jA;
    }
    public ArrayList<String> getArrayList(){
        return a;
    }




}
