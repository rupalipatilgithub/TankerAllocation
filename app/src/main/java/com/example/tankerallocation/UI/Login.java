package com.example.tankerallocation.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tankerallocation.Dao.DaoMaster;
import com.example.tankerallocation.Dao.DaoSession;
import com.example.tankerallocation.Dao.UserDetails;
import com.example.tankerallocation.R;
import com.example.tankerallocation.RetrofitService.APIService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import org.greenrobot.greendao.database.Database;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends Fragment {
    private MaterialButton btnLogin;
    private ImageView topMainLogo;
    private TextView forgotpass;
    APIService apiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        btnLogin = view.findViewById(R.id.btnServerLogin);
        //topMainLogo = view.findViewById(R.id.top_main_logo);
        TextInputEditText emailaddress = view.findViewById(R.id.email);
        TextInputEditText password = view.findViewById(R.id.pass);
        forgotpass = view.findViewById(R.id.tv_forgot_pss);
        forgotpass.setOnClickListener(v -> {
            // Navigation.findNavController(view).navigate(R.id.action_login_to_forgotpass);
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailaddress.getText().toString();
                String pass = password.getText().toString();


                // boolean pass_check = isValidEmail(email);
                if (!email.isEmpty() && !pass.isEmpty()) {

                   // boolean check = CheckLogin(email, pass);//temp comment 18/12

                    boolean check = true;
                    if (check) {
                        Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(view).navigate(R.id.action_login_to_TankerAllocation);


                    } else {
                        Snackbar msg = Snackbar.make(view, "Invalid Login Credentials", Snackbar.LENGTH_INDEFINITE);
                        msg.setAction(R.string.ok_txt, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                msg.dismiss();
                            }
                        }).show();
                    }
                } else {
                    Snackbar.make(getView(), getString(R.string.empty_field_error), Snackbar.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    private boolean CheckLogin(String email, String pass) {
        String android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        JsonObject postParam = new JsonObject();
        try {
            postParam.addProperty("email_id", email);
            postParam.addProperty("pass", pass);
            postParam.addProperty("device_id", android_id);
        } finally {

        }

        Call<JsonObject> call = apiService.userlogin(postParam);
        call.enqueue(
                new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("response", "Getting response from server: " + response);

                        JSONObject movieObject = null;
                        try {
                            movieObject = new JSONObject(String.valueOf(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            String userId = movieObject.getString("user_id");
                            String zone = movieObject.getString("zone_id");
                            String status = movieObject.getString("status");

                            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getContext(), "users.db");
                            Database database = devOpenHelper.getWritableDb();
                            DaoSession daoSession = new DaoMaster(database).newSession();

                            UserDetails userDetails = new UserDetails();
                            userDetails.setUserid(userId);
                            userDetails.setZone(zone);
                            userDetails.setStatus(status);
                            daoSession.insert(userDetails);

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
        return true;
    }
}