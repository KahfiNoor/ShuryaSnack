package com.example.shuryasnack.ui.home;


public class HomeModel {

    private String nama, produkId;
    private int harga;

    public HomeModel(String produkId, String nama, int harga) {
        this.produkId = produkId;
        this.nama = nama;
        this.harga = harga;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getProdukId() {return produkId;}

    public void setProdukId(String produkId) {
        this.produkId = produkId;
    }
}
