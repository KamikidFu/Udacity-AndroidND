package me.kamikid.ichengdu.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

import me.kamikid.ichengdu.R;
import me.kamikid.ichengdu.entity.Viewpoint;

/**
 * Created by KAMIKID-Shinelon on 2018/2/4.
 */

public class ViewpointsAdapter extends ArrayAdapter {

    public ViewpointsAdapter(Activity context, ArrayList<Viewpoint> viewpoints) {
        super(context, 0, viewpoints);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;

        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Viewpoint viewpoint = (Viewpoint) getItem(position);

        TextView title = (TextView) listView.findViewById(R.id.title);
        TextView address = (TextView) listView.findViewById(R.id.address);
        ImageView imageView = (ImageView) listView.findViewById(R.id.image);

        if (viewpoint != null) {
            title.setText(viewpoint.getName());
            address.setText(viewpoint.getAddress());
            imageView.setImageResource(viewpoint.getImgId());
        }

        return listView;
    }
}
