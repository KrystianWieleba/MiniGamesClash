package com.example.kris.minigamesclash;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
import static android.view.View.GONE;

public class ArcheVSActivity extends AppCompatActivity {

    private ImageView elephant;
    private ImageView arche;
    private ImageView lion;
    private ImageView lapin;
    private ImageView mer;
    private TextView winText;
    private FrameLayout layout;
    private Button retour;
    List<ImageView> animauxArche;
    public ImageView animal;
    private int nbAnimaux = 0;
    private float posArche;
    private int coule;
    private String animalCourant;
    private DisplayMetrics metrics;
    private float hauteurEcran;
    private float densiteEcran;
    private float hauteurArche;
    private String nomJ1;
    private String nomJAdv;
    int atoidejouer;
    private int scoreJ1;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Arche");
    DatabaseReference myRef2 = database.getReference("Players");

    private void testcoule(){
        //L'arche coule lorsque une certaine partie de l'image est submergée (49pxls au-dessus de la moitié)
        //Un schéma aide... hauteurArche=(478/1080)*(350*dens)
        //Bon pour l'instant j'ai bidouillé avec un terme en plus pour que ça marche...
        if ((hauteurEcran-posArche-154.9/2*densiteEcran+49*((float)350/(float)1080)*densiteEcran-114.5*densiteEcran)<80*densiteEcran){
            arche.setVisibility(GONE);
            elephant.setVisibility(GONE);
            lion.setVisibility(GONE);
            lapin.setVisibility(GONE);
            for (ImageView anim : animauxArche) {
                anim.setVisibility(GONE);
            }

            winText.setText(nomJ1 + " est le meilleur Noé !");
            myRef.child("win").setValue(1);
            //Message de fin et retour...
            scoreJ1 +=1;
            myRef2.child(nomJ1).setValue(scoreJ1);
            nextActivity();

        }
    }

