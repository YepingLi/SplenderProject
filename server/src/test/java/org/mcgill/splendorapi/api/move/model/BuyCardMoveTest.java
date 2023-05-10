package org.mcgill.splendorapi.api.move.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.api.assetloader.ProducerService;
import org.mcgill.splendorapi.config.AppProperties;
import org.mcgill.splendorapi.config.AssetPath;
import org.mcgill.splendorapi.config.OpenIdAuth2;
import org.mcgill.splendorapi.model.*;
import org.mcgill.splendorapi.model.board.GameBoard;
import org.mcgill.splendorapi.model.card.*;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

class BuyCardMoveTest {

  private Game game;
  private Player player1;
  private Player player2;
  private List<Player> players;
  private DevelopmentCard card;
  private Payment payment;
  private NobleCard noble;
  private Map<GemType, Integer> price;
  private GameBoard gameBoard;
  private List<DevelopmentCard> deck;
  private Map<GemType, Integer> gems;
  private List<NobleCard> nobles = new ArrayList<>();
  private ProducerService loader;

  @BeforeEach
  void setUp() throws InvalidCardType, IOException {
    player1 = Player.builder().playerPrestige(6).name("a").gems(new HashMap<>()).nobles(nobles).build();
    player2 = Player.builder().playerPrestige(6).name("b").gems(new HashMap<>()).nobles(nobles).build();
    players = new ArrayList<>();
    players.add(player1);
    players.add(player2);
    card = new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 1, CardType.DEVELOPMENT),
                               Bonus.EMERALD,
                               2,
                               new HashMap<>());
    price = new HashMap<>();
    price.put(GemType.DIAMOND, 1);
    price.put(GemType.EMERALD, 1);
    price.put(GemType.SAPPHIRE, 1);
    payment = new Payment(price, 0);
    noble = new NobleCard(new CardMeta((short) 1, CardType.NOBLE),
                          1,
                          2,
                          new HashMap<>());
    deck = new ArrayList<>();
    deck.add(card);
    nobles.add(noble);
    gems = new HashMap<>();
    nobles = new ArrayList<>();
    gameBoard = GameBoard.builder().gems(gems).nobles(nobles).deck(deck).build();
    game = Game.builder().board(gameBoard).players(players).type(GameType.BASE).build();
    loader = new ProducerService(
      new AppProperties(
        null, Paths.get(""),
        100,
        200,
        new OpenIdAuth2("", "", ""),
        200,
        200,
        12,
        "cards.json", "orientCards.json", "nobles.json", "tradingPosts.json",
        "cities.json",
        new AssetPath("", Collections.emptyList()),
        new AssetPath("", Collections.emptyList()),
        new AssetPath("", Collections.emptyList()),
        new AssetPath("", Collections.emptyList()),
        new AssetPath("", Collections.emptyList()),
        new AssetPath("", Collections.emptyList()),
        false
      )
    );
  }
  @Test
  void testBuyCardMoveConstructor() {
    BuyCardMove move = new BuyCardMove(card, payment, noble);

    assertEquals(card, move.getCard());
    assertEquals(payment, move.getPayment());
  }
  @Test
  void testCopy() {
    // Create a BuyCardMove instance
    BuyCardMove originalMove = new BuyCardMove(card, payment, noble);

    // Call the copy() method
    BuyCardMove copiedMove = originalMove.copy();

    // Test if the copied move is not the same object as the original move
    assertNotSame(originalMove, copiedMove);

    // Test if the copied move has the same card, payment, and noble as the original move
    assertEquals(originalMove.getCard(), copiedMove.getCard());
    assertEquals(originalMove.getPayment(), copiedMove.getPayment());
    assertEquals(originalMove.getNobleChoice(), copiedMove.getNobleChoice());
  }
  @Test
  void applyMoveTest() throws GameBoardException, IllegalMoveException, IOException {
//    // Set up the initial state of the game and player
//    game.startGame(gameBoard);
//    game.init(loader);
//    // Create a BuyCardMove instance
//    BuyCardMove buyCardMove = new BuyCardMove(card);
//
//    // Apply the move
//    buyCardMove.applyMove(game);
//
//    // Check if the card is in the player's hand
//    assertTrue(game.getCurPlayer().getBonuses().equals( Bonus.EMERALD));
//
//    // Check if the player has paid the correct amount
//    for (Map.Entry<GemType, Integer> entry : price.entrySet()) {
//      assertEquals((int) player1.getGems().get(entry.getKey()) - entry.getValue(), (int) game.getCurPlayer().getGems().get(entry.getKey()));
//    }
//
//    // Check if the player has gained the card's bonus
//    assertEquals(player1.getBonuses().get(card.getBonus()) + 1, (int) game.getCurPlayer().getBonuses().get(card.getBonus()));
//
//    // Check if the game has advanced to the next player
//    assertNotEquals(player1, game.getCurPlayer());
  }






}
