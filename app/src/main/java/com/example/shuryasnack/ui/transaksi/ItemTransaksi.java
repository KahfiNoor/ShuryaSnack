package com.example.shuryasnack.ui.transaksi;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemTransaksi implements Parcelable {

    private String itemId;
    private String nama;
    private int jumlah;
    private int subtotal;

    public ItemTransaksi() {
    }

    public ItemTransaksi(String itemId, String nama, int jumlah, int subtotal) {
        this.itemId = itemId;
        this.nama = nama;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
    }

    protected ItemTransaksi(Parcel in) {
        itemId = in.readString();
        nama = in.readString();
        jumlah = in.readInt();
        subtotal = in.readInt();
    }

    public static final Creator<ItemTransaksi> CREATOR = new Creator<ItemTransaksi>() {
        @Override
        public ItemTransaksi createFromParcel(Parcel in) {
            return new ItemTransaksi(in);
        }

        @Override
        public ItemTransaksi[] newArray(int size) {
            return new ItemTransaksi[size];
        }
    };

    public String getItemId() {
        return itemId;
    }

    public String getNama() {
        return nama;
    }

    public int getJumlah() {
        return jumlah;
    }

    public int getSubtotal() {
        return subtotal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemId);
        dest.writeString(nama);
        dest.writeInt(jumlah);
        dest.writeInt(subtotal);
    }
}

