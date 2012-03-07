/**
 * 
 */
package VAST.HexGame.Game;

import java.util.Vector;

import Aid.MyPoint;

/**
 * Swap gesture controller.
 * 
 * @author SidneyTTW
 * 
 */
public class SwapGestureController implements GestureControllerInterface {
  /**
   * Game.
   */
  GameInterface game;

  /**
   * The rule of the game.
   */
  GameRuleInterface rule;

  /**
   * State of the gesture.
   */
  GestureState gestureState = GestureState.NoGesture;

  /**
   * Cool down of the gesture.
   */
  int gestureCoolDown;

  /**
   * Last index of the gesture.
   */
  int lastIndex;

  /**
   * Constructor.
   * 
   * @param rule
   *          The rule of the game.
   */
  public SwapGestureController(GameInterface game, GameRuleInterface rule) {
    this.game = game;
    this.rule = rule;
    lastIndex = -1;
    gestureCoolDown = 0;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GestureControllerInterface#pressAt(java.awt.Point,
   * int, int)
   */
  @Override
  public void pressAt(MyPoint logicalPos, int button, int mouseId) {
    // Clear
    lastIndex = -1;

    GameEffectAdapter effectPainter = rule.getEffectPainter();
    if (effectPainter != null)
      effectPainter.clearSelectionHints();

    // Record the press
    gestureState = GestureState.LocateGesture;
    GameBoardInterface gameBoard = rule.getGameBoard();
    CoreControllerInterface coreController = rule.getCoreController();
    int index = gameBoard.ballIndexAtLogicalPosition(logicalPos);
    if (index >= 0) {
      if (coreController.getBalls()[index] != null
          && !coreController.getBalls()[index].isLocked()) {
        lastIndex = index;
        if (effectPainter != null)
          effectPainter.selectAt(gameBoard, index);
      } else {
        // Clear
        lastIndex = -1;
        if (effectPainter != null)
          effectPainter.clearSelectionHints();
      }
    } else
      gestureState = GestureState.NoGesture;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GestureControllerInterface#releaseAt(java.awt.Point,
   * int, int)
   */
  @Override
  public void releaseAt(MyPoint logicalPos, int button, int mouseId) {
    // End of the gesture
    gestureState = GestureState.NoGesture;

    // Clear
    lastIndex = -1;
    GameEffectAdapter effectPainter = rule.getEffectPainter();
    if (effectPainter != null)
      effectPainter.clearSelectionHints();
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GestureControllerInterface#dragAt(java.awt.Point,
   * int, int)
   */
  @Override
  public void dragAt(MyPoint logicalPos, int button, int mouseId) {
    GameEffectAdapter effectPainter = rule.getEffectPainter();
    GameBoardInterface gameBoard = rule.getGameBoard();
    CoreControllerInterface coreController = rule.getCoreController();
    // Record the move
    if (gestureState == GestureState.LocateGesture) {
      int index = gameBoard.ballIndexAtLogicalPosition(logicalPos);
      if (index >= 0) {
        if (lastIndex >= 0 && lastIndex != index) {
          // Test the gesture
          testGesture(lastIndex, index);
        } else if (lastIndex != index
            && coreController.getBalls()[index] != null) {
          lastIndex = index;
          if (effectPainter != null)
            effectPainter.selectAt(gameBoard, index);
        }
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GestureControllerInterface#interrupt()
   */
  @Override
  public void interrupt() {
    lastIndex = -1;
    gestureState = GestureState.NoGesture;
    GameEffectAdapter effectPainter = rule.getEffectPainter();
    if (effectPainter != null)
      effectPainter.clearSelectionHints();
  }

  private void testGesture(int index1, int index2) {
    GameEffectAdapter effectPainter = rule.getEffectPainter();
    GameBoardInterface gameBoard = rule.getGameBoard();
    CoreControllerInterface coreController = rule.getCoreController();
    Ball[] balls = coreController.getBalls();
    if (index1 >= 0 && index2 >= 0 && index1 < gameBoard.totalBallCount()
        && index2 < gameBoard.totalBallCount() && index1 != index2) {

      Vector<Integer> aroundFrom = gameBoard.chainAroundIndex(index1);
      boolean isNextTo = false;
      for (int i = 0; i < aroundFrom.size(); ++i)
        if (index2 == aroundFrom.elementAt(i)) {
          isNextTo = true;
          break;
        }

      // Clear
      lastIndex = -1;
      if (effectPainter != null)
        effectPainter.clearSelectionHints();
      gestureState = GestureState.NoGesture;

      // If the swap is valid
      if (isNextTo) {
        // If a swap can be tried
        if (gestureCoolDown != 0)
          return;
        if ((balls[index1] == null) || balls[index1].isLocked()
            || balls[index1].getState() != Ball.State.Stable)
          return;
        if ((balls[index2] == null) || balls[index2].isLocked()
            || balls[index2].getState() != Ball.State.Stable)
          return;

        // Swap the two balls
        Ball tmp = balls[index1];
        balls[index1] = balls[index2];
        balls[index2] = tmp;

        // Set the state of the ball
        balls[index1].setState(Ball.State.SystemMoving);
        balls[index2].setState(Ball.State.SystemMoving);

        // Test whether the swap is successful
        boolean swapSuccessful = rule.getConnectionCalculator() == null;
        if (!swapSuccessful) {
          // Get the connections
          ConnectionInterface connections = rule.getConnectionCalculator()
              .calculateConnection(balls, rule.getGameBoard(), false);
          if (connections.isInAChain(index1) || connections.isInAChain(index2))
            swapSuccessful = true;
        }

        if (swapSuccessful) {
          // Move 2 balls to the new position
          coreController.translateABallTo(balls[index1],
              gameBoard.ballLogicalPositionOfIndex(index1), 4, true);
          coreController.translateABallTo(balls[index2],
              gameBoard.ballLogicalPositionOfIndex(index2), 4, true);
          // A good move
          if (game != null)
            game.goodMove();
        } else {
          // Swap the two balls
          tmp = balls[index1];
          balls[index1] = coreController.getBalls()[index2];
          balls[index2] = tmp;

          // Roll back the 2 balls
          // Some complex calculation to calculate the positions
          // the balls should be at
          int halfSteps = 3;
          MyPoint fromPos = gameBoard.ballLogicalPositionOfIndex(index1);
          double fromX = fromPos.x;
          double fromY = fromPos.y;
          MyPoint toPos = gameBoard.ballLogicalPositionOfIndex(index2);
          double toX = toPos.x;
          double toY = toPos.y;

          balls[index1].getStopPositions().clear();
          balls[index2].getStopPositions().clear();

          // Move to the new position
          for (int i = 0; i < halfSteps; ++i) {
            balls[index1].getStopPositions().add(
                new MyPoint(
                    (int) ((fromX * (halfSteps - i) + toX * i) / halfSteps),
                    (int) ((fromY * (halfSteps - i) + toY * i) / halfSteps)));
            balls[index2].getStopPositions().add(
                new MyPoint(
                    (int) ((toX * (halfSteps - i) + fromX * i) / halfSteps),
                    (int) ((toY * (halfSteps - i) + fromY * i) / halfSteps)));
          }
          // Move back to the original position
          for (int i = 1; i < halfSteps; ++i) {
            balls[index1].getStopPositions().add(
                balls[index1].getStopPositions().elementAt(halfSteps - i));
            balls[index2].getStopPositions().add(
                balls[index2].getStopPositions().elementAt(halfSteps - i));
          }
          // Set the CD
          gestureCoolDown = halfSteps * 2;
          // A bad move
          if (game != null)
            game.badMove();
        }
        gestureState = GestureState.NoGesture;
      }

    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GestureControllerInterface#advance()
   */
  @Override
  public void advance() {
    if (gestureCoolDown > 0) {
      --gestureCoolDown;
    }
  }
}
