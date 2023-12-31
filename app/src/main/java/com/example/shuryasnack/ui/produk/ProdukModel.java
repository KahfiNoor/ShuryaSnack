package com.example.shuryasnack.ui.produk;

public class ProdukModel {

    private String nama, kategori;
    private int harga;

    public ProdukModel(String getNama, int getHarga, String getKategori) {
        this.nama = getNama;
        this.harga = getHarga;
        this.kategori = getKategori;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
