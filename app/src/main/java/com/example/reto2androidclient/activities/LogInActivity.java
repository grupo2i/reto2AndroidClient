package com.example.reto2androidclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.reto2androidclient.R;
import com.example.reto2androidclient.client.RESTUserClient;
import com.example.reto2androidclient.client.RESTUserInterface;
import com.example.reto2androidclient.model.Client;
import com.example.reto2androidclient.security.PublicCrypt;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Controller for the log in window.
 *
 * @author Aitor Fidalgo
 */
public class LogInActivity extends AppCompatActivity {

    private SQLiteDatabase sqLiteDatabase = null;

    private Button buttonSignIn, buttonSignUp;
    private EditText editTextLogin, editTextPassword;
    private Switch switchRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        sqLiteDatabase = openOrCreateDatabase("sqLiteDatabase", Context.MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS sessions " +
                "(id INT PRIMARY KEY NOT NULL," +
                "login VARCHAR NOT NULL," +
                "password VARCHAR NOT NULL)");
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM sessions", null);
        cursor.moveToFirst();
        //If there is a session stored in the database log in with that user account.
        if(cursor.getCount() != 0) {
            //sqLiteDatabase.execSQL("DELETE FROM sessions WHERE login='aitorfidalgo'");
            signIn(cursor.getString(1), cursor.getString(2));
        }

        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String login = editTextLogin.getText().toString();
                    String password = editTextPassword.getText().toString();
                    //Checking if any of the fields is empty...
                    if (login.length() == 0 || password.length() == 0)
                        throw new IOException(getString(R.string.logIn_emptyFieldsError));
                    //Encoding password with RSA.
                    String encodedPassword = PublicCrypt.encode(LogInActivity.this, password);
                    //Checking if remember me switch is checked...
                    if (switchRememberMe.isChecked()) {
                        sqLiteDatabase.execSQL("INSERT INTO sessions VALUES (1, '" + login + "', '" + encodedPassword + "')");
                    }
                    //Sending sign in request to the server.
                    signIn(login, encodedPassword);
                } catch (IOException ex) {
                    Toast.makeText(getApplicationContext(),
                            ex.getMessage(), Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.unexpectedError), Toast.LENGTH_LONG).show();
                }
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

        switchRememberMe = findViewById(R.id.switchRememberMe);
    }

    private void signIn(String login, String encodedPassword) {
        try {
            RESTUserInterface restUserInterface = RESTUserClient.getClient();
            Call<Client> callLogIn = restUserInterface.signIn(login, encodedPassword);
            callLogIn.enqueue(new Callback<Client>() {
                @Override
                public void onResponse(Call<Client> call, Response<Client> response) {
                    switch(response.code()) {
                        case 200: //Success.
                            Client client = response.body();
                            Intent intentToHome = new Intent(LogInActivity.this, HomeActivity.class);
                            intentToHome.putExtra("CLIENT", client);
                            startActivity(intentToHome);
                            break;
                        case 401: //Unauthorized.
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.logIn_credentialsError), Toast.LENGTH_LONG).show();
                            break;
                        default: //Failure
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.unexpectedError), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Client> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.unexpectedError), Toast.LENGTH_LONG).show();
                }
            });
        } catch(Exception ex) {
            Toast.makeText(getApplicationContext(), getString(R.string.unexpectedError), Toast.LENGTH_LONG).show();
        }
    }
}