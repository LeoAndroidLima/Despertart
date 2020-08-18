package com.leoapps.despertart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.leoapps.despertart.R;
import com.leoapps.despertart.adapter.AdapterRecyclerViewCarrinho;
import com.leoapps.despertart.classe.Produto;
import com.leoapps.despertart.singleton.Carrinho;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoActivity extends AppCompatActivity implements AdapterRecyclerViewCarrinho.CarrinhoClick {

    private Button button_voltar_Carrinho;
    private RecyclerView recyclerView;
    private TextView textView_Carrinho_ValorProduto, textView_Carrinho_ValorTotalProduto;
    private CardView cardView_Carrinho_PagarAgora;

    private AdapterRecyclerViewCarrinho adapterRecyclerViewCarrinho;
    private List<Produto> produtos = new ArrayList<Produto>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        button_voltar_Carrinho = findViewById(R.id.button_voltar_Carrinho);
        recyclerView = findViewById(R.id.recyclerView_Carrinho);
        textView_Carrinho_ValorProduto = findViewById(R.id.textView_Carrinho_ValorProduto);
        textView_Carrinho_ValorTotalProduto = findViewById(R.id.textView_Carrinho_ValorTotalProduto);
        cardView_Carrinho_PagarAgora = findViewById(R.id.cardView_Carrinho_PagarAgora);

        button_voltar_Carrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });

        cardView_Carrinho_PagarAgora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonCarrinhoPagar();

            }
        });


        produtos = Carrinho.getInstance().getProdutosCarrinho();

        configRecyclerView();

        atualizarValorTotalProdutos();

    }


    private void atualizarValorTotalProdutos(){

        double valorTotal = 0;


        for (Produto produto: produtos){

            double valor = Double.valueOf(produto.getValor());

            valorTotal = valorTotal + valor;

        }

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String valorTotalString = decimalFormat.format(valorTotal);


        textView_Carrinho_ValorProduto.setText("R$ "+valorTotalString);
        textView_Carrinho_ValorTotalProduto.setText("R$ "+valorTotalString);

    }

















    //----------------------------------------CONFIGURACAO LISTA (RECYCLERVIEW) ------------------------------------------------------------
    private void configRecyclerView(){

        adapterRecyclerViewCarrinho = new AdapterRecyclerViewCarrinho(this, produtos, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterRecyclerViewCarrinho);

    }


    //----------------------------------------------AÇÕES DE CLICK------------------------------------------
    //--------------click em item da lista (recyclerView)
    @Override
    public void carrinhoOnClick(Produto produto) {

        produtos.remove(produto);
        adapterRecyclerViewCarrinho.notifyDataSetChanged();

        atualizarValorTotalProdutos();

        Toast.makeText(getBaseContext(), "Produto removido Carrinho",Toast.LENGTH_LONG).show();

    }







    //--------------Carrinho
    private void buttonCarrinhoPagar(){

        if (produtos.isEmpty()){

            Toast.makeText(getBaseContext(),"Você não possui itens no Carrinho",Toast.LENGTH_LONG).show();

        }else {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if (user!= null){

                Intent intent = new Intent(getBaseContext(), ReceberPedidoActivity.class);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getBaseContext(), android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(intent, options.toBundle());


            }else {

                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getBaseContext(), android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(intent, options.toBundle());

            }

        }

    }

}
