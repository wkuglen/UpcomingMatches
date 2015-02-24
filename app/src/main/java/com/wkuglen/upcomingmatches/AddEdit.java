package com.wkuglen.upcomingmatches;

import android.app.Dialog;
import android.app.TimePickerDialog;
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
import com.google.gson.reflect.TypeToken;
import com.wkuglen.upcomingmatches.matchmanager.Match;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
        boolean toggle;
        ToggleButton allianceToggleButton = (ToggleButton) findViewById(R.id.toggle_alliance_color);
        toggle = allianceToggleButton.isChecked();
        //Match.ALLIANCE_COLOR_BLUE;
        //if true toggle = Blue
        //if false toggle = Red

        //creating match
        addedMatch = new Match(matchInteger.intValue(), hourToBeAdded, minuteToBeAdded, toggle, Match.STATUS_IS_UPCOMING);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText("Added "+addedMatch.toString());
        System.err.println(addedMatch.toString());

        Gson gson = new Gson();
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
        String json = settings.getString("storedJson", null);
        ArrayList newMatchList;
        if(gson.fromJson(json, ArrayList.class) != null) {
            Type collectionType = new TypeToken<ArrayList<Match>>(){}.getType();
            newMatchList = gson.fromJson(json, collectionType);
        }
        else {
            newMatchList = new ArrayList<Match>();
        }


        System.err.println("The Added Match is: "+addedMatch);
        Gson gson2 = new Gson();
        if((newMatchList != null)&&(newMatchList.size()>=1)) {
            ArrayList<Match> newJSONMatchList = insertSorted(addedMatch, newMatchList);
            json = gson.toJson(newJSONMatchList);
        }
        else {
            newMatchList.add(addedMatch);
            ArrayList<Match> newJSONMatchList = new ArrayList<Match>(newMatchList);
            json = gson.toJson(newJSONMatchList);
        }



        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("storedJson", json);

        // Commit the edits!
        editor.commit();
        System.err.println(json);

        finish();
    }

    /*
    // insertion sort for ArrayList
    public ArrayList insertSort(ArrayList source){

        int index = 1;

        while (index < source.size()){
            //insertSorted((String)(source.get(index)), source, index);
            index = index + 1;
        }
        return source;
    }
    */

    // insert the given (City) object into the given list
    // assuming elements 0 through index - 1 are sorted
    // use comp for comparison
    public ArrayList insertSorted(Match toInsert, ArrayList<Match> source){
        int loc = source.size() - 1;
        source.add(new Match());
        Match pointer = source.get(loc);
        while ((loc >= 0) &&
                // c is smaller that the next highest element
                (toInsert.getMatchNumber() < pointer.getMatchNumber())){
            // move element to the right
            source.set(loc + 1, source.get(loc));
            loc = loc - 1;
            if(loc>=0) {
                pointer = source.get(loc);
            }
        }
        if(loc+1 < source.size()) {
            source.set(loc+1, toInsert);
        }
        else {
            source.add(toInsert);

        }
        return new ArrayList<Match>(source);
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
