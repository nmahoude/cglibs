package trigonometry;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;


public class SegmentTest {

  
  @Test
  public void pointPoint_pointVector_equivalent() throws Exception {
    Segment segment = new Segment(new Point(100,100), new Point(200,200));
    Segment segment2 = new Segment(new Point(100,100), new Vector(100,100));

    assertThat(segment.p1).isEqualTo(segment2.p1);
    assertThat(segment.p2).isEqualTo(segment2.p2);
  }
  
  @Test
  public void SegmentToCircle_lineintersectButNotSegment() throws Exception {
    Segment segment = new Segment(new Point(0,0), new Point(100,0));
    Circle circle = new Circle(new Point(0,0), 500);
    
    List<Point> intersection = segment.intersection(circle);
    
    assertThat(intersection).hasSize(0);
  }

  @Test
  public void SegmentToCircle_intersect_2() throws Exception {
    Segment segment = new Segment(new Point(-501,0), new Point(501,0));
    Circle circle = new Circle(new Point(0,0), 500);
    
    List<Point> intersection = segment.intersection(circle);
    
    assertThat(intersection).hasSize(2);
    assertThat(intersection).contains(new Point(500,0));
    assertThat(intersection).contains(new Point(-500,0));
  }
  
  
  @Test
  public void reverseCollisionIgnored_fromFB() throws Exception {
    Circle c = new Circle(new Point(8181.0,1588.0), 300);
    Point p = new Point(10059.0,2018.0); 
    Vector v = new Point(16000.0, 3750.0).sub(p);
    Segment segment = new Segment(p, v);
    
    List<Point> intersectingPoints = segment.intersection(c);

    assertThat(intersectingPoints).hasSize(0);
  }
}
