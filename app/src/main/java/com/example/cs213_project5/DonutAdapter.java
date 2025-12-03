package com.example.cs213_project5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class DonutAdapter extends RecyclerView.Adapter<DonutAdapter.DonutViewHolder>{
    private List<DonutItem> donutList;
    private OnDonutClickListener listener;

    public interface OnDonutClickListener{
        void onDonutSelected(DonutItem donut);
    }

    public DonutAdapter(List<DonutItem> donutList, OnDonutClickListener listener) {
        this.donutList = donutList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DonutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donut, parent, false);
        return new DonutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonutViewHolder holder, int position){
        DonutItem donut = donutList.get(position);

        holder.nameText.setText(donut.getName());
        holder.typeText.setText(donut.getType());
        holder.priceText.setText(String.format("$%.2f", donut.getPrice()));

        if (donut.getQuantity() > 0){
            holder.quantityText.setText("Qty: " + donut.getQuantity());
            holder.quantityText.setVisibility(View.VISIBLE);
        }
        else{
            holder.quantityText.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDonutSelected(donut);
            }
        });
    }

    @Override
    public int getItemCount(){
        return donutList.size();
    }

    public static class DonutViewHolder extends  RecyclerView.ViewHolder{
        ImageView imageView;
        TextView nameText;
        TextView typeText;
        TextView priceText;
        TextView quantityText;

        public DonutViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.donutImage);
            nameText = itemView.findViewById(R.id.donutName);
            typeText = itemView.findViewById(R.id.donutType);
            priceText = itemView.findViewById(R.id.donutPrice);
            quantityText = itemView.findViewById(R.id.donutQuantity);
        }
    }
}
