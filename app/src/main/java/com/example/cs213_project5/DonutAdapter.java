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

/**
 * Adapter for displaying donut items in a RecyclerView.
 * Follows the same pattern as the teacher's ItemsAdapter.
 * @author Sharon Chen
 */
public class DonutAdapter extends RecyclerView.Adapter<DonutAdapter.DonutViewHolder> {
    private Context context;
    private List<DonutItem> donutList;

    /**
     * Constructs a DonutAdapter with given context and donut list.
     * @param context the application context
     * @param donutList the list of donut items to display
     */
    public DonutAdapter(Context context, List<DonutItem> donutList) {
        this.context = context;
        this.donutList = donutList;
    }

    /**
     * Creates a new ViewHolder by inflating the item layout.
     * @param parent the parent ViewGroup
     * @param viewType the view type
     * @return a new DonutViewHolder instance
     */
    @NonNull
    @Override
    public DonutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_donut, parent, false);
        return new DonutViewHolder(view);
    }

    /**
     * Binds donut data to the ViewHolder at the given position.
     * @param holder the ViewHolder to bind data to
     * @param position the position in the list
     */
    @Override
    public void onBindViewHolder(@NonNull DonutViewHolder holder, int position) {
        DonutItem donut = donutList.get(position);
        holder.nameText.setText(donut.getFlavor());
        holder.typeText.setText(donut.getType());
        holder.priceText.setText(String.format(Locale.US, "$%.2f", donut.getPrice()));
        // holder.imageView.setImageResource(donut.getImageResource()); // Add later
    }

    /**
     * Returns the total number of items in the list.
     * @return the number of donut items
     */
    @Override
    public int getItemCount() {
        return donutList.size();
    }

    /**
     * ViewHolder class for donut items in the RecyclerView.
     * Holds references to the views in item_donut.xml.
     */
    public static class DonutViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameText;
        public TextView typeText;
        public TextView priceText;

        /**
         * Constructs a DonutViewHolder and initializes view references.
         * @param itemView the inflated item view
         */
        public DonutViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.donutImage);
            nameText = itemView.findViewById(R.id.donutName);
            typeText = itemView.findViewById(R.id.donutType);
            priceText = itemView.findViewById(R.id.donutPrice);
        }
    }
}