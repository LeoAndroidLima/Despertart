package com.leoapps.despertart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.leoapps.despertart.R;
import com.leoapps.despertart.adapter.SliderAdapterImage;
import com.leoapps.despertart.classe.Produto;
import com.leoapps.despertart.singleton.Carrinho;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class MostrarProdutoActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;

    private Button button_MostrarPR_ClickVoltar;
    private CardView cardView_MostrarPR_Comprar;
    private TextView textView_MostrarPR_NomeProduto, textView_MostrarPR_ValorProduto, textView_MostrarPR_Escolher, textView_MostrarPR_Descricao;
    private RadioGroup radioGroup_MostrarPR_GrupoTamanho;
    private RadioButton radioButton_MostrarPR_TamanhoP, radioButton_MostrarPR_TamanhoM, radioButton_MostrarPR_TamanhoG, radioButton_MostrarPR_TamanhoGG;
    private EditText editText_MostrarPR_Observacao;

    private Produto produto;

    private double valorTotalPedido = 0;

    //SliderView
    private SliderView imageSlider_MostrarPR_Imagem;
    private List<String> urls = new ArrayList<String>();
    private SliderAdapterImage sliderAdapterImage;

    private List<Produto> tamanhoSelecionado = new ArrayList<Produto>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_produto);

        firestore = FirebaseFirestore.getInstance();

        atualizarView();
        configTamanho();

        cardView_MostrarPR_Comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (produto.isSemtamanho()){

                    String semTamanho = "Não Possui Tamano";
                    adicionarPedidoCarrinho(produto.getValor(), semTamanho);

                    finish();
                    Intent intent = new Intent(getBaseContext(), CarrinhoActivity.class);
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(getBaseContext(), android.R.anim.fade_in, android.R.anim.fade_out);
                    startActivity(intent, options.toBundle());

                }else {

                    pegarRadioGroup();

                }




            }
        });


        button_MostrarPR_ClickVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });

    }



    //-------------------------------------------------METODOS INICIAIS--------------------------------------------------

    //-----------atualizar visao do usuario
    private void atualizarView(){


        //Clicks
        button_MostrarPR_ClickVoltar = findViewById(R.id.button_MostrarPR_ClickVoltar);
        cardView_MostrarPR_Comprar = findViewById(R.id.cardView_MostrarPR_Comprar);

        //TextView
        textView_MostrarPR_NomeProduto = findViewById(R.id.textView_MostrarPR_NomeProduto);
        textView_MostrarPR_ValorProduto = findViewById(R.id.textView_MostrarPR_ValorProduto);
        textView_MostrarPR_Escolher = findViewById(R.id.textView_MostrarPR_Escolher);
        textView_MostrarPR_Descricao = findViewById(R.id.textView_MostrarPR_Descricao);

        //Radio
        radioGroup_MostrarPR_GrupoTamanho = findViewById(R.id.radioGroup_MostrarPR_GrupoTamanho);
        radioButton_MostrarPR_TamanhoP = findViewById(R.id.radioButton_MostrarPR_TamanhoP);
        radioButton_MostrarPR_TamanhoM = findViewById(R.id.radioButton_MostrarPR_TamanhoM);
        radioButton_MostrarPR_TamanhoG = findViewById(R.id.radioButton_MostrarPR_TamanhoG);
        radioButton_MostrarPR_TamanhoGG = findViewById(R.id.radioButton_MostrarPR_TamanhoGG);

        //SliderView
        imageSlider_MostrarPR_Imagem = findViewById(R.id.imageSlider_MostrarPR_Imagem);

        //EditText
        editText_MostrarPR_Observacao = findViewById(R.id.editText_MostrarPR_Observacao);

        produto = getIntent().getParcelableExtra("produto");

        textView_MostrarPR_NomeProduto.setText(produto.getNome());
        textView_MostrarPR_ValorProduto.setText("R$: "+ produto.getValor());
        textView_MostrarPR_Descricao.setText(produto.getDescricao());

        String url1 = (produto.getUrl_imagem1());
        String url2 = (produto.getUrl_imagem2());
        String url3 = (produto.getUrl_imagem3());

        configSliderImagem(url1, url2, url3);

        valorTotalPedido = Double.valueOf(produto.getValor());

    }


    private void configSliderImagem(String url1, String url2, String url3){

        urls.add(url1);
        urls.add(url2);
        urls.add(url3);

        sliderAdapterImage = new SliderAdapterImage(getBaseContext(), urls);

        imageSlider_MostrarPR_Imagem.setSliderAdapter(sliderAdapterImage);

        imageSlider_MostrarPR_Imagem.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSlider_MostrarPR_Imagem.setScrollTimeInSec(8);
        imageSlider_MostrarPR_Imagem.startAutoCycle();

    }


    //-----------Configurar Para Exibir o Tamanho
    private void configTamanho(){

        if (produto.isSemtamanho()){

            radioGroup_MostrarPR_GrupoTamanho.setVisibility(View.GONE);
            textView_MostrarPR_Escolher.setVisibility(View.GONE);
            radioButton_MostrarPR_TamanhoP.setVisibility(View.GONE);
            radioButton_MostrarPR_TamanhoM.setVisibility(View.GONE);
            radioButton_MostrarPR_TamanhoG.setVisibility(View.GONE);
            radioButton_MostrarPR_TamanhoGG.setVisibility(View.GONE);

        }

        if (!produto.isTamanho_p()){

            radioButton_MostrarPR_TamanhoP.setVisibility(View.GONE);

        }

        if (!produto.isTamanho_m()){

            radioButton_MostrarPR_TamanhoM.setVisibility(View.GONE);

        }

        if (!produto.isTamanho_g()){

            radioButton_MostrarPR_TamanhoG.setVisibility(View.GONE);

        }

        if (!produto.isTamanho_gg()){

            radioButton_MostrarPR_TamanhoGG.setVisibility(View.GONE);

        }

    }



    //-----------Qual Radio Button foi selecionado
    private void pegarRadioGroup(){

        Intent intent = new Intent(getBaseContext(), CarrinhoActivity.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(getBaseContext(), android.R.anim.fade_in, android.R.anim.fade_out);


        switch (radioGroup_MostrarPR_GrupoTamanho.getCheckedRadioButtonId()) {

            case R.id.radioButton_MostrarPR_TamanhoP:

                String tamanhoP = "Tamanho P";
                adicionarPedidoCarrinho(produto.getValor(), tamanhoP);

                finish();
                startActivity(intent, options.toBundle());

                break;

            case R.id.radioButton_MostrarPR_TamanhoM:

                String tamanhoM = "Tamanho M";
                adicionarPedidoCarrinho(produto.getValor(), tamanhoM);

                finish();
                startActivity(intent, options.toBundle());

                break;

            case R.id.radioButton_MostrarPR_TamanhoG:

                String tamanhoG = "Tamanho G";
                adicionarPedidoCarrinho(produto.getValor(), tamanhoG);

                finish();
                startActivity(intent, options.toBundle());

                break;

            case R.id.radioButton_MostrarPR_TamanhoGG:

                String tamanhoGG = "Tamanho GG";
                adicionarPedidoCarrinho(produto.getValor(), tamanhoGG);

                finish();
                startActivity(intent, options.toBundle());

                break;

            default:

                Toast.makeText(getBaseContext(), "Selecione um tamanho", Toast.LENGTH_LONG).show();


        }
    }









    //-----------Adicionar Pedido no Carrinho
    private void adicionarPedidoCarrinho(String valor, String tamanho){

        //Toast.makeText(getBaseContext(), "Tamanho Selecionado: " + tamanho + "\nValor: " +valor, Toast.LENGTH_LONG).show();

        List<Produto> produtosCarrinho = Carrinho.getInstance().getProdutosCarrinho();

        String observacao = editText_MostrarPR_Observacao.getText().toString();

        Produto prod;

        if (observacao.trim().isEmpty()){

            prod = new Produto(produto.getNome(), produto.getDescricao(), valor, produto.getUrl_imagem1(), tamanho, "Sem Observação:");

        }else {

            prod = new Produto(produto.getNome(), produto.getDescricao(), valor, produto.getUrl_imagem1(), tamanho, "Observação: " +observacao);

        }

        produtosCarrinho.add(prod);

    }










}
