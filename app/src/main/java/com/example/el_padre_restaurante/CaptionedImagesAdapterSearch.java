package com.example.el_padre_restaurante;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.el_padre_restaurante.model.Product;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CaptionedImagesAdapterSearch  extends RecyclerView.Adapter<CaptionedImagesAdapterSearch.ViewHolder> implements Filterable, View.OnClickListener {

    private Context context;
    private List<Product> items;
    private List<Product> itemsfull;
    private static RecyclerViewClickListener listener;

    public CaptionedImagesAdapterSearch(Context context, List<Product> items,RecyclerViewClickListener listener) {
        this.context = context;
        this.items = items;
        this.itemsfull = new ArrayList<>(items);
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_search,
                parent,
                false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Product product = items.get(position);
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.image);
        Glide.with(context).load(product.getImage()).into(imageView);
        TextView txt = (TextView) cardView.findViewById(R.id.txtName);
        txt.setText(product.getName());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, ItemActivity.class);
                Gson gson = new Gson();
                String item_product = gson.toJson(product);
                intent1.putExtra("item_product",item_product);
                context.startActivity(intent1);
            }
        });

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {


        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Product> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(itemsfull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Product item : itemsfull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            items.clear();
            items.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public void onClick(View view) {

    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());
        }
    }



    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }
}
