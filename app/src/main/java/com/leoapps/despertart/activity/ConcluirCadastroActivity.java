package com.leoapps.despertart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;

import com.leoapps.despertart.R;

public class ConcluirCadastroActivity extends AppCompatActivity {

    private CardView cardView_ConcluirRegistro_Obrigado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concluir_cadastro);

        cardView_ConcluirRegistro_Obrigado = findViewById(R.id.cardView_ConcluirRegistro_Obrigado);

        cardView_ConcluirRegistro_Obrigado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
            }
        });
    }
}
