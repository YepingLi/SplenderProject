package org.mcgill.splendorapi.model.card;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * c.
 */
@Getter
public class DevelopmentCardMeta extends CardMeta {
  private final short level;

  @JsonCreator
  public DevelopmentCardMeta(@JsonProperty("id") short myId,
                             @JsonProperty("level") final short level,
                             @JsonProperty("type") CardType cardType) {
    super(myId, cardType);
    this.level = level;
  }
}
