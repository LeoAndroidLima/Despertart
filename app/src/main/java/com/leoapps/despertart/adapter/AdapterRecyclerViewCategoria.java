package com.leoapps.despertart.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.leoapps.despertart.classe.Categoria;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecyclerViewCategoria extends RecyclerView.Adapter<AdapterRecyclerViewCategoria.ViewHolder> {

    private Context context;
    private List<Categoria> categorias = new ArrayList<Categoria>();
    private CategoriaClick categoriaClick;

    public AdapterRecyclerViewCategoria(Context context, List<Categoria> categorias, CategoriaClick categoriaClick){

        this.context = context;
        this.categorias = categorias;
        this.categoriaClick = categoriaClick;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_categoria, parent, false);

        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRecyclerViewCategoria.ViewHolder holder, int position) {

        final Categoria categoria = categorias.get(position);

        holder.progressBar_Item_CategoriaCarregar.setVisibility(View.VISIBLE);

        holder.textView_Item_CategoriaNome.setText(categoria.getNome());

        Glide.with(context).load(categoria.getUrl_imagem()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                holder.progressBar_Item_CategoriaCarregar.setVisibility(View.GONE);

                return false;
            }
        }).into(holder.imageView_Item_CategoriaImagem);


        holder.cardView_Item_CategoriaClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                categoriaClick.categoriaOnClick(categoria);

            }
        });



    }

    @Override
    public int getItemCount() {
        return categorias.size();

    }

    public interface CategoriaClick{

        void categoriaOnClick(Categoria categoria);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView_Item_CategoriaClick;
        private ImageView imageView_Item_CategoriaImagem;
        private ProgressBar progressBar_Item_CategoriaCarregar;
        private TextView textView_Item_CategoriaNome;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView_Item_CategoriaClick = itemView.findViewById(R.id.cardView_Item_CategoriaClick);
            imageView_Item_CategoriaImagem = itemView.findViewById(R.id.imageView_Item_CategoriaImagem);
            progressBar_Item_CategoriaCarregar = itemView.findViewById(R.id.progressBar_Item_CategoriaCarregar);
            textView_Item_CategoriaNome = itemView.findViewById(R.id.textView_Item_CategoriaNome);



        }
    }
}
