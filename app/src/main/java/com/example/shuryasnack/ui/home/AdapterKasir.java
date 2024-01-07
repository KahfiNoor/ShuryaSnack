package com.example.shuryasnack.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shuryasnack.R;
import com.example.shuryasnack.ui.produk.ProdukModel;

import java.util.ArrayList;

public class AdapterKasir extends RecyclerView.Adapter<AdapterKasir.MyHolder> {

    private Context context;
    private ArrayList<ProdukModel> list;
    private AdapterKasirListener listener;

    public AdapterKasir(Context context, ArrayList<ProdukModel> list, AdapterKasirListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.kasir_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface AdapterKasirListener {
        void onQuantityChanged();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView namaMenu, hargaMenu, kategoriMenu, jumlahPerItem, totalCost;
        private ImageView tambahPesanan, kurangPesanan;
        private ProdukModel produkModel;

        MyHolder(@NonNull View itemView) {
            super(itemView);
            namaMenu = itemView.findViewById(R.id.namaMenu);
            hargaMenu = itemView.findViewById(R.id.hargaMenu);
            kategoriMenu = itemView.findViewById(R.id.kategoriMenu);
            jumlahPerItem = itemView.findViewById(R.id.jumlahPerItem);
            totalCost = itemView.findViewById(R.id.totalCost);
            tambahPesanan = itemView.findViewById(R.id.tambahPesanan);
            kurangPesanan = itemView.findViewById(R.id.kurangPesanan);

            tambahPesanan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (produkModel != null) {
                        produkModel.incrementQuantity();
                        notifyItemChanged(getAdapterPosition());
                        listener.onQuantityChanged();
                    }
                }
            });

            kurangPesanan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (produkModel != null) {
                        produkModel.decrementQuantity();
                        notifyItemChanged(getAdapterPosition());
                        listener.onQuantityChanged();
                    }
                }
            });
        }

        void bind(ProdukModel produkModel) {
            this.produkModel = produkModel;
            namaMenu.setText(produkModel.getNama());
            hargaMenu.setText(String.valueOf("Rp. "+produkModel.getHarga()));
            kategoriMenu.setText(produkModel.getKategori());
            jumlahPerItem.setText(String.valueOf(produkModel.getTemporaryQuantity()));
        }


    }
}
