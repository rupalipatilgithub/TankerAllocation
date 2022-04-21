package com.example.tankerallocation.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tankerallocation.Adapter.ApprovedAdapter;
import com.example.tankerallocation.R;


public class ApprovedStatus extends Fragment {

    private RecyclerView pendingitemRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pending_status, container, false);
        pendingitemRecyclerView = view.findViewById(R.id.item_list);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        ApprovedAdapter pendingAdapter = new ApprovedAdapter();
        pendingitemRecyclerView.setLayoutManager(linearLayoutManager);
        pendingitemRecyclerView.setAdapter(pendingAdapter);
    }
}