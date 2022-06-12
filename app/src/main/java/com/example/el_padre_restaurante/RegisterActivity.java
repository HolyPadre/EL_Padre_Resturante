package com.example.el_padre_restaurante;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RegisterActivity extends AppCompatActivity {

    private final String BASE_URL = "http://10.0.2.2/EL_PADRE_RESTURANTE/webservice.php";

    private EditText usernameET;
    private EditText passwordET;
    private TextView errorTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameET = (EditText) findViewById(R.id.username);
        passwordET = (EditText) findViewById(R.id.password);
        errorTV = (TextView) findViewById(R.id.error);
    }

    public void performRegister(View view) {
        String username = usernameET.getText().toString().trim();
        String password = passwordET.getText().toString();

        if(isValidUsername(username) && isValidPassword(password)) {
            String query = "insert into user (username, password, type) values ('" + username + "'," + "'" + password + "',1);";
            String url = BASE_URL + "?query=" + query;

            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);


            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.contains("Duplicate")) {
                        errorTV.setText(R.string.username_duplicate);
                    } else {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }, new Response.ErrorListener() {
                // this is the error listener method which
                // we will call if we get any error from API.
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterActivity.this, "Fail to register data..", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}