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

    Button button;
    EditText playernick;
    String nick;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Players");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);

        playernick = (EditText) findViewById(R.id.playernick);
        button = (Button) findViewById(R.id.button);


        new CountDownTimer(600000, 2000) {
            public void onTick(long tick){

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        int i = 0;

                        for (DataSnapshot childs : dataSnapshot.getChildren()) {
                            i++;
                        }

                        if (i ==4) {

                            cancel();
                            startActivity(new Intent(getApplicationContext(), BubbleVS.class));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w("Failed to read value.", error.toException());
                    }
                });

            }
            public void onFinish() {
                //Temps d'attentes dépassé
            }
        }.start();

    }

    public void name1(View view) {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                button.setVisibility(INVISIBLE);

                nick = playernick.getText().toString();

                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                int i = 0;

                                for (DataSnapshot childs : dataSnapshot.getChildren()) {
                                    i++;
                                }
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
}
