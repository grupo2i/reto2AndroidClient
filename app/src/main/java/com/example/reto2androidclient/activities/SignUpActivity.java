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
import com.example.reto2androidclient.model.Client;
import com.example.reto2androidclient.model.ClientList;
import com.example.reto2androidclient.model.UserPrivilege;
import com.example.reto2androidclient.model.UserStatus;
import com.example.reto2androidclient.security.PublicCrypt;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Controller for the sign up window.
 *
 * @author Aitor Fidalgo
 */
public class SignUpActivity extends AppCompatActivity {

    private Button buttonCancel, buttonAccept;
    private EditText editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        buttonCancel = findViewById(R.id.buttonCancelSignUp);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Switching to LogIn activity...
                Intent intentToLogIn = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intentToLogIn);
            }
        });

        buttonAccept = findViewById(R.id.buttonAcceptSignUp);
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validate data and proceed with the sign up.
                validateData();
            }
        });

        editTextUsername = findViewById(R.id.editTextUsernameSignUp);
        editTextEmail = findViewById(R.id.editTextEmailSignUp);
        editTextPassword = findViewById(R.id.editTextPasswordSignUp);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPasswordSignUp);

    }

    /**
     * Checks if the Users input data is valid.
     *
     * @return True if the data is valid; False if not.
     */
    private void validateData() {
        try {
            //Checking fields have the proper length...
            if(editTextUsername.getText().length() == 0 || editTextUsername.getText().length() > 255)
                throw new IOException(getString(R.string.loginLengthError));
            if(editTextEmail.getText().length() == 0 || editTextEmail.getText().length() > 255)
                throw new IOException(getString(R.string.emailLengthError));
            //Checking email format is correct...
            Pattern patternEmail = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@"
                    + "[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher matcherEmail = patternEmail.matcher(editTextEmail.getText());
            if(!matcherEmail.matches())
                throw new IOException(getString(R.string.emailPatternError));
            //Checking fields have the proper length...
            if(editTextPassword.getText().length() < 6 || editTextPassword.getText().length() > 255)
                throw new IOException(getString(R.string.passwordLengthError));
            //Checking both password fields are the same.
            if(!editTextPassword.getText().toString().equalsIgnoreCase(editTextConfirmPassword.getText().toString()))
                throw new IOException(getString(R.string.passwordsDoNotMatchError));

            //Checking login and email are not registered already in the database.
            checkRegisteredUsers();
        } catch(IOException ex) {
            //Showing Users input error message.
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void checkRegisteredUsers() {
        RESTClientInterface restClientInterface = RESTClientClient.getClient();
        Call<ClientList> callClients = restClientInterface.getAllClients();
        callClients.enqueue(new Callback<ClientList>() {
            @Override
            public void onResponse(Call<ClientList> call, Response<ClientList> response) {
                switch(response.code()) {
                    case 200:
                        try {
                            ClientList clients = response.body();
                            for(Client client:clients.getClients()) {
                                if(editTextUsername.getText().toString().equalsIgnoreCase(client.getLogin())) {
                                    throw new IOException(getString(R.string.loginAlreadyRegisteredError));
                                }
                                else if(editTextEmail.getText().toString().equalsIgnoreCase(client.getEmail())) {
                                    throw new IOException(getString(R.string.emailAlreadyRegisteredError));
                                }
                            }
                            //Login and email are not registered in the database so proceed with the sign up.
                            signUp();
                        } catch(IOException ex) {
                            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ClientList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.unexpectedError, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Makes a sign up request to the server.
     */
    private void signUp() {
        try {
            Client client = new Client();
            //Setting clients data.
            client.setLogin(editTextUsername.getText().toString());
            client.setEmail(editTextEmail.getText().toString());
            client.setPassword(PublicCrypt.encode(getApplication(), editTextPassword.getText().toString()));
            client.setUserPrivilege(UserPrivilege.CLIENT);
            client.setUserStatus(UserStatus.ENABLED);
            client.setFullName("");
            client.setBiography("");
            //Setting a random profile image to the new Client...
            String[] profileImages = getResources().getStringArray(R.array.clientProfile_spinnerAvatarsIds);
            Random random = new Random();
            client.setProfileImage(profileImages[random.nextInt(11)]);
            //Getting current date for create method...
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDate = formatter.format(new Date());

            RESTClientInterface restClientInterface = RESTClientClient.getClient();
            Call<ResponseBody> callCreateClient = restClientInterface.create(client, currentDate);
            callCreateClient.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    switch(response.code()) {
                        case 204:
                            //Showing success message.
                            Toast.makeText(getApplicationContext(), getString(R.string.signUp_successfulSignUp), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                            intent.putExtra("CLIENT", client);
                            startActivity(intent);
                            break;
                        default:
                            //Showing unexpected error message.
                            Toast.makeText(getApplicationContext(), getString(R.string.unexpectedError), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    //Showing unexpected error message.
                    Toast.makeText(getApplicationContext(), getString(R.string.unexpectedError), Toast.LENGTH_LONG).show();
                }
            });
        } catch(Exception ex) {
            //Showing unexpected error message.
            Toast.makeText(getApplicationContext(), getString(R.string.unexpectedError), Toast.LENGTH_LONG).show();
        }
    }
}