package trigonometry;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class VectorTest {

  @Test
  @Disabled
  public void getInertialPointsIntersection_zeroSolution() throws Exception {
    Vector speed = new Vector(0, 100);
    Vector desiredDirection = new Vector(100, 0);
    double maxForce =  99;
    
    Point[] solutions = Vector.getInertialPointsIntersection(speed, desiredDirection, maxForce);
    
    assertThat(solutions).isNull();
  }

  @Test
  @Disabled
  public void getInertialPointsIntersection_oneSolution() throws Exception {
    Vector speed = new Vector(0, 100);
    Vector desiredDirection = new Vector(100, 0);
    double maxForce =  100;
    
    Point[] solutions = Vector.getInertialPointsIntersection(speed, desiredDirection, maxForce);
    
    assertThat(solutions.length).isEqualTo(1);
  }
  
  @Test
  @Disabled
  public void getInertialPointsIntersection() throws Exception {
    Vector speed = new Vector(0, 100);
    Vector desiredDirection = new Vector(100, 0);
    double maxForce =  141.421356237;
    
    Point[] solutions = Vector.getInertialPointsIntersection(speed, desiredDirection, maxForce);
    
    assertThat(solutions.length).isEqualTo(2);
    assertThat(solutions[0]).isEqualTo(new Point(100, 0));
    assertThat(solutions[1]).isEqualTo(new Point(-100, 0));
  }


}
