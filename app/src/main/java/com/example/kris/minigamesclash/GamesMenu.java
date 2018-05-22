package com.example.kris.minigamesclash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GamesMenu extends AppCompatActivity {

    Button boutonGame1;
    Button boutonGame2;
    Button boutonGame3;
    Button boutonGame4;
    Button boutonGame5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_menu);

        boutonGame1 = (Button) findViewById(R.id.boutonGame1);
        boutonGame2 = (Button) findViewById(R.id.boutonGame2);
        boutonGame3 = (Button) findViewById(R.id.boutonGame3);
        boutonGame4 = (Button) findViewById(R.id.boutonGame4);
        boutonGame5 = (Button) findViewById(R.id.boutonGame5);

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

        boutonGame3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), ArcheDeNoe.class));



                // Effet bouton Training

            }
        });

        boutonGame4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), jeuxdescarres.class));



                // Effet bouton Training

            }
        });

        boutonGame4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), MorpionActivity.class));



                // Effet bouton Training

            }
        });
    }
}
