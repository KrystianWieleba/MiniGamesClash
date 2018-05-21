package com.example.kris.minigamesclash;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MorpionVSActivity extends AppCompatActivity {

    private Button but00;
    private Button but01;
    private Button but02;
    private Button but10;
    private Button but11;
    private Button but12;
    private Button but20;
    private Button but21;
    private Button but22;

    String res[][] = new String[3][3];
    ArrayList<Button> buttons = new ArrayList<>();
    boolean player1 = true;
    int turn = 0;
    private String nomJ1;
    private String nomJAdv;
    private String symboleJ1;
    private String symboleJAdv;
    private Drawable croixourondJ1;
    private Drawable croixourondJAdv;
    int atoidejouer=1;
    private String nomJpret=" ";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Morpion");

    private View.OnClickListener onClickListenerCases = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setBackgroundDrawable(croixourondJ1);
            v.setClickable(false);
                if (v.getId() == R.id.button00) {
                    res[0][0] = symboleJ1;
                    myRef.child("case00").setValue(symboleJ1);
                } else if (v.getId() == R.id.button01) {
                    res[0][1] = symboleJ1;
                    myRef.child("case01").setValue(symboleJ1);
                } else if (v.getId() == R.id.button02) {
                    res[0][2] = symboleJ1;
                    myRef.child("case02").setValue(symboleJ1);
                } else if (v.getId() == R.id.button10) {
                    res[1][0] = symboleJ1;
                    myRef.child("case10").setValue(symboleJ1);
                } else if (v.getId() == R.id.button11) {
                    res[1][1] = symboleJ1;
                    myRef.child("case11").setValue(symboleJ1);
                } else if (v.getId() == R.id.button12) {
                    res[1][2] = symboleJ1;
                    myRef.child("case12").setValue(symboleJ1);
                } else if (v.getId() == R.id.button20) {
                    res[2][0] = symboleJ1;
                    myRef.child("case20").setValue(symboleJ1);
                } else if (v.getId() == R.id.button21) {
                    res[2][1] = symboleJ1;
                    myRef.child("case21").setValue(symboleJ1);
                } else if (v.getId() == R.id.button22) {
                    res[2][2] = symboleJ1;
                    myRef.child("case22").setValue(symboleJ1);
                }

            turn++;

            if (Win()) {
                if (player1) {
                    player1Win();
                } else {
                    player2Win();
                }
            } else if (turn == 9) {
                draw();
            } else {
                player1 = !player1;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morpion_vs);

        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                res[i][j] = " ";
            }
        }

        but00 = (Button) findViewById(R.id.button00);
        but01 = (Button) findViewById(R.id.button01);
        but02 = (Button) findViewById(R.id.button02);
        but10 = (Button) findViewById(R.id.button10);
        but11 = (Button) findViewById(R.id.button11);
        but12 = (Button) findViewById(R.id.button12);
        but20 = (Button) findViewById(R.id.button20);
        but21 = (Button) findViewById(R.id.button21);
        but22 = (Button) findViewById(R.id.button22);

        buttons.add(but00);
        buttons.add(but01);
        buttons.add(but02);
        buttons.add(but10);
        buttons.add(but11);
        buttons.add(but12);
        buttons.add(but20);
        buttons.add(but21);
        buttons.add(but22);

        for (Button but : buttons) {
            but.setBackgroundDrawable(null);
            but.setEnabled(false); //pour empêcher de jouer avant que l'autre adversaire soit prêt
            but.setOnClickListener(onClickListenerCases);
        }

        nomJ1 = getIntent().getStringExtra("nomJ1");
        nomJAdv = getIntent().getStringExtra("nomJAdv");

        if (nomJ1.compareTo(nomJAdv)>0){
            symboleJ1="x";
            symboleJAdv="o";
            croixourondJ1 = ContextCompat.getDrawable(MorpionVSActivity.this,R.drawable.morpioncross);
            croixourondJAdv = ContextCompat.getDrawable(MorpionVSActivity.this,R.drawable.morpionround);
        }
        else {
            symboleJ1="o";
            symboleJAdv="x";
            croixourondJ1 = ContextCompat.getDrawable(MorpionVSActivity.this,R.drawable.morpionround);
            croixourondJAdv = ContextCompat.getDrawable(MorpionVSActivity.this,R.drawable.morpioncross);
        }

        for (int i=0;i<3;i++) {
            for (int j=0;j<3;j++) {
                    myRef.child("case" + i + "" + j).setValue(" ");
            }
        }
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //nothing
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (atoidejouer==1){
                    atoidejouer=0;
                    for (Button but : buttons) {
                        but.setEnabled(false);
                    }
                }
                else{
                    atoidejouer=1;
                    switch(dataSnapshot.getKey()){
                        case "case00":
                            res[0][0] = symboleJAdv;
                            but00.setBackgroundDrawable(croixourondJAdv);
                            but00.setClickable(false);
                            break;
                        case "case01":
                            res[0][1] = symboleJAdv;
                            but01.setBackgroundDrawable(croixourondJAdv);
                            but01.setClickable(false);
                            break;
                        case "case02":
                            res[0][2] = symboleJAdv;
                            but02.setBackgroundDrawable(croixourondJAdv);
                            but02.setClickable(false);
                            break;
                        case "case10":
                            res[1][0] = symboleJAdv;
                            but10.setBackgroundDrawable(croixourondJAdv);
                            but10.setClickable(false);
                            break;
                        case "case11":
                            res[1][1] = symboleJAdv;
                            but11.setBackgroundDrawable(croixourondJAdv);
                            but11.setClickable(false);
                            break;
                        case "case12":
                            res[1][2] = symboleJAdv;
                            but12.setBackgroundDrawable(croixourondJAdv);
                            but12.setClickable(false);
                            break;
                        case "case20":
                            res[2][0] = symboleJAdv;
                            but20.setBackgroundDrawable(croixourondJAdv);
                            but20.setClickable(false);
                            break;
                        case "case21":
                            res[2][1] = symboleJAdv;
                            but21.setBackgroundDrawable(croixourondJAdv);
                            but21.setClickable(false);
                            break;
                        case "case22":
                            res[2][2] = symboleJAdv;
                            but22.setBackgroundDrawable(croixourondJAdv);
                            but22.setClickable(false);
                            break;

                    }
                    turn+=3; //test
                    if (Win()) {
                        if (player1) {
                            player1Win();
                        } else {
                            player2Win();
                        }
                    } else if (turn == 9) {
                        draw();
                    } else {
                        player1 = !player1;
                    }
                    for (Button but : buttons) {
                        but.setEnabled(true);
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //nothing
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //nothing
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Failed to read value.", databaseError.toException());
            }
        });

        //pour s'assurer que les deux joueurs sont prêts avant de permettre le jeu
        //le joueur qui ne joue pas se signale prêt via la database
        if (atoidejouer==0){
            myRef.child("pret"+nomJ1).setValue(nomJ1);
        }
        else {
            myRef.child("pret"+nomJAdv).setValue(nomJAdv);
            ValueEventListener eventListenerPrets=new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    nomJpret=dataSnapshot.getValue().toString();
                    if (nomJAdv.equals(nomJpret)) {
                        for (Button but : buttons) {
                            but.setEnabled(true);
                        }
                        myRef.child("pret" + nomJAdv).setValue("trucbidule");//je comprends pas comment ce code peut sexecuter alors que je suis tout seul
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("Failed to read value.", databaseError.toException());
                }
            };
            myRef.child("pret"+nomJAdv).addValueEventListener(eventListenerPrets);
        }
    }


    boolean Win() {

        for (int i=0; i<3; i++) {
            if (res[i][0].equals(res[i][1]) && res[i][0].equals(res[i][2]) && !res[i][0].equals(" ") ) {
                return true;
            }
        }

        for (int j=0; j<3; j++) {
            if ( res[0][j].equals(res[1][j]) && res[0][j].equals(res[2][j]) && !res[0][j].equals(" ") ) {
                return true;
            }
        }

        if ( res[0][0].equals(res[1][1]) && res[0][0].equals(res[2][2]) && !res[0][0].equals(" ") ) {
            return true;
        }

        if ( res[0][2].equals(res[1][1]) && res[0][2].equals(res[2][0]) && !res[0][2].equals(" ") ) {
            return true;
        }

        return false;

    }

    // Les Toast sont là uniquement pour voir les résultats au moment du développement

    void player1Win(){
        Toast.makeText(this, "Player 1 wins !", Toast.LENGTH_SHORT).show();
    }

    void player2Win(){
        Toast.makeText(this, "Player 2 wins !", Toast.LENGTH_SHORT).show();
    }

    void draw(){
        Toast.makeText(this, "Draw !", Toast.LENGTH_SHORT).show();
    }


}
