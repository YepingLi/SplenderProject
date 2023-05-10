package org.mcgill.splendorapi.api.move.generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.mcgill.splendorapi.api.move.enums.Replacement;
import org.mcgill.splendorapi.api.move.model.BuyCardMove;
import org.mcgill.splendorapi.api.move.model.LevelOnePairingMove;
import org.mcgill.splendorapi.api.move.model.LevelThreePairingL2BaseMove;
import org.mcgill.splendorapi.api.move.model.LevelThreePairingL2CascadeMove;
import org.mcgill.splendorapi.api.move.model.LevelThreePairingL2Move;
import org.mcgill.splendorapi.api.move.model.LevelThreePairingL2NobleMove;
import org.mcgill.splendorapi.api.move.model.LevelTwoNobleMove;
import org.mcgill.splendorapi.api.move.model.LevelTwoPairingL1Move;
import org.mcgill.splendorapi.api.move.model.LevelTwoPairingMove;
import org.mcgill.splendorapi.api.move.model.Payment;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.GameType;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.Move;
import org.mcgill.splendorapi.model.Player;
import org.mcgill.splendorapi.model.PowerType;
import org.mcgill.splendorapi.model.board.GameBoard;
import org.mcgill.splendorapi.model.board.OrientGameBoard;
import org.mcgill.splendorapi.model.card.CardState;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.NobleCard;
import org.mcgill.splendorapi.model.card.OrientDevelopmentCard;
import org.mcgill.splendorapi.model.card.OrientPower;


/**
 * Builds all possible Card purchasing moves for the current player in the game.
 */
public class BuyCard extends AbstractMoveGenerator {
  private final Player curPlayer;

  /**
   * Constructor which calls the Abstract Move Service.
   *
   * @param game the current game.
   */
  public BuyCard(Game game) {
    super(game);
    curPlayer = game.getCurPlayer();
  }

  /**
   * Checks if the card is purchasable.
   *
   * @param card The card to buy
   * @return boolean of purchase status
   */
  public boolean purchasableCard(DevelopmentCard card) {
    return (curPlayer.canBuyCard(card) && card.isTurned());
  }

  /**
   * Builds the moves for the nobles.
   *
   * @param moves The moves
   * @return The new moves
   */
  private List<Move> eligibleNobles(List<Move> moves) {
    //Check if player is eligible for more than one noble after the move
    List<Move> movesToUpdate = new ArrayList<>();
    List<Move> updatedMoves = new ArrayList<>();

    for (Move move : moves) {
      BuyCardMove curMove = (BuyCardMove) move;
      List<NobleCard> eligibleNobles = game.getBoard()
                                           .getNobles()
                                           .stream()
                                           .filter(noble ->
                                                     curMove.postPurchaseNobleCheck(curPlayer,
                                                                                    noble))
                                           .collect(Collectors.toList());

      if (eligibleNobles.size() > 1) {
        movesToUpdate.add(move);
        updatedMoves.addAll(eligibleNobles.stream()
                                          .map(noble -> {
                                            BuyCardMove newMove = (BuyCardMove) move.copy();
                                            newMove.setNobleChoice(noble);
                                            return newMove;
                                          })
                                          .collect(Collectors.toList()));
      }
    }
    moves.removeAll(movesToUpdate);
    moves.addAll(updatedMoves);

    return moves;
  }

