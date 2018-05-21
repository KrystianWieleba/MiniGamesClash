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
    private String nick1;
    private String nick2;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Players");
    DatabaseReference myRef2 = database.getReference("BubbleVS");
    DatabaseReference myRef3 = database.getReference("Snail");
    DatabaseReference myRef4 = database.getReference("Morpion");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);

        playernick = (EditText) findViewById(R.id.playernick);
        button = (Button) findViewById(R.id.button);

        // S'assurer que firebase est vide
        myRef.removeValue();
        myRef2.removeValue();
        myRef3.removeValue();
        myRef4.removeValue();


        intent = new Intent(getApplicationContext(), SnailVSActivity.class);

    }

    public void name1(View view) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                waitt();
                button.setVisibility(INVISIBLE);
                //Lit le nom à partir de l'edittext
                nick = playernick.getText().toString();

                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                int i = 0;
                                for (DataSnapshot childs : dataSnapshot.getChildren()) {
                                    i++;
                                }
                                //Astuce pour crée les childs des deux joueurs dans l'ordre
                                if (i == 0 ) {
                                    myRef.child(nick).setValue(0);
                                    myRef.child("nick1").setValue(nick);

                                } else if (i ==2 ) {
                                    myRef.child(nick).setValue(0);
                                    myRef.child("nick2").setValue(nick);
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
                // Attends jusque les deux joueurs soient identifiés
                if (i ==4) {
                    // Astuce sinon erreur
                    nick1 = dataSnapshot.child("nick1").getValue(String.class);
                    nick2 = dataSnapshot.child("nick2").getValue(String.class);

                    // Fait suivre le nom des joueurs à la prochaine activité
                    intent.putExtra("nomJ1", nick1);
                    intent.putExtra("nomJAdv", nick2);

                    // Suppriler le listener et childs inutiles pour la suite
                    myRef.removeEventListener(this);
                    myRef.child("nick1").removeValue();
                    myRef.child("nick2").removeValue();

                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });
    }
}
