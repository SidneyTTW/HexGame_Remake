/**
 * 
 */
package VAST.HexGame.Game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.Vector;

import VAST.HexGame.Aid.SourceManagement;

import AidPackage.ImageAid;

/**
 * A class to do the basic paint. Currently, the functions are all static,
 * because I haven't found anything to store in the painter.
 * 
 * @author SidneyTTW
 * 
 */
public class BasicPainter {
  public static final int MainMenu = 0;
  public static final int PuzzleMenu = 1;
  public static final int StageMenu = 2;
  public static final int Game37 = 3;
  public static final int Game61 = 4;
  public static final int PuzzleGame = 4;
  public static final int Help = 5;
  public static final int Achievement = 6;
  public static final int TwoPlayerGame = 7;
  public static final int MainMenu2 = 8;

  private static Vector<Vector<Image>> ballsImages = new Vector<Vector<Image>>();
  private static Vector<Integer> ballsFrameCounts = new Vector<Integer>();
  private static Vector<Image> lockImages = new Vector<Image>();

  private static Vector<Image> backgroundImages = new Vector<Image>();

  private static void initBallsImages() {
    ImageAid.loadFromFile(SourceManagement.BallFolder,
        SourceManagement.BallFiles, ballsImages, ballsFrameCounts);
  }

  private static void initLockImages() {
    lockImages = ImageAid.loadFromFile(SourceManagement.BallFolder,
        SourceManagement.LockFile);
  }

  private static void initBackgroundImages() {
    backgroundImages = ImageAid.loadFromFile(SourceManagement.BackgroundFolder,
        SourceManagement.BackgroundFiles);
  }

  /**
   * Paint the basic balls.
   * 
   * @param gameBoard
   *          The game board.
   * @param balls
   *          The balls.
   * @param totalCount
   *          Count of the balls.
   * @param graphics
   *          The graphics.
   * @param xRate
   *          The scale in X direction.
   * @param yRate
   *          The scale in Y direction.
   * @param frame
   *          The index of the frame to show.
   */
  public static void paintBasicBalls(GameBoardInterface gameBoard,
      Ball[] balls, Graphics graphics, int frame) {

    // Size of the ball
    double size = gameBoard.getBallRadius() * 2;

    // Initialize balls if necessary
    if (ballsImages.isEmpty())
      initBallsImages();

    // Initialize lock if necessary
    if (lockImages.isEmpty())
      initLockImages();

    // For each ball

    Vector<Vector<Integer>> chains = gameBoard.chains();
    for (int i = 0; i < chains.size(); ++i) {
      Vector<Integer> originalChain = chains.elementAt(i);
      for (int j = 0; j < originalChain.size(); ++j) {
        int index = originalChain.elementAt(j);

        // If the ball exists
        if (balls[index] != null) {
          // Get the position of the ball
          Point pos = balls[index].getPosition();
          
          if (pos.x == 0 && pos.y == 0)
            return;
          
          // Get the color
          int colorIndex = balls[index].getColor();
          // Get the image
          Image image = ballsImages.elementAt(colorIndex).elementAt(
              frame % ballsFrameCounts.elementAt(colorIndex));

          if (image == null)
            continue;

          // Draw the image
          ImageAid.drawImageAt(graphics, image, 1.0, 1.0, pos, true, true);

          // If the ball is locked
          // If the first statement is missed, may cause a null pointer error
          // which may be caused by the multithreading
          if (balls[index] != null && balls[index].isLocked()) {
            image = lockImages.elementAt(frame % lockImages.size());
            ImageAid.drawImageAt(graphics, image, 1.0, 1.0, pos, true, true);
          }
        }
      }
    }
  }

  /**
   * @brief Paint the background.
   * 
   * @param id
   *          The id of the background.
   * @param graphics
   *          The graphics.
   * @param width
   *          The width.
   * @param height
   *          The height.
   * @param frame
   *          The index of the frame to show.
   */
  public static void paintBackGround(int id, Graphics graphics, int width,
      int height, int frame) {
    if (id < 0 || id >= SourceManagement.BackgroundFolder.length())
      return;

    // Initialize backgrounds if necessary
    if (backgroundImages.isEmpty())
      initBackgroundImages();

    // Get the image
    Image image = backgroundImages.elementAt(id);

    if (image == null)
      return;

    // Draw the image
    ImageAid.drawImageAt(graphics, image, 1.0 * width / image.getWidth(null),
        1.0 * height / image.getHeight(null), new Point(0, 0), true, false);
  }

}
