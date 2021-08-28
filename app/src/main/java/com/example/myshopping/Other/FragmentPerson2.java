package com.example.myshopping.Other;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myshopping.Adapter.ItemBuyerAdapter;

import com.example.myshopping.Adapter.ItemSellerAdapter;
import com.example.myshopping.Model.Products;
import com.example.myshopping.R;

import java.util.ArrayList;
import java.util.List;


public class FragmentPerson2 extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    List<Products> list;

    public FragmentPerson2() {

    }


    public static FragmentPerson2 newInstance(String param1, String param2) {
        FragmentPerson2 fragment = new FragmentPerson2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person2, container, false);

        list =new ArrayList<>();
        list.add(new Products());
        list.add(new Products());
        list.add(new Products());

        recyclerView =view.findViewById(R.id.recyclerview);
        ItemSellerAdapter itemAdapter = new ItemSellerAdapter(view.getContext(),list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(itemAdapter);
        return view;
    }
}