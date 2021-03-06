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

import static android.view.View.GONE;


public class BubbleVS extends AppCompatActivity {

    private TextView scoreDisplay;
    private TextView start;
    private ImageView blue;
    private ImageView red;
    private ImageView green;
    private ImageView orange;
    private ImageView blue2;
    private ImageView red2;
    private ImageView blue3;
    private ImageView red3;
    private ImageView blue4;
    private ImageView red4;
    private TextView msg1;
    private TextView msg2;

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

    private int redPlayer;
    private int bluePlayer;
    private int score = 0;
    private boolean init = false;
    private String nomJ1;
    private String nomJAdv;
    private int tour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble_vs);

        // Instancier les view
        scoreDisplay = (TextView) findViewById(R.id.Score);
        start = (TextView) findViewById(R.id.start);
        blue = (ImageView) findViewById(R.id.blue);
        red = (ImageView) findViewById(R.id.red);
        green = (ImageView) findViewById(R.id.green);
        orange = (ImageView) findViewById(R.id.orange);
        blue2 = (ImageView) findViewById(R.id.blue2);
        red2 = (ImageView) findViewById(R.id.red2);
        blue3 = (ImageView) findViewById(R.id.blue3);
        red3 = (ImageView) findViewById(R.id.red3);
        blue4 = (ImageView) findViewById(R.id.blue4);
        red4 = (ImageView) findViewById(R.id.red4);
        msg1 = (TextView) findViewById(R.id.msg1);
        msg2 = (TextView) findViewById(R.id.msg2);

        // Récupérer le nom des deux joueurs
        nomJ1 = getIntent().getStringExtra("nomJ1");
        nomJAdv = getIntent().getStringExtra("nomJAdv");
        tour = getIntent().getIntExtra("tour", 0);

        // Fixer le score invisible au lancement
        scoreDisplay.setVisibility(View.INVISIBLE);

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

        //Placer les deux bulles centrés servant de boutons
        red2.setX(screenWidth/2 - 300);
        red2.setY(screenHeight/2 -200);
        blue2.setX(screenWidth/2 + 100);
        blue2.setY(screenHeight/2 - 200);

        //Placer les explications du VS (optimisé pour mon portable ou similaire)
        red3.setX(100);
        red3.setY(1200);
        blue3.setX(100);
        blue3.setY(1350);
        red4.setX(550);
        red4.setY(1200);
        blue4.setX(550);
        blue4.setY(1350);

        //Listener sur les imageView afin de faire le choix de couleur
        blue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluePlayer = 1;
                redPlayer = 0;
                onTouch();
            }
        });

        red2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluePlayer = 0;
                redPlayer = 1;
                onTouch();
            }
        });
    }

    public boolean onTouch() {

        // Permet de ne lancer le timer qu'une seule fois
        if (init == false) {
            init = true;

            // Instancier le frame
            FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
            // Obtenir la largeur du frame
            frameWidth = frame.getWidth();

            // Changer la visibilité des view
            start.setVisibility(GONE);
            red2.setVisibility(GONE);
            blue2.setVisibility(GONE);
            red3.setVisibility(GONE);
            blue3.setVisibility(GONE);
            red4.setVisibility(GONE);
            blue4.setVisibility(GONE);
            msg1.setVisibility(GONE);
            msg2.setVisibility(GONE);
            scoreDisplay.setVisibility(View.VISIBLE);


            // Compte à rebours de 20sec
            new CountDownTimer(20000, 10) {
                // Appel à la méthode position à chaque tick
                public void onTick(long tick){
                    position();
                }

                // Lance la prochaine activité à la fin du compte à rebours
                public void onFinish() {
                    Intent intent = new Intent(getApplicationContext(), ResultatBubbleVS.class);
                    // Prends en compte le score du joueur
                    intent.putExtra("SCORE", score);
                    // Fait suivre le nom des joueurs
                    intent.putExtra("nomJ1", nomJ1);
                    intent.putExtra("nomJAdv", nomJAdv);
                    intent.putExtra("tour", tour);
                    startActivity(intent);

                }
            }.start();
        }

        // Fixer l'imageView blue en tant que listener
        red.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent user) {
                // Si red est appuyé, réinitialisa sa position et incrémente le score
                if ( user.getAction() == MotionEvent.ACTION_DOWN ) {
                    redY = -100;
                    redX = (int)Math.floor(Math.random() * (frameWidth - red.getWidth()));
                    //En fonction de la couleur choisit, le score incrémenté est différent
                    if (redPlayer == 1) {
                        score += 20;
                    } else if (redPlayer == 0){
                        score -= 20;
                    }
                }
                return true;
            }
        });

        blue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent user) {
                // Si blue est appuyé, réinitialisa sa position et incrémente le score
                if ( user.getAction() == MotionEvent.ACTION_DOWN ) {
                    blueY = -100;
                    blueX = (int) Math.floor(Math.random() * (frameWidth - blue.getWidth()));
                    if (bluePlayer == 1) {
                        score += 20;
                    } else if (bluePlayer == 0) {
                        score -= 20;
                    }
                }
                return true;
            }
        });



        green.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent user) {

                if ( user.getAction() == MotionEvent.ACTION_DOWN ) {
                    greenY = -1000;
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
                    orangeY = -600;
                    orangeX = (int)Math.floor(Math.random() * (frameWidth - orange.getWidth()));
                    score += 25;
                }
                return true;
            }
        });


        return true;
    }


    public void position() {

        // Vitesse
        redY += 10;
        // Reposition des bulles après dépassement de l'écran
        if (redY > screenHeight) {
            // Fréquence d'apparition ( 0 --> apparition immédiate après dépassement de l'écran )
            redY = -100;
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
            greenY = -1000;
            greenX = (int)Math.floor(Math.random() * (frameWidth - green.getWidth()));
        }
        green.setX(greenX);
        green.setY(greenY);


        orangeY += 15;
        if (orangeY > screenHeight) {
            orangeY = -600;
            orangeX = (int)Math.floor(Math.random() * (frameWidth - orange.getWidth()));
        }
        orange.setX(orangeX);
        orange.setY(orangeY);

        // Actualisation du score
        scoreDisplay.setText("Score : " + score);

    }



}
