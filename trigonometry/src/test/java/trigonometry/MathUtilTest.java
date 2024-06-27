package trigonometry;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class MathUtilTest {
  @Test
  public void secondDegree() throws Exception {
    double[] results = MathUtil.resolve2ndDegree(1, 3, -4);
    assertThat(results.length).isEqualTo(2);
    assertThat(results[0]).isEqualTo(1.0);
    assertThat(results[1]).isEqualTo(-4.0);
  }
}
