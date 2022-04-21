package com.example.tankerallocation.UI;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tankerallocation.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.xmp.impl.Base64;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.pdmodel.PDPage;
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream;
import com.tom_roush.pdfbox.pdmodel.font.PDFont;
import com.tom_roush.pdfbox.pdmodel.font.PDType1Font;
import com.tom_roush.pdfbox.pdmodel.graphics.image.JPEGFactory;
import com.tom_roush.pdfbox.pdmodel.graphics.image.LosslessFactory;
import com.tom_roush.pdfbox.pdmodel.graphics.image.PDImageXObject;
import com.tom_roush.pdfbox.util.PDFBoxResourceLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class TankerDetails extends Fragment {
    private Intent intent;
    ImageView tanker_photo;
    private File destination = null;
    File root;
    AssetManager assetManager;
    Bitmap pageImage;
    private static final int PERMISSION_REQUEST_CODE = 200;

    private TextInputEditText token_no_edt;
    private TextInputEditText cust_name_edt;
    private TextInputEditText builname_edt;
    private TextInputEditText address_edt;
    private TextInputEditText quantity_edt;
    private TextInputEditText approvedby_edt;
    private TextInputEditText zone_edit;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tanker_details, container, false);
        token_no_edt = view.findViewById(R.id.edt_token_no);
        cust_name_edt = view.findViewById(R.id.edt_custname);
        builname_edt = view.findViewById(R.id.edt_buildingname);
        address_edt = view.findViewById(R.id.edt_address);
        quantity_edt = view.findViewById(R.id.edt_quantity);
        approvedby_edt = view.findViewById(R.id.edt_approvedby);
        zone_edit = view.findViewById(R.id.edt_zone);
        MaterialAutoCompleteTextView tanker_num = view.findViewById(R.id.autoComplete_totanker_num);
        MaterialAutoCompleteTextView mob_num = view.findViewById(R.id.autoComplete_to_dri_mob);
        MaterialAutoCompleteTextView dirver_name = view.findViewById(R.id.autoComplete_to_driver_name);
        MaterialButton upload_photo = view.findViewById(R.id.choose_img_tanker_btn);
        tanker_photo = view.findViewById(R.id.img_tankerPhoto);
        Button generatepdf = view.findViewById(R.id.next);
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



        upload_photo.setOnClickListener(v -> {

                    if (checkPermission()) {
                        if(true) {

                            intent = new Intent();
                            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                            // checkNCallCamera();
                            // Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 7);
                        }

                    } else {
                        requestPermission();
                    }

                //Navigation.findNavController(getView()).navigate(R.id.action_upload_doc_to_camera);
            /*else {
                activityResultLauncher.launch(permissions);
            }*/
           // intent = null;

        });

        generatepdf.setOnClickListener(v -> {
          //  createPdf();

            String tan_no = tanker_num.getText().toString();
            String mobile_no = mob_num.getText().toString();
            String drivername = dirver_name.getText().toString();
            if (!tan_no.isEmpty() && !mobile_no.isEmpty() && !drivername.isEmpty()){
                try {
                    createpdf();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                Snackbar.make(getView(), getString(R.string.empty_field_error), Snackbar.LENGTH_LONG).show();
            }

            });
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createpdf() throws IOException {

        String pdfpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfpath,"mypdf.pdf");
        OutputStream outputStream = new FileOutputStream(file);
        PdfWriter pdfWriter = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);
        document.setMargins(0f,0f,0f,0f);


        //pdfDocument.setDefaultPageSize(PageSize.A6);
      /*  document.setMargins(24f,24f,32f,32f);
       // document.setMargins(0f,0f,0f,0f);
        Drawable drawable = getActivity().getDrawable(R.drawable.cidco);

        Bitmap b = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte [] bimapdate = byteArrayOutputStream.toByteArray();
        ImageData imageData = ImageDataFactory.create(bimapdate);
        Image image = new Image(imageData);
*/
        Paragraph paragraph = new Paragraph("Note").setBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER);
        float[] width ={200f,200f};
        Table table = new Table(width);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.setMarginTop(20);

        table.addCell(new Cell().add(new Paragraph("Token No")));
        table.addCell(new Cell().add(new Paragraph("")));

        /*table.addCell(new Cell().add(new Paragraph("Tanker No")));
        table.addCell(new Cell().add(new Paragraph("")));*/

        table.addCell(new Cell().add(new Paragraph("Customer Name")));
        table.addCell(new Cell().add(new Paragraph("")));

        table.addCell(new Cell().add(new Paragraph("Customer Mobile No")));
        table.addCell(new Cell().add(new Paragraph("")));

        table.addCell(new Cell().add(new Paragraph("Building Name")));
        table.addCell(new Cell().add(new Paragraph("")));

        table.addCell(new Cell().add(new Paragraph("Address")));
        table.addCell(new Cell().add(new Paragraph("")));

        table.addCell(new Cell().add(new Paragraph("Quantity")));
        table.addCell(new Cell().add(new Paragraph("")));

        table.addCell(new Cell().add(new Paragraph("Approved By")));
        table.addCell(new Cell().add(new Paragraph("")));

        table.addCell(new Cell().add(new Paragraph("Zone")));
        table.addCell(new Cell().add(new Paragraph("")));

        /*Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

       // DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/mm/yyyy");
        table.addCell(new Cell().add(new Paragraph("Delivery Date")));
        table.addCell(new Cell().add(new Paragraph(formattedDate)));
        DateTimeFormatter dateTimeFormatte = DateTimeFormatter.ofPattern("hh:mm:ss");
        table.addCell(new Cell().add(new Paragraph("Delivery Time")));
        table.addCell(new Cell().add(new Paragraph(LocalTime.now().format(dateTimeFormatte).toString())));

        Paragraph signature = new Paragraph(" Customer Signature").setBold().setFontSize(12).setTextAlignment(TextAlignment.RIGHT);
        signature.setMarginRight(20);*/








        //second table

        Table table1 = new Table(width);
        table1.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table1.setMarginTop(20);

        table1.addCell(new Cell().add(new Paragraph("Tanker No")));
        table1.addCell(new Cell().add(new Paragraph("")));

        table1.addCell(new Cell().add(new Paragraph("Customer Name")));
        table1.addCell(new Cell().add(new Paragraph("")));

        table1.addCell(new Cell().add(new Paragraph("Driver Name")));
        table1.addCell(new Cell().add(new Paragraph("")));

        table1.addCell(new Cell().add(new Paragraph("Driver Mobile No")));
        table1.addCell(new Cell().add(new Paragraph("")));

        Paragraph custsig = new Paragraph("Customer Signature:").setTextAlignment(TextAlignment.CENTER);
        custsig.setMargin(10);
        Paragraph custname = new Paragraph("Customer Name :").setTextAlignment(TextAlignment.CENTER);
        Paragraph datetime = new Paragraph("Delivery Date and Time :").setTextAlignment(TextAlignment.CENTER);
        Paragraph otp = new Paragraph("OTP :").setTextAlignment(TextAlignment.CENTER);



        Drawable d = getResources().getDrawable(R.drawable.cidkologo);

        Bitmap b = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte [] bimapdate = byteArrayOutputStream.toByteArray();
        ImageData imageData = ImageDataFactory.create(bimapdate);
        Image image = new Image(imageData);
        image.scaleAbsolute(100, 80);
        image.setMarginTop(20);
        image.setHorizontalAlignment(HorizontalAlignment.CENTER);
        document.add(image);
        document.add(paragraph);
        document.add(table);
        document.add(table1);
        document.add(custsig);
        document.add(custname);
        document.add(datetime);
        document.add(otp);
        document.close();

        //second table






        Toast.makeText(getActivity(),"Pdf Created succesfully",Toast.LENGTH_LONG).show();

        Navigation.findNavController(getView()).navigate(R.id.action_tanker_det_to_pdfview);



    }

    @Override
    public void onStart() {
        super.onStart();
        setup();
    }
    private void setup() {
        // Enable Android-style asset loading (highly recommended)
        PDFBoxResourceLoader.init(getActivity());
        // Find the root of the external storage.
        root = android.os.Environment.getExternalStorageDirectory();
        assetManager = getActivity().getAssets();
        //tv = (TextView) findViewById(R.id.statusTextView);
    }

    public void createPdf() {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        // Create a new font object selecting one of the PDF base fonts
        PDFont font = PDType1Font.HELVETICA;
        // Or a custom font
//      try {
//          PDType0Font font = PDType0Font.load(document, assetManager.open("MyFontFile.TTF"));
//      } catch(IOException e) {
//          e.printStackTrace();
//      }


        PDPageContentStream contentStream;

        try {
            // Define a content stream for adding to the PDF
            contentStream = new PDPageContentStream(document, page);

            // Write Hello World in blue text

            contentStream.beginText();
           /* String text1="Token No :";
            String text2="Tanker No :";
            String text3="Customer Mobile No:";
            String text4="Driver Mobile No:";
            String text5="OTP:";
            String text6="Delivery Date";
            String text7="Delivery Time";*/
            contentStream.setNonStrokingColor(15, 38, 192);
            contentStream.setFont(font, 12);
            contentStream.setLeading(16.0f);
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("");
          /*  contentStream.newLine();
            contentStream.showText(text1);
            contentStream.newLine();
            contentStream.showText(text2);
            contentStream.newLine();
            contentStream.showText(text3);
            contentStream.newLine();
            contentStream.showText(text4);
            contentStream.newLine();
            contentStream.showText(text5);
            contentStream.newLine();
            contentStream.showText(text6);
            contentStream.newLine();
            contentStream.showText(text7);*/
            contentStream.endText();
            contentStream.close();

            // Save the final pdf document to a file
            String path = root.getAbsolutePath() + "/Download/mypdf.pdf";
            document.save(path);
            document.close();
            Toast.makeText(getActivity(),"PDF generated Succesfully",Toast.LENGTH_LONG ).show();
            //tv.setText("Successfully wrote PDF to " + path);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Loads an existing PDF and renders it to a Bitmap
     */



    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

       /* ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);*/
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };

        if (!hasPermissions(getActivity(), PERMISSIONS)) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);
        }
    }

    public static boolean hasPermissions(FragmentActivity context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
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
                                                requestPermission();
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
            tanker_photo.setImageBitmap(bitmap);

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