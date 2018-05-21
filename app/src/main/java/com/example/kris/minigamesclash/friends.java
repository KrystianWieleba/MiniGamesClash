package com.example.kris.minigamesclash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class friends extends AppCompatActivity {

    Button boutonRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        boutonRetour = (Button) findViewById(R.id.boutonRetour);

        boutonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), MainActivity.class));


            }
        });

    }
}
