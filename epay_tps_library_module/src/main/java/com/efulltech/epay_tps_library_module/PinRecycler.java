package com.efulltech.epay_tps_library_module;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PinRecycler extends RecyclerView.Adapter<PinRecycler.MyViewHolder> {
    Context mContext;
    List<PinClass> pinClasses;

    public PinRecycler(Context mContext, List<PinClass> pinClasses) {
        this.mContext = mContext;
        this.pinClasses = pinClasses;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pin_template, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.buttonText.setText(pinClasses.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return pinClasses.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView buttonText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            buttonText = itemView.findViewById(R.id.numberButton);
        }
    }
}
