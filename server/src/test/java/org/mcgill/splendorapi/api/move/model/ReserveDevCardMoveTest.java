package org.mcgill.splendorapi.api.move.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.Player;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.board.GameBoard;
import org.mcgill.splendorapi.model.board.OrientGameBoard;
import org.mcgill.splendorapi.model.card.CardState;
import org.mcgill.splendorapi.model.card.CardType;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.DevelopmentCardMeta;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

import static org.junit.jupiter.api.Assertions.*;

class ReserveDevCardMoveTest {
  Player tPlayer1;
  DevelopmentCard tDevCard1;
  DevelopmentCard tDevCard2;
  DevelopmentCard tDevCard3;
  GameBoard testBoard;

  @BeforeEach
  public void setup() throws InvalidCardType {
    tPlayer1 = Player.builder().gems(new HashMap<>()).bonuses(new HashMap<>()).build();
    List<DevelopmentCard> deck = new ArrayList<>();

    tDevCard1 = new DevelopmentCard(
      new DevelopmentCardMeta((short) 1, (short) 1, CardType.DEVELOPMENT),
      Bonus.DIAMOND,
      0,
      new HashMap<>(),
      1,
      true,
      CardState.FREE);

    tDevCard2 = new DevelopmentCard(
      new DevelopmentCardMeta((short) 16, (short) 1, CardType.DEVELOPMENT),
      Bonus.ONYX,
      3,
      new HashMap<>(),
      1,
      false,
      CardState.FREE);

    tDevCard3 = new DevelopmentCard(
      new DevelopmentCardMeta((short) 12, (short) 1, CardType.DEVELOPMENT),
      Bonus.ONYX,
      3,
      new HashMap<>(),
      1,
      false,
      CardState.FREE);

    deck.add(tDevCard1);
    deck.add(tDevCard2);
    deck.add(tDevCard3);
    testBoard = GameBoard.builder().gems(new HashMap<>()).nobles(new ArrayList<>()).deck(deck).build();
  }

  @Test
  void testApplyMove() throws IllegalMoveException, GameBoardException {
    // Test 1: Reserve a card and verify player's reserved card count and gold token count
    ReserveDevCardMove testMove1 = new ReserveDevCardMove(tDevCard1);
    Game game1 = Game.builder().board(testBoard).curPlayer(tPlayer1).build();
    testMove1.applyMove(game1);
    assertTrue(tPlayer1.hasReservedCard(tDevCard1));
    assertEquals(1, tPlayer1.getGems().get(GemType.GOLD));

    // Test 2: Reserve a different card and verify player's reserved card count and gold token count
    ReserveDevCardMove testMove2 = new ReserveDevCardMove(tDevCard2);
    Game game2 = Game.builder().board(testBoard).curPlayer(tPlayer1).build();
    testMove2.applyMove(game2);
    assertTrue(tPlayer1.hasReservedCard(tDevCard2));
    assertEquals(2, tPlayer1.getGems().get(GemType.GOLD));

    // Test 3: Reserve a card when there is no gold token available on the board
    testBoard.getGems().put(GemType.GOLD, 0);
    ReserveDevCardMove testMove3 = new ReserveDevCardMove(tDevCard3);
    Game game3 = Game.builder().board(testBoard).curPlayer(tPlayer1).build();
    testMove3.applyMove(game3);
    assertTrue(tPlayer1.hasReservedCard(tDevCard3));
    assertEquals(3, tPlayer1.getGems().get(GemType.GOLD));

    // Test 4: Reserve a card from an orient game board
    List<DevelopmentCard> deck = new ArrayList<>();
    deck.add(tDevCard1);
    deck.add(tDevCard2);
    OrientGameBoard orientGameBoard = OrientGameBoard.builder().gems(new HashMap<>())
                                                     .nobles(new ArrayList<>())
                                                     .deck(deck).build();

    ReserveDevCardMove testMove4 = new ReserveDevCardMove(tDevCard1);
    Game game4 = Game.builder().board(orientGameBoard).curPlayer(tPlayer1).build();
    testMove4.applyMove(game4);
    assertTrue(tPlayer1.hasReservedCard(tDevCard1));
  }

  @Test
  void testGetValue() {
    ReserveDevCardMove testMove = new ReserveDevCardMove(tDevCard1);
    assertEquals(tDevCard1.getMeta().getId(), testMove.getValue());
  }
}
