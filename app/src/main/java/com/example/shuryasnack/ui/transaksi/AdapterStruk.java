package com.example.shuryasnack.ui.transaksi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shuryasnack.R;
import com.example.shuryasnack.ui.produk.ProdukModel;

import java.util.ArrayList;

public class AdapterStruk extends RecyclerView.Adapter<AdapterStruk.ViewHolder> {
    private ArrayList<ProdukModel> produkList;
    private int totalTransaksi;
    private int nominalUang;
    private int kembalian;

    public AdapterStruk(ArrayList<ProdukModel> produkList, int totalTransaksi, int nominalUang, int kembalian) {
        this.produkList = produkList;
        this.totalTransaksi = totalTransaksi;
        this.nominalUang = nominalUang;
        this.kembalian = kembalian;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_struk, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProdukModel produkModel = produkList.get(position);

        // Set data to TextViews in ViewHolder as needed
        holder.jumlahItem.setText(String.valueOf(produkModel.getTemporaryQuantity()));
        holder.namaMenuItem.setText(produkModel.getNama());
        holder.totalPerItem.setText(String.valueOf(produkModel.getTotalPerItem()));
    }

    @Override
    public int getItemCount() {
        return produkList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView jumlahItem, namaMenuItem, totalPerItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize TextViews in ViewHolder
            jumlahItem = itemView.findViewById(R.id.jumlahItem);
            namaMenuItem = itemView.findViewById(R.id.namaMenuItem);
            totalPerItem = itemView.findViewById(R.id.totalPerItem);
        }
    }
}
