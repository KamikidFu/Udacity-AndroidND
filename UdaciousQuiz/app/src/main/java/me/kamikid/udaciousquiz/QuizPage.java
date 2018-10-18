package me.kamikid.udaciousquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ConcurrentHashMap;

public class QuizPage extends AppCompatActivity {

    private int mark;
    public TextView textViewSeeMarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView textViewName;
        String name;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);
        name = getIntent().getStringExtra("name");
        String temp_welcome = String.format(getResources().getString(R.string.welcome_quiz), name);
        Toast.makeText(this, temp_welcome, Toast.LENGTH_SHORT).show();
        textViewName = (TextView) findViewById(R.id.textViewShowName);
        textViewName.setText(temp_welcome);
        mark = 0;
        textViewSeeMarks = (TextView) findViewById(R.id.textviewSeeMarks);
        Button buttonSeeMarks = (Button) findViewById(R.id.buttonSeeMarks);
        buttonSeeMarks.setOnClickListener(seeMarksListener);
    }

    /**
     * This method is for quiz one checks
     *
     * @param view
     */
    public void quiz1(View view) {
        RadioButton radioButton_1 = (RadioButton) findViewById(R.id.radio1A1);
        RadioButton radioButton_2 = (RadioButton) findViewById(R.id.radio1B1);
        RadioButton radioButton_3 = (RadioButton) findViewById(R.id.radio1C2);
        RadioButton radioButton_4 = (RadioButton) findViewById(R.id.radio1D3);
        Button button = (Button) findViewById(R.id.button1submit);
        if (radioButton_1.isChecked() && radioButton_2.isChecked() && radioButton_3.isChecked() && radioButton_4.isChecked()) {
            Toast.makeText(this, R.string.success, Toast.LENGTH_LONG).show();
            button.setText(R.string._true);
            button.setClickable(false);
            mark++;
        } else {
            Toast.makeText(this, R.string.failed, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method is for quiz two checks
     *
     * @param view
     */
    public void quiz2(View view) {
        EditText text_1 = (EditText) findViewById(R.id.editText_2_1);
        EditText text_2 = (EditText) findViewById(R.id.editText_2_2);
        EditText text_3 = (EditText) findViewById(R.id.editText_2_3);
        Button button = (Button) findViewById(R.id.button2submit);

        if (text_1.getText().toString().equalsIgnoreCase("textview") &&
                text_2.getText().toString().equalsIgnoreCase("android:text,android:textColor,android:background,android:layout_width,android:layout_height") &&
                text_3.getText().toString().equalsIgnoreCase("6")) {
            Toast.makeText(this, R.string.success, Toast.LENGTH_LONG).show();
            button.setText(R.string._true);
            button.setClickable(false);
            mark++;
        } else {
            Toast.makeText(this, R.string.failed, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method is for quiz three checks
     *
     * @param view
     */
    public void quiz3(View view) {
        CheckBox checkBox_1 = (CheckBox) findViewById(R.id.checkbox_3_1);
        CheckBox checkBox_2 = (CheckBox) findViewById(R.id.checkbox_3_2);
        CheckBox checkBox_3 = (CheckBox) findViewById(R.id.checkbox_3_3);
        Button button = (Button) findViewById(R.id.button3submit);

        if (checkBox_1.isChecked() && checkBox_2.isChecked() && !checkBox_3.isChecked()) {
            Toast.makeText(this, R.string.success, Toast.LENGTH_LONG).show();
            button.setText(R.string._true);
            button.setClickable(false);
            mark++;
        } else {
            Toast.makeText(this, R.string.failed, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method is for quiz four checks
     *
     * @param view
     */
    public void quiz4(View view) {
        EditText editText = (EditText) findViewById(R.id.editText_4);
        Button button = (Button) findViewById(R.id.button4submit);

        if (editText.getText().toString().equalsIgnoreCase("4760")) {
            Toast.makeText(this, R.string.success, Toast.LENGTH_LONG).show();
            button.setText(R.string._true);
            button.setClickable(false);
            mark++;
        } else {
            Toast.makeText(this, R.string.failed, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This is the click listener for buttonSeeMarks
     */
    private View.OnClickListener seeMarksListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String temp = String.format(getResources().getString(R.string.cur_marks), mark);
            textViewSeeMarks.setText(temp);
        }
    };
}
