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

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("BubbleVS");
    DatabaseReference myRef2 = database.getReference("Players");

    private Button player1;
    private Button player2;
    private TextView winner;
    private int score;
    private String nomJ1;
    private String nomJAdv;
    private int scoreJ1;
    private int scoreJAdv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat_bubble_vs);

        TextView finalScore = (TextView) findViewById(R.id.finalScore);
        winner = (TextView) findViewById(R.id.winner);
        player1 = (Button) findViewById(R.id.player1);
        player2 = (Button) findViewById(R.id.player2);

        //Récupération et écriture du score
        score = getIntent().getIntExtra("SCORE", 0);
        finalScore.setText(score + "");

        // Récupérer le nom des joueurs
        nomJ1 = getIntent().getStringExtra("nomJ1");
        nomJAdv = getIntent().getStringExtra("nomJAdv");

        // Ecris le nom des joueurs dans les boutons
        player1.setText(nomJ1);
        player2.setText(nomJAdv);

        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Récupère le score final de chaque joueur pour pouvoir l'incrémenter plus tard
                    scoreJAdv = dataSnapshot.child(nomJAdv).getValue(int.class);
                    scoreJ1 = dataSnapshot.child(nomJ1).getValue(int.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    // Ecrit le score du joueur dand firebase puis appel à la méthode win
    public void name1(View view) {
        player1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child(nomJ1).setValue(score);
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
                myRef.child(nomJAdv).setValue(score);
                player1.setVisibility(View.INVISIBLE);
                player2.setVisibility(View.INVISIBLE);
                win();
            }
        });
    }

    public void win() {
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
                            // Récupère les scores des deux joueurs
                            int player1 = dataSnapshot.child(nomJ1).getValue(int.class);
                            int player2 = dataSnapshot.child(nomJAdv).getValue(int.class);
                            // Vérifie qui remporte le jeu avec affichage d'un message correspondant
                            if (player1 > player2) {
                                winner.setText(nomJ1 + " : " + player1 + "\n" + nomJAdv + " : " + player2 + "\n\n" + nomJ1 + " won !");
                                winner.setVisibility(View.VISIBLE);
                                //incrémente le score final
                                scoreJ1 += 1;
                                myRef2.child(nomJ1).setValue(scoreJ1);

                            } else if (player1 < player2) {
                                winner.setText(nomJ1 + " : " + player1 + "\n" + nomJAdv + " : " + player2 + "\n\n" + nomJAdv + " won !");
                                winner.setVisibility(View.VISIBLE);
                                scoreJAdv += 1;
                                myRef2.child(nomJAdv).setValue(scoreJAdv);

                            } else {
                                winner.setText(nomJ1 + " : " + player1 + "\n" + nomJAdv + " : " + player2 + "\n\n" + "DRAW !");
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


    public void nextActivity() {
        //Au bout de 8sec lance l'interfce
        new CountDownTimer(8000, 10) {
            public void onTick(long tick){}
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), InterfaceActivity.class);
                // Fait suivre le nom des joueurs
                intent.putExtra("nomJ1", nomJ1);
                intent.putExtra("nomJAdv", nomJAdv);
                startActivity(intent);
            }
        }.start();
    }
}
