package me.kamikid.ichengdu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import me.kamikid.ichengdu.entity.Viewpoint;

public class ShowDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);


        Intent intent = getIntent();

        Viewpoint viewpoint = (Viewpoint) intent.getSerializableExtra("Viewpoint");

        TextView titleTextView = (TextView) findViewById(R.id.detailTitle);
        TextView addTextView = (TextView) findViewById(R.id.detailAdd);
        TextView briefTextView = (TextView) findViewById(R.id.detailBrief);

        titleTextView.setText(viewpoint.getName());
        addTextView.setText(viewpoint.getAddress());
        briefTextView.setText(viewpoint.getBrief());
    }
}
