package com.example.kris.minigamesclash;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResultatBubbleVS extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("BubbleVS");
    private DatabaseReference myRef2 = database.getReference("Players");

    private Button player1;
    private Button player2;
    private TextView winner;
    private  long p1;
    private  long p2;
    private  int P1;
    private  int P2;
    private String nom1;
    private String nom2;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat_bubble_vs);

        TextView finalScore = (TextView) findViewById(R.id.finalScore);
        winner = (TextView) findViewById(R.id.winner);
        player1 = (Button) findViewById(R.id.player1);
        player2 = (Button) findViewById(R.id.player2);

        score = getIntent().getIntExtra("SCORE", 0);
        finalScore.setText(score + "");

        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Récupère le score actuel de chaque joueur pour l'incrémenter plus tard
                p1 = dataSnapshot.child("Score player 1").getValue(long.class);
                p2 = dataSnapshot.child("Score player 2").getValue(long.class);
                P1 = (int) p1;
                P2 = (int) p2;

                nom1 = dataSnapshot.child("Player 1").getValue(String.class);
                nom2 = dataSnapshot.child("Player 2").getValue(String.class);

                //Met le nom du joueur comme bouton
                player1.setText(nom1);
                player2.setText(nom2);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    //2crit le score du joueur puis appel à la méthode win
    public void name1(View view) {
        player1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child("Player 1").setValue(score);
                player1.setVisibility(View.INVISIBLE);
                player2.setVisibility(View.INVISIBLE);
                win();
            }
        });
    }

    public void name2(View view) {
        player2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child("Player 2").setValue(score);
                player1.setVisibility(View.INVISIBLE);
                player2.setVisibility(View.INVISIBLE);
                win();
            }
        });
    }

    public void win() {
        //Permet de revérifier tous les tick juqu'a que les deux joueurs aient écrit leur scores'

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int i = 0;
                        for (DataSnapshot childs : dataSnapshot.getChildren()) {
                            i++;
                        }
                        //Vérifie que les deux joueurs aient bien écrit leurs scores
                        if (i == 2) {
                            nextActivity();
                            // le premier joueur a rentrer son score ne rentre pas dans le if...
                            Long player1 = dataSnapshot.child("Player 1").getValue(Long.class);
                            Long player2 = dataSnapshot.child("Player 2").getValue(Long.class);
                            //Check qui remporte le jeu
                            if (player1 > player2) {
                                winner.setText(nom1 + " : " + player1 + "\n" + nom2 + " : " + player2 + "\n\n" + player1 + " won !");
                                winner.setVisibility(View.VISIBLE);
                                //incrémente le score final
                                P1 += 1;
                                myRef2.child("Score player 1").setValue(P1);

                            } else if (player1 < player2) {
                                winner.setText(nom1 + " : " + player1 + "\n" + nom2 + " : " + player2 + "\n\n" + player2 + " won !");
                                winner.setVisibility(View.VISIBLE);
                                P2 += 1;
                                myRef2.child("Score player 2").setValue(P2);

                            } else {
                                winner.setText(nom1 + " : " + player1 + "\n" + nom2 + " : " + player2 + "\n\n" + "DRAW !");
                                winner.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w("Failed to read value.", error.toException());
                    }
                });
    }

    //supprime les chils du jeu bublleVS et au bout de 5sec passe à l'interface
    public void nextActivity() {
        //myRef.child("Player 1").removeValue();
        //myRef.child("Player 2").removeValue();

        new CountDownTimer(8000, 10) {
            public void onTick(long tick){}
            // Lance l'activité result à la fin du compte à rebours
            public void onFinish() {
                startActivity(new Intent(getApplicationContext(), InterfaceActivity.class));
            }
        }.start();
    }
}
