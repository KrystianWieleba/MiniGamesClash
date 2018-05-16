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
    private Button retour;
    private View layout;
    private float posSnail1=50;
    private float posSnailAdv=50;
    private CountDownTimer countDownTimer;
    private boolean debutTimer=false;
    private DisplayMetrics metrics;
    private float longueurEcran;
    private float densiteEcran;
    private String refSnail1;
    private String refSnailAdv;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Snail");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snail_vs);

        temps=(TextView) findViewById(R.id.temps);
        snail1=(ImageView) findViewById(R.id.snail1);
        snailAdv=(ImageView) findViewById(R.id.snail2);
        layout=(View) findViewById(R.id.layout);
        //le bouton de retour au menu est d'abord caché
        retour=(Button) findViewById(R.id.retour);
        retour.setVisibility(View.INVISIBLE);
        layout.setOnClickListener(this);
        retour.setOnClickListener(this);
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        longueurEcran=metrics.widthPixels;
        densiteEcran=metrics.density;
        snail1.setX(50);
        snailAdv.setX(50);

        //Phase d'initialisation des identifiants (qu'on pourra peut-être mettre au debut du vs)
        int temp = (int)Math.random()*1000000;
        refSnail1=Integer.toString(temp);
        myRef.child(refSnail1);

        myRef.child(refSnail1).setValue(snail1);

        ValueEventListener eventL1 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childs : dataSnapshot.getChildren()) {
                    String refTemp=childs.getKey();
                    if (refTemp!=refSnail1){
                        refSnailAdv=refTemp;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Failed to read value.", databaseError.toException());
            }
        };

        while (refSnailAdv==null) {
            myRef.addListenerForSingleValueEvent(eventL1);
        }

        myRef.removeEventListener(eventL1);


        myRef.child(refSnailAdv).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                posSnailAdv=Integer.parseInt(dataSnapshot.getValue().toString());
                snailAdv.setX(posSnailAdv);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Failed to read value.", databaseError.toException());
            }
        });
    }

    //il n'y a pas des choses variées à cliquer, l'activité implémente directement OnClickListener
    //mais une autre manière de faire est possible, je changerai peut-etre
    @Override
    public void onClick(View v) {
        //si ça n'a pas encore commencé, on lance le timer
        if (!debutTimer){
            debutTimer=true;
            countDownTimer= new CountDownTimer(14000,100) {
                @Override
                public void onTick(long millisUntilFinished) {
                    //bidouilles pour afficher un joli compteur à un chiffre après la virgule
                    temps.setText(new Float((float)((14000-millisUntilFinished)/100)/10).toString());
                }
                @Override
                public void onFinish() {
                    temps.setText("Trop nul...");
                }
            }.start();
        }
        //si l'escargot atteint le bout de l'écran, on arrête le timer
        else if ((posSnail1+120*densiteEcran)>=longueurEcran){
            countDownTimer.cancel();
            //On fait apparaître le bouton de retour au menu
            retour.setVisibility(View.VISIBLE);
        }
        //sinon, on fait avancer l'escargot
        else {
            posSnail1 += 12;
            snail1.setX(posSnail1);
            myRef.child(refSnail1).setValue(posSnail1);
        }

        if (v.getId()==retour.getId()){
            //code de retour au menu avec intent
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }
}

//Il faudra ajouter une ligne à la fin pour supprimer le child avc l'id aleatoire (pour l'instant le faire à la main)