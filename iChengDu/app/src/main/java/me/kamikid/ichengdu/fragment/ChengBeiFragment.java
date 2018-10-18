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

public class ChengBeiFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cheng_bei,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ArrayList<Viewpoint> viewpointsAtChengBei = new ArrayList<>();
        ListView listView = (ListView) getView().findViewById(R.id.list_cheng_bei);

        viewpointsAtChengBei.add(new Viewpoint(getString(R.string.wen_shu_yuan),getString(R.string.wen_shu_yuan_add),getString(R.string.wen_shu_yuan_brief),R.drawable.wen_shu_yuan));
        viewpointsAtChengBei.add(new Viewpoint(getString(R.string.du_jiang_yan),getString(R.string.du_jiang_yan_add),getString(R.string.du_jiang_yan_brief),R.drawable.du_jiang_yan));
        viewpointsAtChengBei.add(new Viewpoint(getString(R.string.dong_wu_yuan),getString(R.string.dong_wu_yuan_add),getString(R.string.dong_wu_yuan_brief),R.drawable.dong_wu_yuan));
        viewpointsAtChengBei.add(new Viewpoint(getString(R.string.huan_le_gu),getString(R.string.huan_le_gu_add),getString(R.string.huan_le_gu_brief),R.drawable.huan_le_gu));

        ViewpointsAdapter viewpointsAdapter = new ViewpointsAdapter(getActivity(),viewpointsAtChengBei);

        listView.setAdapter(viewpointsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ShowDetails.class);
                intent.putExtra("Viewpoint", viewpointsAtChengBei.get(position));
                startActivity(intent);
            }
        });
    }
}
