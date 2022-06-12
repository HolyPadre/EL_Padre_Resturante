package com.example.el_padre_restaurante;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.el_padre_restaurante.model.Product;
import com.example.el_padre_restaurante.model.Pruduct_Order_Customer;
import com.example.el_padre_restaurante.model.UserProfile;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {
    private final String BASE_URL = "http://10.0.2.2/EL_PADRE_RESTURANTE/webservice.php";
    private final String IMAGE_URL = "http://10.0.2.2/EL_PADRE_RESTURANTE/";
    RecyclerView recyclerView;
    CaptionedImagesAdapter adapter;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        recyclerView =findViewById(R.id.orders_custormer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,true));
        TextView textView = (TextView) findViewById(R.id.account_name);
        textView.setText(UserProfile.getUsername());
        setProduct();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.all_faces_customer,menu);
        return true;
    }







    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.homepage:
                Intent intent = new Intent(CustomerActivity.this, CustomerHomeActivity.class);
                startActivity(intent);
                break;
            case R.id.search:
                Intent intent1 = new Intent(CustomerActivity.this, SearchActivity.class);
                startActivity(intent1);
                break;
            case R.id.logout:
                UserProfile.setUsername(null);
                UserProfile.setLoggedIn(false);
                UserProfile.setGolden(false);
                UserProfile.setType(1);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(CustomerActivity.this);
                SharedPreferences.Editor editor = prefs.edit();

                editor.remove("userprofile");
                editor.apply();

                Intent intent2 = new Intent(CustomerActivity.this, LoginActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent2);
                finish();

                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    public void setProduct(){
        long ids = UserProfile.getId();
        String query = "SELECT * FROM `product` WHERE `id` IN (SELECT p_id FROM `sold_items` WHERE `u_id` = '"+ids+"');";
        String url = BASE_URL + "?query=" + query;

        RequestQueue queue = Volley.newRequestQueue(CustomerActivity.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length() !=0) {
                    Gson gson = new Gson();
                    Product[] products = gson.fromJson(response.toString(), Product[].class);
                    List<Pruduct_Order_Customer> array = new ArrayList<>();
                    for (int i = 0; i < products.length; i++) {
                        array.add(new Pruduct_Order_Customer(products[i].getId(),products[i].getName(),IMAGE_URL+products[i].getImage()));
                    }

                    adapter = new CaptionedImagesAdapter(CustomerActivity.this,array);
                    recyclerView.setAdapter(adapter);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CustomerActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finish();
            System.exit(0);
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}