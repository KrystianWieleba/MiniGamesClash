package com.tsp.youssef.snake;

import android.app.Activity; // Importee nativement
import android.os.Bundle; // Importee nativement
import android.view.Display; // Utilisation de l'objet Display
import android.graphics.Point; //  Utilisation de l'objet Point
import android.view.SurfaceView;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import java.io.IOException;
import java.util.Random;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


// Declaration d'une activite (Permet d'interagir avec l'utilisateur)

public class SnakeActivity extends Activity {

    SnakeEngine snakeEngine;

    // Codage de la methode onCreate qui va servir a obtenir la resolution de l'ecran en utilisant la classe Display et l'objet Point

    // Obtention de la resolution de l'ecran

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtention des dimensions de l'ecran

        Display display = getWindowManager().getDefaultDisplay();

        // Initialiser les valeurs de display en un objet Point

        Point size = new Point();
        display.getSize(size); // Transfere les valeurs de display a l'objet Point

        // Declarer une nouvelle instance de la classe SnakeEngine

        snakeEngine = new SnakeEngine(this,size);

        // Faire de snakeEngine la vue de l'activite

        setContentView(snakeEngine);
    }

    // Demarrer un thread sur snakeEngine (Continuer)

    @Override
    protected void onResume() {
        super.onResume();
        snakeEngine.resume();
    }

    // Pauser un thread sur snakeEngine (Faire pause)
    protected void onPause() {
        super.onPause();
        snakeEngine.pause();
    }




}

// Extension a SurfaceView fera que l'appel de setContentView de onCreate marchera
// L'implementation a pour role de faire passer snakeEngine au constructeur de thread pour creer une instance de thread

class SnakeEngine extends SurfaceView implements Runnable {
   // Declaration des variables utiles

    // Initialisation d'un thread

    private Thread thread = null;

    //

    private Context context;

    // Pour faire des effets de son

    private SoundPool soundPool;
    private int eat_bob= -1;
    private int snake_crash= -1;

    // Pour suivre le mouvement Heading

    public enum Heading {UP,DOWN,LEFT,RIGHT};

    // Commencer par aller a la gauche

    private Heading heading=Heading.LEFT;

    // Pour obtenir la taille de l'ecran

    private int screenX;
    private int screenY;

    // Pour obtenir la longueur du serpent

    private int snakeLength;

    // Emplacement de l'oeuf

    private int bobX;
    private int bobY;

    // La taille en pixel d'un segment du serpent

    private int blockSize;

    // La taille en segments de l'aire jouable

    private final int NUM_BLOCKS_WIDE= 40;
    private int numBlocksHigh;

    // Controler la pause entre les updates

    private long nextFrameTime;

    // Taux de reactualisation

    private final long FPS=10; // Pourquoi utiliser final ??? Pour ne pas modifier la valeur de FPS dans la suite

    // Declarer le nombre de millisecondes par secondes

    private final long MILLIS_PER_SECOND=1000;

    // Score du joueur

    private int score; // Ca serait drole de l'initialiser en 0 et de le mettre en final :D

    //

    private int[] snakeXs;
    private int[] snakeYs;

    // Outils de dessin

    // Booleeen jeu en marche ou en arret

    private volatile boolean isPlaying; // La variable sera modifiee par plusieurs threads, elle sera placee dans la memoire en commun aux threads

    // Utilisation d'un canvas

    private Canvas canvas;

    // Requis pour utiliser un canvas

    private SurfaceHolder surfaceHolder;

    // De la peinture pour notre canvas

    private Paint paint;

    public SnakeEngine(Context context,Point size) {
        // A quoi cela va servir

        super(context); // Initilisation de l'objet context

        context = context; // Passe la valeur donnee en argument

        // A quoi cela va servir

        screenX = size.x;
        screenY = size.y;

        // Determiner le nombre de blocks en largeur

        blockSize = screenX / NUM_BLOCKS_WIDE;

        // Determiner le nombre de blocks en hauteur

        numBlocksHigh = screenY / blockSize;

        // Mettre le son

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        try {
            // Create objects of the 2 required classes
            // Use m_Context because this is a reference to the Activity
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            // Prepare the two sounds in memory
            descriptor = assetManager.openFd("eat_bob.mp3");
            eat_bob = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("snake_crash.mp3");
            snake_crash = soundPool.load(descriptor, 0);

        } catch (IOException e) {
            // Error

        }

        // Initialisation des objets de dessin

        surfaceHolder = getHolder();
        paint = new Paint();

        // Atteindre un score de 100 fera que le jeu se reinitialisera

        snakeXs = new int[100];
        snakeYs = new int[100];

        // Demarrer le jeu

        newGame();
    }



