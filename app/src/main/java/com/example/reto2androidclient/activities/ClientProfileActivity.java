package com.example.reto2androidclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.reto2androidclient.R;
import com.example.reto2androidclient.client.RESTClientClient;
import com.example.reto2androidclient.client.RESTClientInterface;
import com.example.reto2androidclient.model.Client;
import com.example.reto2androidclient.model.ClientList;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Controller for the client profile window.
 *
 * @author Aitor Fidalgo
 */
public class ClientProfileActivity extends AppCompatActivity {

    private SQLiteDatabase sqLiteDatabase = null;

    private Client client;

    private Button buttonLogOut, buttonChangePassword, buttonEditProfile, buttonCancelEdit, buttonDeleteAccount;
    private EditText editTextUsername, editTextFullName, editTextBiography;
    private String usernameOldValue, fullNameOldValue, biographyOldValue, profileImageOldValue;
    private ImageButton imageButtonHome, imageButtonSearch, imageButtonWishlist, imageButtonProfile;
    private ImageView imageViewProfileImage;
    private Spinner spinnerAvatar;
    private ArrayAdapter<CharSequence> avatarSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);

        sqLiteDatabase = SQLiteDatabase.openDatabase(String.valueOf(getDatabasePath(
                "sqLiteDatabase")), null, SQLiteDatabase.OPEN_READWRITE);

        client = (Client) getIntent().getExtras().getSerializable("CLIENT");

        editTextUsername = findViewById(R.id.editTextUsernameClientProfile);
        editTextUsername.setText(client.getLogin());
        editTextFullName = findViewById(R.id.editTextFullNameClientProfile);
        editTextFullName.setText(client.getFullName());
        editTextBiography = findViewById(R.id.editTextBiographyClientProfile);
        editTextBiography.setText(client.getBiography());
        disableEditTexts();

        buttonEditProfile = findViewById(R.id.buttonEditProfileClientProfile);
        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonEditProfile.getText().toString().equalsIgnoreCase(getString(R.string.clientProfile_saveChangesButton))) {
                    //User wants to save changes.
                    validateData();
                } else { //User wants to edit profile.
                    //Enable EditTexts.
                    editTextUsername.setEnabled(true);
                    editTextFullName.setEnabled(true);
                    editTextBiography.setEnabled(true);
                    //Enable avatar spinner.
                    spinnerAvatar.setEnabled(true);
                    //Enable Cancel Edit button.
                    buttonCancelEdit.setEnabled(true);
                    //Changing Edit Profile buttons text.
                    buttonEditProfile.setText(getString(R.string.clientProfile_saveChangesButton));
                }

            }
        });

        buttonCancelEdit = findViewById(R.id.buttonCancelEditClientProfile);
        buttonCancelEdit.setEnabled(false);
        buttonCancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Resetting EditTexts values...
                editTextUsername.setText(client.getLogin());
                editTextFullName.setText(client.getFullName());
                editTextBiography.setText(client.getBiography());
                //Resetting avatar spinner.
                spinnerAvatar.setSelection(avatarSpinnerAdapter.getPosition(profileImageOldValue));
                //Disable spinner.
                spinnerAvatar.setEnabled(false);
                //Disable EditTexts...
                disableEditTexts();
                //Disable Cancel button.
                buttonCancelEdit.setEnabled(false);
                //Changing Edit Profile buttons text.
                buttonEditProfile.setText(getString(R.string.clientProfile_editProfileButton));
            }
        });

        buttonLogOut = findViewById(R.id.buttonLogOutClientProfile);
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteDatabase.execSQL("DELETE FROM sessions WHERE login='"+ client.getLogin() +"'");
                Intent intentToLogIn = new Intent(ClientProfileActivity.this, LogInActivity.class);
                startActivity(intentToLogIn);
            }
        });

        buttonChangePassword = findViewById(R.id.buttonChangePasswordClientProfile);
        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new UnsupportedOperationException();
            }
        });

        imageButtonProfile = findViewById(R.id.imageButtonProfileClientProfile);
        imageButtonProfile.setEnabled(false);

        imageButtonHome = findViewById(R.id.imageButtonHomeClientProfile);
        imageButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientProfileActivity.this, HomeActivity.class);
                intent.putExtra("CLIENT", client);
                startActivity(intent);
            }
        });

        imageButtonSearch = findViewById(R.id.imageButtonSearchClientProfile);
        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new UnsupportedOperationException();
            }
        });

        imageButtonWishlist = findViewById(R.id.imageButtonWishlistClientProfile);
        imageButtonWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientProfileActivity.this, WishlistActivity.class);
                intent.putExtra("CLIENT", client);
                startActivity(intent);
            }
        });

        imageViewProfileImage = findViewById(R.id.imageViewProfileImageClientProfile);
        imageViewProfileImage.setImageResource(getResources()
                .getIdentifier(client.getProfileImage(), "drawable", getPackageName()));

        spinnerAvatar = findViewById(R.id.spinnerAvatarClientProfile);
        //Create an ArrayAdapter using the string array and a default spinner layout
        avatarSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.clientProfile_spinnerAvatars, android.R.layout.simple_spinner_item);
        //Specify the layout to use when the list of choices appear.
        avatarSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAvatar.setAdapter(avatarSpinnerAdapter);
        spinnerAvatar.setEnabled(false);
        spinnerAvatar.setSelection(avatarSpinnerAdapter.getPosition(getString(getResources()
                .getIdentifier(client.getProfileImage(), "string", getPackageName()))));

        buttonDeleteAccount = findViewById(R.id.buttonDeleteAccountClientProfile);
        buttonDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Deletes the Users' account.
                deleteAccount();
            }
        });

    }

    /**
     * Checks if the Clients input data is valid or not.
     *
     * @return True if input data is valid; False if not.
     */
    private void validateData() {
        try {
            //Saving clients old data in case it is not possible to update the database...
            usernameOldValue = client.getLogin();
            fullNameOldValue = client.getFullName();
            biographyOldValue = client.getBiography();
            profileImageOldValue = client.getProfileImage();

            //Checking all fields have the proper length...
            if(editTextBiography.getText().length() > 255)
                throw new IOException(getString(R.string.biographyLengthError));
            if(editTextFullName.getText().length() > 255 || editTextFullName.getText().length() == 0)
                throw new IOException(getString(R.string.fullNameLengthError));
            if(editTextUsername.getText().length() > 255 || editTextUsername.getText().length() == 0)
                throw new IOException(getString(R.string.loginLengthError));

            if(!editTextUsername.getText().toString().equals(usernameOldValue)) {
                //Checking login is not registered already in the database.
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
                                    }
                                    //Login and email are not registered in the database so proceed with the sign up.
                                    editProfile();
                                } catch(IOException ex) {
                                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                                    handleProfileUpdateFailure();
                                }
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                                handleProfileUpdateFailure();
                        }
                    }

                    @Override
                    public void onFailure(Call<ClientList> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), R.string.unexpectedError, Toast.LENGTH_LONG).show();
                        handleProfileUpdateFailure();
                    }
                });
            } else {
                editProfile();
            }
        } catch (IOException ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            handleProfileUpdateFailure();
        }
    }

    /**
     * Updates Clients data.
     */
    private void editProfile() {
        try {
            //Setting clients new data...
            client.setLogin(editTextUsername.getText().toString());
            client.setFullName(editTextFullName.getText().toString());
            client.setBiography(editTextBiography.getText().toString());
            String[] profileImagesIds = getResources().getStringArray(R.array.clientProfile_spinnerAvatarsIds);
            for(int i = 0; i < 11; i++) {
                if (spinnerAvatar.getSelectedItem().toString().equalsIgnoreCase(getString(getResources()
                        .getIdentifier(profileImagesIds[i], "string", getPackageName())))) {
                    client.setProfileImage(profileImagesIds[i]);
                    break;
                }
            }

            //Updating client in the database...
            RESTClientInterface restClientInterface = RESTClientClient.getClient();
            Call<ResponseBody> response = restClientInterface.edit(client);
            response.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    switch(response.code()) {
                        case 204: //Success
                            handleProfileUpdateSuccess();
                            break;
                        default: //Failure
                            handleProfileUpdateFailure();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    handleProfileUpdateFailure();
                }
            });
        } catch (Exception ex) {
            handleProfileUpdateFailure();
        }
    }

    /**
     * Makes changes over the layout after Clients data is successfully updated.
     */
    private void handleProfileUpdateSuccess() {
        //Showing success confirmation message.
        Toast.makeText(getApplicationContext(),
                R.string.clientProfile_successfulProfileUpdate, Toast.LENGTH_LONG).show();
        //Disable editTexts...
        disableEditTexts();
        //Disable avatar spinner.
        spinnerAvatar.setEnabled(false);
        //Updating profile imageView if necessary.
        if(!profileImageOldValue.equalsIgnoreCase(spinnerAvatar.getSelectedItem().toString())) {
            imageViewProfileImage.setImageResource(getResources().getIdentifier(
                    client.getProfileImage(), "drawable", getPackageName()));
        }
        //Disable cancel edit button.
        buttonCancelEdit.setEnabled(false);
        //Changing Edit Profile buttons text.
        buttonEditProfile.setText(getString(R.string.clientProfile_editProfileButton));
        //Update local database.
        if(!usernameOldValue.equalsIgnoreCase(client.getLogin()))
            sqLiteDatabase.execSQL("UPDATE sessions SET login = '"+ client.getLogin() + "' WHERE id = 1");

    }

    /**
     * Makes changes over the layout after Clients data update is failed.
     */
    private void handleProfileUpdateFailure() {
        //Setting client to its old values...
        client.setLogin(usernameOldValue);
        client.setFullName(fullNameOldValue);
        client.setBiography(biographyOldValue);
        //Setting old values on editText...
        editTextUsername.setText(usernameOldValue);
        editTextFullName.setText(fullNameOldValue);
        editTextBiography.setText(biographyOldValue);
        //Setting old value on avatar spinner.
        spinnerAvatar.setSelection(avatarSpinnerAdapter.getPosition(profileImageOldValue));
        //Disable editTexts...
        disableEditTexts();
        //Disable Cancel Edit button.
        buttonCancelEdit.setEnabled(false);
        //Changing Edit Profile text.
        buttonEditProfile.setText(getString(R.string.clientProfile_editProfileButton));
    }

    /**
     * Disables the three editTexts of the layout.
     */
    private void disableEditTexts() {
        editTextUsername.setEnabled(false);
        editTextFullName.setEnabled(false);
        editTextBiography.setEnabled(false);
    }

    /**
     * Deletes the users' account.
     */
    private void deleteAccount() {
        RESTClientInterface rest = RESTClientClient.getClient();
        Call<ResponseBody> call = rest.remove(client.getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                switch(response.code()) {
                    case 204:
                        Toast.makeText(getApplicationContext(), getString(R.string.clientProfile_successfulDelete), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ClientProfileActivity.this, LogInActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), getString(R.string.unexpectedError), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getString(R.string.unexpectedError), Toast.LENGTH_LONG).show();
            }
        });
    }

}