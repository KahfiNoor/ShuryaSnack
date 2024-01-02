package com.example.shuryasnack.ui.produk;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shuryasnack.R;
import com.example.shuryasnack.databinding.FragmentProdukBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProdukFragment extends Fragment {

    private FragmentProdukBinding binding;
    private View root;
    FirebaseDatabase mdatabase;
    DatabaseReference reference;

    AdapterProduk adapterProduk;
    ArrayList<ProdukModel> list;
    RecyclerView daftarMenu;

    public ProdukFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProdukBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        mdatabase = FirebaseDatabase.getInstance(); // Initialize mdatabase

        reference = mdatabase.getReference("menus");

        daftarMenu = binding.daftarMenu; // Use binding for RecyclerView
        daftarMenu.setHasFixedSize(true);
        daftarMenu.setLayoutManager(new LinearLayoutManager(getActivity()));

        list = new ArrayList<>();
        adapterProduk = new AdapterProduk(getActivity(), list);
        daftarMenu.setAdapter(adapterProduk);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list before adding new data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProdukModel produkModel = dataSnapshot.getValue(ProdukModel.class);
                    list.add(produkModel);
                }
                adapterProduk.notifyDataSetChanged(); // Notify adapter after data change
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.buttonTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ProdukFragment.this).navigate(R.id.action_nav_produk_to_tambah_produk);
            }
        });

        return root;
    }
}
