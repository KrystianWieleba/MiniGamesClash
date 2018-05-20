// Construction d'un jeu sur Android

package com.tsp.youssef.snake;

// Importation des packages

import android.app.Activity;
import android.os.Bundle;

//
import android.view.Display;
import android.graphics.Point;

// Packages pour
import android.view.SurfaceView;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.view.MotionEvent;



import java.io.IOException;
import java.util.Random;
import android.content.res.AssetManager;
// Packages pour le dessin graphique
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
// Packages pour le son
import android.media.AudioManager;
import android.media.SoundPool;

// Declaration d'une activite (Permet d'interagir avec l'utilisateur)
public class SnakeActivity extends Activity {

    classSnake classSnake;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Obtention des dimensions de l'ecran
        Display ecran = getWindowManager().getDefaultDisplay();
        // Initialiser les valeurs de display en un objet Point
        Point size = new Point(); // Coordonnees cartesiennes en (x,y)
        ecran.getSize(size); // Transfere les valeurs de display a l'objet Point
        // Declarer une nouvelle instance de la classe SnakeEngine (Lancer le jeu)
        classSnake = new classSnake(this,size);
        //Faire que le jeu soit lanc\'e dans l'activite
        setContentView(classSnake);
    }

    // Demarrer un thread sur snakeEngine (Continuer) Capturer l'interaction avec l'utilisateur
    @Override
    protected void onResume() {
        super.onResume();
        classSnake.resume();
    }
    protected void onPause() {
        super.onPause();
        classSnake.pause();
    }

}

// Extension a SurfaceView fera que l'appel de setContentView de onCreate marchera
// L'implementation a pour role de faire passer snakeEngine au constructeur de thread pour creer une instance de thread

class classSnake extends SurfaceView implements Runnable {
    // Initialisation d'un thread
    private Thread thread = null;
    private Context context;

    // Pour faire des effets de son
    private SoundPool soundPool;
    private int mange_pomme= -1;
    private int snake_mort= -1;

    public enum Oriente_tete {HAUT,BAS,GAUCHE,DROITE}; // Orientations de la tete du Snake

    // Variables de definition
    private Oriente_tete oriente_tete=Oriente_tete.GAUCHE;
    private int ecranX; // extraire l'abscisse de l'objet Point
    private int ecranY; // extraire l'ordonnee de l'objet Point
    private int snakeLongueur; // Pour obtenir la longueur du serpent
    private int pommeX; // l'abscisse de l'objet pomme
    private int pommeY; // l'ordonnee de l'objet pomme
    private int tailleBloc; // La taille en pixel d'un segment du serpent
    private final int NUM_BLOCS_LARGE= 40; // La taille en segments de l'aire jouable
    private int numBlocsHauteur;
    private int[] snakeXs;
    private int[] snakeYs;
    private int score;

    //Variables de controle
    private long TempsProchaineFrame; // Controler la pause entre les updates
    private final long FPS=10;
    private final long MILLIS_PAR_SECONDE=1000;


    // Booleeen jeu en marche ou en arret
    private volatile boolean isPlaying; // La variable sera modifiee par plusieurs threads, elle sera placee dans la memoire en commun aux threads

    //Variables de dessin
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Paint paint;

    public classSnake(Context context,Point size) {
        super(context);
        context = context;

        ecranX = size.x; // Extraire la valeur de size
        ecranY = size.y; // Extraire la valeur de size
        tailleBloc = ecranX / NUM_BLOCS_LARGE; // Determiner le nombre de blocks en largeur
        numBlocsHauteur = ecranY / tailleBloc; // Determiner le nombre de blocks en hauteur

        snakeXs = new int[50];
        snakeYs = new int[50];

        // Mettre le son : Partie de code importee (Je n'en suis pas l'auteur)

            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
                 try {
                  // Create objects of the 2 required classes
                  // Use m_Context because this is a reference to the Activity
                  AssetManager assetManager = context.getAssets();
                  AssetFileDescriptor descriptor;

                  // Prepare the two sounds in memory

                     descriptor = assetManager.openFd("eat_bob.mp3");
                     mange_pomme = soundPool.load(descriptor, 0);

                     descriptor = assetManager.openFd("snake_crash.mp3");
                     snake_mort = soundPool.load(descriptor, 0);

        } catch (IOException e) {
            // Error

        }

        // Initialisation des objets de dessin
        surfaceHolder = getHolder();
        paint = new Paint();

        nouvellePartie(); // Demarrer le jeu
    }

    // Gestion des differents etats du jeu (Je n'en suis pas l'auteur)
    @Override
    public void run() {

        while (isPlaying) {
            if(updateRequired()) {
                update();
                dessine();
            }
        }
    }

    public void pause() {
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }
    // Fin

    // Initialisation d'une nouvelle partie

