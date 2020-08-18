package com.leoapps.despertart.singleton;

import com.leoapps.despertart.classe.Produto;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    private volatile static Carrinho carrinho;
    private List<Produto> produtosCarrinho;

    public static Carrinho getInstance(){

        if (carrinho == null){

            synchronized (Carrinho.class){

                if (carrinho == null){

                    carrinho = new Carrinho();

                }
            }
        }

        return carrinho;

    }

    public Carrinho(){

        produtosCarrinho = new ArrayList<Produto>();

    }

    public List<Produto> getProdutosCarrinho(){

        return this.produtosCarrinho;

    }
}
