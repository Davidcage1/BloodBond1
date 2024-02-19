package com.example.bloodbond;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.viewHolder> {

    private List<Donation> itemlis;

    public DonationHistoryActivity click;

    public void setClickListner(DonationHistoryActivity mylistner){
        this.click=mylistner;
    }



    public NewAdapter(List<Donation> itemlis, DonationHistoryActivity donationHistoryActivity) {
        this.itemlis = itemlis;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Donation item=itemlis.get(position);
        holder.reqname.setText(item.getRequesterName());
        holder.dte.setText(item.getScheduleDate());
        holder.bldtyp.setText(item.getBloodType());
        holder.confm.setText("Donation Confirmed");
        holder.imgvi.setImageResource(R.drawable.history1);
    }

    @Override
    public int getItemCount() {
        return itemlis.size();
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imgvi;
        TextView reqname,dte,bldtyp,confm;
        public viewHolder(@NonNull View itemView) {

            super(itemView);
            reqname=itemView.findViewById(R.id.title_txt);
            dte=itemView.findViewById(R.id.subtitle);
            bldtyp=itemView.findViewById(R.id.bloodtyp);
            confm=itemView.findViewById(R.id.Dnation);
            imgvi=itemView.findViewById(R.id.imagemy);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(click !=null){
                click.onCLick(view,getAdapterPosition());
            }
        }
    }
}
