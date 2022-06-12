package com.example.el_padre_restaurante;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.el_padre_restaurante.model.Product;
import com.example.el_padre_restaurante.model.User;
import com.example.el_padre_restaurante.model.UserProfile;
import com.google.gson.Gson;

public class ItemActivity extends AppCompatActivity {
    TextView tv;
    ImageView img;
    Product product;
    private final String BASE_URL = "http://10.0.2.2/EL_PADRE_RESTURANTE/webservice.php";
    private final String IMAGE_URL = "http://10.0.2.2/EL_PADRE_RESTURANTE/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Intent intent = getIntent();
        tv = findViewById(R.id.text_view_search);
        img = findViewById(R.id.image_item_search);
        String item_product = intent.getStringExtra("item_product");
        Gson gson = new Gson();
        product = gson.fromJson(item_product, Product.class);
        tv.setText(product.getDis());
        String url = product.getImage();
        Glide.with(this).load(url).into(img);
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
                break;
            case R.id.profile:
                Intent intent = new Intent(ItemActivity.this, CustomerActivity.class);
                startActivity(intent);
                break;
            case R.id.search:
                Intent intent3 = new Intent(ItemActivity.this, SearchActivity.class);
                startActivity(intent3);
                break;
            case R.id.logout:
                UserProfile.setUsername(null);
                UserProfile.setLoggedIn(false);
                UserProfile.setType(1);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ItemActivity.this);
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove("userprofile");
                editor.apply();

                Intent intent1 = new Intent(ItemActivity.this, LoginActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void takeOrder(View view){
            EditText editText = findViewById(R.id.editTextTextAddres);
            if(editText.getText().length() != 0) {
                String query = "INSERT INTO `order`(`p_id`, `address`, `c_id`, `active`, `status`) VALUES ('"+product.getId()+"','"+editText.getText()+"','"+UserProfile.getId()+"','1','1');";
                String url = BASE_URL + "?query=" + query;

                RequestQueue queue = Volley.newRequestQueue(ItemActivity.this);


                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(ItemActivity.this, SearchActivity.class);
                        startActivity(intent);
                        Toast.makeText(ItemActivity.this, "Your order has been successfully", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    // this is the error listener method which
                    // we will call if we get any error from API.
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ItemActivity.this, "Fail to register data..", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(stringRequest);
            }
            else{
                Toast.makeText(ItemActivity.this, "Please type Address", Toast.LENGTH_SHORT).show();

            }

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ItemActivity.this, CustomerActivity.class);
        startActivity(intent);
    }
}