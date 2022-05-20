package com.example.tankerallocation.Adapter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tankerallocation.Model.Approved;
import com.example.tankerallocation.R;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SuccessAdapter extends RecyclerView.Adapter<SuccessAdapter.SuccesslistViewHolder> {
    List<Approved> results;

    public SuccessAdapter(Activity activity, List<Approved> list) {
       /* super(activity, R.layout.row_tweet, tweets);
        inflater = activity.getWindow().getLayoutInflater();*/
        this.results = list;
    }


    public SuccessAdapter.SuccesslistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View lmcItem = inflater.inflate(R.layout.card_delivered, parent, false);
        SuccessAdapter.SuccesslistViewHolder successlistViewHolder = new SuccessAdapter.SuccesslistViewHolder(lmcItem);
        Date date = Calendar.getInstance().getTime();
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return successlistViewHolder;
    }

    public void onBindViewHolder(@NonNull SuccessAdapter.SuccesslistViewHolder holder, int position) {

        RelativeLayout relativeLayout = (RelativeLayout) holder.token.getParent().getParent();
        attachClickListeners(relativeLayout, position);

        String status = results.get(position).getStatus();
        //if (status.equalsIgnoreCase("Work Allocated")) {


        Log.e("DATA", results.get(position).getToken_no());
        holder.token_no.setText(results.get(position).getToken_no());
        holder.val_custname.setText(results.get(position).getCust_name());
        holder.val_buildname.setText(results.get(position).getAddress());
        holder.val_consumerno.setText(results.get(position).getConsumer_no());
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        Date c = Calendar.getInstance().getTime();
        String formattedDate = df.format(c);
        holder.val_datetime.setText(formattedDate);
        // }


    }

    private void attachClickListeners(RelativeLayout relativeLayout, int position) {
        relativeLayout.setOnClickListener(event -> {

            Bundle bundle = new Bundle();
            bundle.putSerializable("approved", results.get(position));
            Navigation.findNavController(relativeLayout).navigate(R.id.deliverydetails, bundle);

            //  Navigation.findNavController(relativeLayout).navigate(R.id.deliverydetails);

        });

    }

    private void detachClickListeners(RelativeLayout relativeLayout, int position) {

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class SuccesslistViewHolder extends RecyclerView.ViewHolder {
        private TextView token;
        private TextView token_no;
        private TextView cust_name;
        private TextView val_custname;
        private TextView bulding_name;
        private TextView val_buildname;
        private TextView datetime;
        private TextView val_datetime;
        private TextView val_consumerno;
        //private Button uloaddelivery_note;


        public SuccesslistViewHolder(@NonNull View itemView) {
            super(itemView);
            token = itemView.findViewById(R.id.tv_toker_no);
            token_no = itemView.findViewById(R.id.val_token_no);
            cust_name = itemView.findViewById(R.id.tv_cust_name);
            val_custname = itemView.findViewById(R.id.val_cus_name);
            bulding_name = itemView.findViewById(R.id.tv_building_name);
            val_buildname = itemView.findViewById(R.id.val_building_name);
            datetime = itemView.findViewById(R.id.tv_date);
            val_datetime = itemView.findViewById(R.id.val_deiverydate);
            val_consumerno = itemView.findViewById(R.id.val_consumerno);
        }

    }
}
