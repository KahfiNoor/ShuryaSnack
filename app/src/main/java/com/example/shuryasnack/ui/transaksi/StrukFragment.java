package com.example.shuryasnack.ui.transaksi;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shuryasnack.R;
import com.example.shuryasnack.databinding.FragmentStrukBinding;
import com.example.shuryasnack.ui.produk.ProdukModel;

import java.util.ArrayList;
import java.util.Locale;

public class StrukFragment extends Fragment {
    private FragmentStrukBinding binding;
    private View root;
    private ArrayList<ProdukModel> produkList;
    private RecyclerView strukTransaksi;
    private TextView totalTransaksiTextView, nominalUangTextView, kembalianTextView, transaksiBaru;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStrukBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        // Check if the arguments bundle is not null
        Bundle dataTransaksi = getArguments();
        Log.d("dataTransaksi", String.valueOf(dataTransaksi));
        if (dataTransaksi != null) {
            // Log the keys present in the bundle
            for (String key : dataTransaksi.keySet()) {
                Log.d("StrukFragment", "Key in Bundle: " + key);
            }

            // Retrieve data from the bundle
            produkList = dataTransaksi.getParcelableArrayList("produkList");
            int totalTransaksi = dataTransaksi.getInt("totalTransaksi", 0);
            int nominalUang = dataTransaksi.getInt("nominalUang", 0);
            int kembalian = dataTransaksi.getInt("kembalian", 0);

            // Log the size of produkList after it's assigned a value
            Log.d("StrukFragment", "onCreateView: produkList size - " + (produkList != null ? produkList.size() : 0));

            // Now you can use the data as needed
            strukTransaksi = root.findViewById(R.id.strukTransaksi);
            strukTransaksi.setLayoutManager(new LinearLayoutManager(requireContext()));

            // Check if produkList is not null and has items
            if (produkList != null && produkList.size() > 0) {
                AdapterStruk adapter = new AdapterStruk(produkList, totalTransaksi, nominalUang, kembalian);
                strukTransaksi.setAdapter(adapter);

                // Set other views in your layout using totalTransaksi, nominalUang, and kembalian
                totalTransaksiTextView = root.findViewById(R.id.totalTransaksi);
                nominalUangTextView = root.findViewById(R.id.nominalTransaksi);
                kembalianTextView = root.findViewById(R.id.kembalianTransaksi);

                // set text xml
                totalTransaksiTextView.setText(String.valueOf("Rp. " + totalTransaksi));
                nominalUangTextView.setText(String.valueOf("Rp. " + nominalUang));
                kembalianTextView.setText(String.valueOf("Rp. " + kembalian));
            } else {
                // Handle the case where produkList is null or empty
                Toast.makeText(requireContext(), "No data available", Toast.LENGTH_SHORT).show();
                Log.d("StrukFragment", "produkList is null");
            }
        } else {
            // Handle the case where the bundle is null (optional)
            Toast.makeText(requireContext(), "No data available", Toast.LENGTH_SHORT).show();
            Log.d("StrukFragment", "bundle is null");
        }

        transaksiBaru = binding.transaksiBaru;
        transaksiBaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(StrukFragment.this).navigate(R.id.action_struk_fragment_to_nav_home);
            }
        });

        return root;
    }
}

