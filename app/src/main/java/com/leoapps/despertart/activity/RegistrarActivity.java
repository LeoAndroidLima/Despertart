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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.irozon.sneaker.Sneaker;
import com.leoapps.despertart.R;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressPie;

public class RegistrarActivity extends AppCompatActivity {

    private Button button_voltar_Registrar;
    private EditText editText_Registrar_DigitarEmail, editText_Registrar_DigitarSenha, editText_Registrar_RepetirSenha;
    private CheckBox checkBox_Registrar_TermosDeUso;
    private TextView textView_Registrar_ClickTermosDeUso;
    private CardView cardView_Login_ClickRegistrar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        auth = FirebaseAuth.getInstance();

        button_voltar_Registrar = findViewById(R.id.button_voltar_Registrar);
        editText_Registrar_DigitarEmail = findViewById(R.id.editText_Registrar_DigitarEmail);
        editText_Registrar_DigitarSenha = findViewById(R.id.editText_Registrar_DigitarSenha);
        editText_Registrar_RepetirSenha = findViewById(R.id.editText_Registrar_RepetirSenha);
        checkBox_Registrar_TermosDeUso = findViewById(R.id.checkBox_Registrar_TermosDeUso);
        textView_Registrar_ClickTermosDeUso = findViewById(R.id.textView_Registrar_ClickTermosDeUso);
        cardView_Login_ClickRegistrar = findViewById(R.id.cardView_Login_ClickRegistrar);

        //----Clicks----
        //Voltar
        button_voltar_Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });

        //Termos de Uso
        textView_Registrar_ClickTermosDeUso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Concluir Registro
        cardView_Login_ClickRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonCadastrar();

            }
        });
    }


    //------------------------------------Validar Campos-----------------------------------
    private void buttonCadastrar(){

        String email = editText_Registrar_DigitarEmail.getText().toString().trim();
        String senha = editText_Registrar_DigitarSenha.getText().toString().trim();
        String senhaConfirmar = editText_Registrar_RepetirSenha.getText().toString().trim();

        if (!senha.equals(senhaConfirmar)){

            Sneaker.with(this)
                    .setTitle("Ops!!!")
                    .setMessage("Senhas Diferentes")
                    .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .sneakWarning();

        }else if (email.isEmpty() || senha.isEmpty() || senhaConfirmar.isEmpty()){

            Sneaker.with(this)
                    .setTitle("Ops!!!")
                    .setMessage("Preencha todos os campos")
                    .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .sneakWarning();

        }else if (!checkBox_Registrar_TermosDeUso.isChecked()){

            Sneaker.with(this)
                    .setTitle("Ops!!!")
                    .setMessage("Aceite os Termos de uso")
                    .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .sneakWarning();

        }else {

            criarContaFirebase(email, senha);

        }
    }



    //------------------------------------Criar a Conta-----------------------------------
    private void criarContaFirebase(String email, String senha){

        final ACProgressPie dialog = new ACProgressPie.Builder(this)
                .ringColor(Color.parseColor("#3d0044"))
                .pieColor(Color.parseColor("#6a2c70"))
                .updateType(ACProgressConstant.PIE_AUTO_UPDATE)
                .speed(70)
                .build();
        dialog.show();

        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                dialog.dismiss();

                if (task.isSuccessful()){

                    Intent intent = new Intent(getBaseContext(), ConcluirCadastroActivity.class);
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(getBaseContext(), android.R.anim.fade_in, android.R.anim.fade_out);
                    startActivity(intent, options.toBundle());
                    finish();


                }else {

                    String resposta = task.getException().toString();
                    tratamentoDeErro(resposta);

                }
            }
        });
    }

    //------------------------------------Tratamento De Erros-----------------------------------
    private void tratamentoDeErro(String resposta){

        if (resposta.contains("Password should be at least 6 characters")){

            Sneaker.with(this)
                    .setTitle("Erro!!!")
                    .setMessage("Digite uma senha maior que 5 Caracteres")
                    .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .sneakError();

        }else if (resposta.contains("The email address is badly formatted")) {

            Sneaker.with(this) // Activity, Fragment or ViewGroup
                    .setTitle("Error!!!")
                    .setMessage("E-mail inválido")
                    .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .sneakError();

        } else if (resposta.contains("The email address is already in use by another account")) {

            Sneaker.with(this) // Activity, Fragment or ViewGroup
                    .setTitle("Error!!!")
                    .setMessage("O E-mail Cadastrado já existe")
                    .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .sneakError();

        } else if (resposta.contains("There is no user record corresponding to this identifier.")) {

            Sneaker.with(this) // Activity, Fragment or ViewGroup
                    .setTitle("Error!!!")
                    .setMessage("O E-mail e Inválido")
                    .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .sneakError();

        } else if (resposta.contains("interrupted connection")) {

            Sneaker.with(this) // Activity, Fragment or ViewGroup
                    .setTitle("Error!!!")
                    .setMessage("Sua conexão não permite o cadastramento")
                    .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .sneakError();

        } else if (resposta.contains("12501:")) {

            Sneaker.with(this) // Activity, Fragment or ViewGroup
                    .setTitle("Error!!!")
                    .setMessage("Cancelado")
                    .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .sneakError();

        } else {

            Sneaker.with(this) // Activity, Fragment or ViewGroup
                    .setTitle("Error!!!")
                    .setMessage("Algo deu errado tenta mais tarde!!!")
                    .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .sneakError();

        }

    }
}
