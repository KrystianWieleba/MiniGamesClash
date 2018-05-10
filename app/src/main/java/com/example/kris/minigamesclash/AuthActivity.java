/*

package com.example.kris.minigamesclash;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AuthActivity extends AppCompatActivity {


    EditText editEmail ;
    EditText editPassword ;
    Button boutonInscription ;
    EditText editEmailb ;
    EditText editPasswordb ;
    Button boutonConnexion ;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);


        editEmail = (EditText)findViewById(R.id.editEmail);
        editPassword = (EditText)findViewById(R.id.editPassword);
        boutonInscription = (Button)findViewById(R.id.boutonInscription);
        editEmailb = (EditText)findViewById(R.id.editEmailb);
        editPasswordb = (EditText)findViewById(R.id.editPasswordb);
        boutonConnexion = (Button)findViewById(R.id.boutonConnexion);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        boutonInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(AuthActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    //message d'erreur
                                    Toast.makeText(AuthActivity.this,"Echec de l'inscription",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

        boutonConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmailb.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(AuthActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                                    Toast.makeText(AuthActivity.this,"Echec de l'authentification",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });


    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}




myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String value = dataSnapshot.child("id0").getValue(String.class);
                //scoreTable.setText(value);
                //myRef.child("score").setValue(value);


                int[] temp = new int[5];

                //J'ai essayer de faire un genre de tri, mais puisque les childs garde leurs valeurs ça ne marche pas..
                for (int i =0; i<=2; i++) {


                    for (DataSnapshot childs : dataSnapshot.getChildren()) {
                        String list = childs.getValue().toString();
                        int res = Integer.parseInt(list);

                        if (res > temp[i]) {
                            temp[i] = res;


                        }

                    }



                    fistPlace.setText("1 : " + temp[0]);
                    secondPlace.setText("2 : " + temp[1]);
                    thirdPlace.setText("3 : " + temp[2]);
                    //fourthPlace.setText("4 : " + temp[3]);
                    //fifthPlace.setText("5 : " + temp[4]);

                    //myRef.child("id"+temp[0]).setValue(00);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });






*/

