package com.example.kris.minigamesclash;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MorpionActivity extends AppCompatActivity implements View.OnClickListener {

    //contiendra l'état des cases
    String res[][] = new String[3][3];

    ArrayList<Button> buttons = new ArrayList<>();
    int turn = 0;
    int vic = 0;

    //chaque bouton représente une case du morpion
    Button but00;
    Button but01;
    Button but02;
    Button but10;
    Button but11;
    Button but12;
    Button but20;
    Button but21;
    Button but22;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morpion);

        //initialise l'état de chaque case
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
            but.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {

        // Vérifie que la case est bien libre

        if (v.getBackground() != null) {
            return;
        }

        // Prise en compte du choix de la case dans le tableau du jeu et affichage pour les joueurs
        //l'utilisateur prend les croix

        if (v.getId() == R.id.button00) {
            res[0][0] = "x";
            v.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.morpioncross));
        } else if (v.getId() == R.id.button01) {
            res[0][1] = "x";
            v.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.morpioncross));
        } else if (v.getId() == R.id.button02) {
            res[0][2] = "x";
            v.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.morpioncross));
        } else if (v.getId() == R.id.button10) {
            res[1][0] = "x";
            v.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.morpioncross));
        } else if (v.getId() == R.id.button11) {
            res[1][1] = "x";
            v.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.morpioncross));
        } else if (v.getId() == R.id.button12) {
            res[1][2] = "x";
            v.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.morpioncross));
        } else if (v.getId() == R.id.button20) {
            res[2][0] = "x";
            v.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.morpioncross));
        } else if (v.getId() == R.id.button21) {
            res[2][1] = "x";
            v.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.morpioncross));
        } else if (v.getId() == R.id.button22) {
            res[2][2] = "x";
            v.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.morpioncross));
        }

        turn++;

        // Vérifie si le joueur a gagné

        if (Win() && vic == 0) {
            vic = 1;
            player1Win();
        } else if (turn == 9) {
            egalite();
        }


        // On construit un tableau qui prend l'état du jeu

        String a = res[0][0];
        String b = res[0][1];
        String c = res[0][2];
        String d = res[1][0];
        String e = res[1][1];
        String f = res[1][2];
        String g = res[2][0];
        String h = res[2][1];
        String i = res[2][2];

        String[] tab = {a, b, c, d, e, f, g, h, i};

        // On génère un nombre aléatoire entre 0 et 8, et on vérifie que la case correspondante est vide

        int n = (int) Math.round(Math.random() * 10);

        while (n == 9 || tab[n] != " ") {
            n = (int) Math.round(Math.random() * 10);
        }

        // // Prise en compte du choix de la case par l'IA dans le tableau du jeu et affichage pour les joueurs

        if (n == 0) {
            res[0][0] = "o";
            but00.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.morpionround));
        } else if (n == 1) {
            res[0][1] = "o";
            but01.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.morpionround));
        } else if (n == 2) {
            res[0][2] = "o";
            but02.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.morpionround));
        } else if (n == 3) {
            res[1][0] = "o";
            but10.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.morpionround));
        } else if (n == 4) {
            res[1][1] = "o";
            but11.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.morpionround));
        } else if (n == 5) {
            res[1][2] = "o";
            but12.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.morpionround));
        } else if (n == 6) {
            res[2][0] = "o";
            but20.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.morpionround));
        } else if (n == 7) {
            res[2][1] = "o";
            but21.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.morpionround));
        } else if (n == 8) {
            res[2][2] = "o";
            but22.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.morpionround));
        }

        // Vérification su l'IA a perdu

        if (turn < 9) {
            turn++;
            if (Win() && vic == 0) {
                vic =1;
                player2Win();
            }
        }
        
    }


    //teste s'il y a victoire
    boolean Win() {

        //teste les lignes

        for (int i=0; i<3; i++) {
            if (res[i][0].equals(res[i][1]) && res[i][0].equals(res[i][2]) && !res[i][0].equals(" ") ) {
                return true;
            }
        }

        // teste les colonnes

        for (int j=0; j<3; j++) {
            if ( res[0][j].equals(res[1][j]) && res[0][j].equals(res[2][j]) && !res[0][j].equals(" ") ) {
                return true;
            }
        }

        // teste les diagonales

        if ( res[0][0].equals(res[1][1]) && res[0][0].equals(res[2][2]) && !res[0][0].equals(" ") ) {
            return true;
        }

        if ( res[0][2].equals(res[1][1]) && res[0][2].equals(res[2][0]) && !res[0][2].equals(" ") ) {
            return true;
        }

        return false;

    }

    // Affichage du vainqueur et retour au menu principal

    //cas de victoire du joueur
    void player1Win(){
        Toast.makeText(this, "Vous remportez la partie !", Toast.LENGTH_SHORT).show();
        //Patiente 1,5s avant de retourner
        new CountDownTimer(1500, 10) {
            public void onTick(long tick){}
            public void onFinish() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }.start();
    }

    //cas de victoire de l'ordinateur
    void player2Win(){
        Toast.makeText(this, "Vous perdez la partie !", Toast.LENGTH_SHORT).show();
        new CountDownTimer(1500, 10) {
            public void onTick(long tick){}
            public void onFinish() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }.start();
    }

    //cas d'égalité
    void egalite(){
        Toast.makeText(this, "Egalité !", Toast.LENGTH_SHORT).show();
        new CountDownTimer(1500, 10) {
            public void onTick(long tick){}
            public void onFinish() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }.start();
    }
}