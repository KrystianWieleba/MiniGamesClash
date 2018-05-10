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

        Query query = myRef.child("Leaderboard").orderByValue();


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot childs : dataSnapshot.getChildren()) {

                        firstPlace.setText(childs.getValue().toString());
                        secondPlace.setText("" + dataSnapshot.getChildrenCount());

                    }
                }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });
    }
}



