package com.example.tankerallocation.UI;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tankerallocation.Adapter.ApprovedAdapter;
import com.example.tankerallocation.Adapter.SuccessAdapter;
import com.example.tankerallocation.Dao.DaoMaster;
import com.example.tankerallocation.Dao.DaoSession;
import com.example.tankerallocation.Dao.UserDetails;
import com.example.tankerallocation.Dao.UserDetailsDao;
import com.example.tankerallocation.Model.Approved;
import com.example.tankerallocation.Model.UserDeatils;
import com.example.tankerallocation.R;

import org.greenrobot.greendao.database.Database;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DeliverdStatus extends Fragment {

    private RecyclerView deliverditemRecyclerView;
    private ArrayList<Approved> deliveredStatusList;
    RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deliverd_status, container, false);
        deliverditemRecyclerView = view.findViewById(R.id.item_list);
        StartAsyncTask();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        deliverditemRecyclerView.setLayoutManager(linearLayoutManager);

    }

    private void StartAsyncTask() {

        LongOperation task = new LongOperation();
        task.execute();
    }

    private class LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String url = "https://tws.elintwater.com/api/API/ImportData";
            requestQueue = Volley.newRequestQueue(getActivity());

            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getContext(), "users.db");
            Database database = devOpenHelper.getWritableDb();
            DaoSession daoSession = new DaoMaster(database).newSession();

            UserDetailsDao userDetails = daoSession.getUserDetailsDao();

            List<UserDetails> allUsers = userDetails.loadAll();
            String userid = allUsers.get(allUsers.size() -1).getUserid();
            String zone = allUsers.get(allUsers.size()-1).getZone();
            Log.d("zone",zone.toString()+"zz");

/*
            JSONObject postParam = new JSONObject();

            try {
                postParam.put("user_id", userid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                postParam.put("node_id", "1");
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            // postParam.addProperty("device_id", android_id);



       /* JSONArray array=new JSONArray();

        JSONObject objp=new JSONObject();
        // use for loop in case of multiple prodcuts
        objp.put("node_name","2");

        array.put(objp.toString());

        postParam.addProperty("nodes", String.valueOf(array).replaceAll("/",""));*/


            StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url,
                    new com.android.volley.Response.Listener<String>() {


                        @Override
                        public void onResponse(String response) {

                            JSONArray respObj = null;
                            try {
                                respObj = new JSONArray(response);
                                deliveredStatusList=new ArrayList<>();

                                for (int i = 0; i < respObj.length(); i++) {
                                    JSONObject jsonObject = respObj.optJSONObject(i);
                                    Approved approved=new Approved();
                                    if(jsonObject.getString("status").
                                            equalsIgnoreCase("Work Allocated")) {

                                        approved.setToken_no(jsonObject.getString("token_no"));
                                        approved.setCust_name(jsonObject.getString("cust_name"));
                                        approved.setCust_mob_no(jsonObject.getString("cust_mob_no"));
                                        approved.setConsumer_no(jsonObject.getString("consumer_no"));
                                        //approved.setBuilding_name(jsonObject.getString("building_name"));
                                        approved.setSector_no(jsonObject.getString("sector_no"));
                                       // approved.setPlot_no(jsonObject.getString("plot_no"));
                                       // approved.setSociety_name(jsonObject.getString("society_name"));
                                        approved.setNode_id(jsonObject.getString("node_id"));
                                        approved.setAddress(jsonObject.getString("address"));
                                        approved.setQuantity(jsonObject.getString("quantity"));
                                        approved.setApproved_by(jsonObject.getString("approved_by"));
                                        approved.setTanker_no(jsonObject.getString("tanker_no"));
                                        approved.setDrivername(jsonObject.getString("driver_name"));
                                        approved.setDrivermob_no(jsonObject.getString("driver_mob_no"));
                                        approved.setFillingstation(jsonObject.getString("filling_location"));
                                        approved.setStatus(jsonObject.getString("status"));
                                        deliveredStatusList.add(approved);
                                    }



                                    //  DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getContext(), "users.db");
                                    //Database database = devOpenHelper.getWritableDb();
                                    //DaoSession daoSession = new DaoMaster(database).newSession();

/*
                                    AllocationDetailsTable allocationDetails = new AllocationDetailsTable();
                                    allocationDetails.setTokenid(token_id);
                                    allocationDetails.setCustname(custName);
                                    allocationDetails.setCust_mobNo(custmobno);
                                    allocationDetails.setSector(sectorno);
                                    allocationDetails.setPltno(plotno);
                                    allocationDetails.setBuil_name(societyname);
                                    // allocationDetails.setBuil_name(buil_name);
                                    allocationDetails.setAddress(add);
                                    allocationDetails.setQuantity(quantity);
                                    allocationDetails.setApprovedby(approvedBy);
                                    allocationDetails.setNode(zone);
                                    allocationDetails.setStaus(status);
                                    daoSession.insert(allocationDetails);
*/


                                }

                                SuccessAdapter successAdapter = new SuccessAdapter(getActivity(),deliveredStatusList);
                                deliverditemRecyclerView.setAdapter(successAdapter);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //resultTextView.setText("Error getting response");
                    Log.d("importerror",error.toString());
                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id",userid);
                    params.put("node_id",zone);
                    return params;
                }
            };
            requestQueue.add(jsonObjectRequest);
            return "Process done";
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}