package org.mcgill.splendorapi.api.game.visitor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.mcgill.splendorapi.model.card.Card;
import org.mcgill.splendorapi.model.card.DevelopmentCard;

/**
 * Utility class.
 */
public class MapperUtils {
  /**
   * Utility function for counting the number of cards of a level.
   *
   * @param level The level wanted to sort by
   * @param cards The cards which are being searched
   * @return The number of cards of that level which are not turned
   */
  protected static Integer countLevel(int level, Stream<DevelopmentCard> cards) {
    return (int) cards.filter(card -> card.getMeta().getLevel() == level && !card.isTurned())
                      .count();
  }

  /**
   * Utility function for filtering development cards of the deck.
   *
   * @param cards  The cards which are being searched
   * @return The list of cards resulting
   */
  protected static List<DevelopmentCard> filterDevelopmentCards(List<DevelopmentCard> cards) {
    return cards.stream()
                .filter(Card::isDevelopment)
                .collect(Collectors.toList());
  }

  /**
   * Utility function for filtering the FREE turned over cards of the deck.
   *
   * @param cards The cards which are being searched
   * @return  The list of cards resulting
   */
  protected static List<DevelopmentCard> filterTurnedCards(List<DevelopmentCard> cards) {
    return cards.stream()
                .filter(DevelopmentCard::isTurned)
                .filter(DevelopmentCard::isFree)
                .collect(Collectors.toList());
  }
}
