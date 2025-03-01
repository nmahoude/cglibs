package trigonometry;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

public class CircleTest {

  @Test
  public void cirleSegmentIntersection() throws Exception {
    Circle c = new Circle(new Point(0,0), 100);
    
    List<Point> points = c.getIntersectingPoints(0, 0);
    
    assertThat(points.size()).isEqualTo(2);
    assertThat(points).contains(new Point(100,0));
    assertThat(points).contains(new Point(-100,0));
  }
  
  @Test
  public void cirleSegmentIntersection_tangentY100() throws Exception {
    Circle c = new Circle(new Point(0,0), 100);
    
    List<Point> points = c.getIntersectingPoints(0, 100);
    
    assertThat(points.size()).isEqualTo(1);
    assertThat(points).contains(new Point(-0.0,100));
  }
  
  @Test
  public void cirleSegmentIntersection_noSolution() throws Exception {
    Circle c = new Circle(new Point(0,0), 50);
    
    List<Point> points = c.getIntersectingPoints(0, 100);
    
    assertThat(points.size()).isEqualTo(0);
  }
  
  @Test
  public void cirleSegmentIntersection_tangentYMinus100() throws Exception {
    Circle c = new Circle(new Point(0,0), 100);
    
    List<Point> points = c.getIntersectingPoints(0, -100);
    
    assertThat(points.size()).isEqualTo(1);
    assertThat(points).contains(new Point(0.0,-100));
  }
  
  @Test
  public void cirleSegmentIntersection_semiHeight() throws Exception {
    Circle c = new Circle(new Point(0,0), 100);
    
    List<Point> points = c.getIntersectingPoints(0, 50);
    
    assertThat(points.size()).isEqualTo(2);
    assertThat(points).contains(new Point( 86.60254037844386,50));
    assertThat(points).contains(new Point(-86.60254037844386,50));
  }
  
  @Test
  public void pointVectorIntersection() throws Exception {
    Circle c = new Circle(new Point(0,0), 100);
    
    List<Point> points = c.getIntersectingPoints(new Point(0,50), new Vector(50,0));
    
    assertThat(points.size()).isEqualTo(2);
    assertThat(points).contains(new Point( 86.60254037844386,50));
    assertThat(points).contains(new Point(-86.60254037844386,50));
  }
  
  @Test
  public void pointVectorIntersection_vertical() throws Exception {
    Circle c = new Circle(new Point(0,0), 100);
    
    List<Point> points = c.getIntersectingPoints(new Point(-200, 0), new Vector(200, 0));
    
    assertThat(points.size()).isEqualTo(2);
    assertThat(points).contains(new Point( -100, 0));
    assertThat(points).contains(new Point( 100, 0));
  }
  
}