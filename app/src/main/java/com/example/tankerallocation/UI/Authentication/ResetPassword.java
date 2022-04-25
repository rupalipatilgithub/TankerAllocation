package com.example.tankerallocation.UI.Authentication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tankerallocation.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;


public class ResetPassword extends Fragment {
private TextInputEditText confirmpass;
private TextInputEditText newpass;
Button updatepass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        newpass = view.findViewById(R.id.edit_new_pass);
        confirmpass = view.findViewById(R.id.edit_confirmpass);
        updatepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newpas = newpass.getText().toString();
                String con_pass = confirmpass.getText().toString();
                if(!newpas.isEmpty() && !con_pass.isEmpty()){
                    if(newpas == con_pass){
                        Navigation.findNavController(getView()).navigate(R.id.action_to_reset_pass_to_login);
                    }
                    else {
                        validation();
                    }
                }
                else {
                    Snackbar.make(getView(), getString(R.string.empty_field_error), Snackbar.LENGTH_LONG).show();
                }

            }
        });
        return view;
    }

    private void validation() {
    }
}