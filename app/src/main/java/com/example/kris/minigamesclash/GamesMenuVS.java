package com.example.kris.minigamesclash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GamesMenuVS extends AppCompatActivity {

    Button boutonGame1;
    Button boutonGame2;
    Button boutonGame3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_menu_vs);

        boutonGame1 = (Button) findViewById(R.id.boutonGame1);
        boutonGame2 = (Button) findViewById(R.id.boutonGame2);
        boutonGame3 = (Button) findViewById(R.id.boutonGame3);

        boutonGame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), BubbleVS.class));



                // Effet bouton Training

            }
        });

        boutonGame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //startActivity(new Intent(getApplicationContext(), .class));



                // Effet bouton Training

            }
        });

        boutonGame3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), ArcheVSActivity.class));

                // Effet bouton Training

            }
        });
    }
}
