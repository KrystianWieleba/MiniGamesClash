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

    TextView fistPlace = null;
    TextView secondPlace = null;
    TextView thirdPlace = null;
    TextView fourthPlace = null;
    TextView fifthPlace = null;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("leaderboard");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_table);

        fistPlace =(TextView) findViewById(R.id.score1);
        secondPlace =(TextView) findViewById(R.id.score2);
        thirdPlace =(TextView) findViewById(R.id.score3);
        fourthPlace =(TextView) findViewById(R.id.score4);
        fifthPlace =(TextView) findViewById(R.id.score5);

        int score = getIntent().getIntExtra("SCORE", 0);

        myRef.child("id" + score ).setValue(score+"");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String value = dataSnapshot.child("id0").getValue(String.class);
                //scoreTable.setText(value);
                //myRef.child("score").setValue(value);


                int[] temp = new int[5];

                //J'ai essayer de faire un genre de tri, mais puisque les childs garde leurs valeurs ça ne marche pas..
                for (int i =0; i<=2; i++) {


                    for (DataSnapshot childs : dataSnapshot.getChildren()) {
                        String list = childs.getValue().toString();
                        int res = Integer.parseInt(list);

                        if (res > temp[i]) {
                            temp[i] = res;


                        }

                    }



                    fistPlace.setText("1 : " + temp[0]);
                    secondPlace.setText("2 : " + temp[1]);
                    thirdPlace.setText("3 : " + temp[2]);

                    //fourthPlace.setText("4 : " + temp[3]);
                    //fifthPlace.setText("5 : " + temp[4]);

                    //myRef.child("id"+temp[0]).setValue(00);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });
    }
}



