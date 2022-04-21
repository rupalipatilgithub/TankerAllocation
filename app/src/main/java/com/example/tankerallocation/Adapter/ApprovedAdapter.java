package com.example.tankerallocation.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tankerallocation.R;

import java.util.Calendar;
import java.util.Date;

public class ApprovedAdapter extends RecyclerView.Adapter<ApprovedAdapter.PendinglistViewHolder> {

    int totalItems = 5;
    public ApprovedAdapter.PendinglistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View lmcItem = inflater.inflate(R.layout.card_layout, parent, false);
        ApprovedAdapter.PendinglistViewHolder pendinglistViewHolder = new ApprovedAdapter.PendinglistViewHolder(lmcItem);
        Date date = Calendar.getInstance().getTime();
       // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return pendinglistViewHolder;
    }

    public void onBindViewHolder(@NonNull ApprovedAdapter.PendinglistViewHolder holder, int position) {
        RelativeLayout relativeLayout = (RelativeLayout) holder.token.getParent().getParent();
        attachClickListeners(relativeLayout, position);


    }

    private void attachClickListeners(RelativeLayout relativeLayout, int position) {

        relativeLayout.setOnClickListener(event -> {

            Navigation.findNavController(relativeLayout).navigate(R.id.tanker_det);

        });



    }

    private void detachClickListeners(RelativeLayout relativeLayout, int position) {

    }

    @Override
    public int getItemCount() {
        return totalItems;
    }

    public class PendinglistViewHolder extends RecyclerView.ViewHolder {
        private TextView token;
        private TextView token_no;
       // private TextView tanker;
       // private TextView tanker_no;
        private TextView cust_name;
        private TextView val_custname;
        private TextView build_name;
        private TextView val_bul_name;
        private TextView address_tv;
        private TextView val_address;
        private TextView tv_quantity;
        private TextView val_quanity;
        private TextView tv_zone;
        private TextView val_zone;


        public PendinglistViewHolder(@NonNull View itemView) {
            super(itemView);
            token = itemView.findViewById(R.id.tv_toker_no);
            token_no = itemView.findViewById(R.id.val_token_no);
            cust_name = itemView.findViewById(R.id.tv_cust_name);
            val_custname = itemView.findViewById(R.id.val_cus_name);
            build_name = itemView.findViewById(R.id.tv_building_no);
            val_bul_name = itemView.findViewById(R.id.val_buil_no);
            address_tv = itemView.findViewById(R.id.tv_address);
            val_address = itemView.findViewById(R.id.val_address);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            val_quanity = itemView.findViewById(R.id.val_quantity);
            tv_zone = itemView.findViewById(R.id.tv_zone);
            val_zone = itemView.findViewById(R.id.val_zone);
        }

    }

}
