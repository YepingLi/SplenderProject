package com.hexanome.splendor;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.hexanome.splendor.retrofit.GetToken;
import com.hexanome.splendor.retrofit.RegisterUser;
import com.hexanome.splendor.retrofit.GSONObjectGenerator;
import com.hexanome.splendor.retrofit.SessionsController;

import java.io.Serializable;


/**
 * Dummy.
 */
public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Button button = findViewById(R.id.bt_start);
    Button buttonCheck = findViewById(R.id.check);
    TextInputEditText username = findViewById(R.id.Username);
    EditText password = findViewById(R.id.Password);
    GetToken getToken = new GetToken();
    getToken.tokenString();
    SessionsController sessionsController = new SessionsController(getToken.finalToken);
    sessionsController.GetSessions();
    buttonCheck.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), Check.class);
        startActivity(intent);
      }
    });
    button.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        Serializable session = sessionsController.getSession();
        //Log.d("Final session", session.toString());
        Intent intent = new Intent(getApplicationContext(), Sessions.class);
        String usernameStr = username.getText().toString();
        String passwordStr = password.getText().toString();
        String token = getToken.getFinalToken();
        if(token.contains("+")){
        token = token.replace("+", "%2B");}
        Log.d("FinalToken", token);
        GSONObjectGenerator GSONObjectGenerator = new GSONObjectGenerator(usernameStr, passwordStr);
        RegisterUser registerUser = new RegisterUser(usernameStr, token, GSONObjectGenerator.createUserGSON());
        registerUser.AddUser();
        intent.putExtra("password", passwordStr);
        intent.putExtra("session", session);
        intent.putExtra("username", usernameStr);
        intent.putExtra("token", token);

        startActivity(intent);
      }
    });
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
          WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
  }
}