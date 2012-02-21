/**
 * 
 */
package VAST.HexGame.Game;

import java.util.Vector;

import AidPackage.MyPoint;

/**
 * A class which tells us the information of a game board with 61 balls.
 * 
 * @author SidneyTTW
 * 
 */
public class SixtyOneGameBoard implements GameBoardInterface {
  /**
   * Total chain(circle) number.
   */
  private static final int CHAIN_NUMBER = 5;

  /**
   * Total item number.
   */
  private static final int TOTAL_ITEM_NUMBER = 61;

  /**
   * Total row number.
   */
  private static final int ROW_NUMBER = (CHAIN_NUMBER * 2 - 1);

  /**
   * Total column number.
   */
  private static final int COLUMN_NUMBER = (2 * ROW_NUMBER - 1);

  /**
   * The width of the item.
   */
  private static final int ITEM_SIZE = 45;

  /**
   * The width of the item in a gesture.
   */
  private static final double ITEM_GESTURE_R = (ITEM_SIZE * 0.8);

  /**
   * The absolute position of game(I must draw a picture~~~)
   */
  private static final double LOCATION_GAME_VIEW_X_FROM = 0;

  /**
   * The absolute position of game(I must draw a picture~~~)
   */
  private static final double LOCATION_GAME_VIEW_Y_FROM = 0;

  /**
   * The absolute position of game(I must draw a picture~~~)
   */
  private static final double LOCATION_GAME_VIEW_X_TO = 1024;

  /**
   * The absolute position of game(I must draw a picture~~~)
   */
  private static final double LOCATION_GAME_VIEW_Y_TO = 600;

  /**
   * The absolute position of game(I must draw a picture~~~)
   */
  private static final double LOCATION_GAME_BOARD_ITEM_X_FROM = 392.0;

  /**
   * The absolute position of game(I must draw a picture~~~)
   */
  private static final double LOCATION_GAME_BOARD_ITEM_Y_FROM = 74.0;

  /**
   * The absolute position of game(I must draw a picture~~~)
   */
  private static final double LOCATION_GAME_BOARD_ITEM_X_TO = 914.0;

  /**
   * The absolute position of game(I must draw a picture~~~)
   */
  private static final double LOCATION_GAME_BOARD_ITEM_Y_TO = 529.0;

  /**
   * The interval of items in X direction.
   */
  private static final double LOCATION_GAME_BOARD_ITEM_X_INTERVAL = ((LOCATION_GAME_BOARD_ITEM_X_TO - LOCATION_GAME_BOARD_ITEM_X_FROM) / (COLUMN_NUMBER - 1));

