package com.example.shuryasnack.ui.produk;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shuryasnack.MainActivity;
import com.example.shuryasnack.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class AdapterProduk extends RecyclerView.Adapter<AdapterProduk.MyViewHolder> {
    Context context;
    ArrayList<ProdukModel> list;

    public AdapterProduk(Context context, ArrayList<ProdukModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.daftar_menu, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProdukModel produkModel = list.get(position);

        holder.namaMenu.setText(produkModel.getNama());
        holder.hargaMenu.setText(String.valueOf(produkModel.getHarga())); // Convert int to String
        holder.kategoriMenu.setText(produkModel.getKategori());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView namaMenu, hargaMenu, kategoriMenu;
        Button ubahMenu, hapusMenu;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            namaMenu = itemView.findViewById(R.id.namaMenu);
            hargaMenu = itemView.findViewById(R.id.hargaMenu);
            kategoriMenu = itemView.findViewById(R.id.kategoriMenu);
            ubahMenu = itemView.findViewById(R.id.ubahMenu);
            hapusMenu = itemView.findViewById(R.id.hapusMenu);
        }
    }
}

