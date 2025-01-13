package cgfx;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import cgfx.Board;
import cgfx.Pos;

public class BoardZones {

	public abstract class Zone {
		private Object data;
		
		public Zone() {
			this(null);
		}
		
		public Zone(Object data) {
			this.data = data;
		}

		public abstract boolean isIn(Pos pos);
		
		public void changeData(Object newData) {
			data = newData;
		}
		public Object data() {
			return data;
		}
	}
	
	public class Rectangle extends Zone {
		
		private Pos from;
		private Pos to;

		public Rectangle(Pos from, Pos to) {
			this(from, to , null);
		}
		public Rectangle(Pos from, Pos to, Object data) {
			super(data);
			this.from = from;
			this.to = to;
		}
		
		@Override
		public boolean isIn(Pos pos) {
			return pos.x >= from.x && pos.y >= from.y && pos.x <= to.x && pos.y <= to.y;
		}
	}
	
	private List<Zone> zones = new ArrayList<>();
	
	public BoardZones(Board board, BiConsumer<Zone, Pos> runnableInZone) {
		this(board, runnableInZone, null);
	}
	
  public BoardZones(Board board, BiConsumer<Zone, Pos> runnableInZone, Runnable onExitAll) {
		board.getCanvas().setOnMouseMoved(mv -> {
			Pos mousePos = Pos.from(mv.getX(), mv.getY());
			for (Zone zone : zones) {
				if (zone.isIn(mousePos)) {
					runnableInZone.accept(zone, mousePos);
					return;
				}
			}
			if (onExitAll != null) onExitAll.run();
    });
	}
	
	
	public void clearZones() {
		zones.clear();
	}
	
	public void addZone(Zone zone) {
		zones.add(zone);
	}
	
}
