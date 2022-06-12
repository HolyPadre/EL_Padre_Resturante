package com.example.el_padre_restaurante;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
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
import com.example.el_padre_restaurante.model.Product;
import com.example.el_padre_restaurante.model.UserProfile;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private final String BASE_URL = "http://10.0.2.2/EL_PADRE_RESTURANTE/webservice.php";
    private final String IMAGE_URL = "http://10.0.2.2/EL_PADRE_RESTURANTE/";
    private List<Product> items = new ArrayList<>();
    private static RecyclerView recycler;
    static CaptionedImagesAdapterSearch adapter;
    private static CaptionedImagesAdapterSearch.RecyclerViewClickListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recycler = findViewById(R.id.foodMenu_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
       loadData();
    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.homepage:
                Intent intent = new Intent(SearchActivity.this, CustomerHomeActivity.class);
                startActivity(intent);
                break;
            case R.id.profile:
                Intent intent3 = new Intent(SearchActivity.this, CustomerActivity.class);
                startActivity(intent3);
                break;
            case R.id.search:
                break;
            case R.id.logout:
                com.example.el_padre_restaurante.model.UserProfile.setUsername(null);
                com.example.el_padre_restaurante.model.UserProfile.setLoggedIn(false);
                com.example.el_padre_restaurante.model.UserProfile.setGolden(false);
                UserProfile.setType(1);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SearchActivity.this);
                SharedPreferences.Editor editor = prefs.edit();

                editor.remove("userprofile");
                editor.apply();

                Intent intent2 = new Intent(SearchActivity.this, LoginActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent2);
                finish();
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.nav,menu);
        MenuItem menuItem = menu.findItem(R.id.nav_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("What are you feeling...?");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
              adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void loadData()
    {
        String queryReq = "Select * from product;";
        String url = BASE_URL + "?query=" + queryReq;
        RequestQueue queue = Volley.newRequestQueue(SearchActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();
               Product[] array=gson.fromJson(response.toString(), Product[].class);
               for(int i=0;i<array.length;i++)
                {
                   items.add(new Product(array[i].getId(),array[i].getName(),array[i].getCategory(),array[i].getDescription(),array[i].getPrice(),IMAGE_URL+array[i].getImage()));
                }

                adapter = new CaptionedImagesAdapterSearch(SearchActivity.this, items, listener);
                recycler.setAdapter(adapter);
            }
        },new Response.ErrorListener() {
            // this is the error listener method which
            // we will call if we get any error from API.
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }
    @Override
    public void onBackPressed() {
        Intent intent1 = new Intent(SearchActivity.this, CustomerHomeActivity.class);
        startActivity(intent1);
    }
}