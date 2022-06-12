package com.example.el_padre_restaurante;
import android.content.Context;
import android.content.Intent;
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
import com.example.el_padre_restaurante.model.DelivarOrder;


import java.util.List;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.ViewHolder> {
    private Context context;
    private List<DelivarOrder> deliveryAdapters;
    private final String BASE_URL = "http://10.0.2.2/EL_PADRE_RESTURANTE/webservice.php";


    public DeliveryAdapter(Context context, List<DelivarOrder> deliveryAdapters) {
        this.context = context;
        this.deliveryAdapters = deliveryAdapters;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_for_delivery_order, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DelivarOrder dA = deliveryAdapters.get(position);
        CardView cardView = holder.cardView;
        TextView txt = (TextView)cardView.findViewById(R.id.username_client);
        txt.setText("Address :"+dA.getUsername());
        TextView txt1 = (TextView)cardView.findViewById(R.id.tv_address);
        txt1.setText("Username :"+dA.getAddress());
        TextView txt2 = (TextView)cardView.findViewById(R.id.priceOrder);
        txt2.setText(dA.getName()+" "+dA.getPrice()+"$");
        Button but = (Button)cardView.findViewById(R.id.button_delivery_approve);
        cardView.findViewById(R.id.button_delivery_approve).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = "INSERT INTO `sold_items`(`u_id`, `p_id`) VALUES ('"+dA.getC_id()+"','"+dA.getP_id()+"');";
                String url = BASE_URL + "?query=" + query;
                RequestQueue queue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String query1 = "DELETE FROM `order` WHERE `order`.`id` = "+dA.getId()+";";

                        String url1 = BASE_URL + "?query=" + query1;

                        RequestQueue queue1 = Volley.newRequestQueue(context);


                        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Intent intent = new Intent(context, DeliveryActivity.class);
                                context.startActivity(intent);;
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "Fail to register data..", Toast.LENGTH_SHORT).show();
                            }
                        });
                        queue1.add(stringRequest1);

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
        return deliveryAdapters.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;
        }

    }

}