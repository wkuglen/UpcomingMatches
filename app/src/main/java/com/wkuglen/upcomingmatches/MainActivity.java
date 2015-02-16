package com.wkuglen.upcomingmatches;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wkuglen.upcomingmatches.matchmanager.Match;
import com.wkuglen.upcomingmatches.matchmanager.MatchQueue;


public class MainActivity extends ActionBarActivity {

    public static final String PREFS_NAME = "JsonPrefsFile";
    Gson gson = new Gson();
    MatchQueue<Match> oldMatchQueue = new MatchQueue<Match>();
    MatchQueue<Match> newMatchQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String json = settings.getString("storedJson", null);

        newMatchQueue = gson.fromJson(json, MatchQueue.class);

        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(json);

    }


    @Override
    protected void onStart() {
        super.onStart();
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String json = settings.getString("storedJson", null);
        newMatchQueue = gson.fromJson(json, MatchQueue.class);

        System.err.println("MAIN ACTIVITY"+json);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(json);
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

    /*
    @Override
    protected void onStop(){
        super.onStop();
        if(newMatchQueue != null && oldMatchQueue != null) {
            //Write to oldMatchQueue in order to avoid infinte recursion as defined by Gson
            oldMatchQueue = new MatchQueue<Match>(newMatchQueue);
            String json = gson.toJson(oldMatchQueue);
            // We need an Editor object to make preference changes.
            // All objects are from android.context.Context
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("storedJson", json);

            // Commit the edits!
            editor.commit();
        }
    }
    */
    private void gotoAddEdit() {
        Intent intent = new Intent(this, AddEdit.class);
        startActivity(intent);
    }
    private void gotoViewMatchQueue() {
        Intent intent = new Intent(this, ViewMatches.class);
        startActivity(intent);
    }
}
