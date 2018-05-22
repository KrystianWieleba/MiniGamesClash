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

    //pour moi il faut que l'interface prenne en entree les noms des joueurs (via le intent)
    //+quand il sort dun minijeu, le gagnant (quoique on peut mettre le score global a jour directmt a la fin du mini jeu)
    // noms recus a partir de ton activite didentication quon mettra au debut
    // ca permettra notamment d'avoir plusieurs parties en cours simultanement
    //Du coup je me demande si on lancerait pas linterface que apres un minijeu (et on a que l'identif au tout debut)
    //Il faudra aussi penser à virer tous les "dechets" de la database avant de quitter

    //Du coup j'ai fait des modifs qui font que depuis resultbubblevs ça peut ne plus marcher dsl :P

    //pour l'instant par rapport au menu on laisse l'accès aux mini-jeux vs individuellement
    //on rajoute juste un bouton "all".

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Players");

    private TextView playersScores;
    private TextView winner;
    private String nomJ1;
    private String nomJAdv;
    private int scoreJ1;
    private int scoreJAdv;
    private int avanceeDuel; //=somme des scores globaux des joueurs
    private Button suite;
    private int tour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface);

        suite = (Button) findViewById(R.id.suite);
        playersScores = (TextView) findViewById(R.id.playersScores);
        winner = (TextView) findViewById(R.id.winner);

        nomJ1 = getIntent().getStringExtra("nomJ1");
        nomJAdv = getIntent().getStringExtra("nomJAdv");
        tour = getIntent().getIntExtra("tour", 0);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //il faudra changer ds les minijeux : ils doivent inscrire leur score avec leur nom comme ref
                scoreJ1 = dataSnapshot.child(nomJ1).getValue(int.class); //pq mettre le score en long ?? ah c'était car il me semblait que int marchait pas ( à cause de mon listener peut être)
                scoreJAdv = dataSnapshot.child(nomJAdv).getValue(int.class);


                playersScores.setText(nomJ1 + " : " + scoreJ1 + "\n\n" + "VS" + "\n\n" + nomJAdv + " : " + scoreJAdv);

                // Condition de fin de la partie
                if (scoreJ1==2) {
                    playersScores.setVisibility(GONE);
                    winner.setText(nomJ1 + " GAGNE LA PARTIE " + scoreJ1 + " A " + scoreJAdv);
                } else if (scoreJAdv==2) {
                    playersScores.setVisibility(GONE);
                    winner.setText(nomJAdv + " GAGNE LA PARTIE " + scoreJAdv + " A " + scoreJ1);
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
                avanceeDuel=(int)(scoreJ1+scoreJAdv);
                if (scoreJ1==2 || scoreJAdv==2){
                    avanceeDuel=-1;
                }
                Intent intent;
                switch (avanceeDuel) {
                    case 0:
                        intent=new Intent(getApplicationContext(), BubbleVS.class);
                        break;
                    case 1:
                        intent=new Intent(getApplicationContext(), ArcheVSActivity.class);
                        break;
                    case 2:
                        intent=new Intent(getApplicationContext(), MorpionVSActivity.class);
                        break;
                    case 3:
                        intent=new Intent(getApplicationContext(), SnailVSActivity.class);
                        break;
                    default:
                        intent=new Intent(getApplicationContext(), MainActivity.class);
                    //startActivity(new Intent(getApplicationContext(), SnailVSActivity.class));
                }
                intent.putExtra("nomJ1",nomJ1);
                intent.putExtra("nomJAdv",nomJAdv);
                intent.putExtra("tour", tour);
                startActivity(intent);


            }
        });
    }
}
