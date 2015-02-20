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

import com.google.gson.Gson;
import com.wkuglen.upcomingmatches.matchmanager.Match;

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
        boolean toggle = Match.ALLIANCE_COLOR_BLUE;
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
//        System.err.println(json);
        ArrayList newMatchList;
        if(gson.fromJson(json, ArrayList.class) != null) {
            newMatchList = new ArrayList<Match>(gson.fromJson(json, ArrayList.class));
        }
        else {
            newMatchList = new ArrayList<Match>();
        }

        newMatchList.add(addedMatch);
        System.err.println("The Added Match is: "+addedMatch);

        newMatchList = new ArrayList<Match>(insertionSort(newMatchList));

        json = gson.toJson(newMatchList);
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("storedJson", json);

        // Commit the edits!
        editor.commit();
        System.err.println(json);

        finish();
    }

    public ArrayList<Match> insertionSort(ArrayList<Match> list)
    {
        Match now = new Match();
        Match temp = new Match();
        int in=0, out=0;
        for(out=1;out<list.size();out++)
        {
            temp = list.get(out);//now = temp.clone(list.get(out));
            in=out;
            while(in>0 && (temp.getMatchNumber() < list.get(in-1).getMatchNumber()) )
            {
                list.set(in, list.get(in-1));//list.get(in).clone(list.get(in-1));
                in--;
            }//while(j>0&&name.compareTo(list[j-1].getName())<0)
            list.set(in, temp);
        }//for(i=1;i<size;i++)

        return new ArrayList<Match>(list);

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
