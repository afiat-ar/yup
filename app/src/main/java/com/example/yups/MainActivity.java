 package com.example.yups;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

 public class MainActivity extends AppCompatActivity {

    private EditText et_email;
    private EditText et_pwd;
    private Button btn_login;
    private Button btn_create;

     private FirebaseAuth mAuth;
     private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }

        et_email = (EditText)findViewById(R.id.et_email);
        et_pwd = (EditText)findViewById(R.id.et_pwd);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_create = (Button)findViewById(R.id.btn_create);

        initGoogleClient();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_new_user();
                et_email.setText("");
                et_pwd.setText("");
            }
        });

    }

     private void login() {
         if(et_pwd.getText().length() > 0 && et_email.getText().length() > 0){
             mAuth.signInWithEmailAndPassword(et_email.getText().toString(), et_pwd.getText().toString())
                     .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if(task.isSuccessful()){
                                 launch_menu();
                                 Toast.makeText(getApplicationContext(), "Conectado",Toast.LENGTH_LONG).show();
                             }else{
                                 Toast.makeText(getApplicationContext(), "Upssss Tonto",Toast.LENGTH_LONG).show();
                             }
                         }
                     });
         }else{
             if(et_email.getText().length() == 0){
                 et_email.setError("Debes introducir un email");
             }
             if(et_pwd.getText().length() == 0){
                 et_pwd.setError("Debes introducir una contraseña");
             }
         }
     }

     private void initGoogleClient() {
         GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                 .requestIdToken(getString(R.string.default_web_client_id))
                 .requestEmail()
                 .build();

         mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
     }

    private void create_new_user() {
        Intent intent = new Intent(getApplicationContext(), NewUserActivity.class);
        startActivity(intent);
    }
     private void launch_menu() {
         Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
         startActivity(intent);
     }
 }
