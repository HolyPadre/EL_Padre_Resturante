package com.example.el_padre_restaurante;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.el_padre_restaurante.model.User;
import com.example.el_padre_restaurante.model.UserProfile;
import com.google.gson.Gson;

import org.json.JSONArray;

public class LoginActivity extends AppCompatActivity {

    private final String BASE_URL = "http://10.0.2.2/EL_PADRE_RESTURANTE/webservice.php";
    private EditText usernameET;
    private EditText passwordET;
    private TextView errorTV;
    private CheckBox rememberCB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String str = prefs.getString("userprofile", "");
        User user = gson.fromJson(str, User.class);
        usernameET = (EditText) findViewById(R.id.username);
        passwordET = (EditText) findViewById(R.id.password);
        errorTV = (TextView) findViewById(R.id.error);
        rememberCB = (CheckBox) findViewById(R.id.remember);
        if(user != null) {
            UserProfile.setId(user.getId());
            UserProfile.setUsername(user.getUsername());
            UserProfile.setType(user.getType());
            UserProfile.setLoggedIn(true);
            if(UserProfile.getType() == 1) {
                Intent intent = new Intent(LoginActivity.this, CustomerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
            else if(UserProfile.getType() == 2)
            {
                Intent intent = new Intent(LoginActivity.this, ChefActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
            else if(UserProfile.getType() == 3)
            {
                Intent intent = new Intent(LoginActivity.this, DeliveryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
            else if(UserProfile.getType() == 4)
            {
                Intent intent = new Intent(LoginActivity.this, ManegerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

        }



    }

    public void performLogin(View view) {
        String username = usernameET.getText().toString().trim();
        String password = passwordET.getText().toString();

        String query = "Select * from `user` where username = '" + username + "' and password = '" + password + "';";
        String url = BASE_URL + "?query=" + query;

        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response.length() == 1) {
                    Gson gson = new Gson();
                    User[] users = gson.fromJson(response.toString(), User[].class);
                    User user = users[0];

                    UserProfile.setId(user.getId());
                    UserProfile.setLoggedIn(true);
                    UserProfile.setUsername(user.getUsername());
                    UserProfile.setType(user.getType());
                    UserProfile.setGolden(false);

                    if(UserProfile.getType() == 1) {
                        Intent intent = new Intent(LoginActivity.this, CustomerActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(UserProfile.getType() == 2)
                    {
                        Intent intent = new Intent(LoginActivity.this, ChefActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else if(UserProfile.getType() == 3)
                    {
                        Intent intent = new Intent(LoginActivity.this, DeliveryActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else if(UserProfile.getType() == 4)
                    {
                        Intent intent = new Intent(LoginActivity.this, ManegerActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if(rememberCB.isChecked()) {
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor editor = prefs.edit();
                        String userProfileJSON = gson.toJson(user);

                        editor.putString("userprofile", userProfileJSON);
                        editor.commit();
                    }
                } else {
                    errorTV.setText(R.string.login_failed);
                }
            }
        }, new Response.ErrorListener() {
            // this is the error listener method which
            // we will call if we get any error from API.
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonArrayRequest);
    }

    public void gotoRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}