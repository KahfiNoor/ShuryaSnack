package com.example.shuryasnack.ui.pendapatan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shuryasnack.R;
import com.example.shuryasnack.databinding.FragmentPendapatanBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PendapatanFragment extends Fragment {

    private FragmentPendapatanBinding binding;
    private View root;
    private DatabaseReference mDatabase;

    public PendapatanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPendapatanBinding.inflate(inflater,container,false);
        root = binding.getRoot();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        binding.pendapatanMingguan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigasi ke pendapatan mingguan
                NavHostFragment.findNavController(PendapatanFragment.this).navigate(R.id.action_nav_pendapatan_to_pendapatan_mingguan);
            }
        });

        binding.pendapatanBulanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigasi ke pendapatan bulanan
                NavHostFragment.findNavController(PendapatanFragment.this).navigate(R.id.action_nav_pendapatan_to_pendapatan_bulanan);
            }
        });

        binding.pendapatanTahunan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigasi ke pendapatan tahunan
                NavHostFragment.findNavController(PendapatanFragment.this).navigate(R.id.action_nav_pendapatan_to_pendapatan_tahunan);
            }
        });

        return root;
    }
}