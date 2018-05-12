package com.example.kris.minigamesclash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultVSBubble extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_vsbubble);

        // Instacnier finalScore
        TextView finalScore = (TextView) findViewById(R.id.finalScore);

        // Obtenir le scorefinal et l'afficher
        int score = getIntent().getIntExtra("SCORE", 0);
        finalScore.setText(score + "");
    }

    public void tryAgain(View view) {
        // Relance l'activité du jeu à l'appuie du bouton tryAgain
        startActivity(new Intent(getApplicationContext(), BubbleVSActivity.class));
    }

    public void scoreTable(View view) {


        Intent intent2 = new Intent(getApplicationContext(), ScoreTable.class);
        int score = getIntent().getIntExtra("SCORE", 0);
        intent2.putExtra("SCORE", score);
        startActivity(intent2);


    }

    public void backToMenu(View view) {

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }



}
