package com.abia.login_registerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {
   // ActivitySignupBinding binding;
    private FirebaseAuth mAuth;
   private FirebaseFirestore db;
   private String TAG = "RegisterActivity";
   private TextView tvsignin;
   private EditText etEmail, etPassword, etfname, etlname, etphone;
   private  String email, signin,  password, fname, lname, phone;
   private Button btnRegister;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
     //   binding = DataBindingUtil.setcontentview(this,R.layout.activity_register )





        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etemailadd);
        etPassword = findViewById(R.id.etpw);
        tvsignin = findViewById(R.id.tvsignin);
        etfname = findViewById(R.id.etfname);
        etlname = findViewById(R.id.etlname);
        etphone = findViewById(R.id.etphonenum);
        btnRegister = findViewById(R.id.btnregister);
        signin = "Sign in";
        dialog = new ProgressDialog(RegisterActivity.this);
      //  dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fname = etfname.getText().toString().trim();
                lname = etlname.getText().toString().trim();
                phone = etphone.getText().toString().trim();

                email = etEmail.getText().toString().trim();
                password = etPassword.getText().toString().trim();

               // String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference myRef = database.getReference("message");
//                myRef.setValue(fname);
//                Log.d("First name", fname);
//                myRef.setValue(lname);
//                Log.d("Last name", lname.toString());
//                myRef.setValue(phone);
//                Log.d("Phone", phone.toString());

               // if (!email.matches(emailPattern) || TextUtils.isEmpty(email))
                if (TextUtils.isEmpty(email))
                {

                    etEmail.setError("Email cannot be empty");
                  //  return;
                }
                else if (TextUtils.isEmpty(password) || password.length() < 5) {

                    etPassword.setError("Password cannot be empty");
                }
                    else {
                        createAccount();
                }
            }
        });

        tvsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                //startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });


    }

    private void createAccount() {
        dialog.setMessage("Processing...");
        dialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {

                            Map<String, Object> newContact = new HashMap<>();
                            newContact.put("fname", fname);
                            newContact.put("lname", lname);
                            newContact.put("phone", phone);
                            db.collection("UserDetails").document("Details").set(newContact);
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("CHECK1", "createUserWithEmail:success");
                            Log.d("tag", "createUserWithEmail:success");
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Data added successfully", Toast.LENGTH_LONG).show();
                          //tvsignin.setText("Sign in");
                            FirebaseUser user = mAuth.getCurrentUser();
                            tvsignin.setText(signin);
                          //  updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            dialog.dismiss();
                            Log.d("CHECK1", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                    //        updateUI(null);
                        }

                        // ...
                    }
                });
//
//        Map<String, Object> newContact = new HashMap<>();
//        newContact.put("fname", fname);
//        newContact.put("lname", lname);
//        newContact.put("phone", phone);
//        db.collection("UserDetails").document("Details").set(newContact)
               // .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(RegisterActivity.this, "User Registered",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(RegisterActivity.this, "ERROR" +e.toString(),
//                                Toast.LENGTH_SHORT).show();
//                        Log.d("TAG", e.toString());
//                    }
//                });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
      Log.d("tag", ""+currentUser);
    //    updateUI(currentUser);
    }



}


