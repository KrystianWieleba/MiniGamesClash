package com.example.kris.minigamesclash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import static android.view.View.GONE;

public class ArcheDeNoe extends AppCompatActivity {

    private ImageView elephant;
    private ImageView arche;
    private ImageView lion;
    private ImageView lapin;
    private ImageView mer;
    private FrameLayout layout;
    List<ImageView> animauxArche;
    public ImageView animal;
    private int nbAnimaux = 0;
    private int posArche;
    private int coule;
    private DisplayMetrics metrics;
    private float hauteurEcran;
    private float densiteEcran;
    private float hauteurArche;

    private OnClickListener clickListenerAnimaux = new OnClickListener() {
        @Override
        public void onClick(View v) {
            animal=new ImageView(ArcheDeNoe.this);
            switch (v.getId()){
                case R.id.elephant:
                    coule=24+(int)(5*Math.random());
                    layout.addView(animal,150,150);
                    animal.setImageResource(R.drawable.elephantdebout);
                    animal.setY(posArche+105*densiteEcran);
                    break;
                case R.id.lion:
                    coule=15+(int)(3*Math.random());
                    layout.addView(animal,120,120);
                    animal.setImageResource(R.drawable.lion);
                    animal.setY(posArche+115*densiteEcran);
                    //animauxArche[nbAnimaux].setImageResource(R.drawable.lion);
                    break;
                case R.id.lapin:
                    coule=3+(int)(2*Math.random());
                    layout.addView(animal,65,65);
                    animal.setImageResource(R.drawable.lapin);
                    animal.setY(posArche+125*densiteEcran);
                    //animauxArche[nbAnimaux].setImageResource(R.drawable.lapin);
                    break;
            }
            animal.setX((float)(Math.random()*240+170)*densiteEcran);
            arche.bringToFront();
            mer.bringToFront();
            nbAnimaux+=1;
            posArche+=coule*(densiteEcran/2); //le divise par 2 vient de ce que j'ai fait mes mesures avec ma densite d'ecran de 2
            arche.setY(posArche);
            for(ImageView anim : animauxArche){
                anim.setY(anim.getY()+coule);
            }
            animauxArche.add(animal);
            //L'arche coule lorsque une certaine partie de l'image est submergée (49pxls au-dessus de la moitié)
            //Un schéma aide... hauteurArche=(478/1080)*(350*dens)
            //peut être utiliser OnMeasure
            //Bon pour l'instant j'ai bidouillé avec un terme en plus pour que ça marche...
            if ((hauteurEcran-posArche-154.9/2*densiteEcran+49*((float)350/(float)1080)*densiteEcran-114.5*densiteEcran)<80*densiteEcran){
                arche.setVisibility(GONE);
                elephant.setVisibility(GONE);
                lion.setVisibility(GONE);
                lapin.setVisibility(GONE);
                for (ImageView anim : animauxArche) {
                    anim.setVisibility(GONE);
                }
                //Message de fin et retour...
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arche_de_noe);

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

        animauxArche = new ArrayList<ImageView>();

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        hauteurEcran=metrics.heightPixels;
        densiteEcran=metrics.density;
        posArche=(int)(hauteurEcran-(175+130)*densiteEcran);//175<->moitié de la hauteur de l'image;130<->surface de l'eau+marge
        arche.setY(posArche);
    }
}


