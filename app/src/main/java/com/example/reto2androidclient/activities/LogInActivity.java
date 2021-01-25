package com.example.reto2androidclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.reto2androidclient.R;
import com.example.reto2androidclient.client.RESTUserFactory;
import com.example.reto2androidclient.client.RESTUserInterface;
import com.example.reto2androidclient.exceptions.UnexpectedErrorException;
import com.example.reto2androidclient.model.Client;
import com.example.reto2androidclient.model.User;
import com.example.reto2androidclient.model.UserPrivilege;
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
    private ImageView imageViewLogo;
    private MediaPlayer mediaPlayerGuitar, mediaPlayerDoh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Checking for open sessions to automatically sign in.
        checkOpenSessions();

        //Initializing MP3 Media Players.
        initializingMP3Media();

        //Logo animation on image view click...
        imageViewLogo = findViewById(R.id.imageViewLogo);
        imageViewLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayerGuitar.start();
                YoYo.with(Techniques.Tada).duration(500).repeat(5).playOn(imageViewLogo);
            }
        });

        //Sign In request on button click and button animation on sign in error.
        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Validates the Users input data and makes a sign in request if everything is ok,
                    validateData();
                } catch (IOException ex) {
                    //Showing Users input error message.
                    Toast.makeText(getApplicationContext(),
                            ex.getMessage(), Toast.LENGTH_LONG).show();
                    playWrongLogInAnimation();
                } catch (Exception ex) {
                    //Showing unexpected error message.
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.unexpectedError), Toast.LENGTH_LONG).show();
                    playWrongLogInAnimation();
                }
            }
        });

        buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Switching to SignUp activity...
                Intent intentToSignUp = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intentToSignUp);
            }
        });

        editTextLogin = findViewById(R.id.editTextUsernameLogIn);
        editTextPassword = findViewById(R.id.editTextPasswordLogIn);

        switchRememberMe = findViewById(R.id.switchRememberMe);
    }

    /**
     * Checks for users open sessions and signs in automatically.
     */
    private void  checkOpenSessions() {
        //Opening or creating SQLite database to store remember me sessions...
        sqLiteDatabase = openOrCreateDatabase("sqLiteDatabase", Context.MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS sessions " +
                "(id INT PRIMARY KEY NOT NULL," +
                "login VARCHAR NOT NULL," +
                "password VARCHAR NOT NULL)");
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM sessions", null);
        cursor.moveToFirst();
        //If there is a session stored in the database log in with that user account.
        if(cursor.getCount() != 0) {
            signIn(cursor.getString(1), cursor.getString(2));
        }
    }

    /**
     * Initializes MP3 Media Players used in animations.
     */
    private void initializingMP3Media() {
        mediaPlayerGuitar = new MediaPlayer();
        mediaPlayerDoh = new MediaPlayer();
        try {
            //Setting data source on media players...
            mediaPlayerGuitar.setDataSource(getApplicationContext(), Uri.parse(
                    "android.resource://" + getPackageName() + "/" + R.raw.electric_guitar1));
            mediaPlayerDoh.setDataSource(getApplicationContext(), Uri.parse(
                    "android.resource://" + getPackageName() + "/" + R.raw.doh));
            //Preparing media players...
            mediaPlayerGuitar.prepare();
            mediaPlayerDoh.prepare();
        } catch(IOException ex) {
            Log.e(LogInActivity.class.getName(), "Unable to prepare MP3 Media Players.");
        }
    }

    /**
     * Validates User input data and makes a sign in request if everything is OK.
     *
     * @throws IOException If the Users' input data is wrong.
     * @throws UnexpectedErrorException If anything goes wrong.
     */
    private void validateData() throws IOException, UnexpectedErrorException {
        //Getting data from login and password editTexts...
        String login = editTextLogin.getText().toString();
        String password = editTextPassword.getText().toString();
        //Checking if any of the fields is empty...
        if (login.length() == 0 || password.length() == 0)
            throw new IOException(getString(R.string.logIn_emptyFieldsError));
        //Encoding password with RSA.
        String encodedPassword = PublicCrypt.encode(LogInActivity.this, password);

        //Making sure only Clients get into de application.
        RESTUserInterface rest = RESTUserFactory.getClient();
        Call<User> call = rest.getPrivilege(login);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                switch(response.code()) {
                    case 200:
                        User user = response.body();
                        if(user.getUserPrivilege() == UserPrivilege.CLIENT) {
                            //The user trying to sign in IS a Client.
                            //Checking if remember me switch is checked...
                            if (switchRememberMe.isChecked()) {
                                sqLiteDatabase.execSQL("INSERT INTO sessions VALUES (1, '" + login + "', '" + encodedPassword + "')");
                            }
                            //Sending sign in request to the server.
                            signIn(login, encodedPassword);
                        } else {
                            //The user trying to sign in IS NOT a Client.
                            Toast.makeText(getApplicationContext(), getString(R.string.logIn_wrongUserPrivilege), Toast.LENGTH_LONG).show();
                            playWrongLogInAnimation();
                        }
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), getString(R.string.unexpectedError), Toast.LENGTH_LONG).show();
                        playWrongLogInAnimation();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getString(R.string.unexpectedError), Toast.LENGTH_LONG).show();
                playWrongLogInAnimation();
            }
        });
    }

    /**
     * Makes a sign in request to the server.
     *
     * @param login Login of the User trying to sign in.
     * @param encodedPassword Password of the User trying to sign in, encoded with RSA.
     */
    private void signIn(String login, String encodedPassword) {
        try {
            RESTUserInterface restUserInterface = RESTUserFactory.getClient();
            Call<Client> callLogIn = restUserInterface.signIn(login, encodedPassword);
            callLogIn.enqueue(new Callback<Client>() {
                @Override
                public void onResponse(Call<Client> call, Response<Client> response) {
                    switch(response.code()) {
                        case 200: //Success.
                            //Switching to Home activity sending Users data...
                            Client client = response.body();
                            Intent intentToHome = new Intent(LogInActivity.this, HomeActivity.class);
                            intentToHome.putExtra("CLIENT", client);
                            startActivity(intentToHome);
                            break;
                        case 401: //Unauthorized.
                            //Showing error message id access to the application is denied.
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.logIn_credentialsError), Toast.LENGTH_LONG).show();
                            break;
                        default:
                            //Showing unexpected error message.
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.unexpectedError), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Client> call, Throwable t) {
                    //Showing unexpected error message.
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.unexpectedError), Toast.LENGTH_LONG).show();
                }
            });
        } catch(Exception ex) {
            //Showing unexpected error message.
            Toast.makeText(getApplicationContext(), getString(R.string.unexpectedError), Toast.LENGTH_LONG).show();
        }
    }

    private void playWrongLogInAnimation() {
        mediaPlayerDoh.start();
        YoYo.with(Techniques.Wave).duration(1000).playOn(buttonSignIn);
    }

    /**
     * Switches to Forgot Password activity.
     *
     * @param view The view that was clicked to execute this method.
     */
    public void handleForgotPassword(View view) {
        Intent intent = new Intent(LogInActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }
}