package com.ium.repetitaiuvant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onLogInButtonClicked(View view) {
        final TextView outputLabel = (TextView) findViewById(R.id.textViewProva);
        //10.0.2.2 means the computer running the emulator
        String url ="http://10.0.2.2:8080/RepetitaIuvant_war_exploded/signIn-servlet";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    // Display the first 500 characters of the response string.
                    outputLabel.setText("Response is: " + response);
                }, error -> outputLabel.setText("That didn't work!"));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}