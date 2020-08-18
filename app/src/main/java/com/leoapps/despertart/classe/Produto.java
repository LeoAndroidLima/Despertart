package com.leoapps.despertart.classe;

import android.os.Parcel;
import android.os.Parcelable;

public class Produto implements Parcelable {

    private String id;
    private String nome;
    private String descricao;
    private String valor;
    private String url_imagem1;
    private String url_imagem2;
    private String url_imagem3;
    private String categoria_id;
    private boolean semtamanho;
    private boolean tamanho_p;
    private boolean tamanho_m;
    private boolean tamanho_g;
    private boolean tamanho_gg;
    private String tamanho;

    //Adicionar ao carrinho
    private String observacao;

    public Produto(){

    }

    public Produto(String id, String nome, String descricao, String valor, String url_imagem1, String url_imagem2, String url_imagem3, String categoria_id, boolean semtamanho, boolean tamanho_p, boolean tamanho_m, boolean tamanho_g, boolean tamanho_gg, String observacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.url_imagem1 = url_imagem1;
        this.url_imagem2 = url_imagem2;
        this.url_imagem3 = url_imagem3;
        this.categoria_id = categoria_id;
        this.semtamanho = semtamanho;
        this.tamanho_p = tamanho_p;
        this.tamanho_m = tamanho_m;
        this.tamanho_g = tamanho_g;
        this.tamanho_gg = tamanho_gg;
        this.observacao = observacao;
    }

    public Produto(String nome, String descricao, String valor, String url_imagem1 ,String tamanho, String observacao) {
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.url_imagem1 = url_imagem1;
        this.tamanho = tamanho;
        this.observacao = observacao;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getUrl_imagem1() {
        return url_imagem1;
    }

    public void setUrl_imagem1(String url_imagem1) {
        this.url_imagem1 = url_imagem1;
    }

    public String getUrl_imagem2() {
        return url_imagem2;
    }

    public void setUrl_imagem2(String url_imagem2) {
        this.url_imagem2 = url_imagem2;
    }

    public String getUrl_imagem3() {
        return url_imagem3;
    }

    public void setUrl_imagem3(String url_imagem3) {
        this.url_imagem3 = url_imagem3;
    }

    public String getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(String categoria_id) {
        this.categoria_id = categoria_id;
    }

    public boolean isSemtamanho() {
        return semtamanho;
    }

    public void setSemtamanho(boolean semtamanho) {
        this.semtamanho = semtamanho;
    }

    public boolean isTamanho_p() {
        return tamanho_p;
    }

    public void setTamanho_p(boolean tamanho_p) {
        this.tamanho_p = tamanho_p;
    }

    public boolean isTamanho_m() {
        return tamanho_m;
    }

    public void setTamanho_m(boolean tamanho_m) {
        this.tamanho_m = tamanho_m;
    }

    public boolean isTamanho_g() {
        return tamanho_g;
    }

    public void setTamanho_g(boolean tamanho_g) {
        this.tamanho_g = tamanho_g;
    }

    public boolean isTamanho_gg() {
        return tamanho_gg;
    }

    public void setTamanho_gg(boolean tamanho_gg) {
        this.tamanho_gg = tamanho_gg;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nome);
        dest.writeString(this.descricao);
        dest.writeString(this.valor);
        dest.writeString(this.url_imagem1);
        dest.writeString(this.url_imagem2);
        dest.writeString(this.url_imagem3);
        dest.writeString(this.categoria_id);
        dest.writeByte(this.semtamanho ? (byte) 1 : (byte) 0);
        dest.writeByte(this.tamanho_p ? (byte) 1 : (byte) 0);
        dest.writeByte(this.tamanho_m ? (byte) 1 : (byte) 0);
        dest.writeByte(this.tamanho_g ? (byte) 1 : (byte) 0);
        dest.writeByte(this.tamanho_gg ? (byte) 1 : (byte) 0);
        dest.writeString(this.tamanho);
        dest.writeString(this.observacao);
    }

    protected Produto(Parcel in) {
        this.id = in.readString();
        this.nome = in.readString();
        this.descricao = in.readString();
        this.valor = in.readString();
        this.url_imagem1 = in.readString();
        this.url_imagem2 = in.readString();
        this.url_imagem3 = in.readString();
        this.categoria_id = in.readString();
        this.semtamanho = in.readByte() != 0;
        this.tamanho_p = in.readByte() != 0;
        this.tamanho_m = in.readByte() != 0;
        this.tamanho_g = in.readByte() != 0;
        this.tamanho_gg = in.readByte() != 0;
        this.tamanho = in.readString();
        this.observacao = in.readString();
    }

    public static final Creator<Produto> CREATOR = new Creator<Produto>() {
        @Override
        public Produto createFromParcel(Parcel source) {
            return new Produto(source);
        }

        @Override
        public Produto[] newArray(int size) {
            return new Produto[size];
        }
    };
}
