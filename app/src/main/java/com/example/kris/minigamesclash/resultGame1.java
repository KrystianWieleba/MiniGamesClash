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

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Bubble Destroyer");

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button leaderboard;
    EditText playernick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_game1);

        // Instacnier finalScore
        TextView finalScore = (TextView) findViewById(R.id.finalScore);
        playernick = (EditText) findViewById(R.id.playernick);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        leaderboard = (Button) findViewById(R.id.leaderboard);

        leaderboard.setVisibility(View.INVISIBLE);

        int score = getIntent().getIntExtra("SCORE", 0);
        finalScore.setText(score + "");


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childs : dataSnapshot.getChildren()) {

                    int First = Integer.parseInt(childs.child("1st").getValue().toString());
                    int Second = Integer.parseInt(childs.child("2nd").getValue().toString());
                    int Third = Integer.parseInt(childs.child("3rd").getValue().toString());
                    int Fourth = Integer.parseInt(childs.child("4th").getValue().toString());
                    int Fifth = Integer.parseInt(childs.child("5th").getValue().toString());


                    int[] tab = {Fifth, Fourth, Third, Second, First};

                    int score = getIntent().getIntExtra("SCORE", 0);

                    for (int i = 4; i > 0; i--) {
                        for (int j = 0; j < i; j++) {
                            if (tab[j] > tab[j + 1]) {
                                int temp = tab[j];
                                tab[j] = tab[j + 1];
                                tab[j + 1] = temp;
                            }
                        }
                    }

                    if (score > tab[4]) {
                        button1.setVisibility(View.VISIBLE);
                        tab[0] = score;

                    } else if (score > tab[3] && score < tab[4]) {
                        button2.setVisibility(View.VISIBLE);
                        tab[0] = score;

                    } else if (score > tab[2] && score < tab[3]) {
                        button3.setVisibility(View.VISIBLE);
                        tab[0] = score;

                    } else if (score > tab[1] && score < tab[2]) {
                        button4.setVisibility(View.VISIBLE);
                        tab[0] = score;

                    } else if (score > tab[0] && score < tab[1]) {
                        button5.setVisibility(View.VISIBLE);

                        tab[0] = score;
                    } else {
                        playernick.setVisibility(View.GONE);
                        leaderboard.setVisibility(View.VISIBLE);

                    }


                    for (int i = 4; i > 0; i--) {
                        for (int j = 0; j < i; j++) {
                            if (tab[j] > tab[j + 1]) {
                                int temp = tab[j];
                                tab[j] = tab[j + 1];
                                tab[j + 1] = temp;
                            }
                        }
                    }

                    myRef.child("Leaderboard").child("5th").setValue(tab[0]);
                    myRef.child("Leaderboard").child("4th").setValue(tab[1]);
                    myRef.child("Leaderboard").child("3rd").setValue(tab[2]);
                    myRef.child("Leaderboard").child("2nd").setValue(tab[3]);
                    myRef.child("Leaderboard").child("1st").setValue(tab[4]);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });

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

    public void name1(View view) {

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nick = playernick.getText().toString();

                myRef.child("Leaderboard").child("Name 1st").setValue(nick);

                playernick.setVisibility(View.GONE);
                button1.setVisibility(View.GONE);
                leaderboard.setVisibility(View.VISIBLE);

            }
        });
    }

    public void name2(View view) {
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nick = playernick.getText().toString();

                myRef.child("Leaderboard").child("Name 2nd").setValue(nick);

                playernick.setVisibility(View.GONE);
                button2.setVisibility(View.GONE);
                leaderboard.setVisibility(View.VISIBLE);

            }
        });
    }

    public void name3(View view) {
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nick = playernick.getText().toString();

                myRef.child("Leaderboard").child("Name 3rd").setValue(nick);

                playernick.setVisibility(View.GONE);
                button3.setVisibility(View.GONE);
                leaderboard.setVisibility(View.VISIBLE);

            }
        });
    }

    public void name4(View view) {
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nick = playernick.getText().toString();

                myRef.child("Leaderboard").child("Name 4th").setValue(nick);

                playernick.setVisibility(View.GONE);
                button4.setVisibility(View.GONE);
                leaderboard.setVisibility(View.VISIBLE);

            }
        });
    }

    public void name5(View view) {
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nick = playernick.getText().toString();

                myRef.child("Leaderboard").child("Name 5th").setValue(nick);

                playernick.setVisibility(View.GONE);
                button5.setVisibility(View.GONE);
                leaderboard.setVisibility(View.VISIBLE);

            }
        });
    }


}

