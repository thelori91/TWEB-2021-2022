package com.ium.repetitaiuvant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ium.repetitaiuvant.ui.login.LoginFragment;
import com.ium.repetitaiuvant.ui.main.MainFragment;
import com.ium.repetitaiuvant.viewmodel.MyViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, MainFragment.newInstance())
                    .commitNow();
        }

        MyViewModel model = new ViewModelProvider(this).get(MyViewModel.class);
        model.getUsername().observe(this, users -> {
            TextView usernameTextView = findViewById(R.id.usernameDisplayTextView);
            usernameTextView.setText(model.getUsername().getValue());
        });

    }

    public void onLoginButtonClicked(View view) {
        //Switch fragment
        Fragment loginFragment = new LoginFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, loginFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}