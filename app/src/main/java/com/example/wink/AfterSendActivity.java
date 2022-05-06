package com.example.wink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AfterSendActivity extends AppCompatActivity {

    Button xButton;
    Button anotherOneButton;
    Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_send);

        //cosmetics -- heading the header
        getSupportActionBar().hide();

        xButton = (Button) findViewById(R.id.x_button);
        anotherOneButton = (Button)  findViewById(R.id.another_one_button);
        doneButton  = (Button)  findViewById(R.id.done_button);

        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterSendActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterSendActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        anotherOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterSendActivity.this, PhotoActivity.class);
                startActivity(intent);
            }
        });



    }
}