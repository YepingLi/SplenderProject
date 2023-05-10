package org.mcgill.splendorapi.api.move;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mcgill.splendorapi.api.InvalidRightsHandler;
import org.mcgill.splendorapi.api.move.model.MoveDto;
import org.mcgill.splendorapi.api.move.model.PerformMoveDto;
import org.mcgill.splendorapi.connector.LobbyServiceAuthResource;
import org.mcgill.splendorapi.model.exceptions.GameBoardException;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the Moves.
 */
@Slf4j
@RequiredArgsConstructor
@RestController("Move controller")
@RequestMapping("/{typeId}/games/{id}/move")
public class MoveController extends InvalidRightsHandler {

  private final LobbyServiceAuthResource authResource;
  private final MoveManagerService moveManagerService;
  private final MoveMapper mapper;

  /**
   * Perform a purchase move.
   *
   * @param gameId The id of the game
   * @param token  The token to be checked
   * @return The gameboard
   * @throws IllegalMoveException   Cannot make this move
   * @throws IllegalAccessException Cannot do this to this board
   */
  @GetMapping("")
  public List<MoveDto> getMoves(@PathVariable("typeId") String typeId,
                                @PathVariable("id") String gameId,
                                @RequestParam("access_token") String token)
        throws IllegalMoveException, IllegalAccessException {
    return moveManagerService.buildMoves(gameId, authResource.getUsername(token))
                             .stream()
                             .map(mapper::map)
                             .collect(Collectors.toList());
  }

  /**
   * make a move.

   * @param gameId game id
   * @param token game token
   * @param body gto body
   * @throws IllegalMoveException Illegal Move
   * @throws IllegalAccessException Illegal Access
   * @throws GameBoardException Illegal GameBoard
   */
  @PutMapping("")
  public void makeMove(@PathVariable("typeId") String typeId,
                       @PathVariable("id") String gameId,
                       @RequestParam("access_token") String token,
                       @RequestBody PerformMoveDto body)
      throws IllegalMoveException, IllegalAccessException, GameBoardException {
    //Should change the currentPlayer and update the board and send the result back to the clients
    moveManagerService.performMove(gameId,
                                   authResource.getUsername(token),
                                   body.getMoveId());

  }
}
