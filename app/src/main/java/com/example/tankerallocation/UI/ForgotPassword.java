package com.example.tankerallocation.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tankerallocation.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


public class ForgotPassword extends Fragment {
    private TextInputEditText etEmail;
    private MaterialButton next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        etEmail = view.findViewById(R.id.edit_email);
        next = view.findViewById(R.id.bt_forget);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Navigation.findNavController(getView()).navigate(R.id.action_forgot_pass_to_change_pass);
            }
        });
        return view;
    }
}