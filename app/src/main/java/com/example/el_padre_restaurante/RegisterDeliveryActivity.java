package com.example.el_padre_restaurante;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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
import com.example.el_padre_restaurante.model.UserProfile;

public class RegisterDeliveryActivity extends AppCompatActivity {

    private final String BASE_URL = "http://10.0.2.2/EL_PADRE_RESTURANTE/webservice.php";

    private EditText usernameET;
    private EditText passwordET;
    private TextView errorTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_delivery);

        usernameET = (EditText) findViewById(R.id.username_delivery);
        passwordET = (EditText) findViewById(R.id.password_delivery);
        errorTV = (TextView) findViewById(R.id.error);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.all_faces_manager,menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                Intent intent1 = new Intent(RegisterDeliveryActivity.this, ManegerActivity.class);
                startActivity(intent1);
                break;
            case R.id.add_chef:
                Intent intent = new Intent(RegisterDeliveryActivity.this, RegisterChefActivity.class);
                startActivity(intent);
                break;
            case R.id.logout:
                com.example.el_padre_restaurante.model.UserProfile.setUsername(null);
                com.example.el_padre_restaurante.model.UserProfile.setLoggedIn(false);
                com.example.el_padre_restaurante.model.UserProfile.setGolden(false);
                UserProfile.setType(1);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(RegisterDeliveryActivity.this);
                SharedPreferences.Editor editor = prefs.edit();

                editor.remove("userprofile");
                editor.apply();

                Intent intent3 = new Intent(RegisterDeliveryActivity.this, LoginActivity.class);
                startActivity(intent3);
                break;

            case R.id.add_delivery:

                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void performRegisterDelivery(View view) {
        String username = usernameET.getText().toString().trim();
        String password = passwordET.getText().toString();

        if(isValidUsername(username) && isValidPassword(password)) {
            String query = "insert into user (username, password, type) values ('" + username + "'," + "'" + password + "',3);";
            String url = BASE_URL + "?query=" + query;

            RequestQueue queue = Volley.newRequestQueue(RegisterDeliveryActivity.this);


            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.contains("Duplicate")) {
                        errorTV.setText(R.string.username_duplicate);
                    } else {
                        Toast.makeText(RegisterDeliveryActivity.this, "Add Delivery successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterDeliveryActivity.this, ManegerActivity.class);
                        startActivity(intent);
                    }

                }
            }, new Response.ErrorListener() {
                // this is the error listener method which
                // we will call if we get any error from API.
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterDeliveryActivity.this, "Fail to register data..", Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(stringRequest);
        }
    }

    public boolean isValidUsername(String username) {
        for (int i = 0; i < username.length(); i++) {
            if (!Character.isLetterOrDigit(username.charAt(i))) {
                errorTV.setText(R.string.username_invalid);
                return false;
            }
        }
        if(username.length() < 3) {
            errorTV.setText(R.string.username_min_characters);
            return false;
        }

        if(username.length() > 15) {
            errorTV.setText(R.string.username_max_characters);
            return false;
        }

        return true;
    }

    public boolean isValidPassword(String password) {
        if(password.length() < 6) {
            errorTV.setText(R.string.password_min_characters);
            return false;
        }

        if(password.length() > 20) {
            errorTV.setText(R.string.password_max_characters);
            return false;
        }

        return true;
    }
    public void gotoLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public void onBackPressed() {
        Intent intent = new Intent(RegisterDeliveryActivity.this, ManegerActivity.class);
        startActivity(intent);
    }
}