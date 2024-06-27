package trigonometry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

import org.junit.jupiter.api.Test;

public class TrigonometryTest {

  @Test
  public void createPoint() throws Exception {
    Point p = new Point(1,2);
    assertThat(p.x).isEqualTo(1.0);
    assertThat(p.y).isEqualTo(2.0);
  }
  
  @Test
  public void addPoint() throws Exception {
    Point p1 = new Point(1,2);
    Point p2 = new Point(7,5);
    Point exceptedPoint = new Point(8,7);
    
    Point result = p1.add(p2);
    
    assertThat(result).isEqualTo(exceptedPoint);
  }

  @Test
  public void point_sub() {
    Point p1 = new Point(1,2);
    Point p2 = new Point(7,5);
    
    assertThat(p2.sub(p1)).isEqualTo(new Vector(6,3));
  }
  @Test
  public void point_hash() throws Exception {
    assertThat(new Point(0,0).hashCode()).isEqualTo(new Point(0,0).hashCode());
  }
  
  @Test
  public void vector_hash() throws Exception {
    assertThat(new Vector(0,0).hashCode()).isEqualTo(new Vector(0,0).hashCode());
  }
  
  @Test
  public void vector_rotate90() {
    Vector v = new Vector(1,0);
    Vector rotateV = v.rotate(Math.PI/2);

    assertThat(rotateV.vx).isEqualTo(0, offset(Circle.PRECISION));
    assertThat(rotateV.vy).isEqualTo(1, offset(Circle.PRECISION));
  }
  
  @Test
  public void vector_rotateMinus90() {
    Vector v = new Vector(1,0);
    Vector rotateV = v.rotate(-Math.PI/2);

    assertThat(rotateV.vx).isEqualTo(0, offset(Circle.PRECISION));
    assertThat(rotateV.vy).isEqualTo(-1, offset(Circle.PRECISION));
  }

  @Test
  public void vector_rotate180() {
    Vector v = new Vector(1,0);
    Vector rotateV = v.rotate(Math.PI);

    assertThat(rotateV.vx).isEqualTo(-1, offset(Circle.PRECISION));
    assertThat(rotateV.vy).isEqualTo(0, offset(Circle.PRECISION));
  }

  @Test
  public void addVector() throws Exception {
    Point p1 = new Point(1,2);
    Vector v2 = new Vector(3,4);
    Point exceptedPoint = new Point(4,6);
    
    Point result = p1.add(v2);
    
    assertThat(result).isEqualTo(exceptedPoint);
  }
  
  @Test
  public void distTo_horizontal() throws Exception {
    Point p1 = new Point(1,2);
    Point p2 = new Point(2,2);

    assertThat(p1.distTo(p2)).isEqualTo(1.0);
  }

  @Test
  public void distTo_vertical() throws Exception {
    Point p1 = new Point(1,2);
    Point p2 = new Point(1,3);

    assertThat(p1.distTo(p2)).isEqualTo(1.0);
  }

  @Test
  public void distTo_diagonal() throws Exception {
    Point p1 = new Point(10,10);
    Point p2 = new Point(20,20);

    assertThat(p1.distTo(p2)).isEqualTo(14.142, offset(Circle.PRECISION));
  }

  @Test
  public void distTo_random() throws Exception {
    Point p1 = new Point(5,17);
    Point p2 = new Point(13,97);

    assertThat(p1.distTo(p2)).isEqualTo(80.399, offset(0.001));
  }

  @Test
  public void distTo_mutative() throws Exception {
    Point p1 = new Point(5,17);
    Point p2 = new Point(13,97);

    assertThat(p1.distTo(p2)).isEqualTo(p2.distTo(p1));
  }
  
  @Test
  public void distToALineFrom2points() throws Exception {
    Point p0 = new Point(0,0);
    Point p1 = new Point(1,0);
    Point p2 = new Point(1,1);
    
    assertThat(p0.distTo(p1, p2)).isEqualTo(1.0);
  }
  
  @Test
  public void distToALineFromPointAndVector() throws Exception {
    Point p0 = new Point(0,0);
    
    Point p1 = new Point(1,0);
    Vector v1 = new Vector(0,1);
    
    assertThat(p0.distTo(p1, v1)).isEqualTo(1.0);
  }
  
  @Test
  public void lengthOfVector() throws Exception {
    assertThat(new Vector(3,4).length()).isEqualTo(5.0);
  }
  
  @Test
  public void vector_dot() throws Exception {
    Vector v1 = new Vector(1,2);
    Vector v2 = new Vector(3,1);
    
    assertThat(v1.dot(v2)).isEqualTo(5.0);
  }
  
  @Test
  public void vector_add() throws Exception {
    Vector v1 = new Vector(1,2);
    Vector v2 = new Vector(3,1);
    
    assertThat(v1.add(v2)).isEqualTo(new Vector(4,3));
  }
  @Test
  public void vector_alignAngles() throws Exception {
    Vector v1 = new Vector(1,0);
    Vector v2 = new Vector(1,0);
    
    assertThat(v1.angle(v2)).isEqualTo(0.0);
  }

  @Test
  public void vector_perpendicularsAngles() throws Exception {
    Vector v1 = new Vector(1,0);
    Vector v2 = new Vector(0,1);
    
    assertThat(v1.angle(v2)).isEqualTo(Math.PI / 2.0);
  }

  @Test
  public void vector_opposedAngles() throws Exception {
    Vector v1 = new Vector(1,0);
    Vector v2 = new Vector(-1,0);
    
    assertThat(v1.angle(v2)).isEqualTo(Math.PI);
  }

  @Test
  public void vector_someAngles() throws Exception {
    Vector v1 = new Vector(3,0);
    Vector v2 = new Vector(5,5);
    
    assertThat(v1.angle(v2)).isEqualTo(Math.PI/4, offset(0.001));
  }
  
  @Test
  public void circle_isIn() throws Exception {
    Circle c = new Circle(new Point(0,0), 10);
    
    assertThat(c.isIn(new Point(5,5))).isEqualTo(true);
  }
  
  @Test
  public void circle_isIn_not() throws Exception {
    Circle c = new Circle(new Point(0,0), 10);
    
    assertThat(c.isIn(new Point(10,1))).isEqualTo(false);
  }
  
  @Test
  public void circle_isOn() throws Exception {
    Circle c = new Circle(new Point(0,0), 10);
    
    assertThat(c.isOn(new Point(0,10))).isTrue();
  }
  
  @Test
  public void circle_isOn_not_inside() throws Exception {
    Circle c = new Circle(new Point(0,0), 10);
    
    assertThat(c.isOn(new Point(0,9.9))).isFalse();
  }
  @Test
  public void circle_isOn_not_outside() throws Exception {
    Circle c = new Circle(new Point(0,0), 10);
    
    assertThat(c.isOn(new Point(0,10.1))).isFalse();
  }
}
