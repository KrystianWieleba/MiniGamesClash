package com.example.christos.jeucarres;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    int res[][] = new int[7][7];
    ArrayList<Button> buttons = new ArrayList<>();
    int player = 1; // quel joueur joue ?
    int bleu = 0;   //score du joueur 1
    int rouge = 0;  // score du joueur 2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button but01 = (Button) findViewById(R.id.button01);
        Button but03 = (Button) findViewById(R.id.button03);
        Button but05 = (Button) findViewById(R.id.button05);
        Button but10 = (Button) findViewById(R.id.button10);
        Button but12 = (Button) findViewById(R.id.button12);
        Button but14 = (Button) findViewById(R.id.button14);
        Button but16 = (Button) findViewById(R.id.button16);
        Button but21 = (Button) findViewById(R.id.button21);
        Button but23 = (Button) findViewById(R.id.button23);
        Button but25 = (Button) findViewById(R.id.button25);
        Button but30 = (Button) findViewById(R.id.button30);
        Button but32 = (Button) findViewById(R.id.button32);
        Button but34 = (Button) findViewById(R.id.button34);
        Button but36 = (Button) findViewById(R.id.button36);
        Button but41 = (Button) findViewById(R.id.button41);
        Button but43 = (Button) findViewById(R.id.button43);
        Button but45 = (Button) findViewById(R.id.button45);
        Button but50 = (Button) findViewById(R.id.button50);
        Button but52 = (Button) findViewById(R.id.button52);
        Button but54 = (Button) findViewById(R.id.button54);
        Button but56 = (Button) findViewById(R.id.button56);
        Button but61 = (Button) findViewById(R.id.button61);
        Button but63 = (Button) findViewById(R.id.button63);
        Button but65 = (Button) findViewById(R.id.button65);

        /*
        Button butA = (Button) findViewById(R.id.buttonA);
        Button butB = (Button) findViewById(R.id.buttonB);
        Button butC = (Button) findViewById(R.id.buttonC);
        Button butD = (Button) findViewById(R.id.buttonD);
        Button butE = (Button) findViewById(R.id.buttonE);
        Button butF = (Button) findViewById(R.id.buttonF);
        Button butG = (Button) findViewById(R.id.buttonG);
        Button butH = (Button) findViewById(R.id.buttonH);
        Button butI = (Button) findViewById(R.id.buttonI);
        */

        buttons.add(but01);
        buttons.add(but03);
        buttons.add(but05);
        buttons.add(but10);
        buttons.add(but12);
        buttons.add(but14);
        buttons.add(but16);
        buttons.add(but21);
        buttons.add(but23);
        buttons.add(but25);
        buttons.add(but30);
        buttons.add(but32);
        buttons.add(but34);
        buttons.add(but36);
        buttons.add(but41);
        buttons.add(but43);
        buttons.add(but45);
        buttons.add(but50);
        buttons.add(but52);
        buttons.add(but54);
        buttons.add(but56);
        buttons.add(but61);
        buttons.add(but63);
        buttons.add(but65);

        for (Button but : buttons) {
            but.setBackgroundDrawable(null);  // Background des boutons à null
            but.setOnClickListener(this);
        }


    }

    @Override
    public void onClick(View v) {

        // Vérification que la case n'a pas été jouée

        if (v.getBackground() != null) {
            return;
        }

        // Prise en compte dans le tableau des cases jouées

        switch(v.getId()) {
            case R.id.button01:
                res[0][1] = player;
                break;
            case R.id.button03:
                res[0][3] = player;
                break;
            case R.id.button05:
                res[0][5] = player;
                break;
            case R.id.button10:
                res[1][0] = player;
                break;
            case R.id.button12:
                res[1][2] = player;
                break;
            case R.id.button14:
                res[1][4] = player;
                break;
            case R.id.button16:
                res[1][6] = player;
                break;
            case R.id.button21:
                res[2][1] = player;
                break;
            case R.id.button23:
                res[2][3] = player;
                break;
            case R.id.button25:
                res[2][5] = player;
                break;
            case R.id.button30:
                res[3][0] = player;
                break;
            case R.id.button32:
                res[3][2] = player;
                break;
            case R.id.button34:
                res[3][4] = player;
                break;
            case R.id.button36:
                res[3][6] = player;
                break;
            case R.id.button41:
                res[4][1] = player;
                break;
            case R.id.button43:
                res[4][3] = player;
                break;
            case R.id.button45:
                res[4][5] = player;
                break;
            case R.id.button50:
                res[5][0] = player;
                break;
            case R.id.button52:
                res[5][2] = player;
                break;
            case R.id.button54:
                res[5][4] = player;
                break;
            case R.id.button56:
                res[5][6] = player;
                break;
            case R.id.button61:
                res[6][1] = player;
                break;
            case R.id.button63:
                res[6][3] = player;
                break;
            case R.id.button65:
                res[6][5] = player;
                break;
        }

        // Matérialisation de la case jouée par un changement de couleur

        Drawable playercolor;
        if (player ==1) {
            playercolor = ContextCompat.getDrawable(this,R.drawable.carrebleu);
        } else {
            playercolor = ContextCompat.getDrawable(this,R.drawable.carrerouge);
        }

        v.setBackgroundDrawable(playercolor);

        // Conditions pour obtenir un point (1point= 1 carré complété)

        if (res[0][1] != 0 && res[1][0] != 0 && res[1][2] != 0 && res[2][1] != 0 && res[1][1] == 0) {
            res[1][1] = player;
            if (player == 1) {
                bleu++;
            } else {
                rouge++;
            }
        }

        if (res[0][3] != 0 && res[1][2] != 0 && res[1][4] != 0 && res[2][3] != 0 && res[1][3] == 0) {
            res[1][3] = player;
            if (player == 1) {
                bleu++;
            } else {
                rouge++;
            }
        }

        if (res[0][5] != 0 && res[1][4] != 0 && res[1][6] != 0 && res[2][5] != 0 && res[1][5] == 0) {
            res[1][5] = player;
            if (player == 1) {
                bleu++;
            } else {
                rouge++;
            }
        }

        if (res[2][1] != 0 && res[3][0] != 0 && res[3][2] != 0 && res[4][1] != 0 && res[3][1] == 0) {
            res[3][1] = player;
            if (player == 1) {
                bleu++;
            } else {
                rouge++;
            }
        }

        if (res[2][3] != 0 && res[3][2] != 0 && res[3][4] != 0 && res[4][3] != 0 && res[3][3] == 0) {
            res[3][3] = player;
            if (player == 1) {
                bleu++;
            } else {
                rouge++;
            }
        }

        if (res[2][5] != 0 && res[3][4] != 0 && res[3][6] != 0 && res[4][5] != 0 && res[3][5] == 0) {
            res[3][5] = player;
            if (player == 1) {
                bleu++;
            } else {
                rouge++;
            }
        }

        if (res[4][1] != 0 && res[5][0] != 0 && res[5][2] != 0 && res[6][1] != 0 && res[5][1] == 0) {
            res[5][1] = player;
            if (player == 1) {
                bleu++;
            } else {
                rouge++;
            }
        }

        if (res[4][3] != 0 && res[5][2] != 0 && res[5][4] != 0 && res[6][3] != 0 && res[5][3] == 0) {
            res[5][3] = player;
            if (player == 1) {
                bleu++;
            } else {
                rouge++;
            }
        }

        if (res[4][5] != 0 && res[5][4] != 0 && res[5][6] != 0 && res[6][5] != 0 && res[5][5] == 0) {
            res[5][5] = player;
            if (player == 1) {
                bleu++;
            } else {
                rouge++;
            }
        }

        // Conditions de fin du jeu (tous les carrés ont été entourés) et de victoire

        if (bleu + rouge == 9) {
            if (bleu > rouge) {
                player1Win();
            } else {
                player2Win();
            }
        }

        // Si le jeu n'est pas fini, changement de joueur

        if (player == 1) {
            player = 2;
        } else {
            player = 1;
        }

    }

    // Transition vers les autres activités
    // A changer pour uniformisation

    void player1Win(){
        Toast.makeText(this, "Player 1 wins !", Toast.LENGTH_SHORT).show();
    }

    void player2Win(){
        Toast.makeText(this, "Player 2 wins !", Toast.LENGTH_SHORT).show();
    }

}
