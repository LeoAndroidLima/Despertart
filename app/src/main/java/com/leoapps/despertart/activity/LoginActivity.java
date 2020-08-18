package com.leoapps.despertart.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.irozon.sneaker.Sneaker;
import com.leoapps.despertart.R;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressPie;

public class LoginActivity extends AppCompatActivity {

    private Button button_voltar_Login;
    private EditText editText_Login_DigitarEmail, editText_Login_DigitarSenha;
    private TextView textView_Login_RecuperarSenha;
    private CardView cardView_Login_CLickEntrar, cardView_Login_ClickRegistrar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();



        button_voltar_Login = findViewById(R.id.button_voltar_Login);
        editText_Login_DigitarEmail = findViewById(R.id.editText_Login_DigitarEmail);
        editText_Login_DigitarSenha = findViewById(R.id.editText_Login_DigitarSenha);
        textView_Login_RecuperarSenha = findViewById(R.id.textView_Login_RecuperarSenha);
        cardView_Login_CLickEntrar = findViewById(R.id.cardView_Login_CLickEntrar);
        cardView_Login_ClickRegistrar = findViewById(R.id.cardView_Login_ClickRegistrar);

        //----Clicks----
        //Voltar
        button_voltar_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });

        //Recuperar Senha
        textView_Login_RecuperarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        //Entrar
        cardView_Login_CLickEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonLogin();

            }
        });

        //Registrar
        cardView_Login_ClickRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(),RegistrarActivity.class);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getBaseContext(), android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(intent, options.toBundle());

            }
        });

    }



    //------------------------------------------- LOGIN --------------------------------------------------
    //----------tratamento de campos
    private void buttonLogin(){

        String email = editText_Login_DigitarEmail.getText().toString().trim();
        String senha = editText_Login_DigitarSenha.getText().toString().trim();

        if (email.trim().isEmpty() || senha.trim().isEmpty()){

            Sneaker.with(this)
                    .setTitle("Ops!!!")
                    .setMessage("Preencha os campos")
                    .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .sneakWarning();


        }else {

            loginFirebase(email, senha);

        }

    }


    //----------login Firebase
    private void loginFirebase(String email, String senha){

        final ACProgressPie dialog = new ACProgressPie.Builder(this)
                .ringColor(Color.parseColor("#3d0044"))
                .pieColor(Color.parseColor("#6a2c70"))
                .updateType(ACProgressConstant.PIE_AUTO_UPDATE)
                .speed(70)
                .build();
        dialog.show();


        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                dialog.dismiss();

                if (task.isSuccessful()){

                    Toast.makeText(getBaseContext(), "Logado com Sucesso!!!", Toast.LENGTH_LONG).show();
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    finish();

                }else {

                    Sneaker.with(LoginActivity.this) // Activity, Fragment or ViewGroup
                            .setTitle("Algo deu errado")
                            .setMessage("Email ou Senha inválidos")
                            .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                            .sneakError();

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null){

                    finish();

                }

            }
        });

    }
}
