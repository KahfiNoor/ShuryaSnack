package com.example.shuryasnack.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shuryasnack.R;
import com.example.shuryasnack.databinding.FragmentHomeBinding;
import com.example.shuryasnack.ui.produk.ProdukModel;
import com.example.shuryasnack.ui.transaksi.AdapterTransaksi;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements AdapterKasir.AdapterKasirListener {

    private FragmentHomeBinding binding;
    private View root;
    private FirebaseDatabase mdatabase;
    private DatabaseReference reference;

    private AdapterKasir adapterKasir;
    private ArrayList<ProdukModel> list;
    private RecyclerView kasirMenu;
    private TextView tombolBayar;
    private Handler handler = new Handler();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        mdatabase = FirebaseDatabase.getInstance();
        reference = mdatabase.getReference("menus");

        TextView totalTransaksiTextView = root.findViewById(R.id.totalTransaksi);

        kasirMenu = binding.kasirMenu;
        kasirMenu.setHasFixedSize(true);
        kasirMenu.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        list = new ArrayList<>();
        adapterKasir = new AdapterKasir(requireActivity(), list, this);
        kasirMenu.setAdapter(adapterKasir);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProdukModel produkModel = dataSnapshot.getValue(ProdukModel.class);
                    list.add(produkModel);
                }
                adapterKasir.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ImageView tombolBatal = binding.tombolBatal;
        tombolBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPesanan();
            }
        });

        tombolBayar = binding.tombolBayar;
        tombolBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ProdukModel> produkDipilih = getProdukDipilih();

                // Pastikan ada setidaknya satu item yang dipilih sebelum pindah ke TransaksiFragment
                if (produkDipilih.size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("produkDipilih", produkDipilih);

                    // Navigasi ke TransaksiFragment dengan membawa Bundle
                    NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_nav_home_to_transaksi_fragment, bundle);
                } else {
                    // Tampilkan pesan bahwa tidak ada item yang dipilih
                    Toast.makeText(getContext(), "Tidak ada item yang dipilih", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    private void resetPesanan() {
        for (ProdukModel produkModel : list) {
            produkModel.setTemporaryQuantity(0);
        }

        adapterKasir.notifyDataSetChanged();
        calculateTotalCostAndItems();
    }


    @Override
    public void onQuantityChanged() {
        handler.removeCallbacksAndMessages(null);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapterKasir.notifyDataSetChanged();
                calculateTotalCostAndItems();
            }
        }, 300);
    }

    private void calculateTotalCostAndItems() {
        int totalCost = 0;
        int totalItems = 0;

        for (ProdukModel produkModel : list) {
            totalCost += produkModel.getTotalPerItem();
            totalItems += produkModel.getTemporaryQuantity();
        }

        // Update the TextViews in fragment_home.xml
        if (totalCost == 0 || totalItems == 0){
            binding.totalCost.setText("Total Cost");
            binding.totalItems.setText("Tidak ada menu yang dipilih");
        } else {
            binding.totalCost.setText(String.valueOf("Total : "+totalCost));
            binding.totalItems.setText(String.valueOf(totalItems+" Dipilih"));
        }
    }

    private ArrayList<ProdukModel> getProdukDipilih() {
        ArrayList<ProdukModel> produkDipilih = new ArrayList<>();
        for (ProdukModel produkModel : list) {
            if (produkModel.getTemporaryQuantity() > 0) {
                produkDipilih.add(produkModel);
            }
        }
        return produkDipilih;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
