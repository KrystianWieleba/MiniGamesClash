package com.example.kris.minigamesclash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GamesMenu extends AppCompatActivity {

    Button boutonGame1;
    Button boutonGame2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_menu);

        boutonGame1 = (Button) findViewById(R.id.boutonGame1);
        boutonGame2 = (Button) findViewById(R.id.boutonGame2);

        boutonGame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), Game1.class));



                // Effet bouton Training

            }
        });

        boutonGame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), Game2.class));



                // Effet bouton Training

            }
        });
    }
}
