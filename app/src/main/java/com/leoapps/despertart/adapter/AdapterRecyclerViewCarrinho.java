package com.leoapps.despertart.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.leoapps.despertart.R;
import com.leoapps.despertart.classe.Produto;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterRecyclerViewCarrinho extends RecyclerView.Adapter<AdapterRecyclerViewCarrinho.ViewHolder> {

    private Context context;
    private List<Produto> produtos = new ArrayList<Produto>();
    private CarrinhoClick carrinhoClick;

    public AdapterRecyclerViewCarrinho(Context context, List<Produto> produtos, CarrinhoClick carrinhoClick){

        this.context = context;
        this.produtos = produtos;
        this.carrinhoClick = carrinhoClick;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_carrinho, parent, false);

        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerViewCarrinho.ViewHolder holder, int position) {

        final Produto produto = produtos.get(position);

        double valorDouble = Double.parseDouble(produto.getValor());
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String valor = decimalFormat.format(valorDouble);

        holder.textView_item_CarrinhoProdutoNome.setText(produto.getNome());
        holder.textView_item_CarrinhoProdutoDescricao.setText(produto.getDescricao());
        holder.textView_item_CarrinhoProdutoValor.setText("R$: " +valor);

        if (produto.getTamanho().equals("Não Possui Tamano")){

            holder.textView_item_CarrinhoProdutoTamanho.setVisibility(View.GONE);

        }else {

            holder.textView_item_CarrinhoProdutoTamanho.setText(produto.getTamanho());

        }

        Glide.with(context).load(produto.getUrl_imagem1()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(holder.imageView_Item_CarrinhoImagemProduto);

        holder.textView_item_CarrinhoProdutoClickRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                carrinhoClick.carrinhoOnClick(produto);

            }
        });

    }

    @Override
    public int getItemCount() {
        return produtos.size();

    }

    public interface CarrinhoClick{

        void carrinhoOnClick(Produto produto);
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView_Item_CarrinhoImagemProduto;

        private TextView textView_item_CarrinhoProdutoNome, textView_item_CarrinhoProdutoDescricao,
                textView_item_CarrinhoProdutoTamanho, textView_item_CarrinhoProdutoValor, textView_item_CarrinhoProdutoClickRemover;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView_Item_CarrinhoImagemProduto = itemView.findViewById(R.id.imageView_Item_CarrinhoImagemProduto);

            textView_item_CarrinhoProdutoNome = itemView.findViewById(R.id.textView_item_CarrinhoProdutoNome);
            textView_item_CarrinhoProdutoDescricao = itemView.findViewById(R.id.textView_item_CarrinhoProdutoDescricao);
            textView_item_CarrinhoProdutoTamanho = itemView.findViewById(R.id.textView_item_CarrinhoProdutoTamanho);
            textView_item_CarrinhoProdutoValor = itemView.findViewById(R.id.textView_item_CarrinhoProdutoValor);
            textView_item_CarrinhoProdutoClickRemover = itemView.findViewById(R.id.textView_item_CarrinhoProdutoClickRemover);

        }
    }
}
