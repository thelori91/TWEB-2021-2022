package com.ium.fragmentexample;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ium.fragmentexample.databinding.FragmentSecondBinding;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import cz.msebera.android.httpclient.Header;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private MyViewModel myViewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Go back to login Event
        binding.buttonToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_ThirdFragment);
            }
        });

        //Get all lessons upon successful login request
        binding.loginRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams requestParams = new RequestParams();
                requestParams.add("uname", binding.usernameEditText.getText().toString());
                requestParams.add("password", binding.passwordEditText.getText().toString());
                myViewModel.myHttpClient.post("http://10.0.2.2:8080/RepetitaIuvant_war_exploded/signIn-servlet", requestParams, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String response = new String(responseBody, StandardCharsets.UTF_8);
                        String[] rows = response.split("\n");
                        if (rows[0].equals("Success:")) {
                            String usernameFromServer = rows[1];
                            String role = rows[2];
                            //This should trigger the onChange declared in MainActivity
                            myViewModel.role.setValue(role);
                            myViewModel.username.setValue(usernameFromServer);
                            myViewModel.password.setValue(binding.passwordEditText.getText().toString());
                            //Second Request!!!/////////////////////
                            myViewModel.myHttpClient.post("http://10.0.2.2:8080/RepetitaIuvant_war_exploded/onLoad-servlet", null, new AsyncHttpResponseHandler() {

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                    String response = new String(responseBody, StandardCharsets.UTF_8);
                                    ArrayList<String> events = null;
                                    try {
                                        events = parseUpcomingEventsJSON(response);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    myViewModel.upcomingEvents.setValue(events);
                                    NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                    Toast.makeText(getContext(), "Manco cosí", Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "Error, Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.i("statusCode", String.valueOf(statusCode));
                    }
                });
            }
        });
    }

    private ArrayList<String> parseUpcomingEventsJSON(String response) throws JSONException {
        ArrayList<String> ret = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response);
        for (int i = 1; i < jsonArray.length(); i++) {
            JSONObject result = jsonArray.getJSONObject(i);
            String lesson = result.getString("course") + " " +
                    result.getString("teacherName") + " " +
                    result.getString("teacherSurname") + " " +
                    result.getString("day") + " " +
                    result.getString("time") + " " +
                    result.getString("state");
            ret.add(lesson);
        }
        return ret;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}