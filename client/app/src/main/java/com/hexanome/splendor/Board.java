package com.hexanome.splendor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.hexanome.splendor.assets.BuyCardMove;
import com.hexanome.splendor.assets.Card;
import com.hexanome.splendor.assets.GameBoard;
import com.hexanome.splendor.assets.Gem;
import com.hexanome.splendor.assets.Player;
import com.hexanome.splendor.retrofit.BoardApi;
import com.hexanome.splendor.retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Stub javadoc.
 */
public class Board extends Activity {
  private Player player;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    String usernameStr = "Player " + intent.getExtras().getString("username");
    setContentView(R.layout.activity_board);
    //loadBoard();    //Loads the GameBoard from the server.
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public Player getPlayer() {
    return this.player;
  }


  //TODO:

  /**
   * Loads the Board.
   */
  public void loadBoard() {
    RetrofitService retrofitService = new RetrofitService();
    BoardApi boardApi = retrofitService.getRetrofit().create(BoardApi.class);
    boardApi.loadBoard()    //Retrieves the Game Board from the Server
        .enqueue(new Callback<GameBoard>() {
          @Override
          public void onResponse(Call<GameBoard> call, Response<GameBoard> response) {
            populateBoard(response.body());
          }

          @Override
          public void onFailure(Call<GameBoard> call, Throwable t) {
            Toast.makeText(Board.this, "Failed to load board", Toast.LENGTH_SHORT).show();
          }
        });


  }

  //Takes in a Game Board and initializes all of the buttons.
  private void populateBoard(GameBoard board) {
    Player curPlayer = board.getCurPlayer();
    //Sets all of the available cards as buttons.
    for (Card card : board.getAvailableCards()) {
      setButton(card);
    }
    //TODO: Add leaderboard info.
    //Sets the text view for board available gems and the current players gems.
    ((TextView) findViewById(R.id.numGold)).setText(String.format("%d",
        board.getAvailableGold().size()));
    ((TextView) findViewById(R.id.GoldNumber)).setText(String.format("%d", curPlayer.getMyGold()));
    ((TextView) findViewById(R.id.numDiamond)).setText(String.format("%d",
        board.getAvailableDiamond().size()));
    ((TextView) findViewById(R.id.DiamondNumber)).setText(String.format("%d",
        curPlayer.getMyDiamond()));
    ((TextView) findViewById(R.id.numEmerald)).setText(String.format("%d",
        board.getAvailableEmerald().size()));
    ((TextView) findViewById(R.id.EmeraldNumber)).setText(String.format("%d",
        curPlayer.getMyEmerald()));
    ((TextView) findViewById(R.id.numOnyx)).setText(String.format("%d",
        board.getAvailableOnyx().size()));
    ((TextView) findViewById(R.id.OnyxNumber)).setText(String.format("%d",
        curPlayer.getMyOnyx()));
    ((TextView) findViewById(R.id.numRuby)).setText(String.format("%d",
        board.getAvailableRuby().size()));
    ((TextView) findViewById(R.id.RubyNumber)).setText(String.format("%d", curPlayer.getMyRuby()));
    ((TextView) findViewById(R.id.numSapphire)).setText(String.format("%d",
        board.getAvailableSapphire().size()));
    ((TextView) findViewById(R.id.SapphireNumber)).setText(String.format("%d",
        curPlayer.getMySapphire()));
    //TODO: Sets the Text view for the Players bonuses.
    Button exitB = findViewById(R.id.exitButton);
    exitB.setOnClickListener(l -> startActivity(new Intent(getApplicationContext(),
        MainActivity.class)));
    Button leaderBoard = findViewById(R.id.FirstPlace);
    leaderBoard.setOnClickListener(l -> createPopupLeaderBoard(l));
  }


