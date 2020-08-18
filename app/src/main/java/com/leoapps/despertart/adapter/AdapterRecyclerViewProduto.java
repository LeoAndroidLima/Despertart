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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.leoapps.despertart.R;
import com.leoapps.despertart.classe.Produto;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecyclerViewProduto extends RecyclerView.Adapter<AdapterRecyclerViewProduto.ViewHolder> {

    private Context context;
    private List<Produto> produtos = new ArrayList<Produto>();
    private ProdutoClick produtoClick;

    public AdapterRecyclerViewProduto(Context context, List<Produto> produtos, ProdutoClick produtoClick){

        this.context = context;
        this.produtos = produtos;
        this.produtoClick = produtoClick;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_produtos, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerViewProduto.ViewHolder holder, int position) {

        final Produto produto = produtos.get(position);

        String valor = "R$: " + produto.getValor();

        holder.textView_Item_NomeProduto.setText(produto.getNome());
        holder.textView_Item_DescricaoProduto.setText(produto.getDescricao());
        holder.textView_Item_ValorProduto.setText(valor);

        Glide.with(context).load(produto.getUrl_imagem1()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(holder.imageView_Item_ImagemProduto);

        holder.cardView_Item_ProdutoClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                produtoClick.produtoOnClick(produto);

            }
        });

    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public interface ProdutoClick{

        void produtoOnClick(Produto produto);

    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView_Item_ProdutoClick;
        private ImageView imageView_Item_ImagemProduto;
        private TextView textView_Item_NomeProduto, textView_Item_DescricaoProduto, textView_Item_ValorProduto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView_Item_ProdutoClick = itemView.findViewById(R.id.cardView_Item_ProdutoClick);
            imageView_Item_ImagemProduto = itemView.findViewById(R.id.imageView_Item_ImagemProduto);
            textView_Item_NomeProduto = itemView.findViewById(R.id.textView_Item_NomeProduto);
            textView_Item_DescricaoProduto = itemView.findViewById(R.id.textView_Item_DescricaoProduto);
            textView_Item_ValorProduto = itemView.findViewById(R.id.textView_Item_ValorProduto);

        }
    }
}
