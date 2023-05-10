package org.mcgill.splendorapi.api.move.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.model.GemType;

public class PaymentTest {

  @Test
  public void testPayment() {
    // Create a Payment object with some gem payments
    Map<GemType, Integer> gems = new HashMap<>();
    gems.put(GemType.RUBY, 2);
    gems.put(GemType.EMERALD, 1);
    Payment payment = Payment.builder()
                             .gems(gems)
                             .numDoubleGold(1)
                             .build();

    // Check that the Payment object has been created correctly
    assertEquals(2, payment.getGems().get(GemType.RUBY));
    assertEquals(1, payment.getGems().get(GemType.EMERALD));
    assertEquals(1, payment.getNumDoubleGold());
  }
}
