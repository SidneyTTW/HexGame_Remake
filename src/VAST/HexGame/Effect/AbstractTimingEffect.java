/**
 * 
 */
package VAST.HexGame.Effect;

/**
 * Abstract class of timing effect.
 * 
 * @author SidneyTTW
 * 
 */
public abstract class AbstractTimingEffect extends AbstractEffect {
  /*
   * (non-Javadoc)
   * 
   * @see VAST.HexGame.Effect.AbstractEffect#advance()
   */
  @Override
  public boolean advance() {
    AbstractTimingEffectPrivateInfo myInfo = (AbstractTimingEffectPrivateInfo) info;
    myInfo.setAge(myInfo.getAge() + 1);
    return myInfo.getAge() <= myInfo.getLimit();
  }

  /**
   * Abstract class of information of timing effect.
   */
  public abstract class AbstractTimingEffectPrivateInfo extends
      AbstractEffectPrivateInfo {
    /**
     * The age of effect.
     */
    protected int age;

    /**
     * The limit age of effect.
     */
    protected int ageLimit;

    /**
     * Get the age of effect.
     */
    public int getAge()
    {return age;}

    /**
     * Set the age of effect.
     */
    public void setAge(int age)
    {this.age = age;}

    /**
     * Get the limit age of effect.
     */
    public int getLimit()
    {return ageLimit;}

    /**
     * Set the limit age of effect.
     */
    public void setLimit(int ageLimit)
    {this.ageLimit = ageLimit;}
  }

}
