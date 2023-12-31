package com.example.shuryasnack.ui.riwayat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shuryasnack.R;
import com.example.shuryasnack.databinding.FragmentRiwayatBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RiwayatFragment extends Fragment {

    private FragmentRiwayatBinding binding;
    private View root;
    private DatabaseReference mDatabase;

    public RiwayatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRiwayatBinding.inflate(inflater,container,false);
        root = binding.getRoot();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        return root;
    }
}