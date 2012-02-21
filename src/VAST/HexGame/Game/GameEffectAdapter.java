/**
 * 
 */
package VAST.HexGame.Game;

import AidPackage.ImageAid;
import AidPackage.ImageMapper;
import AidPackage.MyColor;
import AidPackage.MyGraphics;
import AidPackage.MyImage;
import AidPackage.MyPoint;
import AidPackage.MyPolygon;
import AidPackage.MyRectangle;
import VAST.HexGame.Aid.SourceManagement;
import VAST.HexGame.Effect.AbstractEffect;
import VAST.HexGame.Effect.BorderEffect;
import VAST.HexGame.Effect.EffectPainterInterface;
import VAST.HexGame.Effect.ExplosionEffect;
import VAST.HexGame.Effect.FillEffect;
import VAST.HexGame.Effect.FlashEffect;
import VAST.HexGame.Effect.HintEffect;
import VAST.HexGame.Effect.LightningEffect;
import VAST.HexGame.Effect.WordsEffect;

/**
 * An adapter to use EffectPainter in the game to satisfy the needs of effects
 * in games
 * 
 * @author SidneyTTW
 * 
 */
public class GameEffectAdapter {
  /**
   * The real effect painter.
   */
  private EffectPainterInterface effectPainter;

  /**
   * Constructor.
   */
  public GameEffectAdapter(EffectPainterInterface effectPainter) {
    this.effectPainter = effectPainter;
  }

  /**
   * Function to show that the user selected a ball.
   * 
   * @param gameBoard
   *          The game board.
   * @param index
   *          The index of the ball.
   */
  public void selectAt(GameBoardInterface gameBoard, int index) {
    MyPoint center = gameBoard.ballLogicalPositionOfIndex(index);
    int interval = gameBoard.intervalBetweenTwoLayers() / 2;
    MyPolygon border = new MyPolygon();
    MyColor color = new MyColor(255, 0, 0, 150);
    double dy = 0.577 * interval;
    border.addPoint(center.x - interval, (int) (center.y + dy));
    border.addPoint(center.x, (int) (center.y + dy * 2));
    border.addPoint(center.x + interval, (int) (center.y + dy));
    border.addPoint(center.x + interval, (int) (center.y - dy));
    border.addPoint(center.x, (int) (center.y - dy * 2));
    border.addPoint(center.x - interval, (int) (center.y - dy));
    BorderEffect effect = new BorderEffect(border, color, 5);
    effectPainter.addEffect(effect);
  }

  /**
   * Function to clear the effect which shows that the user selected a ball.
   */
  public void clearSelectionHints() {
    effectPainter.clearEffect(AbstractEffect.IdBorder);
  }

  /**
   * Function to show the elimination if user confirm the gesture.
   * 
   * @param gameBoard
   *          The game board.
   * @param index
   *          The index of the ball.
   */
  public void bonusEliminationHintAt(GameBoardInterface gameBoard, int index) {
    userMovingEliminationHintAt(gameBoard, index);
  }

  /**
   * Function to clear the effect which shows the elimination if user confirm
   * the gesture.
   */
  public void clearBonusEliminationHints() {
    effectPainter.clearEffect(AbstractEffect.IdFill);
  }

  /**
   * Function to show the elimination if user confirm the gesture.
   * 
   * @param gameBoard
   *          The game board.
   * @param index
   *          The index of the ball.
   */
  public void userMovingEliminationHintAt(GameBoardInterface gameBoard,
      int index) {
    MyPoint center = gameBoard.ballLogicalPositionOfIndex(index);
    int interval = gameBoard.intervalBetweenTwoLayers() / 2 + 1;
    MyPolygon border = new MyPolygon();
    MyColor color = new MyColor(255, 255, 255, 150);
    double dy = 0.577 * interval;
    border.addPoint(center.x - interval, (int) (center.y + dy));
    border.addPoint(center.x, (int) (center.y + dy * 2));
    border.addPoint(center.x + interval, (int) (center.y + dy));
    border.addPoint(center.x + interval, (int) (center.y - dy));
    border.addPoint(center.x, (int) (center.y - dy * 2));
    border.addPoint(center.x - interval, (int) (center.y - dy));
    FillEffect effect = new FillEffect(border, color);
    effectPainter.addEffect(effect);
  }

  /**
   * Function to clear the effect which shows the elimination if user confirm
   * the gesture.
   */
  public void clearUserMovingEliminationHints() {
    effectPainter.clearEffect(AbstractEffect.IdFill);
  }

