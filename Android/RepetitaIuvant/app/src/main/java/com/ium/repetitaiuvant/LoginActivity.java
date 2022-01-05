package com.ium.repetitaiuvant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AsyncCache;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onLogInButtonClicked(View view) {
        final TextView outputLabel = (TextView) findViewById(R.id.textViewProva);
        //10.0.2.2 means the computer running the emulator
        String url = "http://10.0.2.2:8080/RepetitaIuvant_war_exploded/signIn-servlet";
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            String[] rows = response.split("\n");
            if (rows[0].equals("Success:")) {
                String usernameFromServer = rows[1];
                String role = rows[2];
                Toast.makeText(LoginActivity.this, "User: " + usernameFromServer + " role: " + role, Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(LoginActivity.this, "Error, Try Again", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Toast.makeText(LoginActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uname", ((TextView)findViewById(R.id.usernameTextView)).getText().toString());
                params.put("password", ((TextView)findViewById(R.id.passwordTextView)).getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}