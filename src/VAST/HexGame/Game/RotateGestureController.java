/**
 * 
 */
package VAST.HexGame.Game;

import java.awt.Point;
import java.util.Vector;

import AidPackage.MathAid;
import VAST.HexGame.Game.GestureControllerInterface.GestureState;

/**
 * Rotate gesture controller.
 * 
 * @author SidneyTTW
 * 
 */
public class RotateGestureController implements GestureControllerInterface {
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
   * Indexes of gesture ball or influenced ball.
   */
  Vector<Integer> indexes;

  /**
   * Original angles of the balls rotating.
   */
  Vector<Double> originalAngles;

  /**
   * Center of the rotation.
   */
  Point centerPosition;

  /**
   * Angle when the gesture starts.
   */
  double gestureStartAngle;

  /**
   * Cool down of the gesture.
   */
  int gestureCoolDown;

  /**
   * The offset of the index.
   */
  int offset;

  /**
   * Whether to rotate empty balls.
   */
  boolean rotateEmpty = false;

  /**
   * Constructor
   * 
   * @param game
   *          The game.
   * @param rule
   *          The rule.
   */
  public RotateGestureController(GameInterface game, GameRuleInterface rule) {
    this.game = game;
    this.rule = rule;
    indexes = new Vector<Integer>();
    gestureCoolDown = 0;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GestureControllerInterface#pressAt(java.awt.Point,
   * int, int)
   */
  @Override
  public void pressAt(Point logicalPos, int button, int mouseId) {
    // Clear
    indexes.clear();
    GameEffectAdapter effectPainter = rule.getEffectPainter();
    if (effectPainter != null)
      effectPainter.clearSelectionHints();

    // Record the press
    gestureState = GestureState.ChooseGesture;
    dragAt(logicalPos, button, mouseId);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GestureControllerInterface#releaseAt(java.awt.Point,
   * int, int)
   */
  @Override
  public void releaseAt(Point logicalPos, int button, int mouseId) {
    GameEffectAdapter effectPainter = rule.getEffectPainter();
    if (effectPainter != null) {
      effectPainter.clearUserMovingEliminationHints();
      effectPainter.clearSelectionHints();
    }
    if (gestureState != GestureState.LocateGesture)
      return;
    GameBoardInterface gameBoard = rule.getGameBoard();
    CoreControllerInterface coreController = rule.getCoreController();
    Ball[] balls = coreController.getBalls();
    int n = indexes.size();
    if (offset == 0) {
      for (int i = 0; i < n; ++i) {
        int index = indexes.elementAt(i);
        coreController.rotateABallTo(balls[index],
            gameBoard.ballLogicalPositionOfIndex(index), centerPosition, 5,
            false, 1);
      }
      indexes.clear();
      gestureState = GestureState.NoGesture;
      return;
    }
    Ball[] originalBalls = new Ball[n];
    Ball[] currentBalls = new Ball[n];
    for (int i = 0; i < n; ++i) {
      int index = indexes.elementAt(i);
      int currentIndex = indexes.elementAt((i + offset) % n);
      originalBalls[i] = balls[index];
      currentBalls[i] = balls[currentIndex];
      if (index < 0 || (balls[index] != null && balls[index].isLocked()))
        return;
    }
    for (int i = 0; i < n; ++i) {
      int index = indexes.elementAt(i);
      balls[index] = currentBalls[i];
      if (balls[index] != null)
        balls[index].setState(Ball.State.SystemMoving);
    }

    boolean rotateSuccessful = rule.getConnectionCalculator() == null;
    if (!rotateSuccessful) {
      // Get the connections
      ConnectionInterface connections = rule.getConnectionCalculator()
          .calculateConnection(balls, rule.getGameBoard(), false);
      for (int i = 0; i < n; ++i) {
        int index = indexes.elementAt(i);
        if (connections.isInAChain(index)) {
          rotateSuccessful = true;
          break;
        }
      }
    }

    if (rotateSuccessful) {
      // Move balls to the new position
      for (int i = 0; i < n; ++i) {
        int index = indexes.elementAt(i);
        coreController.translateABallTo(balls[index],
            gameBoard.ballLogicalPositionOfIndex(index), 7, true);
      }
      // A good move
      if (game != null)
        game.goodMove();
    } else {
      // Rotate balls to the old position
      for (int i = 0; i < n; ++i) {
        int index = indexes.elementAt(i);
        balls[index] = originalBalls[i];
        if (balls[index] != null)
          balls[index].setState(Ball.State.SystemMoving);
        coreController.rotateABallTo(balls[index],
            gameBoard.ballLogicalPositionOfIndex(index), centerPosition, 5,
            false, 1);
      }
      // Set the CD
      gestureCoolDown = 10;
      // A bad move
      if (game != null)
        game.badMove();
    }

    indexes.clear();
    gestureState = GestureState.NoGesture;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GestureControllerInterface#dragAt(java.awt.Point,
   * int, int)
   */
  @Override
  public void dragAt(Point logicalPos, int button, int mouseId) {
    GameBoardInterface gameBoard = rule.getGameBoard();
    if (gestureState == GestureState.ChooseGesture) {
      int index = gameBoard.ballIndexAtLogicalPosition(logicalPos);
      int lastIndex = -1;
      if (!indexes.isEmpty())
        lastIndex = indexes.lastElement();
      if (index >= 0 && index != lastIndex) {
        indexes.add(index);
        GameEffectAdapter effectPainter = rule.getEffectPainter();
        if (effectPainter != null)
          effectPainter.selectAt(gameBoard, index);
        if (indexes.size() == 3) {
          testGesture(logicalPos);
        }
      }
    } else if (gestureState == GestureState.LocateGesture) {
      locateGesture(logicalPos);
    }
  }

