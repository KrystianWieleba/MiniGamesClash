package com.example.kris.minigamesclash;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SnailVSActivity extends AppCompatActivity implements OnClickListener {

    private ImageView snail1;
    private ImageView snailAdv;
    private TextView temps;
    private View layout;
    private float posSnail1;
    private float posSnailAdv;
    private float increment;
    private DisplayMetrics metrics;
    private float longueurEcran;
    private float densiteEcran;
    private String nomJ1;
    private String nomJAdv;
    private int scoreJ1;
    private int tour;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Snail");
    DatabaseReference myRef2 = database.getReference("Players");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snail_vs);

       temps=(TextView) findViewById(R.id.temps);
        snail1=(ImageView) findViewById(R.id.snail1);
        snailAdv=(ImageView) findViewById(R.id.snail2);
        snail1.setY(snail1.getY()+40);
        snailAdv.setY(snailAdv.getY()-40);
        layout=(View) findViewById(R.id.layout);
        //le bouton de retour au menu est d'abord caché
        layout.setOnClickListener(this);
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        longueurEcran=metrics.widthPixels;
        densiteEcran=metrics.density;
        posSnailAdv=longueurEcran/20;
        posSnail1=longueurEcran/20;
        increment=longueurEcran/80;
        snail1.setX(posSnail1);
        snailAdv.setX(posSnailAdv);

        nomJ1 = getIntent().getStringExtra("nomJ1");
        nomJAdv = getIntent().getStringExtra("nomJAdv");
        tour = getIntent().getIntExtra("tour", 0);

        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Récupère le score final de chaque joueur pour pouvoir l'incrémenter plus tard
                scoreJ1 = dataSnapshot.child(nomJ1).getValue(int.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        myRef.child(nomJ1).setValue((posSnail1*10000)/longueurEcran);
        myRef.child(nomJAdv).setValue((posSnail1*10000)/longueurEcran);

        myRef.child(nomJAdv).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                posSnailAdv=(longueurEcran*Float.parseFloat(dataSnapshot.getValue().toString()))/10000;
                if ((posSnailAdv+(longueurEcran/5))>=longueurEcran) {
                    // Immobiliser le joueur perdant
                    layout.setClickable(false);
                    temps.setText("Perdu ! " + nomJAdv +" gagne la manche.");
                    nextActivity();

                }
                snailAdv.setX(posSnailAdv);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Failed to read value.", databaseError.toException());
            }
        });

    }



    //il n'y a pas des choses variées à cliquer, l'activité implémente directement OnClickListener
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.layout) {

            //si l'escargot atteint le bout de l'écran, on arrête le timer
            if ((posSnail1 + (longueurEcran / 5)) >= longueurEcran) {

                //On fait apparaître le bouton de retour au menu
                temps.setText(nomJ1 + " remporte la manche !");
                scoreJ1 += 1;
                myRef2.child(nomJ1).setValue(scoreJ1);
                // Sinon le score continue d'être incrémenté à chque clique
                layout.setClickable(false);
                nextActivity();

            }
            //sinon, on fait avancer l'escargot
            else {
                posSnail1 += increment;
                snail1.setX(posSnail1);
            }
            myRef.child(nomJ1).setValue((posSnail1 * 10000) / longueurEcran);
        }

    }

    private void nextActivity() {
        //Au bout de 8sec lance l'interfce
        new CountDownTimer(8000, 10) {
            public void onTick(long tick){}
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), InterfaceActivity.class);
                // Fait suivre le nom des joueurs
                intent.putExtra("nomJ1", nomJ1);
                intent.putExtra("nomJAdv", nomJAdv);
                intent.putExtra("tour", tour);
                startActivity(intent);
            }
        }.start();
    }


}
