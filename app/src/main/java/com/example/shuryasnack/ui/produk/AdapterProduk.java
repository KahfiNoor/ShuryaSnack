package com.example.shuryasnack.ui.produk;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mdatabase = database.getReference();

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
        holder.hargaMenu.setText(String.valueOf("Rp. "+produkModel.getHarga())); // Convert int to String
        holder.kategoriMenu.setText(produkModel.getKategori());
        holder.hapusMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        // Handle deletion here
                        String produkId = produkModel.getProdukId();
                        if (produkId != null) {
                            mdatabase.child("menus").child(produkId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Deletion successful, you can update UI or show a message
                                    Toast.makeText(context, "Menu berhasil dihapus!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Deletion failed, handle the error
                                    Toast.makeText(context, "Gagal menghapus menu!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            // Log or handle the case where produkId is null
                        }
                    }
                }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        // Handle cancel here
                        dialog.dismiss();
                    }
                }).setMessage("Apakah yakin ingin menghapus? " + produkModel.getNama());

                builder.show();
            }
        });
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
//            ubahMenu = itemView.findViewById(R.id.ubahMenu);
            hapusMenu = itemView.findViewById(R.id.hapusMenu);
        }
    }
}

