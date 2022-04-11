package com.example.tankerallocation.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tankerallocation.R;
import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.PendingViewHolder> {

    @Override
    public PendingAdapter.PendingViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View lmcItem = inflater.inflate(R.layout.card_layout, parent, false);
        PendingAdapter.PendingViewHolder pendinglistViewHolder = new PendingAdapter().PendingViewHolder(lmcItem);
        Date date = Calendar.getInstance().getTime();


        return pendinglistViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PendingAdapter.PendingViewHolder holder, int position) {

       // RelativeLayout relativeLayout = (RelativeLayout) holder.ref_no.getParent().getParent();


        attachClickListeners(relativeLayout, position);
    }
    private void attachClickListeners(RelativeLayout relativeLayout, int position) {
        relativeLayout.setOnClickListener(event -> {

            //Navigation.findNavController(relativeLayout).navigate(R.id.parceldet);

        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

 public class PendingViewHolder extends RecyclerView.ViewHolder
{
    private TextView token;
    private TextView token_no;
    private TextView tanker;
    private TextView tanker_no;
    private TextView driver_no;
    private TextView driver_mob_no;
    private TextView status;
    private TextView val_status;

    MaterialButton start;

    public PendingViewHolder(@NonNull View itemView)
    {
        super(itemView);
        token = itemView.findViewById(R.id.tv_toker_no);
        token_no = itemView.findViewById(R.id.val_token_no);
        tanker = itemView.findViewById(R.id.tv_tanker_no);
        tanker_no = itemView.findViewById(R.id.val_tanker_no);
        driver_no = itemView.findViewById(R.id.tv_driverno);
        driver_mob_no = itemView.findViewById(R.id.val_driverno);
        status = itemView.findViewById(R.id.status);
        val_status = itemView.findViewById(R.id.statusval);



    }


}
