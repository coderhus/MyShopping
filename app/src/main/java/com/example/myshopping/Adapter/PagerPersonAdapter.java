package com.example.myshopping.Adapter;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myshopping.Activity.DetailsActivity;
import com.example.myshopping.Other.FragmentPerson1;
import com.example.myshopping.Other.FragmentPerson2;

public class PagerPersonAdapter extends FragmentPagerAdapter {

    private int numofTabs;



    public  PagerPersonAdapter(FragmentManager fm,int numofTabs){
        super(fm);
        this.numofTabs=numofTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentPerson1();
            case 1:
                return new FragmentPerson2();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return numofTabs;
    }



}
