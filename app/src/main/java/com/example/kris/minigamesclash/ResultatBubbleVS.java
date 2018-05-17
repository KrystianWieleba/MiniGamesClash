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
    public long p1;
    public long p2;
    public int P1;
    public int P2;


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

        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                p1 = dataSnapshot.child("Player 1").getValue(long.class);
                p2 = dataSnapshot.child("Player 2").getValue(long.class);
                P1 = (int) p1;
                P1=0;
                P2 = (int) p2;
                P2=0;


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



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


        new CountDownTimer(60000, 2000) {

            public void onTick(long tick){


                myRef.child("VS").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {





                        int i = 0;

                        for (DataSnapshot childs : dataSnapshot.getChildren()) {
                            i++;

                        }


                        if (i == 2) {

                            cancel();

                            Long player1 = dataSnapshot.child("Player1").getValue(Long.class);
                            Long player2 = dataSnapshot.child("Player2").getValue(Long.class);




                            if (player1 > player2) {
                                winner.setText("Player 1 won !");
                                winner.setVisibility(View.VISIBLE);


                                P1 += 1;
                                myRef2.child("Player 1").setValue(P1);


                                delete();


                            } else if (player1 < player2) {
                                winner.setText("Player 2 won !");
                                winner.setVisibility(View.VISIBLE);

                                P2 += 1;
                                myRef2.child("Player 2").setValue(P2);



                                delete();

                            } else {
                                winner.setText("DRAW !");
                                winner.setVisibility(View.VISIBLE);
                                delete();

                            }





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


            public void delete() {

                myRef.child("VS").child("Player1").removeValue();
                myRef.child("VS").child("Player2").removeValue();

                new CountDownTimer(5000, 10) {
                    // Appel à la méthode position à chaque tick
                    public void onTick(long tick){

                    }

                    // Lance l'activité result à la fin du compte à rebours
                    public void onFinish() {

                        Intent intent = new Intent(getApplicationContext(), InterfaceActivity.class);
                        startActivity(intent);

                    }
                }.start();

            }








}
