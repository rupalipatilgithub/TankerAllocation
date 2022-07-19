package com.example.tankerallocation.UI;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.tankerallocation.Dao.DaoMaster;
import com.example.tankerallocation.Dao.DaoSession;
import com.example.tankerallocation.Dao.UserDetails;
import com.example.tankerallocation.Dao.UserDetailsDao;
import com.example.tankerallocation.Model.Approved;
import com.example.tankerallocation.Model.TankerNoModel;
import com.example.tankerallocation.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.tom_roush.pdfbox.util.PDFBoxResourceLoader;

import org.greenrobot.greendao.database.Database;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


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
    private TextInputEditText cust_mob_no;
    private TextInputEditText builname_edt;
    private TextInputEditText address_edt;
    private TextInputEditText quantity_edt;
    private TextInputEditText approvedby_edt;
    private TextInputEditText plot_edit;
    private TextInputEditText sector_edit;
    private TextInputEditText society_edit;
    private TextInputEditText zone_edit;
    private TextInputEditText cunsumer_no;
    MaterialAutoCompleteTextView tanker_num;
    MaterialAutoCompleteTextView dri_mob_num;
    MaterialAutoCompleteTextView dirver_name;
    MaterialAutoCompleteTextView fillinglocation;
    Approved approved;
    List<String> tankerlist = new ArrayList<String>();
    List<String> filling_location_list = new ArrayList<String>();
    Button generatepdf;

    RequestQueue requestQueue;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tanker_details, container, false);
        token_no_edt = view.findViewById(R.id.edt_token_no);
        cust_name_edt = view.findViewById(R.id.edt_custname);
        cust_mob_no = view.findViewById(R.id.edt_cust_mob_no);
        society_edit = view.findViewById(R.id.edt_buildingname);
        sector_edit = view.findViewById(R.id.edt_sector);
        quantity_edt = view.findViewById(R.id.edt_quantity);
        approvedby_edt = view.findViewById(R.id.edt_approvedby);
        plot_edit = view.findViewById(R.id.edt_plotno);
        zone_edit = view.findViewById(R.id.edt_zone);
        tanker_num = view.findViewById(R.id.autoComplete_totanker_num);
        dri_mob_num = view.findViewById(R.id.autoComplete_to_dri_mob);
        dirver_name = view.findViewById(R.id.autoComplete_to_driver_name);
        MaterialButton upload_photo = view.findViewById(R.id.choose_img_tanker_btn);
        tanker_photo = view.findViewById(R.id.img_tankerPhoto);
        fillinglocation = view.findViewById(R.id.autoComplete_to_fillingStation);
        cunsumer_no = view.findViewById(R.id.edt_cunsumer_no);
        generatepdf = view.findViewById(R.id.next);
       // generatepdf.setVisibility(View.GONE);

        getTankerList();

        GetFillingSttaion();

        approved = new Approved();
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            approved = (Approved) bundle.getSerializable("approved");
            Log.e("tokenid", approved.getToken_no() + "kkk");

        }


        token_no_edt.setText(approved.getToken_no());
        cust_name_edt.setText(approved.getCust_name());
        cust_mob_no.setText(approved.getCust_mob_no());
        zone_edit.setText(approved.getNode_id());
        plot_edit.setText(approved.getAddress());
        sector_edit.setText(approved.getSector_no());
        society_edit.setText(approved.getSociety_name());
        quantity_edt.setText(approved.getQuantity());
        approvedby_edt.setText(approved.getApproved_by());
        cunsumer_no.setText(approved.getConsumer_no());
        // tanker_num.setOnItemSelectedListener(this);

        tanker_num.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                // here is your selected item
                loaddriverDetails(selectedItem);
            }
        });


        upload_photo.setOnClickListener(v -> {

            if (checkPermission()) {
                if (true) {

                    intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    // checkNCallCamera();
                    // Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 7);
                }

            } else {
                requestPermission();
            }

        });

        generatepdf.setOnClickListener(v -> {
            //  createPdf();

            String tan_no = tanker_num.getText().toString();
            String mobile_no = dri_mob_num.getText().toString();
            String drivername = dirver_name.getText().toString();
            if (!tan_no.isEmpty() && !mobile_no.isEmpty() && !drivername.isEmpty()) {
                try {
                    createpdf();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Snackbar.make(getView(), getString(R.string.empty_field_error), Snackbar.LENGTH_LONG).show();
            }

        });
        return view;
    }

    private void GetFillingSttaion() {
        final String url = "https://tws.elintwater.com/api/API/FillingstationList";

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String station_id = jsonObject.getString("fillingstation_id");
                        String fillingstation = jsonObject.getString("filling_location");

                        filling_location_list.add(fillingstation);


                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, filling_location_list);

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                            (getActivity(), android.R.layout.simple_spinner_item, filling_location_list);

                    dataAdapter.setDropDownViewResource
                            (android.R.layout.simple_spinner_dropdown_item);

                    fillinglocation.setAdapter(dataAdapter);
                    //  tanker_num.setOnItemSelectedListener(tanker_num.getItemSelectedListener());

                    // tanker_num.setOnItemSelectedListener(TankerDetails.this);

                } catch (Exception w) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
    }


    private void getTankerList() {
        final String url = "https://tws.elintwater.com/api/API/TankerList";

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {


            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {
                    TankerNoModel tankerNoModel = new TankerNoModel();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String tankerid = jsonObject.getString("tanker_id");
                        String tankerno = jsonObject.getString("tanker_no");

                        tankerlist.add(tankerno);
                       /* tankerNoModel.setTankerNo(tankerno);
                        tankerNoModel.setTanker_id(tankerid);*/


                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, tankerlist);

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                            (getActivity(), android.R.layout.simple_spinner_item, tankerlist);

                    dataAdapter.setDropDownViewResource
                            (android.R.layout.simple_spinner_dropdown_item);

                    tanker_num.setAdapter(dataAdapter);

                } catch (Exception w) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
    }


    private void loaddriverDetails(String tank_no) {
        String url = "https://tws.elintwater.com/api/API/DriverDetails";

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("Tanker_no", tank_no);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Enter the correct url for your api service site

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONObject respObj = null;
                        try {
                            respObj = new JSONObject(response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            String drivername = respObj.getString("driver_name");
                            String driver_mob_no = respObj.getString("driver_mobile_no");

                            dirver_name.setText(drivername);
                            dri_mob_num.setText(driver_mob_no);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //resultTextView.setText("Error getting response");
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createpdf() throws IOException {

        // ExportData();

        //   String pdfpath = Environment.getExternalStorageDirectory()+"/TankerAllocation/DeliveryNote";

       /* String dirPath = getContext().getFilesDir().getAbsolutePath() + "/TankerAllocation/DeliveryNote/";
        // String dirPath = getContext().getFilesDir().toString() + "/images";
        File appDir = new File(dirPath);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }*/

        String pdfpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfpath, "deliver_not.pdf");
        file.createNewFile();



          //  OutputStream outputStream = new FileOutputStream(file);
            PdfWriter pdfWriter = new PdfWriter(file);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);
            document.setMargins(0f, 0f, 0f, 0f);

            Paragraph paragraph = new Paragraph("Delivery Note").setBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER);
            float[] width = {200f, 200f};
            Table table = new Table(width);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);
            table.setMarginTop(40);

            table.addCell(new Cell().add(new Paragraph("Token No")));
            table.addCell(new Cell().add(new Paragraph(approved.getToken_no())));

            table.addCell(new Cell().add(new Paragraph("Consumer No")));
            table.addCell(new Cell().add(new Paragraph(approved.getConsumer_no())));

            table.addCell(new Cell().add(new Paragraph("Node")));
            table.addCell(new Cell().add(new Paragraph(approved.getNode_id())));

            table.addCell(new Cell().add(new Paragraph("Sector")));
            table.addCell(new Cell().add(new Paragraph(approved.getSector_no())));

            table.addCell(new Cell().add(new Paragraph("Address")));
            table.addCell(new Cell().add(new Paragraph(approved.getAddress())));

            table.addCell(new Cell().add(new Paragraph("Consumer Name")));
            table.addCell(new Cell().add(new Paragraph(approved.getCust_name())));

            table.addCell(new Cell().add(new Paragraph("Consumer Mobile No")));
            table.addCell(new Cell().add(new Paragraph(approved.getCust_mob_no())));

            table.addCell(new Cell().add(new Paragraph("Quantity")));
            table.addCell(new Cell().add(new Paragraph(approved.getQuantity())));

            table.addCell(new Cell().add(new Paragraph("Approved By")));
            table.addCell(new Cell().add(new Paragraph(approved.getApproved_by())));


            //second table

            Table table1 = new Table(width);
            table1.setHorizontalAlignment(HorizontalAlignment.CENTER);
            table1.setMarginTop(40);

            table1.addCell(new Cell().add(new Paragraph("Tanker No")));
            table1.addCell(new Cell().add(new Paragraph(tanker_num.getText().toString())));

            table1.addCell(new Cell().add(new Paragraph("Tanker Filling Location")));
            table1.addCell(new Cell().add(new Paragraph(fillinglocation.getText().toString())));

            table1.addCell(new Cell().add(new Paragraph("Driver Name")));
            table1.addCell(new Cell().add(new Paragraph(dirver_name.getText().toString())));

            table1.addCell(new Cell().add(new Paragraph("Driver Mobile No")));
            table1.addCell(new Cell().add(new Paragraph(dri_mob_num.getText().toString())));

            Drawable d = getResources().getDrawable(R.drawable.cidkologo);

            //third table
            Table table2 = new Table(width);
            table2.setHorizontalAlignment(HorizontalAlignment.CENTER);
            table2.setMarginTop(40);

            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String formattedDate = df.format(c);
            table2.addCell(new Cell().add(new Paragraph("Delivery date")));
            table2.addCell(new Cell().add(new Paragraph("")));


            String currentTime = new SimpleDateFormat("HH:mm:ss a", Locale.getDefault()).format(new Date());

            // Date currentTime = Calendar.getInstance().getTime();
            table2.addCell(new Cell().add(new Paragraph("Delivery Time")));
            table2.addCell(new Cell().add(new Paragraph("")));

            Table table3 = new Table(width);
            table3.setHorizontalAlignment(HorizontalAlignment.CENTER);
            table3.setMarginTop(40);

            table3.addCell(new Cell().add(new Paragraph("OTP")));
            table3.addCell(new Cell().add(new Paragraph("")));


            Bitmap b = ((BitmapDrawable) d).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] bimapdate = byteArrayOutputStream.toByteArray();
            ImageData imageData = ImageDataFactory.create(bimapdate);
            Image image = new Image(imageData);
            image.scaleAbsolute(100, 80);
            image.setMarginTop(20);
            image.setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(image);
            document.add(paragraph);
            document.add(table);
            document.add(table1);
            document.add(table2);
            document.add(table3);
            //document.add((IBlockElement) rectangle);


            document.close();


       // }

        //second table


        Toast.makeText(getActivity(), "Pdf Created succesfully", Toast.LENGTH_LONG).show();


        ExportData();

      Navigation.findNavController(getView()).navigate(R.id.action_tanker_det_to_pdfview);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.item3:
                Toast.makeText(getActivity(), "Item 3 Selected", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void ExportData() {

        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getContext(), "users.db");
        Database database = devOpenHelper.getWritableDb();
        DaoSession daoSession = new DaoMaster(database).newSession();

        UserDetailsDao userDetails = daoSession.getUserDetailsDao();

        List<UserDetails> allUsers = userDetails.loadAll();
        String userid = allUsers.get(allUsers.size() -1).getUserid();
        String zone = allUsers.get(allUsers.size()-1).getZone();

        String tokenno = token_no_edt.getText().toString();
        String drivername = dirver_name.getText().toString();
        String driver_mon_no = dri_mob_num.getText().toString();
        String fil_locaon = fillinglocation.getText().toString();
        String tanker_no = tanker_num.getText().toString();
        String Cust_name = cust_name_edt.getText().toString();
        String cust_mob = cust_mob_no.getText().toString();
        String sector = sector_edit.getText().toString();
        //  String tanker_photo = token_no_edt.getText().toString();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        String currentTime = new SimpleDateFormat("HH:mm:ss a", Locale.getDefault()).format(new Date());

        String url = "https://tws.elintwater.com/api/API/ExportData";

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("user_id", userid);
            object.put("token_no", tokenno);
            object.put("driver_name", drivername);
            object.put("driver_mob_no", driver_mon_no);
            object.put("tanker_no", tanker_no);
            object.put("Cust_name", Cust_name);
            object.put("cust_mob_no", cust_mob);
            object.put("sector_no", sector);
           // object.put("plot_no", plot_edit.getText().toString());
           // object.put("society_name", society_edit.getText().toString());
            object.put("node", zone);
            object.put("address", approved.getAddress());
            object.put("quantity", approved.getQuantity());
            object.put("tanker_photo", "");
            object.put("delivery_date", formattedDate);
            object.put("delivery_time", currentTime);
            object.put("filling_location", fil_locaon);
            object.put("status", "Work Allocated");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Enter the correct url for your api service site

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject movieObject = new JSONObject(response.toString());
                            String tkno = movieObject.getString("token_no");
                            String status = movieObject.getString("status");

                            if (status.equalsIgnoreCase("Work Allocated")) {
                                 //generatepdf.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "Export Done", Toast.LENGTH_LONG).show();
                               // Navigation.findNavController(getView()).navigate(R.id.action_tanker_det_to_allocationList);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //resultTextView.setText("Error getting response");
            }
        });
        requestQueue.add(jsonObjectRequest);


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


            byte[] byteArray = bytes.toByteArray();

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

            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

            //  ExportData(encoded);

            //ExportData(encoded);

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