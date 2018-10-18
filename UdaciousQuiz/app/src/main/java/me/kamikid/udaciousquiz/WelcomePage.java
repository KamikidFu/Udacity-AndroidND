package me.kamikid.udaciousquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class WelcomePage extends AppCompatActivity {

    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        name = (EditText) findViewById(R.id.editTextName);
    }

    public void quizEnter(View view) {
        String tempName = name.getText().toString();
        if (tempName != null && tempName.length() > 0) {
            Intent intent = new Intent(this, QuizPage.class);
            intent.putExtra("name", tempName);
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.empty_name, Toast.LENGTH_SHORT).show();
        }
    }

}
