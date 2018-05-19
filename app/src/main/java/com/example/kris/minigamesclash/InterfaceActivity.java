package com.example.kris.minigamesclash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.view.View.GONE;

public class InterfaceActivity extends AppCompatActivity {


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Players");

    private TextView playersScores;
    private long First;
    private long Second;
    private int first;
    private int second;
    private String name1;
    private String name2;
    private Button suite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface);

        suite = (Button) findViewById(R.id.suite);
        playersScores = (TextView) findViewById(R.id.playersScores);


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Lire à partir de firebase les noms et score des joueurs respectifs pour les afficher
                for (DataSnapshot childs : dataSnapshot.getChildren()) {

                    name1 = dataSnapshot.child("Player 1").getValue(String.class);
                    name2 = dataSnapshot.child("Player 2").getValue(String.class);


                    First = dataSnapshot.child("Score player 1").getValue(long.class);
                    first = (int) First;
                    Second = dataSnapshot.child("Score player 2").getValue(long.class);
                    second = (int) Second;

                }
                playersScores.setText(name1 + " : " + first + "\n\n" + name2 + " : " + second);

                if (first==2 || second==2) {
                    suite.setVisibility(GONE);
                    //permettre aux jouerus une revanche ?
                }



            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });


        suite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), ArcheVSActivity.class));

                //startActivity(new Intent(getApplicationContext(), SnailVSActivity.class));



            }
        });
    }
}