  /**
   * Explode at the position.
   * 
   * @param gameBoard
   *          The game board.
   * @param index
   *          The index of the ball.
   */
  public void explodeAt(GameBoardInterface gameBoard, int index) {
    MyPoint center = gameBoard.ballLogicalPositionOfIndex(index);
    int radios = 2 * (gameBoard.getBallRadius() + gameBoard
        .intervalBetweenTwoLayers());
    float[] dist = { 0.0f, 1.0f };
    MyColor[] colors = { new MyColor(255, 255, 255, 200),
        new MyColor(255, 255, 255, 50) };
    ExplosionEffect effect = new ExplosionEffect(center, radios, dist, colors,
        5);
    effectPainter.addEffect(effect);
  }

  private static final float[] dist = { 0.0f, 1.0f };
  private static final MyColor[] colors = { new MyColor(255, 255, 255, 255),
      new MyColor(255, 255, 255, 100) };

  /**
   * Lightning at the position.
   * 
   * @param gameBoard
   *          The game board.
   * @param index
   *          The index of the ball.
   */
  public void lightningAt(GameBoardInterface gameBoard, int index) {
    MyPoint center = gameBoard.ballLogicalPositionOfIndex(index);
    int r = gameBoard.getBallRadius();
    int interval = 6 * gameBoard.intervalBetweenTwoLayers();
    MyPoint[] gradientOffsets = new MyPoint[3];
    MyPoint[] lineOffsets = new MyPoint[3];
    gradientOffsets[0] = new MyPoint((int) (-r / 2 * 1.732), r / 2);
    gradientOffsets[1] = new MyPoint((int) (r / 2 * 1.732), r / 2);
    gradientOffsets[2] = new MyPoint(0, (int) (r));
    lineOffsets[0] = new MyPoint(interval / 2, (int) (interval / 2 * 1.732));
    lineOffsets[1] = new MyPoint(interval / 2, (int) (-interval / 2 * 1.732));
    lineOffsets[2] = new MyPoint(-interval, 0);
    LightningEffect effect = new LightningEffect(center, gradientOffsets,
        lineOffsets, dist, colors, 4);
    effectPainter.addEffect(effect);
  }

  /**
   * Highlight at the position.
   */
  public void highlightAt(GameBoardInterface gameBoard, int index) {
    MyPoint center = gameBoard.ballLogicalPositionOfIndex(index);
    int radios = gameBoard.getBallRadius();
    float[] dist = { 0.0f, 1.0f };
    MyColor[] colors = { new MyColor(255, 255, 255, 200),
        new MyColor(255, 255, 255, 50) };
    ExplosionEffect effect = new ExplosionEffect(center, radios, dist, colors,
        5);
    effectPainter.addEffect(effect);
  }

  /**
   * Show words at the position.
   */
  public void wordsAt(MyPoint pos, String text, int size) {
    WordsEffect effect = new WordsEffect(pos, text, size, 20);
    effectPainter.addEffect(effect);
  }

  /**
   * Flash the whole screen.
   */
  public void flash(MyRectangle area) {
    FlashEffect effect = new FlashEffect(area);
    effectPainter.addEffect(effect);
  }

  private static final int[] dys = { 8, 6, 4, 2, 0, 2, 4, 6, 8, 10, 8, 6, 4, 2,
      0, 2, 4, 6, 8, 10 };

  /**
   * Show a hint at the position.
   */
  public void hintAt(MyPoint pos, int hintType) {
    MyPoint centerPosition = pos.clone();
    MyImage[] image = new MyImage[1];
    image[0] = ImageAid.loadFromFile(SourceManagement.HintFolder, SourceManagement.HintFile[hintType]).elementAt(0);
    MyPoint[] positions = new MyPoint[20];
    int[] indexMapper = new int[20];
    for (int i = 0; i < 20; ++i) {
      positions[i] = new MyPoint(centerPosition.x, centerPosition.y + dys[i]);
      indexMapper[i] = 0;
    }
    ImageMapper imageMapper = new ImageMapper(image, positions, indexMapper);
    HintEffect effect = new HintEffect(imageMapper, 19);
    effectPainter.addEffect(effect);
  }

  /**
   * Advance the effects.
   */
  public void advance() {
    effectPainter.advance();
  }

  /**
   * Paint.
   */
  public void paint(MyGraphics graphics) {
    effectPainter.paint(graphics);
  }
}
