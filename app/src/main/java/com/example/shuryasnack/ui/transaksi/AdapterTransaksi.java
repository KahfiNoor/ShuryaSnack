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

public class AdapterTransaksi extends RecyclerView.Adapter<AdapterTransaksi.ViewHolder> {
    private ArrayList<ProdukModel> produkDipilih;

    public AdapterTransaksi(ArrayList<ProdukModel> produkDipilih) {
        this.produkDipilih = produkDipilih;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_transaksi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProdukModel produkModel = produkDipilih.get(position);

        // Setel data ke TextViews di dalam ViewHolder sesuai dengan kebutuhan
        holder.namaMenuItem.setText(produkModel.getNama());
        holder.jumlahItem.setText(String.valueOf(produkModel.getTemporaryQuantity()));
        holder.totalPerItem.setText(String.valueOf(produkModel.getTotalPerItem()));
    }

    @Override
    public int getItemCount() {
        return produkDipilih.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaMenuItem, jumlahItem, totalPerItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inisialisasi TextViews di dalam ViewHolder
            namaMenuItem = itemView.findViewById(R.id.namaMenuItem);
            jumlahItem = itemView.findViewById(R.id.jumlahItem);
            totalPerItem = itemView.findViewById(R.id.totalPerItem);
        }
    }
}
