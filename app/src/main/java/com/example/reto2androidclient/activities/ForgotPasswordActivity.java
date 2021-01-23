package com.example.reto2androidclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reto2androidclient.R;
import com.example.reto2androidclient.client.RESTClientClient;
import com.example.reto2androidclient.client.RESTClientInterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextConfirmEmail;
    private Button buttonResetPassword, buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmail = findViewById(R.id.editTextEmailForgotPassword);
        editTextConfirmEmail = findViewById(R.id.editTextConfirmEmailForgotPassword);

        buttonResetPassword = findViewById(R.id.buttonResetPasswordForgotPassword);
        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String emailConfirmation = editTextConfirmEmail.getText().toString();
                if(email.equalsIgnoreCase(emailConfirmation)) {
                    RESTClientInterface rest = RESTClientClient.getClient();
                    Call<ResponseBody> call = rest.recoverPassword(email);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            switch(response.code()) {
                                case 204:
                                    Toast.makeText(getApplicationContext(), getString(R.string.forgotPassword_passwordReset), Toast.LENGTH_LONG).show();
                                    break;
                                case 401:
                                    Toast.makeText(getApplicationContext(), getString(R.string.forgotPassword_emailNotRegistered), Toast.LENGTH_LONG).show();
                                    break;
                                default:
                                    Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), getString(R.string.unexpectedError), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        buttonBack = findViewById(R.id.buttonBackForgotPassword);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
    }
}