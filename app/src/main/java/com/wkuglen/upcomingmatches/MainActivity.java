package com.wkuglen.upcomingmatches;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wkuglen.upcomingmatches.matchmanager.Match;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    public static final String PREFS_NAME = "JsonPrefsFile";
    private static int nextMatchPointer = 0;
    Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("storedJson", null);

        // Commit the edits!
        editor.commit();*/
    }


    @Override
    protected void onStart() {
        super.onStart();
        refreshUpcomingMatch();
        /*// Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String json = settings.getString("storedJson", null);
       *//* ArrayList matchList;
        if(json != null) {
            matchList = gson.fromJson(json, ArrayList.class);
        }*//*
        TextView textView = (TextView) findViewById(R.id.main_text_view);
        textView.setText(json);
        System.err.println("MAIN ACTIVITY "+json);*/

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            gotoAddEdit();
            return true;
        }
        if (id == R.id.action_view_matches) {
            gotoViewMatchQueue();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    private void gotoAddEdit() {
        Intent intent = new Intent(this, AddEdit.class);
        startActivity(intent);
    }
    private void gotoViewMatchQueue() {
        Intent intent = new Intent(this, ViewMatches.class);
        startActivity(intent);
    }

    private void refreshUpcomingMatch() {
        // Restore preferences
        String refreshedMatch;
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String json = settings.getString("storedJson", null);

        if(json != null) {
            Type collectionType = new TypeToken<ArrayList<Match>>(){}.getType();
            ArrayList<Match> matchList = gson.fromJson(json, collectionType);

            refreshedMatch = matchList.get(nextMatchPointer).toString();
        }
        else {
            refreshedMatch = "Add Some Matches!";
        }
        TextView textView = (TextView) findViewById(R.id.main_text_view);
        textView.setText(refreshedMatch);
        System.err.println("MAIN ACTIVITY "+json);
    }

    public void movePointer(View view) {
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String json = settings.getString("storedJson", null);

        if(json != null) {
            Type collectionType = new TypeToken<ArrayList<Match>>(){}.getType();
            ArrayList<Match> matchList = gson.fromJson(json, collectionType);

            if(nextMatchPointer+1 < matchList.size())
                nextMatchPointer++;
            else
                nextMatchPointer = 0;
        }
        refreshUpcomingMatch();
    }
}
