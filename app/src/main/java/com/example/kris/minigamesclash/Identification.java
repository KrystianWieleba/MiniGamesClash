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
    private Intent intent;


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Players");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);

        playernick = (EditText) findViewById(R.id.playernick);
        button = (Button) findViewById(R.id.button);

        //temporaire, pour être sure que firebase est vide
        //myRef.child("Player 1").removeValue();


        intent = new Intent(getApplicationContext(), BubbleVS.class);



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
                                    myRef.child(nick).setValue(0);
                                    intent.putExtra("nomJ1", nick);

                                } else if (i ==1 ) {
                                    myRef.child(nick).setValue(0);
                                    intent.putExtra("nomJAdv", nick);
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

                if (i ==2) {

                    startActivity(intent);
                    myRef.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });
    }
}
