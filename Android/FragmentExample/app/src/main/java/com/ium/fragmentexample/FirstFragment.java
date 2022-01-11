package com.ium.fragmentexample;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ium.fragmentexample.databinding.FragmentFirstBinding;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private MyViewModel myViewModel;
    Observer<ArrayList<String>> eventsObserver = null;
    Observer<String> roleObserver = null;
    Observer<String> usernameObserver = null;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        eventsObserver = new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                updateSpinner();
            }
        };

        roleObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                updateButtons(myViewModel.role.getValue().equals("Student") || myViewModel.role.getValue().equals("Admin"));
            }
        };

        usernameObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                updateButtons(myViewModel.role.getValue().equals("Student") || myViewModel.role.getValue().equals("Admin"));
            }
        };

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        return binding.getRoot();
    }

    void updateButtons(boolean logged) {
        binding.buttonLogOut.setVisibility(logged ? View.VISIBLE : View.GONE);
        binding.buttonLogIn.setVisibility(logged ? View.GONE : View.VISIBLE);
        binding.buttonSignUp.setVisibility(logged ? View.GONE : View.VISIBLE);
    }

    public void updateSpinner() {
        //Update the ui
        binding.roleDisplay.setText(myViewModel.role.getValue());
        ArrayList<String> arrayList = myViewModel.getUpcomingEventsOptions();

        if (arrayList != null) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, arrayList);
            binding.upcomingEventsListView.setAdapter(arrayAdapter);
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myViewModel.upcomingEvents.observe(getActivity(), eventsObserver);
        myViewModel.role.observe(getActivity(), roleObserver);
        myViewModel.username.observe(getActivity(), usernameObserver);

        //Updating upcoming events in myViewModel
        myViewModel.myHttpClient.post("http://10.0.2.2:8080/RepetitaIuvant_war_exploded/onLoad-servlet", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody, StandardCharsets.UTF_8);
                ArrayList<String> events = null;
                try {
                    events = SecondFragment.parseUpcomingEventsJSON(response, myViewModel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                myViewModel.upcomingEvents.setValue(events);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getContext(), "Error Getting The request", Toast.LENGTH_LONG).show();
            }
        });

        updateSpinner();


        //Go to login Button
        binding.buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = NavHostFragment.findNavController(FirstFragment.this);
                navController.navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        //Go to Sign Up button
        binding.buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_ThirdFragment);
            }
        });

        //Go to New Lesson button
        binding.newReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_NewLesson);
            }
        });

        //Handle reservations
        binding.handleReservationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(myViewModel.role.getValue().equals("Student") || myViewModel.role.getValue().equals("Admin"))) {
                    Toast.makeText(getContext(), "Please, Log in first!", Toast.LENGTH_SHORT).show();
                } else if (myViewModel.upcomingEvents.getValue().size() == 0) {
                    Toast.makeText(getContext(), "There are no upcoming events to be handled", Toast.LENGTH_SHORT).show();
                } else {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_HandleFragment);
                }

            }
        });

        //Log Out
        binding.buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://10.0.2.2:8080/RepetitaIuvant_war_exploded/logOut-servlet";
                myViewModel.myHttpClient.get(url, null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        myViewModel.username.setValue("");
                        myViewModel.role.setValue("Guest");
                        myViewModel.upcomingEvents.setValue(new ArrayList<>());
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(getContext(), "Failed to log out", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        myViewModel.upcomingEvents.removeObservers(getActivity());
        myViewModel.role.removeObservers(getActivity());
        myViewModel.username.removeObservers(getActivity());
        binding = null;
    }

}