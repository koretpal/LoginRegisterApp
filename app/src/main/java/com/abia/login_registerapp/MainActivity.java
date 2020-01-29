package com.abia.login_registerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private TextView tvcreateacct;
   private EditText etemail, etpassword;
   private Button btnlogin;
   private String email, password;
   private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etemail = findViewById(R.id.etemail1);
        etpassword = findViewById(R.id.etpassword1);
        btnlogin = findViewById(R.id.btnlogin);
        tvcreateacct = findViewById(R.id.tvcreateacct);

        dialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = etemail.getText().toString().trim();
                password = etpassword.getText().toString().trim();
              //  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//
//                //!email.matches(emailPattern) ||
                if (email.isEmpty()) {
                    etemail.setError("Invalid email, Please enter a valid email address");

                }

                    else if (password.isEmpty() || password.length() < 5){
                    etpassword.setError("Invalid password, Please enter a valid password");
                }

                else {

                    proceed();
                }

            }
        });

        tvcreateacct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
    }

    private void proceed() {
        dialog.setMessage("Please wait");
        dialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("info ", "signInWithEmail:success");
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                           // etemail.getText().clear();
                            //etpassword.getText().clear();
                            user = mAuth.getCurrentUser();
//                            updateUI(user);
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                // Name, email address, and profile photo Url
//                                String name = user.getDisplayName();
                                String email = user.getEmail();
                              //  Uri photoUrl = user.getPhotoUrl();

//                              Log.d("info", "name: "+name);
                                Log.d("info", "email: "+email);

                                // Check if user's email is verified
                                boolean emailVerified = user.isEmailVerified();

                                // The user's ID, unique to the Firebase project. Do NOT use this value to
                                // authenticate with your backend server, if you have one. Use
                                // FirebaseUser.getIdToken() instead.
                              //  String uid = user.getUid();
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("info", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
