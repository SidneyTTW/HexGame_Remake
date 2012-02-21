/**
 * 
 */
package AidPackage;

/**
 * A class to map index to the position and image. Each image may appears
 * repeatedly.
 * 
 * @author SidneyTTW
 * 
 */
public class ImageMapper {
  /**
   * The images stored.
   */
  MyImage[] images;

  /**
   * The positions stored.
   */
  MyPoint[] positions;

  /**
   * The index mapper.
   */
  int[] indexMapper;

  /**
   * Constructor
   * 
   * @param images
   *          The images to store.
   * @param positions
   *          The positions to store.
   * @param indexMapper
   *          The index mapper.
   */
  public ImageMapper(MyImage[] images, MyPoint[] positions, int[] indexMapper) {
    this.images = images;
    this.positions = positions;
    this.indexMapper = indexMapper;
  }

  /**
   * Set the images.
   * 
   * @param images
   *          The images to store.
   */
  public void setImages(MyImage[] images) {
    this.images = images;
  }

  /**
   * Set the positions.
   * 
   * @param positions
   *          The positions to store.
   */
  public void setPositions(MyPoint[] positions) {
    this.positions = positions;
  }

  /**
   * Set the index mapper.
   * 
   * @param indexMapper
   *          The index mapper.
   */
  public void setIndexMapper(int[] indexMapper) {
    this.indexMapper = indexMapper;
  }

  /**
   * Get current image.
   * 
   * @param index
   *          The index.
   * @return The image.
   */
  public MyImage getImage(int index) {
    if (index >= 0 && index < indexMapper.length)
      if (indexMapper[index] >= 0 && indexMapper[index] < images.length)
        return images[indexMapper[index]];
    return null;
  }
  
  /**
   * Get current position.
   * 
   * @param index
   *          The index.
   * @return The position.
   */
  public MyPoint getPosition(int index) {
    if (index >= 0 && index < indexMapper.length)
      return positions[index];
    return null;
  }

}
