package me.kamikid.ichengdu;

import android.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import me.kamikid.ichengdu.adapter.FragmentsAdapter;
import me.kamikid.ichengdu.fragment.ChengBeiFragment;
import me.kamikid.ichengdu.fragment.ChengDongFragment;
import me.kamikid.ichengdu.fragment.ChengNanFragment;
import me.kamikid.ichengdu.fragment.ChengXiFragment;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragments = new ArrayList<>();
        titles = new ArrayList<>();

        fragments.add(new ChengBeiFragment());
        fragments.add(new ChengNanFragment());
        fragments.add(new ChengXiFragment());
        fragments.add(new ChengDongFragment());

        titles.add(getString(R.string.cheng_bei));
        titles.add(getString(R.string.cheng_nan));
        titles.add(getString(R.string.cheng_xi));
        titles.add(getString(R.string.cheng_dong));

        FragmentsAdapter fragmentsAdapter = FragmentsAdapter.getInstance(getSupportFragmentManager(),fragments,titles);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        viewPager.setAdapter(fragmentsAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(0);
    }
}
