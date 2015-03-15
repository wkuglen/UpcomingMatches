package com.wkuglen.upcomingmatches;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wkuglen.upcomingmatches.matchmanager.Match;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ViewMatches extends ActionBarActivity {

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_matches);
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
        String json = settings.getString("storedJson", "null");
        System.err.println(json);
        if(!json.equals("null")) {
            Type collectionType = new TypeToken<ArrayList<Match>>(){}.getType();
            ArrayList<Match> matchList = gson.fromJson(json, collectionType);


            System.err.println(matchList);
            ArrayAdapter<Match> adapter = new ArrayAdapter<Match>(this, android.R.layout.simple_list_item_1,  matchList);
            ListView listView = (ListView) findViewById(R.id.list_matches);
            listView.setAdapter(adapter);
        }
        else
        {
            TextView textView = new TextView(this);
            textView.setTextSize(40);
            textView.setText(json);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_matches, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
