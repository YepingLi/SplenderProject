package org.mcgill.splendorapi.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * All the Trading Posts Power types.
 */
@RequiredArgsConstructor
@Getter
public enum PowerType {
  FREE_GEM_ON_PURCHASE,
  TWO_AND_ONE,
  DOUBLE_GOLD,
  FREE_POINTS,
  POINTS_PER_MARKER,
}