  private void testGesture(Point logicalPos) {
    GameBoardInterface gameBoard = rule.getGameBoard();
    Ball[] balls = rule.getCoreController().getBalls();

    // Try to find the center
    int[] connectedCount = new int[gameBoard.totalBallCount()];
    for (int i = 0; i < gameBoard.totalBallCount(); ++i)
      connectedCount[i] = 0;
    for (int i = 0; i < 3; ++i) {
      Vector<Integer> chain = gameBoard.chainAroundIndex(indexes.elementAt(i));
      for (int j = 0; j < chain.size(); ++j)
        ++connectedCount[chain.elementAt(j)];
    }
    int center = -1;
    for (int i = 0; i < gameBoard.totalBallCount(); ++i)
      if (connectedCount[i] == 3) {
        center = i;
        break;
      }
    
    boolean canStartRotate = gameBoard.canBeRotateCenter(center);
    if (!rotateEmpty){
    Vector<Integer> chain = gameBoard.chainAroundIndex(center);
    for (int i = 0; i < chain.size(); ++i)
      if(balls[chain.elementAt(i)] == null)
        canStartRotate = false;
    }

    if (canStartRotate) {
      gestureState = GestureState.LocateGesture;
      indexes = (Vector<Integer>) gameBoard.chainAroundIndex(center).clone();
      originalAngles = new Vector<Double>();
      centerPosition = gameBoard.ballLogicalPositionOfIndex(center);
      for (int i = 0; i < indexes.size(); ++i) {
        double angle = MathAid.angle(
            gameBoard.ballLogicalPositionOfIndex(indexes.elementAt(i)),
            centerPosition);
        if (balls[indexes.elementAt(i)] != null)
          balls[indexes.elementAt(i)].setState(Ball.State.UserMoving);
        originalAngles.add(angle);
        gestureStartAngle = MathAid.angle(logicalPos, centerPosition);
        offset = 0;
      }
    } else {
      indexes.clear();
      gestureState = GestureState.NoGesture;
    }
    GameEffectAdapter effectPainter = rule.getEffectPainter();
    if (effectPainter != null)
      effectPainter.clearSelectionHints();
  }

  private void locateGesture(Point mousePos) {
    Ball[] balls = rule.getCoreController().getBalls();
    if (gestureState != GestureState.LocateGesture)
      return;
    double maxR = rule.getGameBoard().intervalBetweenTwoLayers();
    double mouseCurrentA = MathAid.angle(mousePos, centerPosition);
    double dAngle = mouseCurrentA - gestureStartAngle;

    for (int i = 0; i < indexes.size(); ++i) {
      if (indexes.elementAt(i) < 0 || balls[indexes.elementAt(i)] == null)
        continue;
      double a = originalAngles.elementAt(i) + dAngle;
      double r = MathAid.distanceFromTheCenterWithTheAngle(a, maxR);
      balls[indexes.elementAt(i)].setPosition(MathAid.calculatePosition(a, r,
          centerPosition));
    }
    dAngle += MathAid.PI / 6;
    while (dAngle > MathAid.PI * 2)
      dAngle -= MathAid.PI * 2;
    while (dAngle < 0)
      dAngle += MathAid.PI * 2;
    for (offset = 0; offset < 6; ++offset)
      if ((offset + 1) * MathAid.PI / 3 > dAngle)
        break;
    Ball[] copy = new Ball[balls.length];
    for (int i = 0; i < balls.length; ++i) {
      copy[i] = balls[i];
    }
    ConnectionCalculatorInterface connectionCalculator = rule
        .getConnectionCalculator();
    if (connectionCalculator == null)
      return;
    int n = indexes.size();
    Ball[] currentBalls = new Ball[n];
    for (int i = 0; i < n; ++i) {
      int currentIndex = indexes.elementAt((i + offset) % n);
      currentBalls[i] = balls[currentIndex];
    }
    for (int i = 0; i < n; ++i) {
      int index = indexes.elementAt(i);
      copy[index] = currentBalls[i];
    }
    ConnectionInterface connections = connectionCalculator.calculateConnection(
        copy, rule.getGameBoard(), false);
    if (game != null)
      game.userMovingConnectionTested(connections);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GestureControllerInterface#interrupt()
   */
  @Override
  public void interrupt() {
    GameEffectAdapter effectPainter = rule.getEffectPainter();
    if (effectPainter != null) {
      effectPainter.clearUserMovingEliminationHints();
      effectPainter.clearSelectionHints();
    }
    GameBoardInterface gameBoard = rule.getGameBoard();
    CoreControllerInterface coreController = rule.getCoreController();
    Ball[] balls = coreController.getBalls();
    int n = indexes.size();
    for (int i = 0; i < n; ++i) {
      int index = indexes.elementAt(i);
      balls[index].setPosition(gameBoard.ballLogicalPositionOfIndex(index));
      balls[index].setState(Ball.State.Stable);
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
