package me.kamikid.ichengdu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import me.kamikid.ichengdu.fragment.ChengBeiFragment;
import me.kamikid.ichengdu.fragment.ChengDongFragment;
import me.kamikid.ichengdu.fragment.ChengNanFragment;
import me.kamikid.ichengdu.fragment.ChengXiFragment;

/**
 * Created by KAMIKID-Shinelon on 2018/2/4.
 */

public class FragmentsAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;


    private FragmentsAdapter(FragmentManager fm) {
        super(fm);
    }

    private FragmentsAdapter(FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> titles) {
        super(fm);
            this.fragments = fragments;
            this.titles = titles;
    }

    public static FragmentsAdapter getInstance(FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> titles) {
        if(fragments.size()==titles.size()){
            return new FragmentsAdapter(fm,fragments,titles);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return fragments.get(0);
        }else if(position == 1){
            return fragments.get(1);
        }else if(position == 2){
            return fragments.get(2);
        }else{
            return fragments.get(3);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return titles.get(0);
        }else if(position == 1){
            return titles.get(1);
        }else if(position == 2){
            return titles.get(2);
        }else{
            return titles.get(3);
        }
    }
}
