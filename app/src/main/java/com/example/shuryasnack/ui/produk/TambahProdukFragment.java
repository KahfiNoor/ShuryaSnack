package com.example.shuryasnack.ui.produk;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.shuryasnack.R;
import com.example.shuryasnack.databinding.FragmentTambahProdukBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TambahProdukFragment extends Fragment {

    private FragmentTambahProdukBinding binding;
    private DatabaseReference mDatabase;
    private EditText namaMenu, hargaMenu, kategoriMenu;
    private LinearLayout simpanData;

    public TambahProdukFragment() {
        // Required empty public constructor
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTambahProdukBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        namaMenu = binding.inputNamaMenu;
        hargaMenu = binding.inputHargaMenu;
        kategoriMenu = binding.inputKategoriMenu;
        simpanData = binding.simpanData;

        simpanData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getHargaString = hargaMenu.getText().toString();
                String getNama = namaMenu.getText().toString();
                String getKategori = kategoriMenu.getText().toString();

                int getHarga = 0;

                try {
                    getHarga = Integer.parseInt(getHargaString);
                } catch (NumberFormatException e) {
                    // Handle jika parsing gagal
                    e.printStackTrace();
                }

                Log.d(TAG, getNama);
                Log.d(TAG, String.valueOf(getHarga));
                Log.d(TAG, getKategori);

                if (getNama.isEmpty()) {
                    namaMenu.setError("Tidak boleh kosong");
                } else if (getHarga == 0) {
                    hargaMenu.setError("Tidak boleh kosong");
                } else if (getKategori.isEmpty()) {
                    kategoriMenu.setError("Tidak boleh kosong");
                } else {
                    // Generate produkId secara manual atau menggunakan push().getKey()
                    String produkId = mDatabase.child("menus").push().getKey();

                    tambahProduk(produkId, getNama, getHarga, getKategori);
                }
            }

            public void tambahProduk(String produkId, String nama, int harga, String kategori) {
                try {
                    ProdukModel menu = new ProdukModel(produkId, nama, harga, kategori);
                    mDatabase.child("menus").child(produkId).setValue(menu)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Data berhasil ditambahkan");
                                    Toast.makeText(requireContext(), "Produk berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                                    // Tambahkan notifikasi atau aksi lain yang sesuai di sini
                                    NavHostFragment.findNavController(TambahProdukFragment.this).navigate(R.id.action_tambah_produk_to_nav_produk);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e(TAG, "Gagal menambahkan data", e);
                                    Toast.makeText(requireContext(), "Gagal menambahkan data", Toast.LENGTH_SHORT).show();
                                    // Tambahkan tindakan penanganan kesalahan yang sesuai di sini
                                }
                            });
                } catch (Exception e) {
                    Log.e(TAG, "Gagal membuat objek ProdukModel", e);
                    // Tambahkan tindakan penanganan kesalahan yang sesuai di sini
                }
            }
        });

        return root;
    }
}