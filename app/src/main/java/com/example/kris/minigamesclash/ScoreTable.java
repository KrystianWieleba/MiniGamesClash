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

    TextView scoreTable = null;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("leaderboard");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_table);

        scoreTable =(TextView) findViewById(R.id.scoree);

        int score = getIntent().getIntExtra("SCORE", 0);


        myRef.child("id" + score ).setValue(score + "");




        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String value = dataSnapshot.child("id0").getValue(String.class);
                //scoreTable.setText(value + "");
                //myRef.child("score").setValue(value);

                for (DataSnapshot childs : dataSnapshot.getChildren()) {
                    String list = childs.getValue().toString();
                    scoreTable.setText(list);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });



    }

}



