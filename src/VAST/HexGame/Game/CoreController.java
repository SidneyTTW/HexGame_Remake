/**
 * 
 */
package VAST.HexGame.Game;

import java.awt.Point;
import java.util.Vector;

import AidPackage.MathAid;

/**
 * Class to realize the simulator.
 * 
 * @author SidneyTTW
 * 
 */
public class CoreController implements CoreControllerInterface {
  /**
   * Game.
   */
  GameInterface game;

  /**
   * The rule of the game.
   */
  GameRuleInterface rule;

  /**
   * Balls.
   */
  Ball[] balls;

  /**
   * Whether need to test stable eliminate.
   */
  private boolean needTestStableEliminate = true;

  /**
   * Constructor.
   * 
   * @param game
   *          The game.
   * @param rule
   *          The rule of the game.
   * @param balls
   *          The ball.s
   */
  public CoreController(GameInterface game, GameRuleInterface rule, Ball[] balls) {
    this.game = game;
    this.rule = rule;
    this.balls = balls;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * VAST.HexGame.Game.CoreControllerInterface#setBalls(VAST.HexGame.Game.Ball
   * [])
   */
  @Override
  public void setBalls(Ball[] balls) {
    this.balls = balls;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.CoreControllerInterface#getBalls()
   */
  @Override
  public Ball[] getBalls() {
    return balls;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.CoreControllerInterface#advance()
   */
  @Override
  public boolean advance() {
    int ballCount = rule.getGameBoard().totalBallCount();

    boolean result = false;

    // Advance each ball
    for (int i = 0; i < ballCount; ++i)
      if (balls[i] != null) {
        // If a ball changes its state, it will be necessary to test stable
        // eliminate
        needTestStableEliminate = balls[i].advance() || needTestStableEliminate;

        result = true;
      }

    // If the rule needs to eliminate
    if (rule.getConnectionCalculator() != null) {

      // If need to test stable eliminate
      if (needTestStableEliminate && testStableEliminate()) {
          // Will not need to test again in next few steps unless new balls have
          // been added
          needTestStableEliminate = fill();

          // There is something changed
          result = true;
        } else {
          // Will not need to test again in next few steps unless new balls have
          // been added
          needTestStableEliminate = fill();
        }
    } else {
      return needTestStableEliminate;
    }

    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.CoreControllerInterface#eliminate(Vector<Integer>)
   */
  @Override
  public void eliminate(Vector<Integer> indexes) {
    // The count of the balls eliminated
    int count = 0;
    for (int i = 0; i < indexes.size(); ++i) {
      int index = indexes.elementAt(i);
      // If the ball exists
      if (balls[index] != null) {
        ++count;
        balls[index] = null;
      }
    }
    if (game != null)
      game.eliminated(count);
  }

  /**
   * Test whether there's any ball to eliminate.
   * 
   * @return Whether any ball has been eliminated.
   */
  private boolean testStableEliminate() {
    if (rule.getConnectionCalculator() == null)
      return false;
    // Get the connections
    ConnectionInterface connections = rule.getConnectionCalculator()
        .calculateConnection(balls, rule.getGameBoard(), true);
    // Return if there is no connection
    if (connections.allChains().isEmpty())
      return false;
    // Call the function of game
    if (game != null)
      game.stableConnectionTested(connections);

    // Record the index to eliminate
    Vector<Integer> toEliminate = new Vector<Integer>();
    for (int i = 0; i < rule.getGameBoard().totalBallCount(); ++i)
      if (balls[i] != null)
        if (connections.isInAChain(i))
          toEliminate.add(i);

    // Eliminate
    eliminate(toEliminate);
    return true;
  }

  /**
   * Fill the game board.
   * 
   * @return Whether any ball has moved or added.
   */
  private boolean fill() {
    if (rule.getBallFiller() == null)
      return false;
    boolean result = false;
    if (rule.autoRotate())
      autoRotate();
    BallFillerInterface filler = rule.getBallFiller();
    if (filler != null)
      result = filler.fillBalls(balls);
    if (rule.autoRotate())
      autoRotate();
    return result;
  }

  /**
   * Rotate balls to the correct place.
   */
  private void autoRotate() {
    GameBoardInterface gameBoard = rule.getGameBoard();
    Vector<Vector<Integer>> chains = gameBoard.chains();
    for (int i = 0; i < chains.size(); ++i) {
      Vector<Integer> originalChain = chains.elementAt(i);
      int needRotateCount = 0;
      int currentFillPos = originalChain.size() - 1;
      for (int j = currentFillPos; j >= 0; --j) {
        Ball ball = balls[originalChain.elementAt(j)];
        // If the ball doesn't exist
        if (ball == null) {
          ++needRotateCount;
          continue;
        }

        if (ball.getState() == Ball.State.Stable)
          ball.setPosition(gameBoard.ballLogicalPositionOfIndex(originalChain
              .elementAt(j)));

        switch (ball.getState()) {
        case UserMoving:
          // Interrupt the user's gesture if any of the ball moving by user
          // should
          if (needRotateCount != 0 && rule.getGestureController() != null)
            rule.getGestureController().interrupt();
        case UserReleased:
          // A stable ball
        case Stable:
          // An almost stable ball
        case AlmostStable:
          // A ball moved by the system
        case SystemMoving:
          if (needRotateCount != 0)
          {
            // Current ball index
            int current = originalChain.elementAt(j);
            // Target position index
            int target = originalChain.elementAt(j + needRotateCount);
            // Rotate the ball to the target position
            rotateABallTo(ball, gameBoard.ballLogicalPositionOfIndex(target),
                gameBoard.centerPosition(), 5, true, i);
            // Set the balls
            balls[target] = balls[current];
            balls[current] = null;
          }
          break;
        case JustCreated: {
          // Start position index
          int current = originalChain.elementAt(0);
          // Target position index
          int target = originalChain.elementAt(j + needRotateCount);
          // Set the balls to the start position
          ball.setPosition(gameBoard.ballLogicalPositionOfIndex(current));
          // Rotate the ball to the target position
          rotateABallTo(ball, gameBoard.ballLogicalPositionOfIndex(target),
              gameBoard.centerPosition(), 10, true, i);
          ball.setState(Ball.State.SystemMoving);
        }
        }
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.CoreControllerInterface#rotateABallTo(Ball, Point,
   * Point, int, boolean, int)
   */
  @Override
  public void rotateABallTo(Ball ball, Point toPos, Point centerPos, int steps,
      boolean forceFillDirection, int distance) {
    if (ball == null)
      return;

    Point lastPos = ball.getPosition();
    if (!ball.getStopPositions().isEmpty())
      lastPos = ball.getStopPositions().elementAt(0);
    if (lastPos.distance(toPos) < 20 && ball.getStopPositions().isEmpty()) {
      ball.setPosition(toPos);
      ball.setState(Ball.State.Stable);
      return;
    }

    // Some complex calculation to calculate the positions
    // the ball should be at

    GameBoardInterface gameBoard = rule.getGameBoard();

    // The distance from the center
    double maxR = gameBoard.intervalBetweenTwoLayers();
    Vector<Point> stopPositions = new Vector<Point>();
    ball.setState(Ball.State.SystemMoving);
    Point fromPos = ball.getPosition();
    double currentA = MathAid.angle(fromPos, centerPos);
    double finalA = MathAid.angle(toPos, centerPos);

    double tmp = currentA + MathAid.PI;
    if (tmp > 2 * MathAid.PI)
      tmp -= 2 * MathAid.PI;

    double clockwiseAngleDis = currentA - finalA;
    if (clockwiseAngleDis < 0)
      clockwiseAngleDis += 2 * MathAid.PI;

    boolean clockwise = forceFillDirection || (clockwiseAngleDis < MathAid.PI);

    double angleDis = clockwiseAngleDis;
    angleDis = clockwise ? clockwiseAngleDis : 2 * MathAid.PI
        - clockwiseAngleDis;

    stopPositions.add(toPos);
    int j = steps - 1;
    for (int i = 1; i < steps; ++i, --j) {
      // Calculate the angle and the distance and set the
      // position
      double tmpA = currentA + (angleDis * j / (steps - 1))
          * (clockwise ? -1 : 1);
      double tmpR = distance
          * MathAid.distanceFromTheCenterWithTheAngle(tmpA, maxR);
      stopPositions.add(MathAid.calculatePosition(tmpA, tmpR, centerPos));
    }
    ball.setStopPositions(stopPositions);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.CoreControllerInterface#translateABallTo(Ball,
   * Point, int, boolean)
   */
  @Override
  public void translateABallTo(Ball ball, Point toPos, int steps, boolean plain) {
    Vector<Point> stopPositions = new Vector<Point>();

    if (ball == null)
      return;

    // Some simple calculation to calculate the positions
    // the ball should be at
    Point fromPos = ball.getPosition();
    double fromX = fromPos.getX();
    double fromY = fromPos.getY();
    double toX = toPos.getX();
    double toY = toPos.getY();

    double dis = fromPos.distance(toPos);

    for (int i = 0; i < steps; ++i) {
      double dy = 0;
      if (!plain)
        dy = dis * 3 * MathAid.bridgeY(1.0 * (steps - i) / steps);
      stopPositions.add(new Point(
          (int) ((fromX * i + toX * (steps - i)) / steps),
          (int) ((fromY * i + toY * (steps - i)) / steps - dy)));
    }

    ball.setStopPositions(stopPositions);
  }
}
