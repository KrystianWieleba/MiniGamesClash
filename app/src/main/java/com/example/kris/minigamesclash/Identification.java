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
    private String currentNick;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Players");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);

        playernick = (EditText) findViewById(R.id.playernick);
        button = (Button) findViewById(R.id.button);

        // intent de la première activité à lancer
        intent = new Intent(getApplicationContext(), SnailVSActivity.class);

    }

    public void name1(View view) {
                // apple à waitt qui attend que les deux joueurs soient identifiés
                waitt();

                button.setVisibility(INVISIBLE);

                //Lit le nom à partir de l'edittext
                nick = playernick.getText().toString();
                // servira à ce que chaque joueur soit le joueur1 de son point de vue
                currentNick = nick;

                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                int i = 0;
                                for (DataSnapshot childs : dataSnapshot.getChildren()) {
                                    i++;
                                }
                                // créer les childs des deux joueurs dans l'ordre
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
                    // Afin que le joueur actuel soit vraiment considéré comme joueur1 et son adversaire comme adversaire et pas joueur 1 et joueur 2
                    if (currentNick==nick1) {
                        // Fait suivre le nom des joueurs à la prochaine activité
                        intent.putExtra("nomJ1", nick1);
                        intent.putExtra("nomJAdv", nick2);
                        // sert dans certains jeux pour qu'un joueur attente le tour de l'autre initialement
                        intent.putExtra("tour", 0);
                    } else if (currentNick==nick2) {
                        intent.putExtra("nomJ1", nick2);
                        intent.putExtra("nomJAdv", nick1);
                        intent.putExtra("tour", 1);
                    }
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
