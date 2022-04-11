package com.example.tankerallocation.UI;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.tankerallocation.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class TankerDetails extends Fragment {
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tanker_details, container, false);
        MaterialAutoCompleteTextView tanker_num = view.findViewById(R.id.autoComplete_totanker_num);
        MaterialAutoCompleteTextView mob_num = view.findViewById(R.id.autoComplete_to_dri_mob);
        MaterialAutoCompleteTextView dirver_name = view.findViewById(R.id.autoComplete_to_driver_name);
        MaterialButton upload_photo = view.findViewById(R.id.choose_img_tanker_btn);
        ImageView tanker_photo = view.findViewById(R.id.img_tankerPhoto);
        //MaterialAutoCompleteTextView cityNameTv = view.findViewById(R.id.autoComplete_tocity);
        /*Call<CityResponse> cityResponse = RetrofitClient.getInstance(getActivity()).getApi().getCities();
        cityResponse.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.city_list_item_style, R.id.tV1);
                List<SpinnerCity> cities = response.body().getCities();
                cities.forEach(e -> {
                    arrayAdapter.add(e.getCityName());
                });

                cityNameTv.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
            }
        });*/

        final String[] permissions = {
                "android.permission.CAMERA",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.MANAGE_EXTERNAL_STORAGE"
        };

        upload_photo.setOnClickListener(v -> {

            if(ContextCompat.checkSelfPermission(getContext(), permissions[0]) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(getContext(), permissions[1]) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(getContext(), permissions[2]) == PackageManager.PERMISSION_GRANTED) {
                intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
               // checkNCallCamera();
               // Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 7);
                //Navigation.findNavController(getView()).navigate(R.id.action_upload_doc_to_camera);
            } /*else {
                activityResultLauncher.launch(permissions);
            }*/
            intent = null;

        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
          //  imageView.setImageBitmap(bitmap);
        }
    }


}