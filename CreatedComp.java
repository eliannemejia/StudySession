package cs3500.pa01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Comparator;

/**
 * Compares two study guide sections based on their creation time
 */
public class CreatedComp implements Comparator<Path> {
  /**
   * Compares its two arguments for order.  Returns a negative integer,
   * zero, or a positive integer as the first argument is less than, equal
   * to, or greater than the second.
   * The implementor must ensure that {@link Integer#signum
   * signum}{@code (compare(x, y)) == -signum(compare(y, x))} for
   * all {@code x} and {@code y}.  (This implies that {@code
   * compare(x, y)} must throw an exception if and only if {@code
   * compare(y, x)} throws an exception.)
   * The implementor must also ensure that the relation is transitive:
   * {@code ((compare(x, y)>0) && (compare(y, z)>0))} implies
   * {@code compare(x, z)>0}.
   * Finally, the implementor must ensure that {@code compare(x,
   * y)==0} implies that {@code signum(compare(x,
   * z))==signum(compare(y, z))} for all {@code z}.
   *
   * @param o1 the first object to be compared.
   * @param o2 the second object to be compared.
   * @return a negative integer, zero, or a positive integer as the
   *     first argument is less than, equal to, or greater than the
   *     second.
   * @throws NullPointerException if an argument is null and this
   *                              comparator does not permit null arguments
   * @throws ClassCastException   if the arguments' types prevent them from
   *                              being compared by this comparator.
   */
  @Override
  public int compare(Path o1, Path o2) {
    BasicFileAttributes o1Att;
    BasicFileAttributes o2Att;
    try {
      o1Att = Files.readAttributes(o1, BasicFileAttributes.class);
      o2Att = Files.readAttributes(o2, BasicFileAttributes.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    FileTime o1Create = o1Att.creationTime();
    FileTime o2Create = o2Att.creationTime();
    return o1Create.compareTo(o2Create);
  }
}
