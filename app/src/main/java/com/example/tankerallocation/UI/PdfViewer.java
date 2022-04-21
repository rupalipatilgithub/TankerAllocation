package com.example.tankerallocation.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tankerallocation.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;

import java.io.File;


public class PdfViewer extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View vIew = inflater.inflate(R.layout.fragment_pdf_viewer, container, false);

       PDFView pdfView= vIew.findViewById(R.id.pdfview);
        String pdfpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
       File file = new File(pdfpath,"mypdf.pdf");


        File file1 = new File(pdfpath,"mypdf.pdf");
        pdfView.fromFile(file1).load();
     return vIew;
    }
    public void OnLoadCompleteListner(){}

    public void OnPageChangeListner(){}
}