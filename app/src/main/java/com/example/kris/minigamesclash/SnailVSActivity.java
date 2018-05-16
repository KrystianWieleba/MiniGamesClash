package com.example.kris.minigamesclash;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SnailVSActivity extends AppCompatActivity implements OnClickListener {

    private ImageView snail1;
    private ImageView snail2;
    private TextView temps;
    private Button retour;
    private View layout;
    private float posSnail1=50;
    private float posSnail2=50;
    private CountDownTimer countDownTimer;
    private boolean debutTimer=false;
    private DisplayMetrics metrics;
    private float longueurEcran;
    private float densiteEcran;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Snail");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snail_vs);

        temps=(TextView) findViewById(R.id.temps);
        snail1=(ImageView) findViewById(R.id.snail1);
        snail2=(ImageView) findViewById(R.id.snail2);
        layout=(View) findViewById(R.id.layout);
        //le bouton de retour au menu est d'abord caché
        retour=(Button) findViewById(R.id.retour);
        retour.setVisibility(View.INVISIBLE);
        layout.setOnClickListener(this);
        retour.setOnClickListener(this);
        snail1.setX(50);
        snail2.setX(50);

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        longueurEcran=metrics.widthPixels;
        densiteEcran=metrics.density;
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
        }

        if (v.getId()==retour.getId()){
            //code de retour au menu avec intent
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }
}
