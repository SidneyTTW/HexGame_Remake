/**
 * 
 */
package VAST.HexGame.Game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.Vector;

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

  private static Vector<Image> backgroundImages = new Vector<Image>();

  private static final String ballFolder = "j:/tmp/images/balls";

  private static final String[] ballFiles = { "red0*.png", "blue0*.png",
      "green0*.png", "yellow0*.png", "purple0*.png", "white0*.png" };

  private static final String backgroundFolder = "j:/tmp/images/backgrounds";

  private static final String[] backgroundFiles = { "mainmenubackground.png",
      "puzzlemenubackground.png", "mainmenubackground.png",
      "37gamebackground.png", "61gamebackground.png",
      "helpbackgroundwithbutton.png", "achievementbackground.png",
      "37twoplayergamebackground.png", "mainselectplayersbackground.png" };

  private static void initBallsImages() {
    ImageAid.loadFromFile(ballFolder, ballFiles, ballsImages, ballsFrameCounts);
  }

  private static void initBackgroundImages() {
    backgroundImages = ImageAid.loadFromFile(backgroundFolder, backgroundFiles);
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

    // TODO
    // Initialize lock if necessary
    // if (ballsLockPixmaps.isEmpty())
    // {
    // // A value which won't be used
    // int tmp;
    // initPixmaps(":/images/balls/lock*.png",
    // ballsLockPixmaps,
    // tmp);
    //
    // }

    // For each ball

    Vector<Vector<Integer>> chains = gameBoard.chains();
    for (int i = 0; i < chains.size(); ++i) {
      Vector<Integer> originalChain = chains.elementAt(i);
      for (int j = 0; j < originalChain.size(); ++j) {
        int index = originalChain.elementAt(j);

        // If the ball exists
        if (balls[index] != null) {
          // Get the color
          int colorIndex = balls[index].getColor();
          // Get the image
          Image image = ballsImages.elementAt(colorIndex).elementAt(
              frame % ballsFrameCounts.elementAt(colorIndex));
          // Get the position of the ball
          Point pos = balls[index].getPosition();
          
          if (image == null)
            continue;
          
          // Draw the image
          ImageAid.drawImageAt(graphics, image, 1.0, 1.0, pos, true, true);

          // TODO
          // // If the ball is locked
          // if (balls[i].isLocked())
          // {
          // // Get the image
          // const QPixmap& p2 = ballsLockPixmaps
          // [frame % ballsLockPixmaps.size()];
          //
          // // Draw the image
          // drawPixmapAt(painter,
          // p2,
          // size / p2.width() * xRate * 0.75,
          // size / p2.height() * yRate * 0.75,
          // pos,
          // true,
          // true);
          // }
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
    if (id < 0 || id >= backgroundFolder.length())
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
