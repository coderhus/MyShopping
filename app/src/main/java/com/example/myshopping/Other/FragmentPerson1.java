package com.example.myshopping.Other;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myshopping.Adapter.ItemBuyerAdapter;
import com.example.myshopping.Model.Notifications;
import com.example.myshopping.Model.Orders;
import com.example.myshopping.Model.Products;
import com.example.myshopping.R;
import com.example.myshopping.SupportCode.SupportCode;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPerson1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPerson1 extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    List<Products> list;

    public FragmentPerson1() {

    }


    public static FragmentPerson1 newInstance(String param1, String param2) {
        FragmentPerson1 fragment = new FragmentPerson1();
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
        View view = inflater.inflate(R.layout.fragment_person1, container, false);
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
       list =new ArrayList<>();
        // Most viewed posts
        Query orderslist = mDatabase.child("Orders_Buyer").child(SupportCode.getUID());
        orderslist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot postsnap: snapshot.getChildren()) {
                    Orders a = postsnap.getValue(Orders.class);
                    list.addAll(a.getList());

                }
                //
                if(!list.isEmpty()){

                    recyclerView =view.findViewById(R.id.recyclerview);
                    ItemBuyerAdapter itemAdapter =new ItemBuyerAdapter(view.getContext(),list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));
                    recyclerView.setAdapter(itemAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
     /*    list.add(new Products());
        list.add(new Products());
        list.add(new Products());*/


        return view;
    }
}