package com.example.el_padre_restaurante;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.example.el_padre_restaurante.model.OrderName;
import com.example.el_padre_restaurante.model.UserProfile;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class ChefActivity extends AppCompatActivity {
    private final String BASE_URL = "http://10.0.2.2/EL_PADRE_RESTURANTE/webservice.php";
    RecyclerView recyclerView;
    ChefAdapter adapter;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);
        recyclerView =findViewById(R.id.orders_chef);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        TextView textView = (TextView) findViewById(R.id.account_name);
        textView.setText(UserProfile.getUsername());

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

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ChefActivity.this);
                SharedPreferences.Editor editor = prefs.edit();

                editor.remove("userprofile");
                editor.apply();

                Intent intent = new Intent(ChefActivity.this, LoginActivity.class);
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
        String query = "SELECT o.id, p.name  FROM `order` o, `product` p WHERE o.p_id = p.id  AND o.active = 1;";
        String url = BASE_URL + "?query=" + query;

        RequestQueue queue = Volley.newRequestQueue(ChefActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length() !=0) {
                    Gson gson = new Gson();
                    OrderName[] orderNames = gson.fromJson(response.toString(), OrderName[].class);
                    List<OrderName> array = new ArrayList<>();
                    for (int i = 0; i < orderNames.length; i++) {
                        array.add(new OrderName(orderNames[i].getId(),orderNames[i].getName()));
                    }

                    adapter = new ChefAdapter(ChefActivity.this,array);
                    recyclerView.setAdapter(adapter);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChefActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
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
