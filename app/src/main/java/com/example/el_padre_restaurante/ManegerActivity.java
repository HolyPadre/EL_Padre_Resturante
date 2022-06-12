package com.example.el_padre_restaurante;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.el_padre_restaurante.model.SoldReport;
import com.example.el_padre_restaurante.model.User;
import com.example.el_padre_restaurante.model.UserReport;
import com.example.el_padre_restaurante.model.UserProfile;
import com.google.gson.Gson;

import org.json.JSONArray;



public class ManegerActivity extends AppCompatActivity {
    ListView listView;
    String[] customersList;
    private long backPressedTime;
    private Toast backToast;
    private final String BASE_URL = "http://10.0.2.2/EL_PADRE_RESTURANTE/webservice.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manger);
        TextView textView = (TextView) findViewById(R.id.account_name);
        textView.setText(UserProfile.getUsername());
        listView = (ListView) findViewById(R.id.reports_list);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.all_faces_manager,menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_delivery:
                Intent intent = new Intent(ManegerActivity.this, RegisterDeliveryActivity.class);
                startActivity(intent);
                break;
            case R.id.add_chef:
                Intent intent2 = new Intent(ManegerActivity.this, RegisterChefActivity.class);
                startActivity(intent2);
                break;
            case R.id.logout:
                UserProfile.setUsername(null);
                UserProfile.setLoggedIn(false);
                UserProfile.setGolden(false);
                UserProfile.setType(1);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ManegerActivity.this);
                SharedPreferences.Editor editor = prefs.edit();

                editor.remove("userprofile");
                editor.apply();

                Intent intent3 = new Intent(ManegerActivity.this, LoginActivity.class);
                startActivity(intent3);
                finish();
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Gson gson = new Gson();
        String str = gson.toJson(customersList);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ManegerActivity.this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("customer_list", str);
        editor.apply();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String str = prefs.getString("customer_list", "");
        customersList = gson.fromJson(str, String[].class);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ManegerActivity.this,
                android.R.layout.simple_list_item_1, customersList);
        listView.setAdapter(arrayAdapter);

    }

    public void customerList(View view){
        String query = "SELECT u.id,u.username FROM `user` u WHERE type = 1;";
        String url = BASE_URL + "?query=" + query;

        RequestQueue queue = Volley.newRequestQueue(ManegerActivity.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length() !=0) {
                    Gson gson = new Gson();
                    UserReport[] customer = gson.fromJson(response.toString(), UserReport[].class);
                    customersList = new String[customer.length];
                    for (int i = 0; i < customer.length; i++) {
                        customersList[i] = customer[i].toString();
                    }
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ManegerActivity.this,
                            android.R.layout.simple_list_item_1, customersList);
                    listView.setAdapter(arrayAdapter);

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ManegerActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);

    }

    public void chefsList(View view){

        String query = "SELECT u.id,u.username FROM `user` u WHERE type = 2;";
        String url = BASE_URL + "?query=" + query;

        RequestQueue queue = Volley.newRequestQueue(ManegerActivity.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length() !=0) {
                    Gson gson = new Gson();
                    UserReport[] customer = gson.fromJson(response.toString(), UserReport[].class);
                    customersList = new String[customer.length];
                    for (int i = 0; i < customer.length; i++) {
                        customersList[i] = customer[i].toString();
                    }


                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ManegerActivity.this,
                            android.R.layout.simple_list_item_1, customersList);
                    listView.setAdapter(arrayAdapter);

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ManegerActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }
    public void deliveriesList(View view){

        String query = "SELECT u.id,u.username FROM `user` u WHERE type = 3;";
        String url = BASE_URL + "?query=" + query;

        RequestQueue queue = Volley.newRequestQueue(ManegerActivity.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length() !=0) {
                    Gson gson = new Gson();
                    UserReport[] customer = gson.fromJson(response.toString(), UserReport[].class);
                    customersList = new String[customer.length];
                    for (int i = 0; i < customer.length; i++) {
                        customersList[i] = customer[i].toString();
                    }


                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ManegerActivity.this,
                            android.R.layout.simple_list_item_1, customersList);
                    listView.setAdapter(arrayAdapter);

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ManegerActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);

    }
    public void reportList(View view){
        String query = "SELECT u.username , p.price , p.name FROM `sold_items` s, `user` u , `product` p WHERE u.id = s.u_id AND p.id = s.p_id;";
        String url = BASE_URL + "?query=" + query;

        RequestQueue queue = Volley.newRequestQueue(ManegerActivity.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length() !=0) {
                    Gson gson = new Gson();
                    SoldReport[] customer = gson.fromJson(response.toString(), SoldReport[].class);
                    customersList = new String[customer.length];
                    for (int i = 0; i < customer.length; i++) {
                        customersList[i] = customer[i].toString();
                    }
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ManegerActivity.this,
                            android.R.layout.simple_list_item_1, customersList);
                    listView.setAdapter(arrayAdapter);

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ManegerActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
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