    @Override
    public void run() {

        while (isPlaying) {

            // Update 10 times a second
            if(updateRequired()) {
                update();
                draw();
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

    // Initialisation d'une nouvelle partie

    public void newGame() {

        // Initialisation d'un nouveau serpent

        snakeLength=1;
        snakeXs[0]= NUM_BLOCKS_WIDE / 2;
        snakeYs[0]= numBlocksHigh / 2;

        // Initialisation d'un nouvel oeuf

        spawnBob();

        // Remettre le score a zero

        score = 0;

        // Initialiser le changement de trames

        nextFrameTime = System.currentTimeMillis();
    }


    public void spawnBob() {

        Random random = new Random();
        bobX=random.nextInt(NUM_BLOCKS_WIDE -1) +1;
        bobY=random.nextInt(numBlocksHigh -1) +1;
    }

    public void eatBob() {
        snakeLength++; // J'ajoute une longueur au serpent
        spawnBob(); // Je rescuscite l'oeuf
        score= score + 1; // J'ajoute un point au score (Pourquoi score++ ne marche pas !!!)
        soundPool.play(eat_bob,1,1,0,0,1);

    }

    private void moveSnake() {

        // Faire bouger le corps du serpent sans prendre en compte sa tete

        for (int i = snakeLength; i>0; i--) {
            // Donner a l'emplacement i l'emplacement de i-1
            snakeXs[i] = snakeXs[i - 1];
            snakeYs[i] = snakeYs[i - 1];
        }

        // Faire bouger la tete du serpent

        switch (heading) {
            case UP:
                snakeYs[0]--;
                break;

            case DOWN:
                snakeYs[0]++;
                break;

            case LEFT:
                snakeXs[0]--;
                break;

            case RIGHT:
                snakeXs[0]++;
                break;
        }

    }

    private boolean detectDeath() {
        // Est ce que le serpent est mort ??

        boolean dead = false;

        // Mort en touchant les bords
        if (snakeXs[0] == -1) dead = true;
        if (snakeXs[0] >= NUM_BLOCKS_WIDE) dead = true;
        if (snakeYs[0] == -1) dead = true;
        if (snakeYs[0] == numBlocksHigh) dead = true;

        // Suicide du serpent
        for (int i = snakeLength - 1; i > 0; i--) {
            if ((i > 4) && (snakeXs[0] == snakeXs[i]) && (snakeYs[0] == snakeYs[i])) {
                dead = true;
            }
        }

        return dead;

    }

    public void update() {
        // Est-ce que le serpent a mange l'oeuf ??

        if ( snakeXs[0]==bobX && snakeYs[0]==bobY) {
            eatBob();
        }

        moveSnake();

        if(detectDeath()){
            // On reprend le jeu

            soundPool.play(snake_crash,1,1,0,0,1);

            newGame();
        }

    }

    public void draw() {
        // Get a lock on the canvas
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            // Fill the screen with Game Code School blue
            canvas.drawColor(Color.argb(255, 26, 128, 182)); // Dessiner l'arriere plan en bleu

            // Set the color of the paint to draw the snake white
            paint.setColor(Color.argb(255, 255, 255, 255)); // Choisir le serpent en blanc

            // Scale the HUD text
            paint.setTextSize(90); // Taille du texte
            canvas.drawText("Score:" + score, 10, 70, paint); // Affichage du score

            // Draw the snake one block at a time
            for (int i = 0; i < snakeLength; i++) {
                canvas.drawRect(snakeXs[i] * blockSize,
                        (snakeYs[i] * blockSize),
                        (snakeXs[i] * blockSize) + blockSize,
                        (snakeYs[i] * blockSize) + blockSize,
                        paint); // On determine les quatres cotes du rectangle a dessiner
            }

            // Set the color of the paint to draw Bob red
            paint.setColor(Color.argb(255, 255, 0, 0)); // Couleur de l'oeuf en rouge

            // Draw Bob
            canvas.drawRect(bobX * blockSize,
                    (bobY * blockSize),
                    (bobX * blockSize) + blockSize,
                    (bobY * blockSize) + blockSize,
                    paint); // Dessiner l'oeuf sous forme rectangulaire en donnant les dimensions du bloc

            // Unlock the canvas and reveal the graphics for this frame
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }


    // Methode pour savoir si une mise a jour est requise ou pas

    public boolean updateRequired() {

        // Are we due to update the frame
        if(nextFrameTime <= System.currentTimeMillis()){
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            nextFrameTime =System.currentTimeMillis() + MILLIS_PER_SECOND / FPS;

            // Return true so that the update and draw
            // functions are executed
            return true;
        }

        return false;
    }

    // Methode pour acquerir les mouvements executes par le joueur

    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (motionEvent.getX() >= screenX / 2) {
                    switch(heading){
                        case UP:
                            heading = Heading.RIGHT;
                            break;
                        case RIGHT:
                            heading = Heading.DOWN;
                            break;
                        case DOWN:
                            heading = Heading.LEFT;
                            break;
                        case LEFT:
                            heading = Heading.UP;
                            break;
                    }
                } else {
                    switch(heading){
                        case UP:
                            heading = Heading.LEFT;
                            break;
                        case LEFT:
                            heading = Heading.DOWN;
                            break;
                        case DOWN:
                            heading = Heading.RIGHT;
                            break;
                        case RIGHT:
                            heading = Heading.UP;
                            break;
                    }
                }
        }
        return true;
    }



    }

















