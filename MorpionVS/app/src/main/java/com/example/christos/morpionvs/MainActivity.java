package com.example.christos.morpionvs;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String res[][] = new String[3][3];
    ArrayList<Button> buttons = new ArrayList<>();
    boolean player1 = true;
    int turn = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                res[i][j] = " ";
            }
        }

        Button but00 = (Button) findViewById(R.id.button00);
        Button but01 = (Button) findViewById(R.id.button01);
        Button but02 = (Button) findViewById(R.id.button02);
        Button but10 = (Button) findViewById(R.id.button10);
        Button but11 = (Button) findViewById(R.id.button11);
        Button but12 = (Button) findViewById(R.id.button12);
        Button but20 = (Button) findViewById(R.id.button20);
        Button but21 = (Button) findViewById(R.id.button21);
        Button but22 = (Button) findViewById(R.id.button22);

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

        // Vérification que la case est libre

        if (v.getBackground() != null) {
            return;
        }

        // Changements impliqués lorsque le joueur 1 ou le joueur 2 sélectionnent une case
        // Le choix du joueur est pris en compte dans le tableau d'état du jeu
        // Et il apparaît sur l'écran avec le symbole du joueur

        if (player1) {
            if (v.getId() == R.id.button00) {
                res[0][0] = "x";
                v.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.morpioncross));
            } else if (v.getId() == R.id.button01) {
                res[0][1] = "x";
                v.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.morpioncross));
            } else if (v.getId() == R.id.button02) {
                res[0][2] = "x";
                v.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.morpioncross));
            } else if (v.getId() == R.id.button10) {
                res[1][0] = "x";
                v.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.morpioncross));
            } else if (v.getId() == R.id.button11) {
                res[1][1] = "x";
                v.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.morpioncross));
            } else if (v.getId() == R.id.button12) {
                res[1][2] = "x";
                v.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.morpioncross));
            } else if (v.getId() == R.id.button20) {
                res[2][0] = "x";
                v.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.morpioncross));
            } else if (v.getId() == R.id.button21) {
                res[2][1] = "x";
                v.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.morpioncross));
            } else if (v.getId() == R.id.button22) {
                res[2][2] = "x";
                v.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.morpioncross));
            }

        } else {
            if (v.getId() == R.id.button00) {
                res[0][0] = "o";
                v.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.morpionround));
            } else if (v.getId() == R.id.button01) {
                res[0][1] = "o";
                v.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.morpionround));
            } else if (v.getId() == R.id.button02) {
                res[0][2] = "o";
                v.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.morpionround));
            } else if (v.getId() == R.id.button10) {
                res[1][0] = "o";
                v.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.morpionround));
            } else if (v.getId() == R.id.button11) {
                res[1][1] = "o";
                v.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.morpionround));
            } else if (v.getId() == R.id.button12) {
                res[1][2] = "o";
                v.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.morpionround));
            } else if (v.getId() == R.id.button20) {
                res[2][0] = "o";
                v.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.morpionround));
            } else if (v.getId() == R.id.button21) {
                res[2][1] = "o";
                v.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.morpionround));
            } else if (v.getId() == R.id.button22) {
                res[2][2] = "o";
                v.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.morpionround));
            }
        }

        // On incrémente nombre de tour (utilisé pour le cas d'égalité)

        turn++;

        // Conditions de victoire ou d'égalité
        // Si elles ne sont pas vérifiées, on passe au tour suivant et on change de joueur

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

    //Conditions de victoire

    boolean Win() {

        // Ligne

        for (int i=0; i<3; i++) {
            if (res[i][0].equals(res[i][1]) && res[i][0].equals(res[i][2]) && !res[i][0].equals(" ") ) {
                return true;
            }
        }

        // Colonne

        for (int j=0; j<3; j++) {
            if ( res[0][j].equals(res[1][j]) && res[0][j].equals(res[2][j]) && !res[0][j].equals(" ") ) {
                return true;
            }
        }

        // Diagonales

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
