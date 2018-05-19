package com.example.kris.minigamesclash;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.view.View.INVISIBLE;

public class Identification extends AppCompatActivity {

    private Button button;
    private EditText playernick;
    private String nick;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Players");
    DatabaseReference myRef2 = database.getReference("BubbleVS");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);

        playernick = (EditText) findViewById(R.id.playernick);
        button = (Button) findViewById(R.id.button);

        //temporaire, pour être sure que firebase est vide
        //myRef.child("Player 1").removeValue();
        //myRef.child("Player 2").removeValue();
        //myRef.child("Score player 1").removeValue();
        //myRef.child("Score player 2").removeValue();
        //myRef2.child("Player 1").removeValue();
        //myRef2.child("Player 2").removeValue();


    }

    public void name1(View view) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                waitt();
                button.setVisibility(INVISIBLE);
                //Lit le nom à partir de l'edittext
                nick = playernick.getText().toString();

                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                int i = 0;

                                for (DataSnapshot childs : dataSnapshot.getChildren()) {
                                    i++;
                                }
                                //Astuce pour crée les childs des deux joueurs dans l'ordre
                                if (i == 0 ) {
                                    myRef.child("Player 1").setValue(nick);
                                    myRef.child("Score player 1").setValue(0);
                                } else if (i ==2 ) {
                                    myRef.child("Player 2").setValue(nick);
                                    myRef.child("Score player 2").setValue(0);
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError error) {
                                Log.w("Failed to read value.", error.toException());
                            }
                        });
            }
        });
    }

    public void waitt() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;

                for (DataSnapshot childs : dataSnapshot.getChildren()) {
                    i++;
                }

                if (i ==4) {
                    startActivity(new Intent(getApplicationContext(), BubbleVS.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });
    }
}
