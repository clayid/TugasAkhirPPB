package com.derynm.uas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText username,password;
    Button login,reset;
    TextView register;

    SharedPreferences.Editor username_shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username_login);
        password = findViewById(R.id.password_login);
        login = findViewById(R.id.login_btn);
        reset = findViewById(R.id.reset_btn_login);
        register = findViewById(R.id.register_txt);

        username_shared = getSharedPreferences("username",MODE_PRIVATE).edit();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_log = username.getText().toString();
                String paswd_log = password.getText().toString();
                TryLogin(user_log,paswd_log);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setText("");
                username.setText("");
            }
        });
    }

    private void TryLogin(String username, String password) {
        String URL = "http://10.0.2.2/wisata_tempelmahbang/rest_api/login.php";
        RequestQueue queue = Volley.newRequestQueue(Login.this);
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("1")) {

                            Intent dashboard = new Intent(Login.this, Dashboard.class);
                            username_shared.putString("username",username);
                            username_shared.apply();
                            startActivity(dashboard);
//                            Login.this.finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Salahhh", Toast.LENGTH_SHORT).show();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                return params;
            }
        };
        queue.add(request);
    }

}