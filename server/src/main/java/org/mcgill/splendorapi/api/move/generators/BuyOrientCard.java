package org.mcgill.splendorapi.api.move.generators;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import org.mcgill.splendorapi.api.move.enums.Replacement;
//import org.mcgill.splendorapi.api.move.model.BuyCardMove;
//import org.mcgill.splendorapi.api.move.model.LevelOnePairingMove;
//import org.mcgill.splendorapi.api.move.model.LevelTwoPairingMove;
//import org.mcgill.splendorapi.model.Bonus;
//import org.mcgill.splendorapi.model.Game;
//import org.mcgill.splendorapi.model.Move;
//import org.mcgill.splendorapi.model.Player;
//import org.mcgill.splendorapi.model.board.GameBoard;
//import org.mcgill.splendorapi.model.board.OrientGameBoard;
//import org.mcgill.splendorapi.model.card.CardState;
//import org.mcgill.splendorapi.model.card.DevelopmentCard;
//
///**
// * BuyOrientCard class.
// */
//
//public class BuyOrientCard extends AbstractMoveGenerator {

//  private final Player curPlayer;
//  /**
//   * Constructor which calls the Abstract Move Service.
//   *
//   * @param game the current game.
//   */
//
//  public BuyOrientCard(Game game) {
//    super(game);
//    curPlayer = game.getCurPlayer();
//  }
//
//
//  /**
//   * Checks if the card is purchasable.
//   *
//   * @param card The card to buy
//   * @return boolean of purchase status
//   */
//  public boolean purchasableCard(DevelopmentCard card) {
//    return (card.isFree() || (card.getState().equals(CardState.RESERVED)
//      && curPlayer.hasReservedCard(card)))
//      && curPlayer.canBuyCard(card) && card.isTurned();
//  }


