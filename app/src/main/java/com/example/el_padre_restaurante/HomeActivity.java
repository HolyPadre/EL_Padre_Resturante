package com.example.el_padre_restaurante;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.example.el_padre_restaurante.model.User;
import com.example.el_padre_restaurante.model.UserProfile;
import com.google.gson.Gson;

public class HomeActivity extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String str = prefs.getString("userprofile", "");
        User user = gson.fromJson(str, User.class);
        if(user != null) {
            UserProfile.setId(user.getId());
            UserProfile.setUsername(user.getUsername());
            UserProfile.setType(user.getType());
            UserProfile.setLoggedIn(true);
            if(UserProfile.getType() == 1) {
                Intent intent = new Intent(HomeActivity.this, CustomerHomeActivity.class);
                startActivity(intent);
            }
            else if(com.example.el_padre_restaurante.model.UserProfile.getType() == 2)
            {
                Intent intent = new Intent(HomeActivity.this, ChefActivity.class);
                startActivity(intent);
            }
            else if(com.example.el_padre_restaurante.model.UserProfile.getType() == 3)
            {
                Intent intent = new Intent(HomeActivity.this, DeliveryActivity.class);
                startActivity(intent);
            }
            else if(UserProfile.getType() == 4)
            {
                Intent intent = new Intent(HomeActivity.this, ManegerActivity.class);
                startActivity(intent);
            }

        }
    }
    public void goToLoginPage(View view){
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    public void goToRegisterPage(View view){
        Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
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