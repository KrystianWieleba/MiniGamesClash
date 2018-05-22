package com.example.kris.minigamesclash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import static android.view.View.GONE;

public class ArcheDeNoe extends AppCompatActivity {

    //animaux cliquables à choisir pour poser sur l'arche
    private ImageView elephant;
    private ImageView arche;
    private ImageView lion;
    private ImageView lapin;
    private ImageView mer;
    private FrameLayout layout;
    private Button retour;
    List<ImageView> animauxArche;
    public ImageView animal;
    private int nbAnimaux = 0;
    //position verticale de l'arche et translation relative
    private int posArche;
    private int coule;
    //caracteristiques de l'écran
    private DisplayMetrics metrics;
    private float hauteurEcran;
    private float densiteEcran;
    private float hauteurArche;

    private OnClickListener clickListenerAnimaux = new OnClickListener() {
        @Override
        public void onClick(View v) {
            //charge l'image de l'animal à ajouter sur l'arche
            animal=new ImageView(ArcheDeNoe.this);
            //applique les caractéristiques d'un animal en fonction de la vue choisie
            switch (v.getId()){
                case R.id.elephant:
                    //le terme aléatoire permet à un éventuel connaisseur de ne pas prévoir le déroulement exact
                    coule=24+(int)(5*Math.random());
                    layout.addView(animal,(int)(75*densiteEcran),(int)(75*densiteEcran));
                    animal.setImageResource(R.drawable.elephantdebout);
                    //positionne cet animal dans l'arche selon Y
                    animal.setY(posArche+105*densiteEcran);
                    break;
                case R.id.lion:
                    coule=15+(int)(3*Math.random());
                    layout.addView(animal,(int)(60*densiteEcran),(int)(60*densiteEcran));
                    animal.setImageResource(R.drawable.lion);
                    animal.setY(posArche+115*densiteEcran);
                    break;
                case R.id.lapin:
                    coule=3+(int)(2*Math.random());
                    layout.addView(animal,(int)(32.5*densiteEcran),(int)(32.5*densiteEcran));
                    animal.setImageResource(R.drawable.lapin);
                    animal.setY(posArche+125*densiteEcran);
                    break;
            }
            //positionne cet animal à une position horizontale aléatoire dans l'arche
            animal.setX((float)(Math.random()*240+170)*densiteEcran);
            //ramène en avant plant l'arche et la mer pour que l'animal soit vu dans l'arche
            arche.bringToFront();
            mer.bringToFront();
            nbAnimaux+=1;
            //applique la translation relative correspondante à l'arche (liée au poids de l'animal)
            //dépendemment de la densite de l'ecran du joueur
            posArche+=coule*(densiteEcran/2); //le divise par 2 vient de ce que j'ai fait mes mesures avec ma densite d'ecran de 2
            arche.setY(posArche);
            //applique cette translation aux animaux à l'intérieur
            for(ImageView anim : animauxArche){
                anim.setY(anim.getY()+coule*(densiteEcran/2));
            }
            animauxArche.add(animal);

            //L'arche coule lorsque une certaine partie de l'image est submergée (calculs et essais pour y arriver)
            if ((hauteurEcran-posArche-154.9/2*densiteEcran+49*((float)350/(float)1080)*densiteEcran-114.5*densiteEcran)<80*densiteEcran){
                //fait disparaitre les vues maintenant inutiles
                arche.setVisibility(GONE);
                elephant.setVisibility(GONE);
                lion.setVisibility(GONE);
                lapin.setVisibility(GONE);
                for (ImageView anim : animauxArche) {
                    anim.setVisibility(GONE);
                }
                //Apparition et activation du bouton retour
                retour.setVisibility(View.VISIBLE);
                retour.setOnClickListener(clickListenerRetour);
            }
        }
    };

    private OnClickListener clickListenerRetour = new OnClickListener() {
        @Override
        public void onClick(View view) {
            //code de retour au menu avec intent
            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arche_de_noe);
        //le bouton de retour au menu est d'abord caché
        retour=(Button) findViewById(R.id.retour);
        retour.setVisibility(View.INVISIBLE);

        //obtient les caractéristiques de l'écran
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        hauteurEcran=metrics.heightPixels;
        densiteEcran=metrics.density;

        elephant=(ImageView) findViewById(R.id.elephant);
        lion=(ImageView) findViewById(R.id.lion);
        lapin=(ImageView) findViewById(R.id.lapin);
        arche=(ImageView) findViewById(R.id.arche);
        mer=(ImageView)findViewById(R.id.mer);
        layout=(FrameLayout) findViewById(R.id.layout);
        hauteurArche=arche.getMeasuredHeight();
        elephant.setOnClickListener(clickListenerAnimaux);
        lion.setOnClickListener(clickListenerAnimaux);
        lapin.setOnClickListener(clickListenerAnimaux);

        //chaque animal ajouté au bateau est stocké dans animauxArche
        //ce qui permet d'y appliquer des translations par la suite
        animauxArche = new ArrayList<ImageView>();

        //établit la position initiale de l'arche
        posArche=(int)(hauteurEcran-(175+130)*densiteEcran);//175<->moitié de la hauteur de l'image;130<->surface de l'eau+marge
        arche.setY(posArche);
    }
}


