package com.example.shuryasnack.ui.produk;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ProdukModel implements Parcelable{

    private String nama, kategori, produkId;

    private ArrayList<ProdukModel> list;
    private int harga, quantity, totalCost, temporaryQuantity, totalPerItem;

    public ProdukModel() {
        // Default constructor required for calls to DataSnapshot.getValue(ProdukModel.class)
    }

    public ProdukModel(String produkId, String nama, int harga, String kategori) {
        this.produkId = produkId;
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
        this.quantity = 0;
//        this.totalCost = 0;
        this.temporaryQuantity = 0;
//        this.totalPerItem = 0;  // Inisialisasi totalPerItem
        this.list = new ArrayList<>();
    }

    protected ProdukModel(Parcel in) {
        // Baca nilai dari Parcel dan inisialisasi objek Anda
        nama = in.readString();
        kategori = in.readString();
        produkId = in.readString();
        harga = in.readInt();
        quantity = in.readInt();
        totalCost = in.readInt();
        temporaryQuantity = in.readInt();
        totalPerItem = in.readInt();
    }

    public static final Creator<ProdukModel> CREATOR = new Creator<ProdukModel>() {
        @Override
        public ProdukModel createFromParcel(Parcel in) {
            return new ProdukModel(in);
        }

        @Override
        public ProdukModel[] newArray(int size) {
            return new ProdukModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Tulis nilai objek Anda ke Parcel
        dest.writeString(nama);
        dest.writeString(kategori);
        dest.writeString(produkId);
        dest.writeInt(harga);
        dest.writeInt(quantity);
        dest.writeInt(totalCost);
        dest.writeInt(temporaryQuantity);
        dest.writeInt(totalPerItem);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public int getTemporaryQuantity() {
        return temporaryQuantity;
    }

    public void setTemporaryQuantity(int temporaryQuantity) {
        this.temporaryQuantity = temporaryQuantity;
    }

    public void incrementQuantity() {
        temporaryQuantity++;
    }

    public void decrementQuantity() {
        if (temporaryQuantity > 0) {
            temporaryQuantity--;
        }
    }

    public int getTotalPerItem() {
        return harga * temporaryQuantity;
    }

    public int getTotalCost() {
        int totalCost = 0;
        if (list != null) {
            for (ProdukModel produkModel : list) {
                // Pastikan produkModel tidak null sebelum mengakses metodenya
                if (produkModel != null) {
                    totalCost += produkModel.getTotalPerItem();
                }
            }
        }
        return totalCost;
    }


    public ArrayList<ProdukModel> getList() {
        return list;
    }

    public void setList(ArrayList<ProdukModel> list) {
        this.list = list;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public void setTotalPerItem(int totalPerItem) {
        this.totalPerItem = totalPerItem;
    }
}

