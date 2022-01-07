package com.ium.fragmentexample;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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

import java.util.HashMap;
import java.util.Map;

public class ThirdFragment extends Fragment {

    private FragmentThirdBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentThirdBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonToLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ThirdFragment.this)
                        .navigate(R.id.action_ThirdFragment_to_SecondFragment);
            }
        });

        binding.buttonSignUpRequest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Register new User
                int passwordLength = binding.passwordEditTextSignUp.getText().toString().length();
                if(passwordLength < 8 || passwordLength > 20)
                {
                    Toast.makeText(getContext(), "Password should be between 8 and 20 characters long", Toast.LENGTH_LONG).show();
                    return;
                }
                String url = "http://10.0.2.2:8080/RepetitaIuvant_war_exploded/signUp-servlet";
                StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
                    String[] rows = response.split("\n");
                    if (rows[0].equals("Success:")) {
                        String usernameFromServer = rows[1];
                        String role = rows[2];
                        Toast.makeText(getContext(), "User: " + usernameFromServer + " role: " + role, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getContext(), "Error, Try Again", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
                    Toast.makeText(getContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("name", binding.nameEditText.getText().toString());
                        params.put("surname", binding.surnameEditText.getText().toString());
                        params.put("uname", binding.usernameEditTextSignUp.getText().toString());
                        params.put("password", binding.passwordEditTextSignUp.getText().toString());
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(request);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}