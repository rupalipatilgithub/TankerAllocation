package com.example.tankerallocation.UI;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.tankerallocation.Model.Approved;
import com.example.tankerallocation.R;
import com.example.tankerallocation.RetrofitService.RetrofitClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class DisplaySuccessDelivery extends Fragment {

    private TextView tokenno;
    private TextView val_tok_no;
    private TextView tv_custname;
    private TextView val_custname;
    private TextView tv_cust_mob;
    private TextView va_cust_mob;
    private TextView tv_builname;
    private TextView val_build_name;
    private TextView tv_add;
    private TextView val_add;
    private TextView tv_quantity;
    private TextView val_quantity;
    private TextView tv_zone;
    private TextView val_zone;
    private TextView tv_tanker_num;
    private TextView val_tankerno;
    private TextView tv_driver_name;
    private TextView val_dri_name;
    private TextView tv_driver_mob;
    private TextView val_driver_mob;
    private TextView tv_dliverydate;
    private TextView val_deliverydat;
    private TextView tv_sector;
    private TextView val_sector;
    private TextView tv_plot;
    private TextView val_plot;
    private TextView approvedby_tv;
    private TextView val_approvedby;
    private TextView val_tanker_filling_loc;
    private TextView tv_consumer_no;
    private TextView val_consumer_no;
    private Button deliverynote;
    Approved approved;

    private Intent intent;
    private File destination = null;
    File root;
    AssetManager assetManager;
    Bitmap pageImage;
    private static final int PERMISSION_REQUEST_CODE = 200;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_display_success_delivery, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tokenno = view.findViewById(R.id.tv_tokenno);
        val_tok_no = view.findViewById(R.id.val_token_no);
        tv_custname = view.findViewById(R.id.tv_cust_name);
        val_custname = view.findViewById(R.id.val_cus_name);
        tv_cust_mob = view.findViewById(R.id.tv_cust_mob);
        va_cust_mob = view.findViewById(R.id.val_cus_mob_no);
        tv_builname = view.findViewById(R.id.tv_building_name);
        val_build_name = view.findViewById(R.id.val_building_name);
        val_tanker_filling_loc = view.findViewById(R.id.val_tanker_filling_loc);
        // tv_add = view.findViewById(R.id.tv_address);
        //  val_add = view.findViewById(R.id.val_address);
        tv_sector = view.findViewById(R.id.tv_sector);
        val_sector = view.findViewById(R.id.val_sector);
        tv_plot = view.findViewById(R.id.tv_plotno);
        val_plot = view.findViewById(R.id.val_plotno);
        tv_quantity = view.findViewById(R.id.tv_quantity);
        val_quantity = view.findViewById(R.id.val_quantity);
        approvedby_tv = view.findViewById(R.id.tv_approvedby);
        val_approvedby = view.findViewById(R.id.val_approvedby);
        tv_zone = view.findViewById(R.id.tv_zone);
        val_zone = view.findViewById(R.id.val_zone);
        tv_tanker_num = view.findViewById(R.id.tv_tankernum);
        val_tankerno = view.findViewById(R.id.val_tankerno);
        tv_driver_name = view.findViewById(R.id.tv_driver_name);
        val_dri_name = view.findViewById(R.id.val_drivername);
        tv_driver_mob = view.findViewById(R.id.tv_driverMob);
        val_driver_mob = view.findViewById(R.id.val_driverno);
        tv_dliverydate = view.findViewById(R.id.tv_deliverydate);
        val_deliverydat = view.findViewById(R.id.val_deliverydate);
        tv_consumer_no = view.findViewById(R.id.tv_consumerno);
        val_consumer_no = view.findViewById(R.id.val_consumer_no);
        deliverynote = view.findViewById(R.id.btnviewdeliverynote);


        approved = new Approved();
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            approved = (Approved) bundle.getSerializable("approved");
            Log.e("tokenid", approved.getToken_no() + "kkk");

        }


        val_tok_no.setText(approved.getToken_no());
        val_custname.setText(approved.getCust_name());
        va_cust_mob.setText(approved.getCust_mob_no());
        val_zone.setText(approved.getNode_id());
        val_plot.setText(approved.getAddress());
        val_build_name.setText(approved.getSociety_name());

        val_quantity.setText(approved.getQuantity());
        val_approvedby.setText(approved.getApproved_by());

        val_tankerno.setText(approved.getTanker_no());
        val_dri_name.setText(approved.getDrivername());
        val_driver_mob.setText(approved.getDrivermob_no());
        val_tanker_filling_loc.setText(approved.getDrivermob_no());
        val_consumer_no.setText(approved.getConsumer_no());


        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        val_deliverydat.setText(formattedDate);

        deliverynote.setOnClickListener(v -> {

            if (checkPermission()) {
                intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                // checkNCallCamera();
                // Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 7);
            } else {
                requestPermission();
            }

            // Navigation.findNavController(getView()).navigate(R.id.action_delivery_det_show_camera_screen);


        });

    }


    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    // main logic

                    intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    // checkNCallCamera();
                    // Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 7);

                } else {
                    Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override

                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                // requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            //  tanker_photo.setImageBitmap(bitmap);

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            Log.e("Activity", "Pick from Camera::>>> ");

            String dirPath = getContext().getFilesDir().getAbsolutePath() + "/TankerAllocation/Photo/";
            File appDir = new File(dirPath);
            if (!appDir.exists()) {
                appDir.mkdirs();
            }
            // String newImgPath = dirPath + "pro_" + System.currentTimeMillis();

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            destination = new File(dirPath, "IMG_" + timeStamp + ".jpg");
            FileOutputStream fo;

            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

           /* File file=new File(String.valueOf(destination));
            Bundle bundle = new Bundle();
            bundle.putString("new_img_path", file.getAbsolutePath());*/

            uploaddeliverynote();

            Toast.makeText(getActivity(), "Delivery Note Uploaded Succesfully", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(getView()).navigate(R.id.action_delivery_det_to_tanker_allocation);

        }
    }

    private void uploaddeliverynote() {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        JsonObject postParam = new JsonObject();
        try {
            postParam.addProperty("token_no", approved.getToken_no());
            postParam.addProperty("consumer_no", approved.getConsumer_no());
            postParam.addProperty("delivery_note_photo", "");
            postParam.addProperty("demandgeneration_date", formattedDate);
            postParam.addProperty("status", "work completed");
        } finally {

        }
        Call<JsonObject> call = RetrofitClient.getInstance(getActivity())
                .getApi()
                .SuccessData(postParam);

        call.enqueue(
                new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("response", "Getting response from server: " + response);

                        JSONObject movieObject = null;
                        try {
                            movieObject = new JSONObject(String.valueOf(response.body()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            String token_no = movieObject.getString("token_no");
                            String status = movieObject.getString("Work Completed");


                            if (status.equals("work completed")) {

                                Toast.makeText(getActivity(), "Delivery note uploaded succesfully for" + token_no, Toast.LENGTH_SHORT).show();
                            } else {

                                Snackbar msg = Snackbar.make(view, "upload error", Snackbar.LENGTH_INDEFINITE);
                                msg.setAction(R.string.ok_txt, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        msg.dismiss();
                                    }
                                }).show();
                            }


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

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


}

