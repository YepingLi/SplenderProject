package org.mcgill.splendorapi.model.card;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * s.
 */
@Getter
public class CardMeta {
  protected final short id;
  protected final CardType type;

  @JsonCreator
  public CardMeta(@JsonProperty("id") short myId, @JsonProperty("type") CardType cardType) {
    id = myId;
    type = cardType;
  }


}