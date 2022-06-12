package com.example.el_padre_restaurante;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.el_padre_restaurante.model.Pruduct_Order_Customer;


import java.util.List;

public class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {
    private Context context;
    private List<Pruduct_Order_Customer> product;


    public CaptionedImagesAdapter(Context context, List<Pruduct_Order_Customer> product) {
        this.context = context;
        this.product = product;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_for_customer_order, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Pruduct_Order_Customer pruduct_order_customer = product.get(position);
        CardView cardView = holder.cardView;
        cardView.setId(pruduct_order_customer.getId());
        ImageView imageView = (ImageView) cardView.findViewById(R.id.image);
        Glide.with(context).load(pruduct_order_customer.getImageURL()).into(imageView);
        TextView txt = (TextView)cardView.findViewById(R.id.txtName);
        txt.setText(pruduct_order_customer.getName());
        cardView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
    }
    @Override
    public int getItemCount() {
        return product.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;
        }

    }

}