package com.example.shuryasnack.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shuryasnack.ui.produk.ProdukModel;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<ArrayList<ProdukModel>> selectedMenuList = new MutableLiveData<>();

    public void setSelectedMenuList(ArrayList<ProdukModel> menuList) {
        selectedMenuList.setValue(menuList);
    }

    public LiveData<ArrayList<ProdukModel>> getSelectedMenuList() {
        return selectedMenuList;
    }

    public void updateSelectedMenuList(ArrayList<ProdukModel> list) {
        selectedMenuList.postValue(list);
    }
}

