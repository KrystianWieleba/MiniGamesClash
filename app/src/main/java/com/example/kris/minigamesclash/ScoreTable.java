package com.example.kris.minigamesclash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ScoreTable extends AppCompatActivity {

    TextView msg = null;
    TextView ranking = null;
    TextView leaderboardText;

    //ArrayList<String> mRef;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Bubble Destroyer");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_table);

        msg =(TextView) findViewById(R.id.message);
        ranking =(TextView) findViewById(R.id.ranking);
        leaderboardText =(TextView) findViewById(R.id.leaderboard);

        leaderboardText.setVisibility(View.INVISIBLE);


        //mRef = new ArrayList<>();



        //myRef.child("Leaderboard").push().child("score").setValue(score);



        //Query query = myRef.child("Leaderboard").orderByValue();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childs : dataSnapshot.getChildren()) {

                    String list = childs.getValue().toString();

                    String first = childs.child("1st").getValue().toString();
                    String second = childs.child("2nd").getValue().toString();
                    String third = childs.child("3rd").getValue().toString();
                    String fourth = childs.child("4th").getValue().toString();
                    String fifth = childs.child("5th").getValue().toString();

                    int First = Integer.parseInt(first);
                    int Second = Integer.parseInt(second);
                    int Third = Integer.parseInt(third);
                    int Fourth = Integer.parseInt(fourth);
                    int Fifth = Integer.parseInt(fifth);

                    //firstPlace.setText(list + "\n");

                    int[] tab = { Fifth, Fourth, Third, Second, First };

                    int score = getIntent().getIntExtra("SCORE", 0);

                    for (int i=4; i>0; i--) {
                        for (int j=0; j<i; j++) {
                            if (tab[j] > tab[j+1]) {
                                int temp = tab[j];
                                tab[j]=tab[j+1];
                                tab[j+1]=temp;
                            }
                        }
                    }


                    if (score > tab[4]) {
                        tab[0]=score;
                        msg.setText("Félicitation, vous êtes dans le Top 1 ! ");
                    } else if ( score > tab[3] && score < tab[4]) {
                        tab[0]=score;
                        msg.setText("Félicitation, vous êtes dans le Top 2 ! ");

                    } else if ( score > tab[2] && score < tab[3]) {
                        tab[0]=score;
                        msg.setText("Félicitation, vous êtes dans le Top 3 ! ");

                    } else if ( score > tab[1] && score < tab[2]) {
                        tab[0]=score;
                        msg.setText("Félicitation, vous êtes dans le Top 4 ! ");

                    } else if ( score > tab[0] && score < tab[1]) {
                        tab[0]=score;
                        msg.setText("Félicitation, vous êtes dans le Top 5 ! ");

                    } else {
                        msg.setText("Trop nul ! ");
                    }


                    for (int i=4; i>0; i--) {
                        for (int j=0; j<i; j++) {
                            if (tab[j] > tab[j+1]) {
                                int temp = tab[j];
                                tab[j]=tab[j+1];
                                tab[j+1]=temp;
                            }
                        }
                    }


                    myRef.child("Leaderboard").child("5th").setValue(tab[0]);
                    myRef.child("Leaderboard").child("4th").setValue(tab[1]);
                    myRef.child("Leaderboard").child("3rd").setValue(tab[2]);
                    myRef.child("Leaderboard").child("2nd").setValue(tab[3]);
                    myRef.child("Leaderboard").child("1st").setValue(tab[4]);

                    leaderboardText.setVisibility(View.VISIBLE);

                    ranking.setText("1 - " + tab[4] + "\n\n" + "2 - " + tab[3] + "\n\n" + "3 - " + tab[2] + "\n\n" + "4 - " + tab[1] + "\n\n" + "5 - " + tab[0]);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });


    }
}