//  /**
//   * Build moves for buy orient card action.
//
//   * @return the moves built.
//   */
//  @Override
//  public List<Move> buildMoves() {

    //    Player curPlayer = game.getCurPlayer();
    //    OrientGameBoard orientBoard = (OrientGameBoard) game.getBoard();
    //
    //
    //    List<Move> moves = orientBoard.getDeck()
    //                                .stream()
    //                                .filter(card -> card.isOrientCard() && purchasableCard(card))
    //                                .map(generatePossibleMove((OrientDevelopmentCard) card))
    //                                .collect(Collectors.toList());
    //
    //    //PART 2.1: Loops through the level one
    //    // orient cards adding one or more moves per available card.
    //
    //
    //    for (DevelopmentCard card : orientBoard.getL1OrientCards()) {
    //      OrientDevelopmentCard orientCard = (OrientDevelopmentCard) card;
    //      if (purchasableCard(card)) {
    //
    //        //If the card is a pairingCard adds one move per bonus type that the player has.
    //        if (orientCard.getPower().equals(OrientPower.PAIRING)) {
    //          for (Bonus possibleBonus : curPlayer.getBonuses().keySet()) {
    //            if (possibleBonus.equals(Bonus.GOLD) || curPlayer.getBonuses()
    //                                                             .get(possibleBonus) <= 0) {
    //              continue;
    //            }
    //            moves.add(new LevelOnePairingMove(card, possibleBonus));
    //          }
    //          //Otherwise it just adds the current (DooubleGold) card
    //        } else {
    //          moves.add(new BuyCardMove(card));
    //        }
    //      }
    //    }
    //
    //    //2.2: LEVEL TWO ORIENT: Loops through the level two
    //    // orient cards adding one or more moves per available card.
    //    for (DevelopmentCard card : orientBoard.getL2OrientCards()) {
    //      OrientDevelopmentCard orientCard = (OrientDevelopmentCard) card;
    //      if (purchasableCard(card)) {
    //
    //        //2.2.1: If the card is a pairingCascadeCard,
    //        // it adds multiple moves for each bonus type the player has
    //        if (orientCard.getPower().equals(OrientPower.PAIRING_CASCADE)) {
    //          for (Bonus possibleBonus : curPlayer.getBonuses().keySet()) {
    //            //BASE CASCADE CARD: Creates a move for each level One base card given the bonus.
    //            for (DevelopmentCard cascadeCardBase : gameBoard.getLevelOne()) {
    //              moves.add(new LevelTwoPairingMove(card, cascadeCardBase, possibleBonus));
    //            }
    //            //ORIENT CASCADE CARD: Creates moves for each level
    //            //One Orient card given the bonus.
    //            for (DevelopmentCard cascadeCard : ((OrientGameBoard) gameBoard)
    //            .getL1OrientCards()) {
    //              OrientDevelopmentCard cascadeCardOrient = (OrientDevelopmentCard) cascadeCard;
    //
    //              if (cascadeCardOrient.getPower().equals(OrientPower.DOUBLE_GOLD)) {
    //                moves.add(new LevelTwoPairingMove(card, cascadeCardOrient, possibleBonus));
    //              } else if (cascadeCardOrient.getPower().equals(OrientPower.PAIRING)) {
    //                for (Bonus cascadeBonus : curPlayer1.getBonuses().keySet()) {
    //                  if (curPlayer1.getBonuses().get(cascadeBonus) > 0) {
    //                    moves.add(new BuyCardMove(card, possibleBonus,
    //                                              cascadeCardOrient, cascadeBonus));
    //                  }
    //                }
    //              }
    //            }
    //          }
    //          //If the Card is noble reserving, it adds a move per availableNoble.
    //        } else if (orientCard.getPower().equals(OrientPower.RESERVE_NOBLE)) {
    //          for (NobleCard noble : game.getBoard().getNobles()) {
    //            if (noble.getState().equals(CardState.FREE)) {
    //              moves.add(new BuyCardMove(card, noble));
    //            }
    //          }
    //          //Otherwise (Double Bonus level two orient) it just adds the purchasingMove.
    //        } else {
    //          moves.add(new BuyCardMove(card));
    //        }
    //      }
    //    }
    //    //2.3: LEVEL THREE: Loops through the level three orient cards adding one more
    //    // moves per card.
    //    for (DevelopmentCard card : orientBoard.getL3OrientCards()) {
    //      OrientDevelopmentCard orientCard = (OrientDevelopmentCard) card;
    //      if (purchasableCard(card)) {
    //
    //        //2.3.1: Add all discard Bonus moves.
    //        if (orientCard.getPower()
    //                      .name()
    //                      .startsWith("DISCARD") && curPlayer1.getBonuses()
    //                                                          .get(Bonus.valueOf(
    //                                                            Replacement.DISCARD.replace(
    //                                                              orientCard.getPower()
    //                                                                        .name()))) >= 2) {
    //          moves.add(new BuyCardMove(card));
    //        } else {
    //          //If the level two cascade card is a base card, simply add the move.
    //          for (DevelopmentCard cascadeCard : GameBoard
    //          .getFaceUpCards(gameBoard.getLevelTwo())) {
    //            moves.add(new BuyCardMove(card, cascadeCard));
    //          }
    //
    //          //If the cascade card is an orient,
    //          // add different types of moves depending on the cards power.
    //          List<DevelopmentCard> cards = GameBoard.getFaceUpCards(
    //            ((OrientGameBoard) gameBoard).getL2OrientCards()
    //          );
    //
    //          for (DevelopmentCard cascadeCard : cards) {
    //            OrientDevelopmentCard cascadeCardOrient = (OrientDevelopmentCard) cascadeCard;
    //
    //            //Case 2.1: The Card is a Double Bonus.
    //            if (cascadeCardOrient.getPower().equals(OrientPower.DOUBLE_BONUS)) {
    //              moves.add(new BuyCardMove(card, cascadeCard));
    //
    //              //Case 2.2: The Card is a NobleReserving.
    //            } else if (cascadeCardOrient.getPower().equals(OrientPower.RESERVE_NOBLE)) {
    //              for (NobleCard noble : game.getBoard().getNobles()) {
    //                if (noble.getState().equals(CardState.FREE)) {
    //                  moves.add(new BuyCardMove(card, cascadeCard, noble));
    //                }
    //              }
    //              //CASE 2.3: The Card is a Level Two Cascade.
    //            } else if (cascadeCardOrient.getPower().equals(OrientPower.PAIRING_CASCADE)) {
    //              for (Bonus possibleBonus : curPlayer1.getBonuses().keySet()) {
    //                if (curPlayer1.getBonuses().get(possibleBonus) <= 0) {
    //                  continue;
    //                }
    //                //BASE CASCADE CARD: Creates a move for each level
    //                //One base card given the bonus.
    //                for (DevelopmentCard levelOneCascade : GameBoard
    //                  .getFaceUpCards(gameBoard.getLevelOne())) {
    //                  moves.add(new BuyCardMove(card, cascadeCard,
    //                  possibleBonus, levelOneCascade));
    //                }
    //
    //                //ORIENT CASCADE CARD: Creates moves
    //                // for each level One Orient card given the bonus.
    //                for (DevelopmentCard levelOneCascade : GameBoard
    //                  .getFaceUpCards(((OrientGameBoard) gameBoard)
    //                                    .getL1OrientCards())) {
    //                  OrientDevelopmentCard l1Cascade = (OrientDevelopmentCard) levelOneCascade;
    //
    //                  //If the Selected Level One card is a Double Gold card.
    //                  if (l1Cascade.getPower().equals(OrientPower.DOUBLE_GOLD)) {
    //                    moves.add(new BuyCardMove(card, cascadeCard,
    //                                              possibleBonus, levelOneCascade, 1));
    //                  } else if (l1Cascade.getPower().equals(OrientPower.PAIRING)) {
    //                    for (Bonus cascadeBonus : curPlayer1.getBonuses().keySet()) {
    //                      moves.add(new BuyCardMove(card, cascadeCard,
    //                                                possibleBonus,
    //                                                levelOneCascade, cascadeBonus));
    //                    }
    //                  }
    //                }
    //              }
    //            }
    //          }
    //        }
    //      }
    //    }

//    return List.of();
//  }
//}