  private List<Move> performPurchaseChecks(List<Move> moves) {
    //PART 3: If the Player has the Power FreeGemOnPurchase, add a move per gem choice.
    if (curPlayer.getPowers().contains(PowerType.FREE_GEM_ON_PURCHASE)) {
      moves = moves.stream()
                   .flatMap(move -> game.getBoard()
                                        .getGems()
                                        .entrySet()
                                        .stream()
                                        .filter(e -> e.getValue() >= 1)
                                        .map(e -> {
                                          BuyCardMove newMove = (BuyCardMove) move.copy();
                                          newMove.setFreeGemOnPurchase(e.getKey());
                                          return newMove;
                                        }))
                   .collect(Collectors.toList());
    }

    //PART 4: IF THE PLAYER HAS A GOLD TOKEN, ADD A MOVE PER POSSIBLE PAYMENT.
    if (curPlayer.getGems().get(GemType.GOLD) <= 0 && curPlayer.getNumGoldCards() <= 0) {
      return moves;
    }

    return moves.stream()
                .map(move -> {
                  BuyCardMove curMove = (BuyCardMove) move;

                  //Returns the list of possiblePayments given a move.
                  List<Payment> possiblePayments = Optional.ofNullable(curPlayer
                                                                         .getPossiblePayments(
                                                                           curMove.getCard()
                                                                                  .getPrice()))
                                                           .orElse(List.of());
                  if (possiblePayments.isEmpty()) {
                    return List.of(curMove);
                  }
                  return possiblePayments.stream()
                                         .map(payment -> {
                                           BuyCardMove newMove = (BuyCardMove) move.copy();
                                           newMove.setPayment(payment);
                                           return newMove;
                                         })
                                         .collect(Collectors.toList());
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());
  }

  /**
   * Generates a list of all possible moves of purchasing cards for curPlayer.
   *
   * @return List of all possible BuyCardsMoves
   */
  @Override
  public List<Move> buildMoves() {
    GameBoard gameBoard = game.getBoard();
    //PART 1: Loops through the level one base cards adding a move per available card.
    List<Move> moves = gameBoard.getDeck()
                                .stream()
                                .filter(card -> card.isDevelopment() && purchasableCard(card))
                                .map(BuyCardMove::new)
                                .collect(Collectors.toList());

    //PART 2: Loop through the Orient cards if they are in the game.
    if (game.getType().equals(GameType.BASE)) {
      return eligibleNobles(performPurchaseChecks(moves));
    }

    OrientGameBoard orientBoard = (OrientGameBoard) gameBoard;

    //PART 2.1: Loops through the level one
    // orient cards adding one or more moves per available card.
    for (DevelopmentCard card : orientBoard.getL1OrientCards()) {
      OrientDevelopmentCard orientCard = (OrientDevelopmentCard) card;
      if (purchasableCard(card)) {
        //If the card is a pairingCard adds one move per bonus type that the player has.
        if (orientCard.getPower().equals(OrientPower.PAIRING)) {
          for (Bonus possibleBonus : curPlayer.getBonuses().keySet()) {
            //            if (possibleBonus.equals(Bonus.GOLD) || curPlayer.getBonuses()
            //            .get(possibleBonus) <= 0) {
            //              continue;
            //            }
            moves.add(new LevelOnePairingMove(card, possibleBonus));
          }
          //Otherwise it just adds the current (DooubleGold) card
        } else {
          moves.add(new BuyCardMove(card));
        }
      }
    }

    //2.2: LEVEL TWO ORIENT: Loops through the level two
    // orient cards adding one or more moves per available card.
    for (DevelopmentCard card : orientBoard.getL2OrientCards()) {
      OrientDevelopmentCard orientCard = (OrientDevelopmentCard) card;
      if (purchasableCard(card)) {

        //2.2.1: If the card is a pairingCascadeCard,
        // it adds multiple moves for each bonus type the player has
        if (orientCard.getPower().equals(OrientPower.PAIRING_CASCADE)) {
          for (Bonus possibleBonus : curPlayer.getBonuses().keySet()) {
            //BASE CASCADE CARD: Creates a move for each level One base card given the bonus.
            for (DevelopmentCard cascadeCardBase : gameBoard.getLevelOne()) {
              moves.add(new LevelTwoPairingMove(card, cascadeCardBase, possibleBonus));
            }
            //ORIENT CASCADE CARD: Creates moves for each level One Orient card given the bonus.
            for (DevelopmentCard cascadeCard : ((OrientGameBoard) gameBoard).getL1OrientCards()) {
              OrientDevelopmentCard cascadeCardOrient = (OrientDevelopmentCard) cascadeCard;

              if (cascadeCardOrient.getPower().equals(OrientPower.DOUBLE_GOLD)) {
                moves.add(new LevelTwoPairingMove(card, cascadeCardOrient, possibleBonus));
              } else if (cascadeCardOrient.getPower().equals(OrientPower.PAIRING)) {
                for (Bonus cascadeBonus : curPlayer.getBonuses().keySet()) {
                  if (curPlayer.getBonuses().get(cascadeBonus) > 0) {
                    moves.add(new LevelTwoPairingL1Move(card,
                                                        cascadeCardOrient,
                                                        possibleBonus, cascadeBonus));
                  }
                }
              }
            }
          }
          //If the Card is noble reserving, it adds a move per availableNoble.
        } else if (orientCard.getPower().equals(OrientPower.RESERVE_NOBLE)) {
          if (game.getBoard().getNobles()
                  .stream()
                  .filter(n -> !n.getState().equals(CardState.PURCHASED))
                  .count() == 0) {
            moves.add(new BuyCardMove(card));
          } else {
            for (NobleCard noble : game.getBoard().getNobles()) {
              if (noble.getState().equals(CardState.FREE)) {
                moves.add(new LevelTwoNobleMove(card, noble));
              }
            }
          }
          //Otherwise (Double Bonus level two orient) it just adds the purchasingMove.
        } else {
          moves.add(new BuyCardMove(card));
        }
      }
    }
    //2.3: LEVEL THREE: Loops through the level three orient cards adding one more moves per card.
    for (DevelopmentCard card : orientBoard.getL3OrientCards()) {
      OrientDevelopmentCard orientCard = (OrientDevelopmentCard) card;
      if (purchasableCard(card)) {

        //2.3.1: Add all discard Bonus moves.
        if (Replacement.DISCARD.startsWith(orientCard.getPower()
                                                     .name())
            && curPlayer.getBonuses()
                      .get(Bonus.valueOf(
                        Replacement.DISCARD.replace(
                          orientCard.getPower()
                                    .name()))) >= 2) {
          moves.add(new BuyCardMove(card));
        } else {
          //If the level two cascade card is a base card, simply add the move.
          for (DevelopmentCard cascadeCard : GameBoard.getFaceUpCards(gameBoard.getLevelTwo())) {
            moves.add(new LevelThreePairingL2BaseMove(card, cascadeCard));
          }

          //If the cascade card is an orient,
          // add different types of moves depending on the cards power.
          List<DevelopmentCard> cards = GameBoard.getFaceUpCards(
              ((OrientGameBoard) gameBoard).getL2OrientCards()
          );

          for (DevelopmentCard cascadeCard : cards) {
            OrientDevelopmentCard cascadeCardOrient = (OrientDevelopmentCard) cascadeCard;

            //Case 2.1: The Card is a Double Bonus.
            if (cascadeCardOrient.getPower().equals(OrientPower.DOUBLE_BONUS)) {
              moves.add(new LevelThreePairingL2BaseMove(card, cascadeCard));

              //Case 2.2: The Card is a NobleReserving.
            } else if (cascadeCardOrient.getPower().equals(OrientPower.RESERVE_NOBLE)) {
              if (game.getBoard().getNobles()
                      .stream()
                      .filter(n -> !n.getState().equals(CardState.PURCHASED))
                      .count() == 0) {
                moves.add(new LevelThreePairingL2BaseMove(card, cascadeCard));
              } else {
                for (NobleCard noble : game.getBoard().getNobles()) {
                  if (noble.getState().equals(CardState.FREE)) {
                    moves.add(new LevelThreePairingL2NobleMove(card, noble, cascadeCard));
                  }
                }
              }
              //CASE 2.3: The Card is a Level Two Cascade.
            } else if (cascadeCardOrient.getPower().equals(OrientPower.PAIRING_CASCADE)) {
              for (Bonus possibleBonus : curPlayer.getBonuses().keySet()) {
                if (curPlayer.getBonuses().get(possibleBonus) <= 0) {
                  continue;
                }
                //BASE CASCADE CARD: Creates a move for each level One base card given the bonus.
                for (DevelopmentCard levelOneCascade : GameBoard
                    .getFaceUpCards(gameBoard.getLevelOne())) {
                  moves.add(
                    new LevelThreePairingL2Move(card, levelOneCascade, possibleBonus, cascadeCard));
                }

                //ORIENT CASCADE CARD: Creates moves
                // for each level One Orient card given the bonus.
                for (DevelopmentCard levelOneCascade : GameBoard
                    .getFaceUpCards(((OrientGameBoard) gameBoard)
                                    .getL1OrientCards())) {
                  OrientDevelopmentCard l1Cascade = (OrientDevelopmentCard) levelOneCascade;

                  //If the Selected Level One card is a Double Gold card.
                  if (l1Cascade.getPower().equals(OrientPower.DOUBLE_GOLD)) {
                    moves.add(new LevelThreePairingL2Move(card, cascadeCard,
                                                          possibleBonus, levelOneCascade));
                  } else if (l1Cascade.getPower().equals(OrientPower.PAIRING)) {
                    for (Bonus cascadeBonus : curPlayer.getBonuses().keySet()) {
                      moves.add(new LevelThreePairingL2CascadeMove(card, levelOneCascade,
                                                                   possibleBonus, cascadeBonus,
                                                                   cascadeCard));
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    //moves.sort((o1, o2) -> o1.hashCode() - o2.hashCode()); //TODO: Is this still needed???
    return eligibleNobles(performPurchaseChecks(moves));
  }
}
