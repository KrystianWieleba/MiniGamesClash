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
    DatabaseReference myRef2 = database.getReference("ScoreCount");

    Button player1;
    Button player2;
    TextView winner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat_bubble_vs);

        TextView finalScore = (TextView) findViewById(R.id.finalScore);
        winner = (TextView) findViewById(R.id.winner);
        player1 = (Button) findViewById(R.id.player1);
        player2 = (Button) findViewById(R.id.player2);




        int score = getIntent().getIntExtra("SCORE", 0);
        finalScore.setText(score + "");



    }

    public void name1(View view) {

        player1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int score = getIntent().getIntExtra("SCORE", 0);
                myRef.child("VS").child("Player1").setValue(score);

                win();








            }
        });
    }

    public void name2(View view) {
        player2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int score = getIntent().getIntExtra("SCORE", 0);
                myRef.child("VS").child("Player2").setValue(score);


                win();



            }
        });
    }

    public void win() {


        new CountDownTimer(60000, 10) {

            public void onTick(long tick){


                myRef.child("VS").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {





                        int i = 0;

                        for (DataSnapshot childs : dataSnapshot.getChildren()) {
                            i++;

                        }


                        if (i == 2) {



                            Long player1 = dataSnapshot.child("Player1").getValue(Long.class);
                            Long player2 = dataSnapshot.child("Player2").getValue(Long.class);




                            if (player1 > player2) {
                                winner.setText("Player 1 won !");
                                winner.setVisibility(View.VISIBLE);
                                myRef2.child("Player 1").setValue(+1);


                            } else if (player1 < player2) {
                                winner.setText("Player 2 won !");
                                winner.setVisibility(View.VISIBLE);
                                myRef2.child("Player 2").setValue(+1);

                            } else {
                                winner.setText("DRAW !");
                                winner.setVisibility(View.VISIBLE);

                            }

                            //myRef.child("Player1").removeValue();
                            //myRef.child("Player2").removeValue();



                        }
                    }



                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w("Failed to read value.", error.toException());
                    }
                });
            }


            public void onFinish() {

                //Temps d'attentes dépassé

            }
        }.start();







            }









}
