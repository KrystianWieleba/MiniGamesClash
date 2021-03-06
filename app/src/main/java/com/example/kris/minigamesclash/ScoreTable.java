package com.example.kris.minigamesclash;

import android.content.Intent;
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

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Bubble Destroyer");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_table);

        rankingScore = (TextView) findViewById(R.id.rankingScore);
        leaderboardText = (TextView) findViewById(R.id.leaderboard);


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Lit les jouerus du top5 et leurs scores à partir de firebase
                for (DataSnapshot childs : dataSnapshot.getChildren()) {

                    int first = Integer.parseInt(childs.child("1st").getValue().toString());
                    int second = Integer.parseInt(childs.child("2nd").getValue().toString());
                    int third = Integer.parseInt(childs.child("3rd").getValue().toString());
                    int fourth = Integer.parseInt(childs.child("4th").getValue().toString());
                    int fifth = Integer.parseInt(childs.child("5th").getValue().toString());

                    String nfirst = childs.child("Name 1st").getValue().toString();
                    String nsecond = childs.child("Name 2nd").getValue().toString();
                    String nthird = childs.child("Name 3rd").getValue().toString();
                    String nfourth = childs.child("Name 4th").getValue().toString();
                    String nfifth = childs.child("Name 5th").getValue().toString();

                    //Affichage du top5
                    rankingScore.setText("1 - " + first + "  ( " + nfirst + " )" + "\n\n" + "2 - " + second + "  ( " + nsecond + " )"  + "\n\n" + "3 - " + third + "  ( " + nthird + " )"  + "\n\n" + "4 - " + fourth + "  ( " + nfourth + " )"  + "\n\n" + "5 - " + fifth + "  ( " + nfifth + " )" );
                }
                leaderboardText.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });



}

    public void backToMenu(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

}



