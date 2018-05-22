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

    private TextView winner;
    private int score;
    private String nomJ1;
    private String nomJAdv;
    private int scoreJ1;
    private int tour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat_bubble_vs);

        winner = (TextView) findViewById(R.id.winner);

        //Récupération et écriture du score
        score = getIntent().getIntExtra("SCORE", 0);

        // Récupérer le nom des joueurs
        nomJ1 = getIntent().getStringExtra("nomJ1");
        nomJAdv = getIntent().getStringExtra("nomJAdv");
        tour = getIntent().getIntExtra("tour", 0);

        myRef.child(nomJ1).setValue(score);

        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Récupère le score final de chaque joueur pour pouvoir l'incrémenter plus tard
                    scoreJ1 = dataSnapshot.child(nomJ1).getValue(int.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot childs : dataSnapshot.getChildren()) {
                    i++;
                }
                //Vérifie que les deux joueurs aient bien écrit leurs scores
                if (i == 2) {

                    // Récupère les scores des deux joueurs
                    int scoreAdv = dataSnapshot.child(nomJAdv).getValue(int.class);
                    // Vérifie qui remporte le jeu avec affichage d'un message correspondant
                    if (score > scoreAdv) {
                        winner.setText(nomJ1 + " : " + score + "\n" + nomJAdv + " : " + scoreAdv + "\n\n" + nomJ1 + " won !");
                        winner.setVisibility(View.VISIBLE);
                        //incrémente le score final
                        scoreJ1 += 1;
                        myRef2.child(nomJ1).setValue(scoreJ1);
                    } else if (score < scoreAdv) {
                        winner.setText(nomJ1 + " : " + score + "\n" + nomJAdv + " : " + scoreAdv + "\n\n" + nomJAdv + " won !");
                        winner.setVisibility(View.VISIBLE);

                    } else {
                        winner.setText(nomJ1 + " : " + score + "\n" + nomJAdv + " : " + scoreAdv + "\n\n" + "DRAW !");
                        winner.setVisibility(View.VISIBLE);
                    }

                    //myRef.removeEventListener(this);
                    nextActivity();


                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });

    }

    private void nextActivity() {
        //Au bout de 8sec lance l'interfce
        new CountDownTimer(8000, 10) {
            public void onTick(long tick){}
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), InterfaceActivity.class);
                // Fait suivre le nom des joueurs
                intent.putExtra("nomJ1", nomJ1);
                intent.putExtra("nomJAdv", nomJAdv);
                intent.putExtra("tour", tour);
                startActivity(intent);
            }
        }.start();
    }
}