    private void nextActivity() {
        //Au bout de 8sec lance l'interfce
        new CountDownTimer(8000, 10) {
            public void onTick(long tick){}
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), InterfaceActivity.class);
                // Fait suivre le nom des joueurs
                intent.putExtra("nomJ1", nomJ1);
                intent.putExtra("nomJAdv", nomJAdv);
                startActivity(intent);
            }
        }.start();
    }

    private OnClickListener clickListenerAnimaux = new OnClickListener() {
        @Override
        public void onClick(View v) {
            animal=new ImageView(ArcheVSActivity.this);
            switch (v.getId()){
                case R.id.elephant:
                    myRef.child("animal").setValue("elephant");
                    coule=24+(int)(5*Math.random());
                    layout.addView(animal,(int)(75*densiteEcran),(int)(75*densiteEcran));
                    animal.setImageResource(R.drawable.elephantdebout);
                    animal.setY(posArche+105*densiteEcran);
                    break;
                case R.id.lion:
                    myRef.child("animal").setValue("lion");
                    coule=15+(int)(3*Math.random());
                    layout.addView(animal,(int)(60*densiteEcran),(int)(60*densiteEcran));
                    animal.setImageResource(R.drawable.lion);
                    animal.setY(posArche+115*densiteEcran);
                    //animauxArche[nbAnimaux].setImageResource(R.drawable.lion);
                    break;
                case R.id.lapin:
                    myRef.child("animal").setValue("lapin");
                    coule=3+(int)(2*Math.random());
                    layout.addView(animal,(int)(32.5*densiteEcran),(int)(32.5*densiteEcran));
                    animal.setImageResource(R.drawable.lapin);
                    animal.setY(posArche+125*densiteEcran);
                    //animauxArche[nbAnimaux].setImageResource(R.drawable.lapin);
                    break;
            }
            animal.setX((float)(Math.random()*240+170)*densiteEcran);
            arche.bringToFront();
            mer.bringToFront();
            nbAnimaux+=1;
            posArche+=coule*(densiteEcran/2);
            myRef.child("coule").setValue(coule);
            arche.setY(posArche);
            for(ImageView anim : animauxArche){
                anim.setY(anim.getY()+coule*(densiteEcran/2));
            }
            animauxArche.add(animal);
            testcoule();
        }
    };

    private OnClickListener clickListenerRetour = new OnClickListener() {
        @Override
        public void onClick(View view) {
            //code de retour au menu avec intent
            Intent intent=new Intent(getApplicationContext(), InterfaceActivity.class);
            intent.putExtra("nomJ1",nomJ1);
            intent.putExtra("nomJAdv",nomJAdv);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arche_vs);

        //le bouton de retour au menu est d'abord caché
        retour=(Button) findViewById(R.id.retour);
        retour.setVisibility(View.INVISIBLE);
        retour.setOnClickListener(clickListenerRetour);

        elephant=(ImageView) findViewById(R.id.elephant);
        lion=(ImageView) findViewById(R.id.lion);
        lapin=(ImageView) findViewById(R.id.lapin);
        arche=(ImageView) findViewById(R.id.arche);
        mer=(ImageView)findViewById(R.id.mer);
        winText=(TextView)findViewById(R.id.winText);
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
        posArche=(hauteurEcran-(175+130)*densiteEcran);//175<->moitié de la hauteur de l'image;130<->surface de l'eau+marge
        arche.setY(posArche);

        nomJ1 = getIntent().getStringExtra("nomJ1");
        nomJAdv = getIntent().getStringExtra("nomJAdv");
        atoidejouer = getIntent().getIntExtra("tour", 0);

        // Initialiser les chils
        myRef.child("win").setValue(0);
        myRef.child("animal").setValue("");
        myRef.child("coule").setValue(0);


        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Récupère le score final de chaque joueur pour pouvoir l'incrémenter plus tard
                scoreJ1 = dataSnapshot.child(nomJ1).getValue(int.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        myRef.child("animal").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                animalCourant=dataSnapshot.getValue().toString();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Failed to read value.", databaseError.toException());
            }
        });

        myRef.child("coule").addValueEventListener(new ValueEventListener() {
                                                          @Override
                                                          public void onDataChange(DataSnapshot dataSnapshot) {
                                                              if (atoidejouer==0){
                                                                  miseajournewanimal();
                                                                  coule=Integer.parseInt(dataSnapshot.getValue().toString());
                                                                  posArche=posArche+coule*(densiteEcran/2);
                                                                  arche.setY(posArche);
                                                                  for(ImageView anim : animauxArche){
                                                                      anim.setY(anim.getY()+(coule));
                                                                  }
                                                                  animauxArche.add(animal);
                                                                  elephant.setVisibility(View.VISIBLE);
                                                                  lion.setVisibility(View.VISIBLE);
                                                                  lapin.setVisibility(View.VISIBLE);
                                                                  atoidejouer=1;
                                                                  testcoule();
                                                              }

                                                              else {
                                                                  elephant.setVisibility(View.INVISIBLE);
                                                                  lion.setVisibility(View.INVISIBLE);
                                                                  lapin.setVisibility(View.INVISIBLE);
                                                                  atoidejouer=0;
                                                              }





                                                          }

                                                          @Override
                                                          public void onCancelled(DatabaseError databaseError) {
                                                              Log.w("Failed to read value.", databaseError.toException());
                                                          }
                                                      });

        myRef.child("win").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if ( dataSnapshot.getValue(int.class) == 1 ) {

                    winText.setText(nomJAdv + " est le meilleur Noé !");

                    arche.setVisibility(GONE);
                    elephant.setVisibility(GONE);
                    lion.setVisibility(GONE);
                    lapin.setVisibility(GONE);
                     for (ImageView anim : animauxArche) {
                       anim.setVisibility(GONE);
                     }
                     nextActivity();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void miseajournewanimal(){
        animal=new ImageView(ArcheVSActivity.this);
        switch(animalCourant){
            case "elephant":
                layout.addView(animal,(int)(75*densiteEcran),(int)(75*densiteEcran));
                animal.setImageResource(R.drawable.elephantdebout);
                animal.setY(posArche+105*densiteEcran);
                break;
            case "lion":
                layout.addView(animal,(int)(60*densiteEcran),(int)(60*densiteEcran));
                animal.setImageResource(R.drawable.lion);
                animal.setY(posArche+115*densiteEcran);
                break;
            case "lapin":
                layout.addView(animal,(int)(32.5*densiteEcran),(int)(32.5*densiteEcran));
                animal.setImageResource(R.drawable.lapin);
                animal.setY(posArche+125*densiteEcran);
                break;
        }
        animal.setX((float)(Math.random()*240+170)*densiteEcran);
        arche.bringToFront();
        mer.bringToFront();
        nbAnimaux+=1;
    }
}
