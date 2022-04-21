package com.example.tankerallocation.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tankerallocation.R;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SuccessAdapter extends RecyclerView.Adapter<SuccessAdapter.SuccesslistViewHolder> {
    int totalItems = 5;
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


    }

    private void attachClickListeners(RelativeLayout relativeLayout, int position) {
        relativeLayout.setOnClickListener(event -> {

            Navigation.findNavController(relativeLayout).navigate(R.id.deliverydetails);

        });

    }

    private void detachClickListeners(RelativeLayout relativeLayout, int position) {

    }

    @Override
    public int getItemCount() {
        return totalItems;
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
        //private Button uloaddelivery_note;


        public SuccesslistViewHolder(@NonNull View itemView) {
            super(itemView);
            token = itemView.findViewById(R.id.tv_toker_no);
            token_no = itemView.findViewById(R.id.val_token_no);
            cust_name = itemView.findViewById(R.id.tv_cust_name);
            val_custname = itemView.findViewById(R.id.val_cus_name);
            bulding_name = itemView.findViewById(R.id.tv_building_no);
            val_buildname = itemView.findViewById(R.id.val_buil_no);
            datetime = itemView.findViewById(R.id.tv_date);
            val_datetime = itemView.findViewById(R.id.val_deiverydate);
        }

    }
}
