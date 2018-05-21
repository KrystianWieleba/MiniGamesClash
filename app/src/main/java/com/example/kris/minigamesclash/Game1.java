package com.example.kris.minigamesclash;

import android.content.Intent;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class Game1 extends AppCompatActivity {

    private TextView Score;
    private TextView start;
    private ImageView blue;
    private ImageView red;
    private ImageView green;
    private ImageView orange;


    private int frameWidth;
    private int screenWidth;
    private int screenHeight;

    private int redX = -500;
    private int redY = 2000;
    private int blueX = -500;
    private int blueY = 2000;
    private int greenX = -500;
    private int greenY = 2000;
    private int orangeX = -500;
    private int orangeY = 2000;

    private int score = 0;

    private boolean init = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);

        // Instancier les view
        Score = (TextView) findViewById(R.id.Score);
        start = (TextView) findViewById(R.id.start);
        blue = (ImageView) findViewById(R.id.blue);
        red = (ImageView) findViewById(R.id.red);
        green = (ImageView) findViewById(R.id.green);
        orange = (ImageView) findViewById(R.id.orange);


        // Fixer le score invisible au lancement
        Score.setVisibility(View.INVISIBLE);

        // Obtenir la taille de l'écran
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        // Placer initialement les bulles en dehors de l'écran
        red.setX(-500);
        red.setY(2000);
        blue.setX(-500);
        blue.setY(2000);
        green.setX(-500);
        green.setY(2000);
        orange.setX(-500);
        orange.setY(2000);

    }


    public boolean onTouchEvent(MotionEvent user) {

        // Permet de ne lancer le timer qu'une seule fois
        if (init == false) {

            init = true;

            // Instancier le frame
            FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
            // Obtenir la largeur du frame
            frameWidth = frame.getWidth();

            // Changer la visibilité
            start.setVisibility(View.GONE);
            Score.setVisibility(View.VISIBLE);


            // Compte à rebours de 20sec
            new CountDownTimer(20000, 10) {
                // Appel à la méthode position à chaque tick
                public void onTick(long tick){
                    position();
                }

                // Lance l'activité result à la fin du compte à rebours
                public void onFinish() {
                    Intent intent = new Intent(getApplicationContext(), resultGame1.class);
                    // Prends en compte le score final dans result
                    intent.putExtra("SCORE", score);
                    startActivity(intent);

                }
            }.start();
        }

        // Fixer l'imageView blue en tant que listener
        blue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent user) {
                // Si blue est appuyé, réinitialisa sa position et incrémente le score
                if ( user.getAction() == MotionEvent.ACTION_DOWN ) {
                    blueY = -100;
                    blueX = (int) Math.floor(Math.random() * (frameWidth - blue.getWidth()));
                    score += 10;
                }
                return true;
            }
        });

        red.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent user) {

                if ( user.getAction() == MotionEvent.ACTION_DOWN ) {
                    redY = -1000;
                    redX = (int)Math.floor(Math.random() * (frameWidth - red.getWidth()));
                    score += 40;
                }
                return true;
            }
        });

        green.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent user) {

                if ( user.getAction() == MotionEvent.ACTION_DOWN ) {
                    greenY = -700;
                    greenX = (int)Math.floor(Math.random() * (frameWidth - green.getWidth()));
                    score += 30;
                }
                return true;
            }
        });

        orange.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent user) {

                if ( user.getAction() == MotionEvent.ACTION_DOWN ) {
                    orangeY = -400;
                    orangeX = (int)Math.floor(Math.random() * (frameWidth - orange.getWidth()));
                    score += 20;
                }
                return true;
            }
        });

        return true;

    }


    public void position() {

        // Vitesse
        redY += 25;
        if (redY > screenHeight) {
            // Fréquence d'apparition ( 0 --> apparition immédiate après dépassement de l'écran )
            redY = -1000;
            // Apparition aléatoire sur X
            redX = (int)Math.floor(Math.random() * (frameWidth - red.getWidth()));
        }
        // Actualisation de la position
        red.setX(redX);
        red.setY(redY);


        blueY += 10;
        if (blueY > screenHeight) {
            blueY = -100;
            blueX = (int)Math.floor(Math.random() * (frameWidth - blue.getWidth()));
        }
        blue.setX(blueX);
        blue.setY(blueY);


        greenY += 20;
        if (greenY > screenHeight) {
            greenY = -700;
            greenX = (int)Math.floor(Math.random() * (frameWidth - green.getWidth()));
        }
        green.setX(greenX);
        green.setY(greenY);


        orangeY += 15;
        if (orangeY > screenHeight) {
            orangeY = -400;
            orangeX = (int)Math.floor(Math.random() * (frameWidth - orange.getWidth()));
        }
        orange.setX(orangeX);
        orange.setY(orangeY);

        // Actualisation du score
        Score.setText("Score : " + score);

    }



}
