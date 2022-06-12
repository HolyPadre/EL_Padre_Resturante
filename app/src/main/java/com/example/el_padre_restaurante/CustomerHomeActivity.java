package com.example.el_padre_restaurante;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.el_padre_restaurante.model.User;
import com.example.el_padre_restaurante.model.UserProfile;
import com.google.gson.Gson;

public class CustomerHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_loged_in);
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
                Intent intent = new Intent(CustomerHomeActivity.this, CustomerActivity.class);
                startActivity(intent);
                break;
            case R.id.search:
                Intent intent4 = new Intent(CustomerHomeActivity.this, SearchActivity.class);
                startActivity(intent4);
                break;
            case R.id.logout:
                UserProfile.setUsername(null);
                UserProfile.setLoggedIn(false);
                UserProfile.setType(1);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(CustomerHomeActivity.this);
                SharedPreferences.Editor editor = prefs.edit();

                editor.remove("userprofile");
                editor.apply();

                Intent intent1 = new Intent(CustomerHomeActivity.this, LoginActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void goToSearch(View view){
        Intent intent1 = new Intent(CustomerHomeActivity.this, SearchActivity.class);
        startActivity(intent1);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CustomerHomeActivity.this, CustomerActivity.class);
        startActivity(intent);
    }
}