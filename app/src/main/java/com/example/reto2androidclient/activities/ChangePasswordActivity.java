package com.example.reto2androidclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reto2androidclient.R;
import com.example.reto2androidclient.client.RESTClientClient;
import com.example.reto2androidclient.client.RESTClientInterface;
import com.example.reto2androidclient.exceptions.UnexpectedErrorException;
import com.example.reto2androidclient.model.Client;
import com.example.reto2androidclient.model.User;
import com.example.reto2androidclient.security.PublicCrypt;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.reto2androidclient.security.PublicCrypt.*;

/**
 * Controller for the forgot password window.
 *
 * @author Matteo Fernández
 */
public class ChangePasswordActivity extends AppCompatActivity {
    private EditText editTextOldPassword, editTextNewPassword, editTextRewritePassword;
    private Button buttonChangePassword, buttonBack;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        editTextOldPassword = findViewById(R.id.editTextOldPassword);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextRewritePassword = findViewById(R.id.editTextRewritePassword);

        buttonChangePassword = findViewById(R.id.buttonChangePassword);
        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Makes a password change request.
                changePassword();
            }
        });

        buttonBack = findViewById(R.id.buttonBackChangePassword);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Switching to Log In activity...
                Intent intent = new Intent(ChangePasswordActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
    }
    /**
     * Makes a change password request to the server.
     */
    private void changePassword() {
        //Checking both passwords are equal...
        String pass = editTextNewPassword.getText().toString();
        String passwordConfirmation = editTextRewritePassword.getText().toString();
        if(pass.equalsIgnoreCase(passwordConfirmation)) {
            String encodedPassword;
            try {
                encodedPassword = encode(ChangePasswordActivity.this, passwordConfirmation);
            } catch (UnexpectedErrorException e) {
                e.printStackTrace();
            }
            RESTClientInterface rest = RESTClientClient.getClient();
            Call<ResponseBody> call;
            call = rest.edit((Client) user);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    switch(response.code()) {
                        case 204:
                            //The password was changed successfully.
                            Toast.makeText(getApplicationContext(), getString(R.string.changePassword_passwordChanged), Toast.LENGTH_LONG).show();
                            break;
                        case 400:
                            //Password was not changed.
                            Toast.makeText(getApplicationContext(), getString(R.string.changePassword_notPasswordChanged), Toast.LENGTH_LONG).show();
                            break;
                        default:
                            //Unexpected error
                            Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                    }
                }  @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    //The request did not get to the server.
                    Toast.makeText(getApplicationContext(), getString(R.string.unexpectedError), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.changePassword_passwordDoNotMatch), Toast.LENGTH_LONG).show();
        }
        }
    }