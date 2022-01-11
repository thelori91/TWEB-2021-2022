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

import com.ium.fragmentexample.databinding.FragmentSecondBinding;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

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
                if (binding.passwordEditText.getText().toString().trim().length() >= 8 && binding.passwordEditText.getText().toString().trim().length() <= 20) {
                    RequestParams requestParams = new RequestParams();
                    requestParams.add("uname", binding.usernameEditText.getText().toString().trim());
                    requestParams.add("password", binding.passwordEditText.getText().toString().trim());
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
                                myViewModel.password.setValue(binding.passwordEditText.getText().toString().trim());
                                //Second Request!!!/////////////////////
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
                                        NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
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
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Log.i("statusCode", String.valueOf(statusCode));
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Error, Password should be between 8 and 20 characters long", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static ArrayList<String> parseUpcomingEventsJSON(String response, MyViewModel vm) throws JSONException {
        ArrayList<String> ret = new ArrayList<>();
        ArrayList<String> pastEvents = new ArrayList<>();
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
            else {
                String toAdd = lesson.split("\n")[0] + "\n" + " " + result.getString("state");
                pastEvents.add(toAdd);
            }
        }
        vm.pastEvents.setValue(pastEvents);
        return ret;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}