package com.example.kris.minigamesclash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button boutonTraining;
    Button boutonVs;
    Button boutonFriends;
    Button boutonCredits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        boutonTraining = (Button) findViewById(R.id.boutonTraining);
        boutonVs = (Button) findViewById(R.id.boutonVs);
        boutonFriends = (Button) findViewById(R.id.boutonFriends);
        boutonCredits = (Button) findViewById(R.id.boutonCredits);

        boutonTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), GamesMenu.class));



                // Effet bouton Training

            }
        });

        boutonVs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //startActivity(new Intent(getApplicationContext(), AuthActivity.class));



                // Effet bouton Vs

            }
        });

        boutonFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), friends.class));

                // Effet bouton Friends

            }
        });

        boutonCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), credits.class));

                // Effet bouton Credits

            }
        });


    }

}
