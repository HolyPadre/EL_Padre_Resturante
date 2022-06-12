package com.example.el_padre_restaurante;

import android.content.Context;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.el_padre_restaurante.model.OrderName;


import java.util.List;

public class ChefAdapter extends RecyclerView.Adapter<ChefAdapter.ViewHolder> {
    private Context context;
    private List<OrderName> orderNames;
    private final String BASE_URL = "http://10.0.2.2/EL_PADRE_RESTURANTE/webservice.php";


    public ChefAdapter(Context context, List<OrderName> orderNames) {
        this.context = context;
        this.orderNames = orderNames;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_for_chef_order, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final OrderName on = orderNames.get(position);
        CardView cardView = holder.cardView;
        cardView.setId(on.getId());
        TextView txt = (TextView)cardView.findViewById(R.id.tv_address);
        txt.setText(on.getName());
        TextView txt2 = (TextView)cardView.findViewById(R.id.priceOrder);
        txt2.setText("Number Order "+on.getId());
        Button but = (Button)cardView.findViewById(R.id.button_chef_aprove);
        cardView.findViewById(R.id.button_chef_aprove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ID",""+on.getId());
                String query = "UPDATE `order` SET `active` = '0' WHERE `order`.`id` = "+on.getId()+";";
                String url = BASE_URL + "?query=" + query;
                RequestQueue queue = Volley.newRequestQueue(context);


                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(context, ChefActivity.class);
                        context.startActivity(intent);;
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Fail to register data..", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(stringRequest);



            }
        });
    }
    @Override
    public int getItemCount() {
        return orderNames.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;
        }

    }

}