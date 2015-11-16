package com.wkuglen.upcomingmatches;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

        refreshMatchList();
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
        if (id == R.id.action_delete) {
            deleteMatchList();
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteMatchList() {
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("storedJson", null);

        // Commit the edits!
        editor.commit();
        //Refresh the screen
        refreshMatchList();
        //Tell the User
        Context context = getApplicationContext();
        CharSequence text = "Matches Cleared";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }
    private ArrayList deleteMatch(int deletePosition){
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
        String json = settings.getString("storedJson", "null");
        ArrayList<Match> newJSONMatchList = null;
        if(json != null) {
            Type collectionType = new TypeToken<ArrayList<Match>>() {}.getType();
            ArrayList<Match> matchList = gson.fromJson(json, collectionType);
            System.err.println(matchList);
            matchList.remove(deletePosition);
            newJSONMatchList = new ArrayList<Match>(matchList);
            json = gson.toJson(newJSONMatchList);
        }
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("storedJson", json);

        // Commit the edits!
        editor.commit();
        /*//Reset the deleteWorthy boolean
        DeleteSingleDialogFragment.setDeleteWorthy(false);
        System.err.println("Reset to false");
        //Refresh the screen
        refreshMatchList();*/
        return newJSONMatchList;
    }
    private void refreshMatchList() {
        DeleteSingleDialogFragment.setDeleteWorthy(false);

        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
        String json = settings.getString("storedJson", "null");
        System.err.println(json);
        ArrayAdapter<Match> adapter;
        if(!json.equals("null")) {
            Type collectionType = new TypeToken<ArrayList<Match>>(){}.getType();
            ArrayList<Match> matchList = gson.fromJson(json, collectionType);
            System.err.println(matchList);
            adapter = new ArrayAdapter<Match>(this, android.R.layout.simple_list_item_1,  matchList);

        }
        else
        {
            adapter = null;
        }
        ListView listView = (ListView) findViewById(R.id.list_matches);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> adapter,View v, int position, long l){
                System.err.println("List Clicked @ "+position);
                DialogFragment newFragment = new DeleteSingleDialogFragment();
                newFragment.show(getSupportFragmentManager(), "deleteSingleDialog");
                if(DeleteSingleDialogFragment.isDeleteWorthy())
                {
                    //Context context  = getBaseContext();
                    //adapter = new ArrayAdapter<Match>(context, android.R.layout.simple_list_item_1, deleteMatch(position));
                }
            }


        });
    }
}

class DeleteSingleDialogFragment extends DialogFragment {

    private static boolean deleteWorthy = false;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to delete the match?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        //getActivity().deleteMatch();
                        deleteWorthy = true;
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteWorthy = false;
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public static boolean isDeleteWorthy() {
        return deleteWorthy;
    }

    public static void setDeleteWorthy(boolean deleteWorthy) {
        DeleteSingleDialogFragment.deleteWorthy = deleteWorthy;
    }
}