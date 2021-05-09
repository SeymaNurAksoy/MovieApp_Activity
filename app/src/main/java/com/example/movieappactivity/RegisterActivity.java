package com.example.movieappactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText registerFullName,registerEmail,registerPassword,registerConfPass;
    Button registerUserBtn,gotoLogin;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerFullName = findViewById(R.id.registerFullName);
        registerEmail  = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerConfPass = findViewById(R.id.confPassword);
        registerUserBtn = findViewById(R.id.registerBtn);
        gotoLogin = findViewById(R.id.gotoLogin);

        fAuth = FirebaseAuth.getInstance();

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });

        registerUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //extra the data from the from

                String FullName = registerFullName.getText().toString();
                String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();
                String confPass = registerConfPass.getText().toString();

                if(FullName.isEmpty()){
                    registerFullName.setError("Tam Adınızı Giriniz");
                    return;
                }
                if(email.isEmpty()){
                    registerEmail.setError("Mail adresinizi Giriniz");
                    return;
                }
                if(password.isEmpty()){
                    registerPassword.setError("Şifre Giriniz");
                    return;
                }
                if(confPass.isEmpty()){
                    registerConfPass.setError("Şifreyi Yeniden Giriniz");
                    return;
                }
                if(!password.equals(confPass)){
                    registerConfPass.setError("Şifreniz eşleşmedi");
                    return;
                }

                //data is validated
                //register the user using firebase

                Toast.makeText( RegisterActivity.this," Kayıt Onaylandı", Toast.LENGTH_SHORT).show();

                fAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //send user to next page
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        }
}