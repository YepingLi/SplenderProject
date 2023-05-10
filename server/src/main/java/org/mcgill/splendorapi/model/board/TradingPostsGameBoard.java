package org.mcgill.splendorapi.model.board;

import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.mcgill.splendorapi.model.GameType;
import org.mcgill.splendorapi.model.TradingPost;

/**
 * Represents the Game Board with Trading Post extension.
 */
@Jacksonized
@Getter
@SuperBuilder
public class TradingPostsGameBoard  extends OrientGameBoard {

  private final List<TradingPost> tradingPosts;

  public GameType fixType(GameType t) {
    return Optional.ofNullable(t).orElse(GameType.TRADING_POSTS);
  }
}
