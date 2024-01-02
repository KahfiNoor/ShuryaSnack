package com.example.shuryasnack.ui.produk;

public class ProdukModel {

    private String nama, kategori, produkId;
    private int harga;

    public ProdukModel() {
        // Default constructor required for calls to DataSnapshot.getValue(ProdukModel.class)
    }

    public ProdukModel(String produkId, String nama, int harga, String kategori) {
        this.produkId = produkId;
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
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

    public String getProdukId() {
        return produkId;
    }

    public void setProdukId(String produkId) {
        this.produkId = produkId;
    }
}

