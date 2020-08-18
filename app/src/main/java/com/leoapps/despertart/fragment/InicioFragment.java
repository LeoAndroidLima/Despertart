package com.leoapps.despertart.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.leoapps.despertart.R;
import com.leoapps.despertart.activity.ProdutosActivity;
import com.leoapps.despertart.adapter.AdapterRecyclerViewCategoria;
import com.leoapps.despertart.adapter.SliderAdapterImage;
import com.leoapps.despertart.classe.Categoria;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class InicioFragment extends Fragment implements AdapterRecyclerViewCategoria.CategoriaClick{

    private SliderView sliderView;
    private TextView textView_HomeFragment_FraseDoDia;
    private RecyclerView recyclerView;

    //slider de imagens
    private SliderAdapterImage sliderAdapterImage;
    private List<String> urls = new ArrayList<String>();

    //Lista de categoria
    private AdapterRecyclerViewCategoria adapterRecyclerViewCategoria;
    private List<Categoria> categorias = new ArrayList<Categoria>();

    //firebase
    private FirebaseFirestore firestore;

    private List<String> keys = new ArrayList<String>();



    public InicioFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inicio, container, false);

        sliderView = rootView.findViewById(R.id.imageSlider_home);
        textView_HomeFragment_FraseDoDia = rootView.findViewById(R.id.textView_HomeFragment_FraseDoDia);
        recyclerView = rootView.findViewById(R.id.recyclerView__HomeFragment_ListaCategoria);

        firestore = FirebaseFirestore.getInstance();

        configRecyclerView();

        iniciarOuvinteHomeApp();
        iniciarOuvinteCategoria();

        return rootView;
    }



    //-------------------------click em item da lista (recyclerView)
    @Override
    public void categoriaOnClick(Categoria categoria) {

        Intent intent = new Intent(getContext(), ProdutosActivity.class);
        intent.putExtra("nomeCategoria", categoria.getNome());
        intent.putExtra("idCategoria", categoria.getId());
        ActivityOptions options = ActivityOptions.makeCustomAnimation(getContext(), android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(intent, options.toBundle());

    }


    //-----------------------------------CONFIGURAÇÃO LISTA--------------------------------------------------
    private void configRecyclerView(){

        adapterRecyclerViewCategoria = new AdapterRecyclerViewCategoria(getContext(), categorias, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterRecyclerViewCategoria);

    }



    //----------------------------------- OUVINTE HOME APP --------------------------------------------------
    private void iniciarOuvinteHomeApp(){

        DocumentReference reference = firestore.collection("app").document("homeapp");

        com.google.firebase.firestore.EventListener eventListener = new EventListener<DocumentSnapshot>(){
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (documentSnapshot.exists()) {

                    String informativo = (String) documentSnapshot.getData().get("informativo");
                    String url1 = (String) documentSnapshot.getData().get("url_imagem1");
                    String url2 = (String) documentSnapshot.getData().get("url_imagem2");
                    String url3 = (String) documentSnapshot.getData().get("url_imagem3");
                    String url4 = (String) documentSnapshot.getData().get("url_imagem4");

                    /*Log.d("dyww Informativo", informativo );
                    Log.d("dyww URL1", url1 );
                    Log.d("dyww Url2", url2 );*/

                    configSliderImagem(informativo, url1, url2, url3, url4);

                }



            }
        };

        reference.addSnapshotListener(eventListener);

    }

    private void configSliderImagem(String informativo, String url1, String url2, String url3, String url4){

        textView_HomeFragment_FraseDoDia.setText(informativo);

        if (!urls.isEmpty()){

            urls.clear();

        }

        urls.add(url1);
        urls.add(url2);
        urls.add(url3);
        urls.add(url4);

        sliderAdapterImage = new SliderAdapterImage(getContext(), urls);

        sliderView.setSliderAdapter(sliderAdapterImage);

        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setScrollTimeInSec(8);
        sliderView.startAutoCycle();

    }





    //-----------------------------------OUVINTE CATEGORIAS--------------------------------------------------
    private void iniciarOuvinteCategoria(){

        CollectionReference reference = firestore.collection("categorias");

        reference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                Categoria categoria;
                int index;

                for (DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){

                    switch (doc.getType()){

                        case ADDED:

                            categoria = doc.getDocument().toObject(Categoria.class);

                            categorias.add(categoria);

                            keys.add(categoria.getId());

                            adapterRecyclerViewCategoria.notifyDataSetChanged();

                            break;

                        case MODIFIED:

                            categoria = doc.getDocument().toObject(Categoria.class);

                            index = keys.indexOf(categoria.getId());

                            categorias.set(index, categoria);

                            adapterRecyclerViewCategoria.notifyDataSetChanged();

                            break;

                        case REMOVED:

                            categoria = doc.getDocument().toObject(Categoria.class);

                            index = keys.indexOf(categoria.getId());

                            categorias.remove(index);
                            keys.remove(index);

                            adapterRecyclerViewCategoria.notifyItemRemoved(index);
                            adapterRecyclerViewCategoria.notifyItemChanged(index,categorias.size());

                            break;

                    }
                }
            }
        });

    }


}




