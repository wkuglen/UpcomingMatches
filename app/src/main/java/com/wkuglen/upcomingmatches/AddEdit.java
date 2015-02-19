package com.wkuglen.upcomingmatches;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.wkuglen.upcomingmatches.matchmanager.Match;
import com.wkuglen.upcomingmatches.matchmanager.MatchQueue;

import java.util.Calendar;


public class AddEdit extends ActionBarActivity {

    private static int hourToBeAdded;
    private static int minuteToBeAdded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_edit, menu);
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

    public void submitNewMatch(View view) {
        Match addedMatch;

        //Match Number
        EditText numberEditText = (EditText) findViewById(R.id.match_number);
        Integer matchInteger = new Integer(numberEditText.getText().toString());

        //Alliance Color
        boolean toggle = Match.ALLIANCE_COLOR_BLUE;
        //if true toggle = Blue
        //if false toggle = Red

        //creating match
        addedMatch = new Match(matchInteger.intValue(), hourToBeAdded, minuteToBeAdded, toggle, Match.STATUS_IS_UPCOMING);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText("Added "+addedMatch.toString());
        System.err.println(addedMatch.toString());

        Gson oldGson = new Gson();
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
        String oldJson = settings.getString("storedJson", null);
        MatchQueue<Match> newMatchQueue;
        //if(gson.fromJson(json, MatchQueue.class) != null) {
            newMatchQueue = new MatchQueue<Match>(oldGson.fromJson(oldJson, MatchQueue.class));
        //}
        //else {
        //    newMatchQueue = new MatchQueue<Match>();
        //}

        newMatchQueue.enQueue(addedMatch);
        System.err.println("The Added Match is: "+addedMatch);
        MatchQueue<Match> queueToJson = new MatchQueue(newMatchQueue);
        Gson newGson = new Gson();
        String newJson = newGson.toJson(queueToJson);
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("storedJson", newJson);

        // Commit the edits!
        editor.commit();
        System.err.println(newJson);

        finish();
    }

    public static void setTime(int hour, int minute) {
        hourToBeAdded = hour;
        minuteToBeAdded = minute;

    }
}

class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        AddEdit.setTime(hourOfDay, minute);
    }
}
