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

public class ChengNanFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cheng_nan,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ArrayList<Viewpoint> viewpointsAtChengNan = new ArrayList<>();
        ListView listView = (ListView) getView().findViewById(R.id.list_cheng_nan);

        viewpointsAtChengNan.add(new Viewpoint(getString(R.string.san_sheng_xiang),getString(R.string.san_sheng_xiang_add),getString(R.string.san_sheng_xiang_brief),R.drawable.san_sheng_xiang));
        viewpointsAtChengNan.add(new Viewpoint(getString(R.string.wu_hou_ci),getString(R.string.wu_hou_ci_add),getString(R.string.wu_hou_ci_brief),R.drawable.wu_hou_ci));
        viewpointsAtChengNan.add(new Viewpoint(getString(R.string.chun_xi_road),getString(R.string.chun_xi_road_add),getString(R.string.chun_xi_road_brief),R.drawable.chunxi_road));
        viewpointsAtChengNan.add(new Viewpoint(getString(R.string.du_fu_cao_tang),getString(R.string.du_fu_cao_tang_add),getString(R.string.du_fu_cao_tang_brief),R.drawable.du_fu_cao_tang));

        ViewpointsAdapter viewpointsAdapter = new ViewpointsAdapter(getActivity(),viewpointsAtChengNan);

        listView.setAdapter(viewpointsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ShowDetails.class);
                intent.putExtra("Viewpoint", viewpointsAtChengNan.get(position));
                startActivity(intent);
            }
        });
    }
}
