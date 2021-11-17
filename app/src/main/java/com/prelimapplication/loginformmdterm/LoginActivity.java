package com.prelimapplication.loginformmdterm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.prelimapplication.loginformmdterm.api.RetrofitBuilder;
import com.google.android.material.button.MaterialButton;
import com.prelimapplication.loginformmdterm.api.RequestPlaceholder;
import com.prelimapplication.loginformmdterm.pojos.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public EditText username,password, email;
    public MaterialButton loginBtn;

    public RetrofitBuilder retrofitBuilder;
    public RequestPlaceholder requestPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        loginBtn = findViewById(R.id.loginBtn);

        retrofitBuilder = new RetrofitBuilder();
        requestPlaceholder = retrofitBuilder.getRetrofit().create(RequestPlaceholder.class);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Login> loginCall = requestPlaceholder.login(new Login(null, username.getText().toString(),null, null, password.getText().toString()));
                loginCall.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        if (response.isSuccessful()){
                            Log.e("Logging in Error", response.message());
                        }else{
                            if (response.code() == 200){
                                Toast.makeText(LoginActivity.this, "logging in Successful ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "You have an error logging the Api", Toast.LENGTH_SHORT).show();
                        Log.e("Logging in Error", t.getMessage());
                    }
                });
            }
        });
    }
}