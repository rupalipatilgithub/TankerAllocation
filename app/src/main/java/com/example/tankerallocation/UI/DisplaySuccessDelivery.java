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
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tankerallocation.R;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    private TextView val_deliverydate;
    private Button deliverynote;

    private Intent intent;
    private File destination = null;
    File root;
    AssetManager assetManager;
    Bitmap pageImage;
    private static final int PERMISSION_REQUEST_CODE = 200;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_success_delivery, container, false);
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
        tv_add = view.findViewById(R.id.tv_address);
        val_add = view.findViewById(R.id.val_address);
        tv_quantity = view.findViewById(R.id.tv_quantity);
        val_quantity = view.findViewById(R.id.val_quantity);
        tv_zone = view.findViewById(R.id.tv_zone);
        tv_tanker_num = view.findViewById(R.id.tv_tankernum);
        val_tankerno = view.findViewById(R.id.val_tankerno);
        tv_driver_name = view.findViewById(R.id.tv_driver_name);
        val_dri_name = view.findViewById(R.id.val_drivername);
        tv_driver_mob = view.findViewById(R.id.tv_driverMob);
        val_driver_mob = view.findViewById(R.id.val_driverno);
        tv_dliverydate = view.findViewById(R.id.tv_deliverydate);
        val_deliverydate = view.findViewById(R.id.val_deiverydate);
        deliverynote = view.findViewById(R.id.btnviewdeliverynote);

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

            Toast.makeText(getActivity(),"Delivery Note Uploaded Succesfully",Toast.LENGTH_SHORT).show();
            Navigation.findNavController(getView()).navigate(R.id.action_delivery_det_to_tanker_allocation);

        }
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

