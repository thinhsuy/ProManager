package com.example.promanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.Set;

public class MyFragment extends Fragment {
    // Store instance variables
    private int page;
    private String userId;
    public Query db;
    public static View BubbleView;

    // newInstance constructor for creating fragment with arguments
    public static MyFragment newInstance(int page, String userId, View viewChange) {
        MyFragment fragmentFirst = new MyFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("User Id", userId);
        fragmentFirst.setArguments(args);
        BubbleView = viewChange;
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = ((GlobalVar)getActivity().getApplication()).getLocalQuery();
        page = getArguments().getInt("someInt", 0);
        userId = getArguments().getString("User Id", "none");
    }


    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView bubble = (BubbleView.findViewById(R.id.user_bubble_textview));
        String[] textTips = {"How about your tasks?",
                            "Reviewing yourself now?!",
                            "Be a nice manager",
                            "So bored? Let's work",
                            "How was your days?",
                            "Be strong, don't give up!"};
        int random = new Random().nextInt((textTips.length-1 - 0) + 1) + 0;
        bubble.setText(textTips[random]);
        switch (page){
            case 0: return SetUp.getActivityFragment(db, inflater.inflate(R.layout.fragment_acitvity, container, false), userId);
            case 1: return SetUp.getInformationFragment(db, inflater.inflate(R.layout.fragment_infor, container, false), userId);
            case 2:
                try {
                    return SetUp.getOwnFragment(db, inflater.inflate(R.layout.fragment_own, container, false), userId);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            case 3: return SetUp.getSeekFragment(db, inflater.inflate(R.layout.fragment_seek, container, false), userId);
            default: {return null;}
        }
    }
}