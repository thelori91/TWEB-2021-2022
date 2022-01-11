package com.ium.fragmentexample;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ium.fragmentexample.databinding.FragmentSecondBinding;
import com.ium.fragmentexample.databinding.FragmentThirdBinding;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class ThirdFragment extends Fragment {

    private FragmentThirdBinding binding;
    private MyViewModel myViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        binding = FragmentThirdBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ThirdFragment.this)
                        .navigate(R.id.action_ThirdFragment_to_SecondFragment);
            }
        });

        binding.buttonSignUpRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Register new User
                int passwordLength = binding.passwordEditTextSignUp.getText().toString().length();
                if (passwordLength < 8 || passwordLength > 20) {
                    Toast.makeText(getContext(), "Password should be between 8 and 20 characters long", Toast.LENGTH_LONG).show();
                    return;
                }
                RequestParams requestParams = new RequestParams();
                requestParams.add("name", binding.nameEditText.getText().toString().trim());
                requestParams.add("surname", binding.surnameEditText.getText().toString().trim());
                requestParams.add("uname", binding.usernameEditTextSignUp.getText().toString().trim());
                requestParams.add("password", binding.passwordEditTextSignUp.getText().toString().trim());
                String url = "http://10.0.2.2:8080/RepetitaIuvant_war_exploded/signUp-servlet";
                myViewModel.myHttpClient.post(url, requestParams, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String response = new String(responseBody, StandardCharsets.UTF_8);
                        String[] rows = response.split("\n");
                        if (rows[0].equals("Success:")) {
                            String usernameFromServer = rows[1];
                            String role = rows[2];
                            myViewModel.role.setValue(role);
                            myViewModel.username.setValue(usernameFromServer);
                            myViewModel.password.setValue(binding.passwordEditTextSignUp.getText().toString().trim());
                            myViewModel.myHttpClient.post("http://10.0.2.2:8080/RepetitaIuvant_war_exploded/onLoad-servlet", null, new AsyncHttpResponseHandler() {

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                    String response = new String(responseBody, StandardCharsets.UTF_8);
                                    ArrayList<String> events = null;
                                    try {
                                        events = parseUpcomingEventsJSON(response, myViewModel);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    myViewModel.upcomingEvents.setValue(events);
                                    NavHostFragment.findNavController(ThirdFragment.this).navigate(R.id.action_ThirdFragment_to_FirstFragment);
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                    Toast.makeText(getContext(), "Error Getting The request", Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "Error, Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          byte[] responseBody, Throwable error) {
                        Log.i("statusCode", String.valueOf(statusCode));

                    }
                });
            }
        });
    }

    public static ArrayList<String> parseUpcomingEventsJSON(String response, MyViewModel vm) throws JSONException {
        ArrayList<String> ret = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response);
        JSONObject starter = jsonArray.getJSONObject(0);
        vm.username.setValue(starter.getString("username"));
        vm.role.setValue(starter.getString("role"));
        for (int i = 1; i < jsonArray.length(); i++) {
            JSONObject result = jsonArray.getJSONObject(i);
            boolean isActive = result.getString("state").equals("Active");
            String lesson = result.getString("course") + " " +
                    result.getString("teacherName") + " " +
                    result.getString("teacherSurname") + " " +
                    result.getString("day") + " " +
                    result.getString("time") + "\n" +
                    result.getString("lessonId");

            if (isActive) ret.add(lesson);
        }
        return ret;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}