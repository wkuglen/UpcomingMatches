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
import com.wkuglen.upcomingmatches.matchmanager.Match;
import com.wkuglen.upcomingmatches.matchmanager.MatchQueue;

import java.util.List;


public class ViewMatches extends ActionBarActivity {

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_matches);

        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
        String json = settings.getString("storedJson", "null");
        if(!json.equals("null")) {
            MatchQueue matchQueue = gson.fromJson(json, MatchQueue.class);
            List list = matchQueue.getMyQueue();
            ArrayAdapter<Match> adapter = new ArrayAdapter<Match>(this, android.R.layout.simple_list_item_1, list);
            ListView listView = (ListView) findViewById(R.id.list_item);
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
