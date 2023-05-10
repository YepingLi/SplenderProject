package org.mcgill.splendorapi.model.exceptions;

import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.card.CardState;
import org.mcgill.splendorapi.model.card.CardType;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.DevelopmentCardMeta;

import static org.junit.jupiter.api.Assertions.*;

public class CardExceptionTest extends Throwable {
  @Test
  public void exceptionTest1() throws CardExceptionTest, InvalidCardType {
    DevelopmentCard aCard =
      new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 1, CardType.DEVELOPMENT),
                          Bonus.EMERALD, 2, new HashMap<>());
    CardException theException = new CardException(aCard, CardState.FREE);
    try {
      throw theException;
    }catch (CardException e){
      assertEquals(String.format("Card is not able to be %s since it is already %s",
                                 CardState.FREE.name(),
                                 CardState.FREE.name()),
                   e.getMessage());

      assertTrue(true);
    }
  }
  @Test
  public void exceptionTest2() throws CardExceptionTest, InvalidCardType {
    DevelopmentCard aCard =
      new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 1, CardType.DEVELOPMENT),
                          Bonus.EMERALD, 2, new HashMap<>());
    CardException theException = CardException.builder().card(aCard)
                                              .newState(CardState.FREE)
      .build();
    try {
      throw theException;
    }catch (CardException e){
      assertEquals(String.format("Card is not able to be %s since it is already %s",
                                 CardState.FREE.name(),
                                 CardState.FREE.name()),
                   e.getMessage());

      assertTrue(true);
    }
  }
}
