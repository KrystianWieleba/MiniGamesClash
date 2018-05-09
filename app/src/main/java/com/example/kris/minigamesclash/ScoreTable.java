package com.example.kris.minigamesclash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    TextView firstPlace = null;
    TextView secondPlace = null;
    TextView thirdPlace = null;
    TextView fourthPlace = null;
    TextView fifthPlace = null;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Bubble Destroyer");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_table);

        firstPlace =(TextView) findViewById(R.id.score1);
        secondPlace =(TextView) findViewById(R.id.score2);
        thirdPlace =(TextView) findViewById(R.id.score3);
        fourthPlace =(TextView) findViewById(R.id.score4);
        fifthPlace =(TextView) findViewById(R.id.score5);

        int score = getIntent().getIntExtra("SCORE", 0);


        myRef.child("Leaderboard").push().child("score").setValue(score);

        Query query = myRef.child("Leaderboard").orderByValue().limitToLast(5);

        //myRef.limitToLast(5).toJSON();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String value = dataSnapshot.child("id0").getValue(String.class);
                //scoreTable.setText(value);
                //myRef.child("score").setValue(value);

                //just pour tester si le for parcourt tout
                int i =0;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        String list = child.getValue().toString();
                        firstPlace.setText(list);
                        i++;
                    }
                //firstPlace.setText(""+i);

                    //fistPlace.setText("1 : " + temp[0]);
                    //secondPlace.setText("2 : " + temp[1]);
                    //thirdPlace.setText("3 : " + temp[2]);
                    //fourthPlace.setText("4 : " + temp[3]);
                    //fifthPlace.setText("5 : " + temp[4]);

                    //myRef.child("id"+temp[0]).setValue(00);
                }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });
    }
}

/*Query meilleurs = myRef.orderByValue().limitToFirst(5);
        final String[] temp = new String[5];
        meilleurs.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            int i=4;
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                temp[i]=snapshot.getValue(String.class);
                                i=i-1;
                            }
                        }

                     }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w("Failed to read value.", error.toException());
                                                     }
                                                 }

        );

        fistPlace.setText("1 : " + temp[0]);
        secondPlace.setText("2 : " + temp[1]);
        thirdPlace.setText("3 : " + temp[4]);*/



