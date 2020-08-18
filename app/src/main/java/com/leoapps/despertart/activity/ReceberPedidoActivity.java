package com.leoapps.despertart.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.irozon.sneaker.Sneaker;
import com.leoapps.despertart.R;
import com.leoapps.despertart.fragment.MinhaContaFragment;
import com.vicmikhailau.maskededittext.MaskedEditText;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressPie;

public class ReceberPedidoActivity extends AppCompatActivity {

    private Button button_ReceberPedido_Voltar;

    private EditText editText_ReceberPedido_NomeCliente, editText_ReceberPedido_EstadoCliente, editText_ReceberPedido_CidadeCliente, editText_ReceberPedido_BairroCliente,
            editText_ReceberPedido_RuaCliente, editText_ReceberPedido_NumeroEndereco, editText_ReceberPedido_ComplementoEndereco;

    private MaskedEditText maskedEditText_ReceberPedido_CpfCliente, maskedEditText_ReceberPedido_NumeroContato, maskedEditText_ReceberPedido_CepEndereco;

    private CardView cardView_ReceberPedido_ConfirmarDados;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receber_pedido);

        firestore = FirebaseFirestore.getInstance();

        //Button
        button_ReceberPedido_Voltar = findViewById(R.id.button_ReceberPedido_Voltar);

        //EditText
        editText_ReceberPedido_NomeCliente = findViewById(R.id.editText_ReceberPedido_NomeCliente);
        editText_ReceberPedido_EstadoCliente = findViewById(R.id.editText_ReceberPedido_EstadoCliente);
        editText_ReceberPedido_CidadeCliente = findViewById(R.id.editText_ReceberPedido_CidadeCliente);
        editText_ReceberPedido_BairroCliente = findViewById(R.id.editText_ReceberPedido_BairroCliente);
        editText_ReceberPedido_RuaCliente = findViewById(R.id.editText_ReceberPedido_RuaCliente);
        editText_ReceberPedido_NumeroEndereco = findViewById(R.id.editText_ReceberPedido_NumeroEndereco);
        editText_ReceberPedido_ComplementoEndereco = findViewById(R.id.editText_ReceberPedido_ComplementoEndereco);

        //MaskedEditText
        maskedEditText_ReceberPedido_CpfCliente = findViewById(R.id.maskedEditText_ReceberPedido_CpfCliente);
        maskedEditText_ReceberPedido_NumeroContato = findViewById(R.id.maskedEditText_ReceberPedido_NumeroContato);
        maskedEditText_ReceberPedido_CepEndereco = findViewById(R.id.maskedEditText_ReceberPedido_CepEndereco);

        //CardView
        cardView_ReceberPedido_ConfirmarDados = findViewById(R.id.cardView_ReceberPedido_ConfirmarDados);

        //----Clicks
        //voltar
        button_ReceberPedido_Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });

        //confirmar dados
        cardView_ReceberPedido_ConfirmarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        recuperarDadosUsuario();

    }

    //------------------------------------------ RECUPERAR DADOS FIRESTORE -----------------------------------------
    private void recuperarDadosUsuario(){

        final ACProgressPie dialog = new ACProgressPie.Builder(ReceberPedidoActivity.this)
                .ringColor(Color.parseColor("#3d0044"))
                .pieColor(Color.parseColor("#6a2c70"))
                .updateType(ACProgressConstant.PIE_AUTO_UPDATE)
                .speed(70)
                .build();
        dialog.show();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        firestore.collection("usuarios").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                dialog.dismiss();

                if (documentSnapshot.exists()){

                    String nomeCompleto = (String)documentSnapshot.getData().get("nomeCompleto");
                    String cpf = (String)documentSnapshot.getData().get("cpf");
                    String numeroContato = (String)documentSnapshot.getData().get("numeroContato");
                    String cep = (String)documentSnapshot.getData().get("cep");
                    String estado = (String)documentSnapshot.getData().get("estado");
                    String cidade = (String)documentSnapshot.getData().get("cidade");
                    String bairro = (String)documentSnapshot.getData().get("bairro");
                    String rua = (String)documentSnapshot.getData().get("rua");
                    String numeroEndereco = (String)documentSnapshot.getData().get("numeroEndereco");
                    String complemento = (String)documentSnapshot.getData().get("complemento");

                    editText_ReceberPedido_NomeCliente.setText(nomeCompleto);
                    maskedEditText_ReceberPedido_CpfCliente.setText(cpf);
                    maskedEditText_ReceberPedido_NumeroContato.setText(numeroContato);
                    maskedEditText_ReceberPedido_CepEndereco.setText(cep);
                    editText_ReceberPedido_EstadoCliente.setText(estado);
                    editText_ReceberPedido_CidadeCliente.setText(cidade);
                    editText_ReceberPedido_BairroCliente.setText(bairro);
                    editText_ReceberPedido_RuaCliente.setText(rua);
                    editText_ReceberPedido_NumeroEndereco.setText(numeroEndereco);
                    editText_ReceberPedido_ComplementoEndereco.setText(complemento);

                }else {

                    Sneaker.with(ReceberPedidoActivity.this)
                            .setTitle("Ops!!!")
                            .setMessage("Você ainda não cadastrou os seus dados")
                            .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                            .sneakWarning();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                dialog.dismiss();

                Sneaker.with(ReceberPedidoActivity.this)
                        .setTitle("Erro!!!")
                        .setMessage("Falha ao recuperar dados")
                        .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .sneakError();

            }
        });

    }
}
