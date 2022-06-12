package com.example.el_padre_restaurante;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.el_padre_restaurante.model.DelivarOrder;
import com.example.el_padre_restaurante.model.UserProfile;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class DeliveryActivity extends AppCompatActivity {
    private final String BASE_URL = "http://10.0.2.2/EL_PADRE_RESTURANTE/webservice.php";
    RecyclerView recyclerView;
    DeliveryAdapter adapter;
    private long backPressedTime;
    private Toast backToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        TextView textView = (TextView) findViewById(R.id.account_name);
        textView.setText(UserProfile.getUsername());
        recyclerView =findViewById(R.id.orders_delivery);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        setOrders();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.all_faces,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                UserProfile.setUsername(null);
                UserProfile.setLoggedIn(false);
                UserProfile.setGolden(false);
                UserProfile.setType(1);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(DeliveryActivity.this);
                SharedPreferences.Editor editor = prefs.edit();

                editor.remove("userprofile");
                editor.apply();

                Intent intent = new Intent(DeliveryActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

                break;
            default:
                break;


        }
        return super.onOptionsItemSelected(item);
    }
    public void setOrders(){
        long ids = UserProfile.getId();
        String query = "SELECT o.id , u.username , o.address , p.price , p.name, o.c_id, o.p_id FROM `order` o, `user` u , `product` p WHERE u.id = o.c_id AND p.id = o.p_id  AND o.active = 0;";
        String url = BASE_URL + "?query=" + query;

        RequestQueue queue = Volley.newRequestQueue(DeliveryActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length() !=0) {
                    Gson gson = new Gson();
                    DelivarOrder[] delivarOrders = gson.fromJson(response.toString(), DelivarOrder[].class);
                    List<DelivarOrder> array = new ArrayList<>();
                    Log.d("SIZE",""+delivarOrders.length);
                    for (int i = 0; i < delivarOrders.length; i++) {
                        array.add(new DelivarOrder(delivarOrders[i].getId(),delivarOrders[i].getAddress(),delivarOrders[i].getUsername(),delivarOrders[i].getPrice(),delivarOrders[i].getName(),delivarOrders[i].getC_id(),delivarOrders[i].getP_id()));
                    }

                    adapter = new DeliveryAdapter(DeliveryActivity.this,array);
                    recyclerView.setAdapter(adapter);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DeliveryActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}
