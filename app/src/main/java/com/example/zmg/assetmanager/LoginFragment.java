package com.example.zmg.assetmanager;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class LoginFragment extends Fragment {

    private Button btnLogin;
    private ImageButton btnSettings;
    private EditText loginUsername;
    private EditText loginPassword;

    public static String username;
    public static String password;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View loginFragment = inflater.inflate(R.layout.fragment_login, container, false);

        initWidgets(loginFragment);
        setupListeners();
        getPreference();

        return loginFragment;
    }

    private void loginCheck() {



        username = loginUsername.getText().toString();
        password = loginPassword.getText().toString();

        setPreference();

        // TODO:
        // napraviti login rute na API-ju
        // retrofitom ulogirati korisnika

        if (username.equals("fran") && password.equals("12345")){

            Intent intent = new Intent(getActivity(), AssetListActivity.class);
            startActivity(intent);

        }else{
            Toast.makeText(getActivity(), "Login failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupListeners() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginCheck();

            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), CustomSettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initWidgets(View loginFragment) {
        btnSettings = loginFragment.findViewById(R.id.settingsButton);
        btnLogin = loginFragment.findViewById(R.id.loginButton);
        loginUsername = loginFragment.findViewById(R.id.loginUsername);
        loginPassword = loginFragment.findViewById(R.id.loginPassword);
    }

    private void setPreference(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        SharedPreferences.Editor editor = preferences.edit();

        if(preferences.getBoolean("login", true)){
            editor.putString("username", username);
            editor.putString("password", password);
            editor.commit();
        }
    }

    private void getPreference(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        if (preferences.contains("username")){
            username = preferences.getString("username", "");
            loginUsername.setText(username);
        }

        if (preferences.contains("password")){
            password = preferences.getString("password", "");
            loginPassword.setText(password);
        }
    }

}