    public void nouvellePartie() {
        score = 0; // Mettre le score a zero

        // Initialisation d'un nouveau serpent
        snakeLongueur=2;
        snakeXs[0]= NUM_BLOCS_LARGE / 2;
        snakeYs[0]= numBlocsHauteur / 2;

        // Initialisation d'un nouvel oeuf
        positionePomme();

        // Initialiser le changement de trames (Pour gerer le raffraichissemnt)
        TempsProchaineFrame = System.currentTimeMillis();
    }


    public void positionePomme() {

        Random random = new Random();
        pommeX=random.nextInt(NUM_BLOCS_LARGE -1) +1;
        pommeY=random.nextInt(numBlocsHauteur -1) +1;
    }

    public void mangePomme() {
        score= score + 1; // J'ajoute un point au score
        snakeLongueur++; // J'ajoute une longueur au serpent
        positionePomme(); // Je rescuscite la pomme
        soundPool.play(mange_pomme,1,1,0,0,1); // Lancer le son

    }

    private void mouvementSnake() {
        // Faire bouger le corps du serpent sans prendre en compte sa tete
        for (int i = snakeLongueur; i>0; i--) {
            snakeXs[i] = snakeXs[i - 1];
            snakeYs[i] = snakeYs[i - 1]; // Donner a l'emplacement i l'emplacement de i-1
        }
        // Faire bouger la tete du serpent
        switch (oriente_tete) {
            case HAUT:
                snakeYs[0]--;
                break;

            case BAS:
                snakeYs[0]++;
                break;

            case GAUCHE:
                snakeXs[0]--;
                break;

            case DROITE:
                snakeXs[0]++;
                break;
        }

    }

    private boolean detecteMort() {
        boolean mort = false;

        if (snakeXs[0] == -1) mort = true;
        if (snakeXs[0] >= NUM_BLOCS_LARGE) mort = true;
        if (snakeYs[0] == -1) mort = true;
        if (snakeYs[0] == numBlocsHauteur) mort = true;

        // Suicide du serpent
        for (int i = snakeLongueur - 1; i > 0; i--) {
            if ((i > 4) && (snakeXs[0] == snakeXs[i]) && (snakeYs[0] == snakeYs[i])) {
                mort = true;
            }
        }
        return mort;

    }

    // Je declare update : 3 cas sont envisages (Soit le serpent meurt / Soit il continue / Soit il mange la pomme)

    public void update(){
        if ( snakeXs[0]==pommeX && snakeYs[0]==pommeY) {
            mangePomme();
        }
        mouvementSnake();
        if(detecteMort()){
            // On reprend le jeu
            soundPool.play(snake_mort,1,1,0,0,1);
            nouvellePartie();
        }

    }

    public void dessine() {
        // La methode ci dessous est tiree du tutoriel d'Android Studio
        if (surfaceHolder.getSurface().isValid()) {

            canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.argb(255, 26, 128, 182)); // Dessiner l'arriere plan en bleu

            paint.setColor(Color.argb(255, 255, 255, 255)); // Choisir le serpent en blanc


            paint.setTextSize(50); // Taille du texte
            canvas.drawText("Score:" + score, 10, 70, paint); // Affichage du score

            for (int i = 0; i < snakeLongueur; i++) {
                canvas.drawRect(snakeXs[i] * tailleBloc,
                        (snakeYs[i] * tailleBloc),
                        (snakeXs[i] * tailleBloc) + tailleBloc,
                        (snakeYs[i] * tailleBloc) + tailleBloc,
                        paint); // Methode drawRect pour dessiner un rectangle
            }

            paint.setColor(Color.argb(255, 255, 0, 0)); // Couleur de l'oeuf en rouge

            canvas.drawRect(pommeX * tailleBloc,
                    (pommeY * tailleBloc),
                    (pommeX * tailleBloc) + tailleBloc,
                    (pommeY * tailleBloc) + tailleBloc,
                    paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }


    // Partie Dynamique

    // Methode pour acquerir l'interaction de l'utilisateur

    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (motionEvent.getX() >= ecranX / 2) {
                    switch(oriente_tete){
                        case HAUT:
                            oriente_tete = Oriente_tete.DROITE;
                            break;
                        case DROITE:
                            oriente_tete = Oriente_tete.BAS;
                            break;
                        case BAS:
                            oriente_tete = Oriente_tete.GAUCHE;
                            break;
                        case GAUCHE:
                            oriente_tete = Oriente_tete.HAUT;
                            break;
                    }
                } else {
                    switch(oriente_tete){
                        case HAUT:
                            oriente_tete = Oriente_tete.GAUCHE;
                            break;
                        case GAUCHE:
                            oriente_tete = Oriente_tete.BAS;
                            break;
                        case BAS:
                            oriente_tete = Oriente_tete.DROITE;
                            break;
                        case DROITE:
                            oriente_tete = Oriente_tete.HAUT;
                            break;
                    }
                }
        }
        return true;
    }


    public boolean updateRequired() {

        if(TempsProchaineFrame <= System.currentTimeMillis()) {
            TempsProchaineFrame =System.currentTimeMillis() + MILLIS_PAR_SECONDE / FPS;
            return true;
        }

        return false;
    }

}







