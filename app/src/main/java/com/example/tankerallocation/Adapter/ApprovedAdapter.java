package com.example.tankerallocation.Adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.example.tankerallocation.Model.Approved;
import com.example.tankerallocation.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ApprovedAdapter extends RecyclerView.Adapter<ApprovedAdapter.PendinglistViewHolder> {



  List<Approved> results;


    public ApprovedAdapter(Activity activity, List<Approved> list) {
       /* super(activity, R.layout.row_tweet, tweets);
        inflater = activity.getWindow().getLayoutInflater();*/
        this.results = list;
    }

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


            holder.token_no.setText(results.get(position).getToken_no());
            holder.val_custname.setText(results.get(position).getCust_name());
            holder.val_custmob_no.setText(results.get(position).getCust_mob_no());
            holder.val_secotr.setText(results.get(position).getSector_no());
            holder.val_plotno.setText(results.get(position).getAddress());
            // holder.val_bul_name.setText(results.get(position).getSociety_name());
            holder.val_quanity.setText(results.get(position).getQuantity());
            holder.val_zone.setText(results.get(position).getNode_id());
            holder.val_approvedby.setText(results.get(position).getApproved_by());
            holder.val_consumer_no.setText(results.get(position).getConsumer_no());



    }

    private void attachClickListeners(RelativeLayout relativeLayout, int position) {

        relativeLayout.setOnClickListener(event -> {
            Bundle bundle=new Bundle();
            bundle.putSerializable("approved", results.get(position));
            Navigation.findNavController(relativeLayout).navigate(R.id.tanker_det,bundle);

        });




    }

    private void detachClickListeners(RelativeLayout relativeLayout, int position) {

    }

    @Override
    public int getItemCount() {
        return results.size();
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
        private TextView tv_sector;
        private TextView val_secotr;
        private TextView tv_quantity;
        private TextView val_quanity;
        private TextView tv_zone;
        private TextView val_zone;
        private TextView tv_plotno;
        private TextView val_plotno;
        private TextView val_approvedby;
        private TextView val_custmob_no;
        private TextView val_consumer_no;
        private TextView tv_consumer_no;


        public PendinglistViewHolder(@NonNull View itemView) {
            super(itemView);
            token = itemView.findViewById(R.id.tv_toker_no);
            token_no = itemView.findViewById(R.id.val_token_no);
            cust_name = itemView.findViewById(R.id.tv_cust_name);
            val_custname = itemView.findViewById(R.id.val_cus_name);
            build_name = itemView.findViewById(R.id.tv_building_name);
            val_bul_name = itemView.findViewById(R.id.val_building_name);
            tv_sector = itemView.findViewById(R.id.tv_sector);
            val_secotr = itemView.findViewById(R.id.val_sector);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            val_quanity = itemView.findViewById(R.id.val_quantity);
            tv_zone = itemView.findViewById(R.id.tv_zone);
            val_zone = itemView.findViewById(R.id.val_zone);
            tv_plotno = itemView.findViewById(R.id.tv_plotno);
            val_plotno = itemView.findViewById(R.id.val_plotno);
            val_approvedby = itemView.findViewById(R.id.val_approvedby);
            val_custmob_no = itemView.findViewById(R.id.val_cus_mob_no);
            tv_consumer_no = itemView.findViewById(R.id.tv_consumer_no);
            val_consumer_no = itemView.findViewById(R.id.val_consumer_no);
        }

    }

}
