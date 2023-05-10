package org.mcgill.splendorapi.model.card;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardMetaTest {

  private CardMeta cardMeta;
  private short testId;
  private CardType testCardType;

  @BeforeEach
  void setUp() {
    testId = 1;
    testCardType = CardType.NOBLE;
    cardMeta = new CardMeta(testId, testCardType);
  }

  @Test
  void getId() {
    assertEquals(testId, cardMeta.getId(), "CardMeta ID should match the test ID");
  }

  @Test
  void getType() {
    assertEquals(testCardType, cardMeta.getType(), "CardMeta type should match the test CardType");
  }

  @Test
  void testSerialization() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    String jsonString = objectMapper.writeValueAsString(cardMeta);
    CardMeta deserializedCardMeta = objectMapper.readValue(jsonString, CardMeta.class);

    assertEquals(cardMeta.getId(), deserializedCardMeta.getId(), "Deserialized CardMeta ID should match the original");
    assertEquals(cardMeta.getType(), deserializedCardMeta.getType(), "Deserialized CardMeta type should match the original");
  }

}
