package com.example.kris.minigamesclash;

import android.content.Intent;
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

public class resultGame1 extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Bubble Destroyer");
    private Button button1;
    private Button leaderboard;
    private EditText playernick;
    private int score;
    private String nick;
    private int[] tab = new int[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_game1);

        // Instancier
        TextView finalScore = (TextView) findViewById(R.id.finalScore);
        playernick = (EditText) findViewById(R.id.playernick);
        button1 = (Button) findViewById(R.id.button1);
        leaderboard = (Button) findViewById(R.id.leaderboard);

        leaderboard.setVisibility(View.INVISIBLE);

        // R&cupère et écrit le score obtenu
        score = getIntent().getIntExtra("SCORE", 0);
        finalScore.setText(score + "");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childs : dataSnapshot.getChildren()) {

                    //lit les 5 scores du leaderboard à partir de firebase
                    tab[4] = Integer.parseInt(childs.child("1st").getValue().toString());
                    tab[3] = Integer.parseInt(childs.child("2nd").getValue().toString());
                    tab[2] = Integer.parseInt(childs.child("3rd").getValue().toString());
                    tab[1] = Integer.parseInt(childs.child("4th").getValue().toString());
                    tab[0] = Integer.parseInt(childs.child("5th").getValue().toString());

                    // Si le score est dans le top 5, remplace le dernier score le supprimant
                    if (score > tab[0]) {
                        tab[0] = score;
                        button1.setVisibility(View.VISIBLE);
                    } else {
                        playernick.setVisibility(View.GONE);
                        leaderboard.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });
    }

    public void name1(View view) {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nick = playernick.getText().toString();

                playernick.setVisibility(View.GONE);
                button1.setVisibility(View.GONE);
                leaderboard.setVisibility(View.VISIBLE);
                writeName();
            }
        });
    }

    public void writeName() {
        // en fonction du score obtenu, le compare aux scores du classement, et le remplace dnas la place correspondante dans firebase
        if (score > tab[4]) {
            myRef.child("Leaderboard").child("Name 1st").setValue(nick);

        } else if (score > tab[3] && score < tab[4]) {
            myRef.child("Leaderboard").child("Name 2nd").setValue(nick);

        } else if (score > tab[2] && score < tab[3]) {
            myRef.child("Leaderboard").child("Name 3rd").setValue(nick);

        } else if (score > tab[1] && score < tab[2]) {
            myRef.child("Leaderboard").child("Name 4th").setValue(nick);

        } else if (score > tab[0] && score < tab[1]) {
            myRef.child("Leaderboard").child("Name 5th").setValue(nick);
        }

        //tri à bulle
        for (int i = 4; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (tab[j] > tab[j + 1]) {
                    int temp = tab[j];
                    tab[j] = tab[j + 1];
                    tab[j + 1] = temp;
                }
            }
        }

        //Remplace les scores avec celles  récemmennt triées
        myRef.child("Leaderboard").child("5th").setValue(tab[0]);
        myRef.child("Leaderboard").child("4th").setValue(tab[1]);
        myRef.child("Leaderboard").child("3rd").setValue(tab[2]);
        myRef.child("Leaderboard").child("2nd").setValue(tab[3]);
        myRef.child("Leaderboard").child("1st").setValue(tab[4]);
    }

    public void tryAgain(View view) {
        // Relance l'activité du jeu à l'appuie du bouton tryAgain
        startActivity(new Intent(getApplicationContext(), Game1.class));
    }

    public void scoreTable(View view) {
        Intent intent2 = new Intent(getApplicationContext(), ScoreTable.class);
        startActivity(intent2);

    }

    public void backToMenu(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }





}

