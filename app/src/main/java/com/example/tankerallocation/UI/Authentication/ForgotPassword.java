package com.example.tankerallocation.UI.Authentication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.tankerallocation.Dao.DaoMaster;
import com.example.tankerallocation.Dao.DaoSession;
import com.example.tankerallocation.Dao.UserDetails;
import com.example.tankerallocation.R;
import com.example.tankerallocation.RetrofitService.RetrofitClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import org.greenrobot.greendao.database.Database;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgotPassword extends Fragment {
    private TextInputEditText etEmail;
    private TextInputEditText confirmpass;
    private TextInputEditText newpass;
    private MaterialButton next;
    private TextInputLayout confirmPassTil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        etEmail = view.findViewById(R.id.edit_email);
        newpass = view.findViewById(R.id.edit_new_pass);
        confirmpass = view.findViewById(R.id.edit_confirmpass);
        confirmPassTil = view.findViewById(R.id.layout_confirm_pass);
        next = view.findViewById(R.id.bt_forget);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newpas = newpass.getText().toString();
                String con_pass = confirmpass.getText().toString();
                String email = etEmail.getText().toString();
                //if(!newpas.isEmpty() && !con_pass.isEmpty() &&email.isEmpty()){

                    if (!TextUtils.isEmpty(newpas) && !TextUtils.isEmpty(con_pass) && !TextUtils.isEmpty(email)) {
                        if (newpas.equals(con_pass)) {
                            confirmPassTil.setErrorEnabled(false);
                            System.out.println("Valid");
                            forgotapi(email,newpas,con_pass);
                        } else if (!newpas.equals(con_pass)) {
                           // confirmPassTil.setErrorEnabled(true);
                            validation();
                        }

                        //forgotapi(email,newpas,con_pass);


                   /* else {
                        validation();
                    }*/
                }
                else {
                    Snackbar.make(getView(), getString(R.string.empty_field_error), Snackbar.LENGTH_LONG).show();
                }

            }

        });
        return view;
    }

    private void mismatchvalidation() {
    }

    private void forgotapi(String email, String newpas, String con_pass) {


        JsonObject postParam = new JsonObject();
        try {
            postParam.addProperty("emailid", email);
            postParam.addProperty("new_pass", newpas);
            postParam.addProperty("con_pass", con_pass);
        } finally {

        }
        Call<JsonObject> call = RetrofitClient.getInstance(getActivity())
                .getApi()
                .ForgotPassword(postParam);
        // Call<JsonObject> call = apiService.Login(postParam);
        call.enqueue(
                new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("respone_login", "Getting response from server: " + response);

                        JSONObject movieObject = null;
                        try {
                            movieObject = new JSONObject(String.valueOf(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {


                            String userid = movieObject.getString("user_id");
                            String password = movieObject.getString("new_pass");
                            String status = movieObject.getString("status");

                            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getContext(), "users.db");
                            Database database = devOpenHelper.getWritableDb();
                            DaoSession daoSession = new DaoMaster(database).newSession();
                            UserDetails userdet = new UserDetails();
                            userdet.setPassword(password);
                            daoSession.insert(userdet);

                            if(status.equalsIgnoreCase("Done")) {

                                Navigation.findNavController(getView()).navigate(R.id.action_to_forgot_pass_to_login);
                            }
                            // return true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("response", "Getting response from server: " + t);

                    }
                }
        );

    }

    private void validation() {

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setMessage(getString(R.string.passwordchange));
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok_txt),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

        alertDialog.show();

        Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
        layoutParams.weight = 10;
        btnPositive.setLayoutParams(layoutParams);
    }
}