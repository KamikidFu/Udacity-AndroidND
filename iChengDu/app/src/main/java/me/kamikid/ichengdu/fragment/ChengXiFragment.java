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

public class ChengXiFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cheng_xi,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ArrayList<Viewpoint> viewpointsAtChengXi = new ArrayList<>();
        ListView listView = (ListView) getView().findViewById(R.id.list_cheng_xi);

        viewpointsAtChengXi.add(new Viewpoint(getString(R.string.huan_hua_xi),getString(R.string.huan_hua_xi_add),getString(R.string.huan_hua_xi_brief),R.drawable.huan_hua_xi));
        viewpointsAtChengXi.add(new Viewpoint(getString(R.string.jin_sha),getString(R.string.jin_sha_add),getString(R.string.jin_sha_brief),R.drawable.jin_sha));
        viewpointsAtChengXi.add(new Viewpoint(getString(R.string.kuan_zai_xiang_zi),getString(R.string.kuan_zai_xiang_zi_add),getString(R.string.kuan_zai_xiang_zi_brief),R.drawable.kuan_zai_xiang_zi));
        viewpointsAtChengXi.add(new Viewpoint(getString(R.string.qing_yang_gong),getString(R.string.qing_yang_gong_add),getString(R.string.qing_yang_gong_brief),R.drawable.qing_yang_gong));

        ViewpointsAdapter viewpointsAdapter = new ViewpointsAdapter(getActivity(),viewpointsAtChengXi);

        listView.setAdapter(viewpointsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ShowDetails.class);
                intent.putExtra("Viewpoint", viewpointsAtChengXi.get(position));
                startActivity(intent);
            }
        });
    }
}
