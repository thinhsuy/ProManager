package com.example.promanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Set;

public class MyFragment extends Fragment {
    // Store instance variables
    private int page;
    private String userId;

    // newInstance constructor for creating fragment with arguments
    public static MyFragment newInstance(int page, String userId) {
        MyFragment fragmentFirst = new MyFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("User Id", userId);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        userId = getArguments().getString("User Id", "none");
    }


    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        switch (page){
            case 0: return SetUp.getActivityFragment(inflater.inflate(R.layout.fragment_acitvity, container, false), userId);
            case 1: return SetUp.getInformationFragment(inflater.inflate(R.layout.fragment_infor, container, false), userId);
            case 2: return SetUp.getOwnFragment(inflater.inflate(R.layout.fragment_own, container, false), userId);
            default: return SetUp.getSeekFragment(inflater.inflate(R.layout.fragment_seek, container, false), userId);
        }
    }
}