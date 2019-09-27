package com.vogella.android.myapplication.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.ViewGroup;

import com.vogella.android.myapplication.R;
import com.vogella.android.myapplication.TagItemsListFragment;
import com.vogella.android.myapplication.utils.FragmentSwitchListener;

public class MainActivity extends AppCompatActivity implements FragmentSwitchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onFragmentSwitch(new TagItemsListFragment());

    }

    @Override
    public void onFragmentSwitch(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        ((ViewGroup) findViewById(R.id.fragment_container)).removeAllViews();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
