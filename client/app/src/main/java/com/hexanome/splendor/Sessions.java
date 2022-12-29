package com.hexanome.splendor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hexanome.splendor.assets.Session;
import com.hexanome.splendor.retrofit.SessionsController;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Stub.
 */
public class Sessions extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    String token = intent.getExtras().getString("token");
    Session session = (Session) intent.getSerializableExtra("session");
    String username = intent.getExtras().getString("username");
    Log.d("Token in new activity", token);
    setContentView(R.layout.sessions);
    Log.d("Check", "now");
    findViewById(R.id.Create).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SessionsController sessionsController = new SessionsController(token);
        sessionsController.CreateSession(token,username);
      }
    });
    if (session != null) {

        ((TextView) findViewById(R.id.Creator1)).setText(session.getCreator());
        ((TextView) findViewById(R.id.Game1)).setText(session.getGame().getName());
        if (session.getLaunched()) {
          ((TextView) findViewById(R.id.Launched1)).setText("true");
        } else {
          ((TextView) findViewById(R.id.Launched1)).setText("false");
        }
        for (String playername : session.getPlayers()) {
          ((TextView) findViewById(R.id.Players1)).append(playername + ", ");
        }
        ((TextView) findViewById(R.id.Locations1)).setText("{}");
        ((TextView) findViewById(R.id.saveGameID1)).setText(session.getSaveGameId());
        ((TextView) findViewById(R.id.Creator1)).setText(session.getCreator());
        findViewById(R.id.Join1).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            SessionsController sessionsController = new SessionsController(token);
            sessionsController.joinSession(token,username);
          }
        });
        if (session.getCreator().equals(intent.getExtras().getString("username"))){
          findViewById(R.id.Launch1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              SessionsController sessionsController = new SessionsController(token);
              sessionsController.launchSession(token);
            }
          });
        }
        findViewById(R.id.Create).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            SessionsController sessionsController = new SessionsController(token);
            sessionsController.CreateSession(token,username);
          }
        });
      }
    }

  }

