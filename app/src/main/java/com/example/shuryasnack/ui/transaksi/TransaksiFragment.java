package com.example.shuryasnack.ui.transaksi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shuryasnack.R;
import com.example.shuryasnack.databinding.FragmentTransaksiBinding;
import com.example.shuryasnack.ui.produk.ProdukModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TransaksiFragment extends Fragment {
    private FragmentTransaksiBinding binding;
    private View root;
    private RecyclerView kasirMenuTransaksi;
    private AdapterTransaksi adapterTransaksi;
    private DatabaseReference mDatabase;

    public TransaksiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTransaksiBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // Menerima data dari HomeFragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<ProdukModel> produkDipilih = bundle.getParcelableArrayList("produkDipilih");

            // Inisialisasi RecyclerView
            kasirMenuTransaksi = root.findViewById(R.id.kasirMenuTransaksi);
            kasirMenuTransaksi.setHasFixedSize(true);
            kasirMenuTransaksi.setLayoutManager(new LinearLayoutManager(getActivity()));

            // Setel adapter pada RecyclerView
            adapterTransaksi = new AdapterTransaksi(produkDipilih);
            kasirMenuTransaksi.setAdapter(adapterTransaksi);

            // Hitung dan tampilkan total transaksi
            hitungDanTampilkanTotalTransaksi(produkDipilih);
            // Menangani klik pada tombol transaksi
            // Menangani klik pada tombol transaksi
            TextView tombolTransaksi = root.findViewById(R.id.tombolTransaksi);
            tombolTransaksi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText nominalUangEditText = root.findViewById(R.id.nominalUang);
                    String nominalUangString = nominalUangEditText.getText().toString();

                    if (!nominalUangString.isEmpty()) {
                        int nominalUang = Integer.parseInt(nominalUangString);

                        // Panggil metode untuk menyimpan transaksi ke database
                        tambahTransaksi(produkDipilih, nominalUang);
                    } else {
                        Toast.makeText(requireContext(), "Masukkan nominal uang", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return root;
    }

    private void tambahTransaksi(ArrayList<ProdukModel> produkDipilih, int nominalUang) {
        // Mendapatkan instance Timestamp untuk waktu saat ini
        long timestamp = System.currentTimeMillis();

        // Hitung total transaksi berdasarkan produk yang dipilih
//        int totalTransaksi = 0;
//        for (ProdukModel produkModel : produkDipilih) {
//            totalTransaksi += produkModel.getTotalPerItem();
//        }

        // Hitung total transaksi berdasarkan produk yang dipilih
        final int totalTransaksi = calculateTotalTransaksi(produkDipilih);

        // Hitung kembalian
        int kembalian = nominalUang - totalTransaksi;

        // Buat objek TransaksiModel dari produkDipilih, nominal uang, total transaksi, dan timestamp
        TransaksiModel transaksiModel = new TransaksiModel();
        transaksiModel.setProdukList(produkDipilih);
        transaksiModel.setNominalUang(nominalUang);
        transaksiModel.setTotalTransaksi(totalTransaksi);
        transaksiModel.setKembalian(kembalian);
        transaksiModel.setTimestamp(timestamp);

        // Generate transaksiId secara manual atau menggunakan push().getKey()
        String transaksiId = mDatabase.child("transaksi").push().getKey();

        // Tambahkan transaksi ke database
        mDatabase.child("transaksi").child(transaksiId).setValue(transaksiModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Tampilkan pesan sukses atau lakukan tindakan lain setelah transaksi berhasil ditambahkan
                        Toast.makeText(requireContext(), "Transaksi berhasil", Toast.LENGTH_SHORT).show();
                        // Buat objek Bundle untuk mengirim data ke StrukFragment
                        Bundle dataTransaksi = new Bundle();
                        dataTransaksi.putParcelableArrayList("produkList", produkDipilih);
                        dataTransaksi.putInt("totalTransaksi", totalTransaksi);
                        dataTransaksi.putInt("nominalUang", nominalUang);
                        dataTransaksi.putInt("kembalian", kembalian);

                        Log.d("TransaksiFragment", "onSuccess: Navigating to StrukFragment");
                        NavHostFragment.findNavController(TransaksiFragment.this).navigate(R.id.action_transaksi_fragment_to_struk_fragment, dataTransaksi);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle kesalahan saat menambahkan transaksi
                        Toast.makeText(requireContext(), "Gagal menambahkan transaksi", Toast.LENGTH_SHORT).show();
                        Log.e("TransaksiFragment", "Gagal menambahkan transaksi", e);
                    }
                });
    }


    private void hitungDanTampilkanTotalTransaksi(ArrayList<ProdukModel> produkDipilih) {
        // Hitung total transaksi berdasarkan produk yang dipilih
        int totalTransaksi = calculateTotalTransaksi(produkDipilih);

        // Tampilkan total transaksi
        TextView totalTransaksiTextView = root.findViewById(R.id.totalTransaksi);
        totalTransaksiTextView.setText(String.valueOf("Rp. " + totalTransaksi));
    }

    // Helper method to calculate totalTransaksi
    private int calculateTotalTransaksi(ArrayList<ProdukModel> produkDipilih) {
        int totalTransaksi = 0;
        for (ProdukModel produkModel : produkDipilih) {
            totalTransaksi += produkModel.getTotalPerItem();
        }
        return totalTransaksi;
    }
}
