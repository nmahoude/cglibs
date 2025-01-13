package cgfx;

import javafx.scene.image.Image;

import java.io.InputStream;

/**
 * an image with sprites to display
 */
public class Brush {
    public final Image brush;
    public final int resolution; // same in x & y

    public Brush(InputStream filename, int resolution) {
         brush = new Image(filename);
        this.resolution = resolution;
    }


    public Image image() {
        return brush;
    }

    public Pos get(int bx, int by) {
        return Pos.from(bx * resolution, by * resolution);
    }
}
