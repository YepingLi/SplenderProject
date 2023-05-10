package org.mcgill.splendorapi.api.move.model;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mcgill.splendorapi.model.Action;

/**
 * Represents the atomic actions which make up a move object.
 */
@RequiredArgsConstructor
@Getter
public class MoveDto {
  private final List<Action<?>> actions;
}
