package com.example.tankerallocation.UI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tankerallocation.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class DeliveryNote extends Fragment {
ImageView deliverynote;
    private String imagePath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivery_note, container, false);
        deliverynote=  view.findViewById(R.id.delivery_note);

//        Bundle bundle = getArguments();
//        imagePath = bundle.getString("new_img_path");
//        FileInputStream fileInputStream = null;
//        try {
//            fileInputStream = new FileInputStream(new File(imagePath));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
//        deliverynote.setImageBitmap(bitmap);
        return view;
    }
}