package com.example.bloodbond;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// HospitalListAdapter.java
public class HospitalListAdapter extends RecyclerView.Adapter<HospitalListAdapter.HospitalViewHolder> {
    private List<Hospital> hospitals;
    private OnHospitalClickListener onHospitalClickListener;

    public HospitalListAdapter(List<Hospital> hospitals, OnHospitalClickListener onHospitalClickListener) {
        this.hospitals = hospitals;
        this.onHospitalClickListener = onHospitalClickListener;
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital, parent, false);
        return new HospitalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Hospital hospital = hospitals.get(position);
        holder.textViewHospitalName.setText(hospital.getName());
        holder.textViewHospitalAddress.setText(hospital.getAddress());
        holder.textViewHospitalDistance.setText(hospital.getDistance());

        // Handle click event
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onHospitalClickListener != null) {
                    onHospitalClickListener.onHospitalClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return hospitals.size();
    }

    public interface OnHospitalClickListener {
        void onHospitalClick(int position);
    }

    public static class HospitalViewHolder extends RecyclerView.ViewHolder {
        TextView textViewHospitalName, textViewHospitalAddress, textViewHospitalDistance,brder;

        public HospitalViewHolder(View itemView) {
            super(itemView);
            textViewHospitalName = itemView.findViewById(R.id.textViewHospitalName);
            textViewHospitalAddress = itemView.findViewById(R.id.textViewHospitalAddress);
            textViewHospitalDistance = itemView.findViewById(R.id.textViewHospitalDistance);
            brder=itemView.findViewById(R.id.textviewbrder);
        }
    }
}
