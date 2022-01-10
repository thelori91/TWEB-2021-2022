package com.ium.fragmentexample;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.os.ResultReceiver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ium.fragmentexample.databinding.FragmentHandleBinding;
import com.ium.fragmentexample.databinding.FragmentThirdBinding;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class HandleFragment extends Fragment {

    FragmentHandleBinding binding;
    MyViewModel myViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        binding = FragmentHandleBinding.inflate(inflater, container, false);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Populate the spinner with your lessons
        binding.lessonSelectionSpinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, myViewModel.upcomingEvents.getValue()));


        binding.sendToCancelledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUpdateRequest("Cancelled");
            }
        });

        binding.sendToDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUpdateRequest("Done");
            }
        });

    }

    public void sendUpdateRequest(String newState) {
        String selectedLesson = (String) binding.lessonSelectionSpinner.getSelectedItem();
        String id = "";
        for (String s : myViewModel.upcomingEvents.getValue()) {
            String sOption = s.split("\n")[0];
            String sId = s.split("\n")[1];
            if (selectedLesson.equals(sOption)) {
                id = sId;
            }
        }

        String url = "http://10.0.2.2:8080/RepetitaIuvant_war_exploded/updateLesson-servlet";
        RequestParams requestParams = new RequestParams();
        requestParams.add("nextState", newState);
        requestParams.add("lessonId", id);
        myViewModel.myHttpClient.get(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody, StandardCharsets.UTF_8);
                String firstRow = response.split("\n")[0];
                Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                if (firstRow.equals("Success:")) {
                    NavHostFragment.findNavController(HandleFragment.this)
                            .navigate(R.id.action_Handle_to_FirstFragment);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getContext(), "Sorry we were unable to reach the server!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}