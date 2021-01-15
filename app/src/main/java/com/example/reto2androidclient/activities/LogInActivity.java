package com.example.reto2androidclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reto2androidclient.R;
import com.example.reto2androidclient.client.RESTUserClient;
import com.example.reto2androidclient.client.RESTUserInterface;
import com.example.reto2androidclient.model.Client;
import com.example.reto2androidclient.security.PublicCrypt;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Controller for the log in window.
 *
 * @author Aitor Fidalgo
 */
public class LogInActivity extends AppCompatActivity {

    private Button buttonSignIn, buttonSignUp;
    private EditText editTextLogin, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToSignUp = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intentToSignUp);
            }
        });

        editTextLogin = findViewById(R.id.editTextUsernameLogIn);
        editTextPassword = findViewById(R.id.editTextPasswordLogIn);
    }

    private void signIn() {
        String login = editTextLogin.getText().toString();
        String password = editTextPassword.getText().toString();
        String encodedPassword = PublicCrypt.encode(LogInActivity.this, password);
        Toast.makeText(getApplicationContext(), encodedPassword, Toast.LENGTH_LONG).show();

        RESTUserInterface restUserInterface = RESTUserClient.getClient();
        Call<Client> callLogIn = restUserInterface.signIn(login, encodedPassword);
        callLogIn.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                switch(response.code()) {
                    case 200:
                        //Success.
                        Client client = response.body();
                        Intent intentToHome = new Intent(LogInActivity.this, HomeActivity.class);
                        intentToHome.putExtra("CLIENT", client);
                        startActivity(intentToHome);
                        break;
                    case 401:
                        //Unauthorized.
                        Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                        break;
                    case 500:
                        //Internal Server Error.
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
            }
        });
    }
}