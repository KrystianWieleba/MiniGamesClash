package com.example.kris.minigamesclash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ScoreTable extends AppCompatActivity {

    private TextView rankingScore = null;
    private TextView leaderboardText;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Bubble Destroyer");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_table);

        rankingScore = (TextView) findViewById(R.id.rankingScore);
        leaderboardText = (TextView) findViewById(R.id.leaderboard);


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Lit les jouerus du top5 et leurs scores à partie de firebase
                for (DataSnapshot childs : dataSnapshot.getChildren()) {

                    int First = Integer.parseInt(childs.child("1st").getValue().toString());
                    int Second = Integer.parseInt(childs.child("2nd").getValue().toString());
                    int Third = Integer.parseInt(childs.child("3rd").getValue().toString());
                    int Fourth = Integer.parseInt(childs.child("4th").getValue().toString());
                    int Fifth = Integer.parseInt(childs.child("5th").getValue().toString());

                    String Nfirst = childs.child("Name 1st").getValue().toString();
                    String Nsecond = childs.child("Name 2nd").getValue().toString();
                    String Nthird = childs.child("Name 3rd").getValue().toString();
                    String Nfourth = childs.child("Name 4th").getValue().toString();
                    String Nfifth = childs.child("Name 5th").getValue().toString();

                    //Affichage du top5
                    rankingScore.setText("1 - " + First + "  ( " + Nfirst + " )" + "\n\n" + "2 - " + Second + "  ( " + Nsecond + " )"  + "\n\n" + "3 - " + Third + "  ( " + Nthird + " )"  + "\n\n" + "4 - " + Fourth + "  ( " + Nfourth + " )"  + "\n\n" + "5 - " + Fifth + "  ( " + Nfifth + " )" );
                }
                leaderboardText.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });

}

}



