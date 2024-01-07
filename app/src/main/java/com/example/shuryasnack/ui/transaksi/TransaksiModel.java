package com.example.shuryasnack.ui.transaksi;

import com.example.shuryasnack.ui.produk.ProdukModel;

import java.util.ArrayList;

public class TransaksiModel {
    private ArrayList<ProdukModel> produkList;
    private int nominalUang,totalTransaksi,kembalian;

    private long timestamp;

    public TransaksiModel() {
        // Default constructor required for calls to DataSnapshot.getValue(TransaksiModel.class)
    }

    public TransaksiModel(ArrayList<ProdukModel> produkList, int nominalUang, int totalTransaksi, long timestamp) {
        this.produkList = produkList;
        this.nominalUang = nominalUang;
        this.totalTransaksi = totalTransaksi;
        this.timestamp = timestamp;
    }

    public ArrayList<ProdukModel> getProdukList() {
        return produkList;
    }

    public void setProdukList(ArrayList<ProdukModel> produkList) {
        this.produkList = produkList;
    }

    public int getNominalUang() {
        return nominalUang;
    }

    public void setNominalUang(int nominalUang) {
        this.nominalUang = nominalUang;
    }

    public int getTotalTransaksi() {
        return totalTransaksi;
    }

    public void setTotalTransaksi(int totalTransaksi) {
        this.totalTransaksi = totalTransaksi;
    }

    public int getKembalian() {
        return kembalian;
    }

    public void setKembalian(int kembalian) {
        this.kembalian = kembalian;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
