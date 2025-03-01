package cgfx;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.function.Consumer;

/**
 * Represents a cell board (like chess)
 * 
 * all cells are 128x128 ! you need to change the scale to fit your screen
 * It allows to make a single viewer that can be zoom in (exemple : 4 viewers in an application)
 * 
 * 
 * 
 * @author nmahoude
 *
 */
public class CellBoard extends Board {

  public static final int CELL_SIZE = 128;
  public static final int HALF_CELL_SIZE = CELL_SIZE / 2;
  
  private static final int DECAL_FOR_COORDINATES = 32;
  
	private Decal boardDecal;
	private boolean showCoordinates;

    private int currentCellX = -1, currentCellY = -1;
    private Consumer<Cell> onCellEnter = c -> {};
    private Consumer<Cell> onCellExit = c -> {};


	public CellBoard(Group parent, double scale, int width, int height) {
		this(parent, scale, width, height, false);
	}

	public CellBoard(Group parent, double scale, int width, int height, boolean showCoordinates) {
		this(parent, scale, width, height, showCoordinates, Decal.NO);
	}
	public CellBoard(Group parent, double scale, int width, int height, boolean showCoordinates, Decal decal) {
		super(parent, CELL_SIZE * width + (showCoordinates ? DECAL_FOR_COORDINATES+decal.dx : 0), CELL_SIZE * height + (showCoordinates ? DECAL_FOR_COORDINATES+decal.dy : 0), scale);
		this.showCoordinates = showCoordinates;
		
		this.boardDecal = showCoordinates ? Decal.of(DECAL_FOR_COORDINATES, DECAL_FOR_COORDINATES).add(decal) : decal;

        this.getCanvas().setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int cellx = (int)(event.getX() / CELL_SIZE / scale);
                int celly = (int)(event.getY() / CELL_SIZE / scale);

                if (cellx != currentCellX || celly != currentCellY) {
                    onCellExit.accept(Cell.at(cellx, celly));
                    currentCellX = cellx;
                    currentCellY = celly;
                    onCellEnter.accept(Cell.at(cellx, celly));
                }
            }
        });

	}

    public void setOnCellEnter(Consumer<Cell> consumer) {
        this.onCellEnter = consumer;
    }
    public void setOnCellExit(Consumer<Cell> consumer) {
        this.onCellEnter = consumer;
    }

	@Override
	public void clear() {
		super.clear();
		
		if (showCoordinates) {
			for (int x=0;x<width;x++) {
				this.drawText(Color.BLACK, cgfx.Pos.from(boardDecal.dx + CELL_SIZE/2 + CELL_SIZE* x, boardDecal.dy-2), ""+x);
			}
			for (int y=0;y<height;y++) {
				this.drawText(Color.BLACK, cgfx.Pos.from(0, boardDecal.dy + CELL_SIZE/ 2 + CELL_SIZE* y), ""+y);
			}
		}
	}
	
  public void drawCellAt(Color color, int x, int y, Inset inset) {
    drawCellAt(color, Cell.at(x, y), inset);
  }

  public void drawCellAt(Color color, Cell cell, Inset inset) {
    drawRect(color, Pos.from(boardDecal.dx + CELL_SIZE * cell.x+inset.l, boardDecal.dy + CELL_SIZE * cell.y + inset.l), 
                    Length.of(CELL_SIZE-inset.l*2, CELL_SIZE-inset.l*2));
  }

  public void fillCellAt(Color color, Cell cell, Inset inset) {
    fillRect(color, color, 
        Pos.from(boardDecal.dx + CELL_SIZE * cell.x+inset.l, boardDecal.dy + CELL_SIZE * cell.y + inset.l), 
        Length.of(CELL_SIZE-inset.l*2, CELL_SIZE-inset.l*2));
  }

  public void fillCellAt(Color border, Color inside, Cell cell, Inset inset) {
    fillRect(border, inside,
        Pos.from(boardDecal.dx + CELL_SIZE * cell.x+inset.l, boardDecal.dy + CELL_SIZE * cell.y + inset.l),
        Length.of(CELL_SIZE-inset.l*2, CELL_SIZE-inset.l*2));
  }
  
  public void fillCellAt(Color border, Color inside, Cell cell, Decal decal, Length length) {
    fillRect(border, inside,
            getPosFromCellCoords(cell, decal),
        length);
  }

  public void drawCellText(Color color, Cell cell, String text) {
    drawCellText(color, cell, Decal.NO, text);
  }

  /**
   * @deprecated use the one with Decal instead of Length
   */
  @Deprecated
  public void drawCellText(Color color, Cell cell, Length decal, String text) {
    super.drawText(color, getPosFromCellCoords(cell, Decal.of(decal.dx, decal.dy)), text);
  }

  public void drawCellText(Color color, Cell cell, Decal decal, String text) {
    super.drawText(color, getPosFromCellCoords(cell, decal).add(0,16), text);
  }

    private Pos getPosFromCellCoords(Cell cell, Decal decal) {
        return Pos.from(boardDecal.dx + CELL_SIZE * cell.x + decal.dx, boardDecal.dy + CELL_SIZE * cell.y + decal.dy);
    }

    public void drawCellCircle(Color color, Cell cell) {
    strokeCellCircle(color, cell, Inset.NO, CELL_SIZE / 2);
  }

  public void drawCellCircle(Color color, Cell cell, Inset inset) {
    strokeCellCircle(color, cell, inset, CELL_SIZE / 2);
  }

  private void strokeCellCircle(Color color, Cell cell, Inset inset, int radius) {
    drawCellCircle(color, null, cell, inset, radius);
  }

  public void drawCellCircle(Color color, Color fill, Cell cell, Inset inset, int radius) {
    drawCircle(color, fill, Pos.from(boardDecal.dx + cell.x * CELL_SIZE + CELL_SIZE / 2 + inset.l/4,
    									boardDecal.dy + cell.y * CELL_SIZE + CELL_SIZE / 2 + inset.l/4), 
                      Length.of(0, 0), radius - inset.l);
  }

  public void drawCellCircle(Color color, Color fill, Cell cell, Inset inset) {
    drawCellCircle(color,  fill,  cell, inset, CELL_SIZE / 2);
  }

  public void fillCellCircle(Color color, Cell cell, Inset inset) {
    drawCellCircle(color, color, cell, inset, CELL_SIZE/2);
  }

	public Pos getCellOrigin(Cell cell) {
		return Pos.from(boardDecal.dx + cell.x* CELL_SIZE, boardDecal.dy + cell.y * CELL_SIZE);
	}

    public void drawImage(Cell cell, Brush brush, int bx, int by) {
      var origin = getPosFromCellCoords(cell, Decal.NO);
      var brushOrigin = brush.get(bx, by);
        super.drawImage(origin.x, origin.y,
                CELL_SIZE, CELL_SIZE,
                brush,
                brushOrigin.x,
                brushOrigin.y
                );
    }

}
