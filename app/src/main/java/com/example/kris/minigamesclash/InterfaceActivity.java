package com.example.kris.minigamesclash;

import android.content.Intent;
import android.os.CountDownTimer;
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

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Players");

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
                // Récupère les scores des joueurs
                scoreJ1 = dataSnapshot.child(nomJ1).getValue(int.class);
                scoreJAdv = dataSnapshot.child(nomJAdv).getValue(int.class);

                avanceeDuel=(int)(scoreJ1+scoreJAdv);

                // Appel à la méthode de condition de fin de partie
                checkEnd();

                // Ecrit les scores deux deux joueurs
                playersScores.setText(nomJ1 + " : " + scoreJ1 + "\n\n" + "VS" + "\n\n" + nomJAdv + " : " + scoreJAdv);
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
                // Pour revenir au menu à la fin de la partie
                if (avanceeDuel==4){
                    avanceeDuel=-1;
                }
                Intent intent;
                // En fonction de l'avancée du duel le bouton lancera une activité différente
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
                //  Fait passer les extras à l'activité suivante
                intent.putExtra("nomJ1",nomJ1);
                intent.putExtra("nomJAdv",nomJAdv);
                intent.putExtra("tour", tour);
                startActivity(intent);



            }
        });
    }

    public void checkEnd() {

        // Condition de fin de la partie + écriture du massage adéquat
        if (avanceeDuel >= 4) {
            playersScores.setVisibility(GONE);
            if (scoreJ1 > scoreJAdv) {
                winner.setText(nomJ1 + " gagne la partie " + scoreJ1 + " à " + scoreJAdv);
        } else if (scoreJ1 < scoreJAdv) {
                winner.setText(nomJAdv + " gagne la partie " + scoreJAdv + " à " + scoreJ1);
        } else winner.setText(" EGALITE  " + scoreJ1 + " - " + scoreJAdv);
        }

    }
}