  //Sets the Card to be a clickable button
  private void setButton(Card card) {
    ImageButton cardButton = findViewById(card.getId());
    try {
      cardButton.setImageResource(card.getResourceImage());
      cardButton.setOnClickListener(l -> {
        try {
          createPopup(l, card);
        } catch (NoSuchFieldException | IllegalAccessException e) {
          e.printStackTrace();
        }
      });
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }



  private void purchaseCard(Card card) {
    //If the card does not require further action, then create the BuyCardMove object.
    if (card.isTerminating()) {
      BuyCardMove move = new BuyCardMove(this.player, card);
    } else {
      //TODO: Add the functionality for the Orient Cards.
    }
  }

  /**
   * Dummy.
   */
  public void createPopup(View listenerView, Card card)
      throws NoSuchFieldException, IllegalAccessException {
    // TODO: figure out why null is bad and what to replace it with
    View view = getLayoutInflater().inflate(R.layout.popup, null);
    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
    PopupWindow popupWindow = new PopupWindow(view, width, height, true);
    ((ImageView) view.findViewById(R.id.popup_image)).setImageResource(card.getResourceImage());
    popupWindow.showAtLocation(listenerView, Gravity.CENTER, 0, 0);

    Button button = view.findViewById(R.id.popupClose);
    button.setOnClickListener(listener -> popupWindow.dismiss());
    Button purchase = view.findViewById(R.id.popupPurchase);
    purchase.setOnClickListener(listener -> {
      card.changeState(Card.State.PURCHASED);
      popupWindow.dismiss();
      purchaseCard(card);
    });
    /*
        Button reserve = view.findViewById(R.id.popupReserve);
        reserve.setOnClickListener(listener -> {
            aCard.changeState(Card.State.RESERVED);
            popupWindow.dismiss();
            updateGameOnCardChange(aCard.getId());
        });
         */
  }

  /**
   * Dummy.
   */
  public void createPopupLeaderBoard(View listenerView) {
    View view = getLayoutInflater().inflate(R.layout.popup_leaderboard, null);
    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
    PopupWindow popupWindow = new PopupWindow(view, width, height, true);
    popupWindow.showAtLocation(listenerView, Gravity.CENTER, 0, 0);
    Button button = view.findViewById(R.id.popupClose);
    button.setOnClickListener(listener -> popupWindow.dismiss());
  }
  /**
   * Dummy.
   */

  public void createPopupDiamond(View listenerView, Gem gem) {
    View view = View.inflate(this, R.layout.diamond_popup, null);
    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
    PopupWindow popupWindow = new PopupWindow(view, width, height, true);
    popupWindow.showAtLocation(listenerView, Gravity.CENTER, 0, 0);
    Button closePopup = view.findViewById(R.id.popupClose);
    closePopup.setOnClickListener(listener -> popupWindow.dismiss());
    Button collect = view.findViewById(R.id.popupReserve);
    collect.setOnClickListener(listener -> {
      //TODO: Add gemPurchasing move and then send it to the server here

    });
  }
  /**
   * Dummy.
   */

  public void createPopupEmerald(View listenerView, Gem gem) {
    View view = View.inflate(this, R.layout.emerald_popup, null);
    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
    PopupWindow popupWindow = new PopupWindow(view, width, height, true);
    popupWindow.showAtLocation(listenerView, Gravity.CENTER, 0, 0);
    Button closePopup = view.findViewById(R.id.popupClose);
    closePopup.setOnClickListener(listener -> popupWindow.dismiss());
    Button collect = view.findViewById(R.id.popupReserve);
    collect.setOnClickListener(listener -> {

    });
  }
  /**
   * Dummy.
   */

  public void createPopupOnyx(View listenerView, Gem gem) {
    View view = View.inflate(this, R.layout.onyx_popup, null);
    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
    PopupWindow popupWindow = new PopupWindow(view, width, height, true);
    popupWindow.showAtLocation(listenerView, Gravity.CENTER, 0, 0);
    Button closePopup = view.findViewById(R.id.popupClose);
    closePopup.setOnClickListener(listener -> popupWindow.dismiss());
    Button collect = view.findViewById(R.id.popupReserve);
    collect.setOnClickListener(listener -> {
      //TODO:

    });
  }
  /**
   * Dummy.
   */

  public void createPopupRuby(View listenerView, Gem gem) {
    View view = View.inflate(this, R.layout.ruby_popup, null);
    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
    PopupWindow popupWindow = new PopupWindow(view, width, height, true);
    popupWindow.showAtLocation(listenerView, Gravity.CENTER, 0, 0);
    Button closePopup = view.findViewById(R.id.popupClose);
    closePopup.setOnClickListener(listener -> popupWindow.dismiss());
    Button collect = view.findViewById(R.id.popupReserve);
    collect.setOnClickListener(listener -> {


    });
  }
  /**
   * Dummy.
   */

  public void createPopupSapphire(View listenerView, Gem gem) {
    View view = View.inflate(this, R.layout.sapphire_popup, null);
    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
    PopupWindow popupWindow = new PopupWindow(view, width, height, true);
    popupWindow.showAtLocation(listenerView, Gravity.CENTER, 0, 0);
    Button closePopup = view.findViewById(R.id.popupClose);
    closePopup.setOnClickListener(listener -> popupWindow.dismiss());
    Button collect = view.findViewById(R.id.popupReserve);
    collect.setOnClickListener(listener -> {


    });
  }
  /**
   * Dummy.
   */

  public void createPopupGold(View listenerView, Gem gem) {
    View view = View.inflate(this, R.layout.gold_popup, null);
    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
    PopupWindow popupWindow = new PopupWindow(view, width, height, true);
    popupWindow.showAtLocation(listenerView, Gravity.CENTER, 0, 0);
    Button closePopup = view.findViewById(R.id.popupClose);
    closePopup.setOnClickListener(listener -> popupWindow.dismiss());
    Button collect = view.findViewById(R.id.popupReserve);
    collect.setOnClickListener(listener -> {


    });
  }
}