  /**
   * The interval of items in Y direction.
   */
  private static final double LOCATION_GAME_BOARD_ITEM_Y_INTERVAL = ((LOCATION_GAME_BOARD_ITEM_Y_TO - LOCATION_GAME_BOARD_ITEM_Y_FROM) / (ROW_NUMBER - 1));

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameBoardInterface#getWidth()
   */
  @Override
  public int getWidth() {
    return (int) (LOCATION_GAME_VIEW_X_TO - LOCATION_GAME_VIEW_X_FROM);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameBoardInterface#getHeight()
   */
  @Override
  public int getHeight() {
    return (int) (LOCATION_GAME_VIEW_Y_TO - LOCATION_GAME_VIEW_Y_FROM);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameBoardInterface#getBallRadius()
   */
  @Override
  public int getBallRadius() {
    return ITEM_SIZE / 2;
  }

  private static final int col[] = { 4, 6, 8, 10, 12, 3, 5, 7, 9, 11, 13, 2, 4,
      6, 8, 10, 12, 14, 1, 3, 5, 7, 9, 11, 13, 15, 0, 2, 4, 6, 8, 10, 12, 14,
      16, 1, 3, 5, 7, 9, 11, 13, 15, 2, 4, 6, 8, 10, 12, 14, 3, 5, 7, 9, 11,
      13, 4, 6, 8, 10, 12 };

  /**
   * The column number of the item with the index.
   * 
   * @param index
   *          Index of the ball.
   * @return Column of the ball.
   */
  private int columnOfIndex(int index) {
    if (index < 0 || index >= TOTAL_ITEM_NUMBER)
      return 0;
    return col[index];
  }

  private static final int row[] = { 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 2, 2, 2,
      2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5,
      5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8 };

  /**
   * The row number of the item with the index.
   * 
   * @param index
   *          Index of the ball.
   * @return Column of the ball.
   */
  private int rowOfIndex(int index) {
    if (index < 0 || index >= TOTAL_ITEM_NUMBER)
      return 0;
    return row[index];
  }

  private static final int itemCountInAChain[] = { 1, 6, 12, 18, 24 };

  private static final int originalChain[] = { 30, 29, 21, 22, 31, 39, 38, 28,
      20, 13, 14, 15, 23, 32, 40, 47, 46, 45, 37, 27, 19, 12, 6, 7, 8, 9, 16,
      24, 33, 41, 48, 54, 53, 52, 51, 44, 36, 26, 18, 11, 5, 0, 1, 2, 3, 4, 10,
      17, 25, 34, 42, 49, 55, 60, 59, 58, 57, 56, 50, 43, 35 };

  // The index of the item at the position of the scene
  private static final int positionToIndex[] = { -1, -1, -1, -1, 0, 0, 1, 1, 2,
      2, 3, 3, 4, 4, -1, -1, -1, -1, -1, -1, -1, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9,
      10, 10, -1, -1, -1, -1, -1, 11, 11, 12, 12, 13, 13, 14, 14, 15, 15, 16,
      16, 17, 17, -1, -1, -1, 18, 18, 19, 19, 20, 20, 21, 21, 22, 22, 23, 23,
      24, 24, 25, 25, -1, 26, 26, 27, 27, 28, 28, 29, 29, 30, 30, 31, 31, 32,
      32, 33, 33, 34, 34, -1, 35, 35, 36, 36, 37, 37, 38, 38, 39, 39, 40, 40,
      41, 41, 42, 42, -1, -1, -1, 43, 43, 44, 44, 45, 45, 46, 46, 47, 47, 48,
      48, 49, 49, -1, -1, -1, -1, -1, 50, 50, 51, 51, 52, 52, 53, 53, 54, 54,
      55, 55, -1, -1, -1, -1, -1, -1, -1, 56, 56, 57, 57, 58, 58, 59, 59, 60,
      60, -1, -1, -1, -1 };

  /*
   * (non-Javadoc)
   * 
   * @see
   * VAST.HexGame.Game.GameBoardInterface#ballIndexAtLogicalPosition(java.awt
   * .Point)
   */
  @Override
  public int ballIndexAtLogicalPosition(MyPoint position) {
    int c = (int) Math
        .floor((position.x + LOCATION_GAME_BOARD_ITEM_X_INTERVAL - LOCATION_GAME_BOARD_ITEM_X_FROM)
            / LOCATION_GAME_BOARD_ITEM_X_INTERVAL);
    int r = (int) Math.floor((position.y + LOCATION_GAME_BOARD_ITEM_Y_INTERVAL
        / 2 - LOCATION_GAME_BOARD_ITEM_Y_FROM)
        / LOCATION_GAME_BOARD_ITEM_Y_INTERVAL);
    if (c < 0 || c > COLUMN_NUMBER || r < 0 || r >= ROW_NUMBER)
      return -1;
    int index = positionToIndex[r * (COLUMN_NUMBER + 1) + c];
    if (position.distance(ballLogicalPositionOfIndex(index)) > ITEM_GESTURE_R)
      return -1;
    return index;
  }

  /**
   * Position of index.
   */
  Vector<MyPoint> positionOfIndex = new Vector<MyPoint>();

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameBoardInterface#ballLogicalPositionOfIndex(int)
   */
  @Override
  public MyPoint ballLogicalPositionOfIndex(int index) {
    if (positionOfIndex.isEmpty()) {
      for (int i = 0; i < TOTAL_ITEM_NUMBER; ++i) {
        int r = rowOfIndex(i);
        int c = columnOfIndex(i);

        double x = LOCATION_GAME_BOARD_ITEM_X_FROM + c
            * LOCATION_GAME_BOARD_ITEM_X_INTERVAL;
        double y = LOCATION_GAME_BOARD_ITEM_Y_FROM + r
            * LOCATION_GAME_BOARD_ITEM_Y_INTERVAL;
        positionOfIndex.add(new MyPoint((int) x, (int) y));
      }
    }
    if (index < 0 || index >= TOTAL_ITEM_NUMBER)
      return new MyPoint();
    return positionOfIndex.elementAt(index);
  }

  private static final int indexToLeftDown[] = { 5, 6, 7, 8, 9, 11, 12, 13, 14,
      15, 16, 18, 19, 20, 21, 22, 23, 24, 26, 27, 28, 29, 30, 31, 32, 33, -1,
      35, 36, 37, 38, 39, 40, 41, 42, -1, 43, 44, 45, 46, 47, 48, 49, -1, 50,
      51, 52, 53, 54, 55, -1, 56, 57, 58, 59, 60, -1, -1, -1, -1, -1 };

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameBoardInterface#leftDownIndex(int)
   */
  @Override
  public int leftDownIndex(int index) {
    if (index < 0 || index >= TOTAL_ITEM_NUMBER)
      return -1;
    return indexToLeftDown[index];
  }

  private static final int indexToRightDown[] = { 6, 7, 8, 9, 10, 12, 13, 14,
      15, 16, 17, 19, 20, 21, 22, 23, 24, 25, 27, 28, 29, 30, 31, 32, 33, 34,
      35, 36, 37, 38, 39, 40, 41, 42, -1, 43, 44, 45, 46, 47, 48, 49, -1, 50,
      51, 52, 53, 54, 55, -1, 56, 57, 58, 59, 60, -1, -1, -1, -1, -1, -1 };

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameBoardInterface#rightDownIndex(int)
   */
  @Override
  public int rightDownIndex(int index) {
    if (index < 0 || index >= TOTAL_ITEM_NUMBER)
      return -1;
    return indexToRightDown[index];
  }

  private static final int indexToRight[] = { 1, 2, 3, 4, -1, 6, 7, 8, 9, 10,
      -1, 12, 13, 14, 15, 16, 17, -1, 19, 20, 21, 22, 23, 24, 25, -1, 27, 28,
      29, 30, 31, 32, 33, 34, -1, 36, 37, 38, 39, 40, 41, 42, -1, 44, 45, 46,
      47, 48, 49, -1, 51, 52, 53, 54, 55, -1, 57, 58, 59, 60, -1 };

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameBoardInterface#rightIndex(int)
   */
  @Override
  public int rightIndex(int index) {
    if (index < 0 || index >= TOTAL_ITEM_NUMBER)
      return -1;
    return indexToRight[index];
  }

  private static final int indexToRightUp[] = { -1, -1, -1, -1, -1, 0, 1, 2, 3,
      4, -1, 5, 6, 7, 8, 9, 10, -1, 11, 12, 13, 14, 15, 16, 17, -1, 18, 19, 20,
      21, 22, 23, 24, 25, -1, 27, 28, 29, 30, 31, 32, 33, 34, 36, 37, 38, 39,
      40, 41, 42, 44, 45, 46, 47, 48, 49, 51, 52, 53, 54, 55 };

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameBoardInterface#rightUpIndex(int)
   */
  @Override
  public int rightUpIndex(int index) {
    if (index < 0 || index >= TOTAL_ITEM_NUMBER)
      return -1;
    return indexToRightUp[index];
  }

  private static final int indexToLeftUp[] = { -1, -1, -1, -1, -1, -1, 0, 1, 2,
      3, 4, -1, 5, 6, 7, 8, 9, 10, -1, 11, 12, 13, 14, 15, 16, 17, -1, 18, 19,
      20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 35, 36, 37, 38,
      39, 40, 41, 43, 44, 45, 46, 47, 48, 50, 51, 52, 53, 54 };

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameBoardInterface#leftUpIndex(int)
   */
  @Override
  public int leftUpIndex(int index) {
    if (index < 0 || index >= TOTAL_ITEM_NUMBER)
      return -1;
    return indexToLeftUp[index];
  }

  private static final int indexToLeft[] = { -1, 0, 1, 2, 3, -1, 5, 6, 7, 8, 9,
      -1, 11, 12, 13, 14, 15, 16, -1, 18, 19, 20, 21, 22, 23, 24, -1, 26, 27,
      28, 29, 30, 31, 32, 33, -1, 35, 36, 37, 38, 39, 40, 41, -1, 43, 44, 45,
      46, 47, 48, -1, 50, 51, 52, 53, 54, -1, 56, 57, 58, 59, };

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameBoardInterface#leftIndex(int)
   */
  @Override
  public int leftIndex(int index) {
    if (index < 0 || index >= TOTAL_ITEM_NUMBER)
      return -1;
    return indexToLeft[index];
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameBoardInterface#nearbyIndex(int, int)
   */
  @Override
  public int nearbyIndex(int index, int direction) {
    if (direction < 0 || direction > 5)
      return -1;
    switch (direction) {
    case 0:
      return leftDownIndex(index);
    case 1:
      return rightDownIndex(index);
    case 2:
      return rightIndex(index);
    case 3:
      return rightUpIndex(index);
    case 4:
      return leftUpIndex(index);
    case 5:
      return leftIndex(index);
    default:
      return -1;
    }
  }

  // The chains to reload items(from inner to outter)
  private Vector<Vector<Integer>> _chains = new Vector<Vector<Integer>>();

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameBoardInterface#chains()
   */
  @Override
  public Vector<Vector<Integer>> chains() {
    if (_chains.isEmpty()) {
      int currentIndexInOriginalChain = 0;
      for (int i = 0; i < CHAIN_NUMBER; ++i) {
        Vector<Integer> currentChain = new Vector<Integer>();
        for (int j = 0; j < itemCountInAChain[i]; ++j)
          currentChain.add(originalChain[currentIndexInOriginalChain++]);
        _chains.add(currentChain);
      }
    }
    return _chains;
  }

  // Indexes around the index
  // At most 6 indexes
  // The order is left, leftUp, rightUp, right, rightDown, leftDown
  private Vector<Vector<Integer>> _chainAroundIndex = new Vector<Vector<Integer>>();

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameBoardInterface#chainAroundIndex(int)
   */
  @Override
  public Vector<Integer> chainAroundIndex(int index) {
    if (index < 0 || index >= TOTAL_ITEM_NUMBER)
      return new Vector<Integer>();
    if (_chainAroundIndex.isEmpty())
      for (int i = 0; i < TOTAL_ITEM_NUMBER; ++i)
        _chainAroundIndex.add(new Vector<Integer>());
    if (_chainAroundIndex.elementAt(index).isEmpty()) {
      Vector<Integer> result = _chainAroundIndex.elementAt(index);
      int tmp = leftUpIndex(index);
      if (tmp >= 0)
        result.add(tmp);
      tmp = rightUpIndex(index);
      if (tmp >= 0)
        result.add(tmp);
      tmp = rightIndex(index);
      if (tmp >= 0)
        result.add(tmp);
      tmp = rightDownIndex(index);
      if (tmp >= 0)
        result.add(tmp);
      tmp = leftDownIndex(index);
      if (tmp >= 0)
        result.add(tmp);
      tmp = leftIndex(index);
      if (tmp >= 0)
        result.add(tmp);
    }
    return _chainAroundIndex.elementAt(index);
  }

  // Whether the index can be a center to rotate
  private static final int indexToCanBeRotateCenter[] = { 0, 0, 0, 0, 0, 0, 1,
      1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1,
      1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1,
      0, 0, 0, 0, 0, 0 };

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameBoardInterface#canBeRotateCenter(int)
   */
  @Override
  public boolean canBeRotateCenter(int index) {
    if (index < 0 || index >= TOTAL_ITEM_NUMBER)
      return false;
    return indexToCanBeRotateCenter[index] == 1;
  }

  // Whether the index is a joint of the chains(circles)
  private static final int indexToIsJoint[] = { 1, 0, 0, 0, 1, 0, 1, 0, 0, 1,
      0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1,
      1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0,
      0, 0, 1 };

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameBoardInterface#isJoint(int)
   */
  @Override
  public boolean isJoint(int index) {
    if (index < 0 || index >= TOTAL_ITEM_NUMBER)
      return false;
    return indexToIsJoint[index] == 1;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameBoardInterface#centerPosition()
   */
  @Override
  public MyPoint centerPosition() {
    return ballLogicalPositionOfIndex(originalChain[0]);
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameBoardInterface#totalBallCount()
   */
  @Override
  public int totalBallCount() {
    return TOTAL_ITEM_NUMBER;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameBoardInterface#intervalBetweenTwoLayers()
   */
  @Override
  public int intervalBetweenTwoLayers() {
    return (int) (LOCATION_GAME_BOARD_ITEM_X_INTERVAL * 2);
  }
}
