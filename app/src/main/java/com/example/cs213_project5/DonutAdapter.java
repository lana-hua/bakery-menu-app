package com.example.cs213_project5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;


public class DonutAdapter extends RecyclerView.Adapter<DonutAdapter.DonutViewHolder>{
    private List<DonutItem> donutList;
    private OnDonutClickListener clickListener;

    public interface OnDonutClickListener {
        void onDonutClick(DonutItem donut, int position);
    }

    /**
     * Constructs a DonutAdapter with given context and donut list.
     * @param context the application context
     * @param donutList the list of donut items to display
     * @param clickListener the listener of the RecyclerView
     */
    public DonutAdapter(Context context, List<DonutItem> donutList, OnDonutClickListener clickListener) {
        this.context = context;
        this.donutList = donutList;
        this.clickListener = clickListener;  // Store the listener
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
        holder.priceText.setText(String.format(Locale.US, "$%.2f", donut.getPrice()));

        holder.imageView.setImageResource(donut.getImageResource());

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onDonutClick(donut, position);
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
