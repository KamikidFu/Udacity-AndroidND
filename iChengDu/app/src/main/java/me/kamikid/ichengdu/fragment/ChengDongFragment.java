package me.kamikid.ichengdu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import me.kamikid.ichengdu.R;
import me.kamikid.ichengdu.ShowDetails;
import me.kamikid.ichengdu.adapter.ViewpointsAdapter;
import me.kamikid.ichengdu.entity.Viewpoint;

/**
 * Created by KAMIKID-Shinelon on 2018/2/4.
 */

public class ChengDongFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cheng_dong,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ArrayList<Viewpoint> viewpointsAtChengDong = new ArrayList<>();
        ListView listView = (ListView) getView().findViewById(R.id.list_cheng_dong);

        viewpointsAtChengDong.add(new Viewpoint(getString(R.string.dong_jiao_ji_yi),getString(R.string.dong_jiao_ji_yi_add),getString(R.string.dong_jiao_ji_yi_brief),R.drawable.dong_jiao_ji_yi));
        viewpointsAtChengDong.add(new Viewpoint(getString(R.string.da_ci_si),getString(R.string.da_ci_si_add),getString(R.string.da_ci_si_brief),R.drawable.da_ci_si));
        viewpointsAtChengDong.add(new Viewpoint(getString(R.string.ta_zi_shan),getString(R.string.ta_zi_shan_add),getString(R.string.ta_zi_shan_brief),R.drawable.ta_zi_shan));
        viewpointsAtChengDong.add(new Viewpoint(getString(R.string.xin_hua_gong_yuan),getString(R.string.xin_hua_gong_yuan_add),getString(R.string.xin_hua_gong_yuan_brief),R.drawable.xin_hua_gong_yuan));


        ViewpointsAdapter viewpointsAdapter = new ViewpointsAdapter(getActivity(),viewpointsAtChengDong);

        listView.setAdapter(viewpointsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ShowDetails.class);
                intent.putExtra("Viewpoint", viewpointsAtChengDong.get(position));
                startActivity(intent);
            }
        });
    }
}
