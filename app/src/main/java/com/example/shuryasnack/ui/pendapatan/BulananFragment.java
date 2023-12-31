package com.example.shuryasnack.ui.pendapatan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shuryasnack.R;
import com.example.shuryasnack.databinding.FragmentBulananBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BulananFragment extends Fragment {

    private FragmentBulananBinding binding;
    private View root;
    private DatabaseReference mDatabase;

    public BulananFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBulananBinding.inflate(inflater,container,false);
        root = binding.getRoot();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        return root;
    }
}