package com.wkuglen.upcomingmatches;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wkuglen.upcomingmatches.matchmanager.MatchQueue;


public class RetainedFragment extends Fragment {

    // data object we want to retain
    private MatchQueue data;

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public void setData(MatchQueue data) {
        this.data = data;
    }

    public MatchQueue getData() {
        return data;
    }
}