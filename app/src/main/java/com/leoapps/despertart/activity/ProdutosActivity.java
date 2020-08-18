package com.leoapps.despertart.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.leoapps.despertart.R;
import com.leoapps.despertart.adapter.AdapterRecyclerViewProduto;
import com.leoapps.despertart.classe.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutosActivity extends AppCompatActivity implements AdapterRecyclerViewProduto.ProdutoClick {

    private RecyclerView recyclerView;
    private Button button_voltar_Produtos;
    private TextView textView_titulo_Produtos;

    private AdapterRecyclerViewProduto adapterRecyclerViewProduto;

    private List<Produto> produtos = new ArrayList<Produto>();
    private List<String> keys = new ArrayList<String>();

    private FirebaseFirestore firestore;

    private String idCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        firestore = FirebaseFirestore.getInstance();

        idCategoria = getIntent().getStringExtra("idCategoria");

        textView_titulo_Produtos = findViewById(R.id.textView_titulo_Produtos);
        String nomeCategoria = getIntent().getStringExtra("nomeCategoria");
        textView_titulo_Produtos.setText(nomeCategoria);


        recyclerView = findViewById(R.id.recyclerView_Produtos);
        button_voltar_Produtos = findViewById(R.id.button_voltar_Produtos);

        button_voltar_Produtos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });

        configRecyclerView();
        iniciarOuvinteProduto();

    }



    //----------------------------------- CONFIGURAÇÃO LISTA (RECYCLERVIEW) --------------------------------------------------
    private void configRecyclerView(){

        adapterRecyclerViewProduto = new AdapterRecyclerViewProduto(getBaseContext(), produtos, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterRecyclerViewProduto);

    }

    @Override
    public void produtoOnClick(Produto produto) {

        Intent intent = new Intent(this,MostrarProdutoActivity.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out);
        intent.putExtra("produto", produto);
        startActivity(intent, options.toBundle());

    }



    //-----------------------------------OUVINTE PRODUTOS--------------------------------------------------
    private void iniciarOuvinteProduto(){

        CollectionReference reference = firestore.collection("produtos");
        Query query = reference.whereEqualTo("categoria_id", idCategoria);

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                Produto produto;
                int index;

                for (DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){

                    switch (doc.getType()){

                        case ADDED:

                            produto = doc.getDocument().toObject(Produto.class);

                            produtos.add(produto);

                            keys.add(produto.getId());

                            adapterRecyclerViewProduto.notifyDataSetChanged();

                            break;

                        case MODIFIED:

                            produto = doc.getDocument().toObject(Produto.class);

                            index = keys.indexOf(produto.getId());

                            produtos.set(index, produto);

                            adapterRecyclerViewProduto.notifyDataSetChanged();

                            break;

                        case REMOVED:

                            produto = doc.getDocument().toObject(Produto.class);

                            index = keys.indexOf(produto.getId());

                            produtos.remove(index);
                            keys.remove(index);

                            adapterRecyclerViewProduto.notifyItemRemoved(index);
                            adapterRecyclerViewProduto.notifyItemChanged(index,produtos.size());

                            break;
                    }
                }
            }
        });
    }


}
