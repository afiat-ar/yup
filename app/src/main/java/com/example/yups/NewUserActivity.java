package com.example.yups;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;

import objects.Users;

public class NewUserActivity extends AppCompatActivity {

    private ImageButton btn_exit;
    private Button btn_create;
    private EditText et_email;
    private EditText et_pwd;
    private EditText et_pwd2;
    private EditText et_username;
    private EditText et_name;
    private EditText et_apellidos;
    private EditText et_tlf;

    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;

    public NewUserActivity(){}

    public NewUserActivity(FirebaseFirestore db) {
        this.db = db;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        btn_create = (Button)findViewById(R.id.btn_create_new);
        btn_exit = (ImageButton)findViewById(R.id.btn_exit);

        et_email = (EditText)findViewById(R.id.et_email);
        et_pwd = (EditText)findViewById(R.id.et_pwd);
        et_pwd2 = (EditText)findViewById(R.id.et_pwd2);
        et_username = (EditText)findViewById(R.id.et_username);
        et_name = (EditText)findViewById(R.id.et_name);
        et_apellidos = (EditText)findViewById(R.id.et_apellidos);
        et_tlf = (EditText)findViewById(R.id.et_tlf);

        initGoogleClient();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initGoogleClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void createUser() {

        if (et_email.getText().length() > 0 && et_pwd.getText().length() > 0
            && et_pwd2.getText().length() > 0 && et_username.getText().length() > 0
            && et_name.getText().length() > 0 && et_apellidos.getText().length() > 0
            && et_tlf.getText().length() > 0){

            if (et_pwd.getText().toString().equalsIgnoreCase(et_pwd2.getText().toString())){
                mAuth.createUserWithEmailAndPassword(et_email.getText().toString(), et_pwd.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Cuenta creada",Toast.LENGTH_LONG).show();
                                    Map<String, Object> object = new HashMap<>();
                                    object.put("name", et_name.getText().toString());
                                    object.put("last_name", et_apellidos.getText().toString());
                                    object.put("username", et_username.getText().toString());
                                    object.put("email", et_email.getText().toString());
                                    object.put("phone", et_tlf.getText().toString());

                                    user = FirebaseAuth.getInstance().getCurrentUser();

                                    db.collection("users").document(user.getEmail().toString())
                                            .set(object)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(getApplicationContext(), "Datos añadidos con exito",Toast.LENGTH_LONG).show();
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getApplicationContext(), "Ha ocurrido un error al añadir tus datos",Toast.LENGTH_LONG).show();
                                                }
                                            });
                                }else{
                                    Toast.makeText(getApplicationContext(), "Error al crear usuario",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }else{
                et_pwd.setError("Password does not match");
            }
        }else{
            //error
        }

    }
}
