package com.vogella.android.myapplication.views.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vogella.android.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemDetailsFragment extends Fragment {


    public ItemDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_details, container, false);
    }

}
