/**
 * 
 */
package VAST.HexGame.Game;

/**
 * Class of standard game rule.
 * 
 * @author SidneyTTW
 * 
 */
public class StandardGameRule implements GameRuleInterface {

  private ConnectionCalculatorInterface connectionCalculator;

  private CoreControllerInterface coreController;

  private BallFillerInterface ballFiller;

  private GameBoardInterface gameBoard;

  private GestureControllerInterface gestureController;

  private GameEffectAdapter gameEffectAdapter;

  private boolean _rollBack;

  private boolean _autoRotate;

  private boolean _endlessFill;
  
  public StandardGameRule() {}

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameRuleInterface#getConnectionCalculator()
   */
  @Override
  public ConnectionCalculatorInterface getConnectionCalculator() {
    return connectionCalculator;
  }

  public void setConnectionCalculator(
      ConnectionCalculatorInterface connectionCalculator) {
    this.connectionCalculator = connectionCalculator;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameRuleInterface#getCoreController()
   */
  @Override
  public CoreControllerInterface getCoreController() {
    return coreController;
  }

  public void setCoreController(CoreControllerInterface coreController) {
    this.coreController = coreController;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameRuleInterface#getBallFiller()
   */
  @Override
  public BallFillerInterface getBallFiller() {
    return ballFiller;
  }

  public void setBallFiller(BallFillerInterface ballFiller) {
    this.ballFiller = ballFiller;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameRuleInterface#getGameBoard()
   */
  @Override
  public GameBoardInterface getGameBoard() {
    return gameBoard;
  }

  public void setGameBoard(GameBoardInterface gameBoard) {
    this.gameBoard = gameBoard;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameRuleInterface#getGestureController()
   */
  @Override
  public GestureControllerInterface getGestureController() {
    return gestureController;
  }

  public void setGestureController(GestureControllerInterface gestureController) {
    this.gestureController = gestureController;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameRuleInterface#getEffectPainter()
   */
  @Override
  public GameEffectAdapter getEffectPainter() {
    return gameEffectAdapter;
  }

  public void setEffectPainter(GameEffectAdapter gameEffectAdapter) {
    this.gameEffectAdapter = gameEffectAdapter;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameRuleInterface#rollBackWhenNoConnection()
   */
  @Override
  public boolean rollBackWhenNoConnection() {
    return _rollBack;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameRuleInterface#autoRotate()
   */
  @Override
  public boolean autoRotate() {
    return _autoRotate;
  }

  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Game.GameRuleInterface#endlessFill()
   */
  @Override
  public boolean endlessFill() {
    return _endlessFill;
  }

  public void setSomeBooleans(boolean rollBack, boolean autoRotate,
      boolean endlessFill) {
    _rollBack = rollBack;
    _autoRotate = autoRotate;
    _endlessFill = endlessFill;
  }

}
