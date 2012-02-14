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
    if (gestureState != GestureState.LocateGesture)
      return;
    GameEffectAdapter effectPainter = rule.getEffectPainter();
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
      if (index < 0 || balls[index] == null || balls[index].isLocked())
        return;
    }
    for (int i = 0; i < n; ++i) {
      int index = indexes.elementAt(i);
      balls[index] = currentBalls[i];
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
    if (gameBoard.canBeRotateCenter(center)) {
      gestureState = GestureState.LocateGesture;
      indexes = (Vector<Integer>) gameBoard.chainAroundIndex(center).clone();
      originalAngles = new Vector<Double>();
      centerPosition = gameBoard.ballLogicalPositionOfIndex(center);
      for (int i = 0; i < indexes.size(); ++i) {
        double angle = 0;
        if (balls[indexes.elementAt(i)] != null) {
          angle = MathAid.angle(balls[indexes.elementAt(i)].getPosition(),
              centerPosition);
          balls[indexes.elementAt(i)].setState(Ball.State.UserMoving);
        }
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
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GestureControllerInterface#interrupt()
   */
  @Override
  public void interrupt() {
    // TODO Auto-generated method stub

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
