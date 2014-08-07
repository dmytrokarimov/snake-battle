package org.snakebattle.gui.primitive.snake;

import java.awt.Point;

import org.snakebattle.gui.Drawable;
import org.snakebattle.gui.Graph;


public class MindPolyGraph extends Graph {
	private static final long serialVersionUID = -6710178398758312928L;

	public enum OWNER_TYPES {
		SNAKE, ENEMY, NEUTRAL;
	}

	public enum LOGIC_TYPES {
		OR, AND, OR_NOT, AND_NOT, NOT;
		public final static int TYPES_COUNT = 5;
		public boolean compare(boolean b1, boolean b2){
			if (this == OR){
				return b1 || b2;
			}
			if (this == AND){
				return b1 && b2;
			}
			if (this == OR_NOT){
				return b1 || !b2;
			}
			if (this == AND_NOT){
				return b1 && !b2;
			}
			if (this == NOT){
				return !b2;
			}
			return b1;
		}
		public boolean getDefault(){
			if (this == OR){
				return false;
			}
			if (this == AND){
				return true;
			}
			if (this == OR_NOT){
				return true;
			}
			if (this == AND_NOT){
				return false;
			}
			if (this == NOT){
				return false;
			}
			return true;
		}
		public static boolean getDefault(int i){
			LOGIC_TYPES lt = LOGIC_TYPES.values()[i];
			return lt.getDefault();
		}
	}

	public enum LOGIC_FLAGS {
		WHITE, BLACK, GREEN, RED;
		public final static int FLAGS_COUNT = 4;
		
		public static boolean[][] getColorFlagsBool(){
			boolean[][] flags = new boolean[FLAGS_COUNT][];
			for (int i = 0; i < FLAGS_COUNT; i++){
				flags[i] = new boolean[LOGIC_TYPES.TYPES_COUNT];
				for (int j = 0; j < LOGIC_TYPES.TYPES_COUNT; j++){
					flags[i][j] = LOGIC_TYPES.getDefault(j);
				}
			}
			return flags;
		}
		public static boolean[][] getColorFlagsBoolTrue(){
			boolean[][] flags = new boolean[FLAGS_COUNT][];
			for (int i = 0; i < FLAGS_COUNT; i++){
				flags[i] = new boolean[LOGIC_TYPES.TYPES_COUNT];
				for (int j = 0; j < LOGIC_TYPES.TYPES_COUNT; j++){
					flags[i][j] = true;
				}
			}
			return flags;
		}
	}

	private Drawable value;
	private OWNER_TYPES owner;
	private LOGIC_TYPES logic;
	private LOGIC_FLAGS flags;

	public MindPolyGraph(Point coord, int width, int height) {
		super(coord, width, height);
		value = null;
		owner = OWNER_TYPES.NEUTRAL;
		logic = LOGIC_TYPES.AND;
		flags = LOGIC_FLAGS.WHITE;
		graph_on = false;
	}

	public MindPolyGraph(Point coord, int width, int height, Drawable value,
			OWNER_TYPES owner) {
		super(coord, width, height);
		this.value = value;
		this.owner = owner;
		graph_on = false;
		
		logic = LOGIC_TYPES.AND;
		flags = LOGIC_FLAGS.WHITE;
	}

	public void draw() {
		if (!graph_on)
			if (value != null)
				value.draw();
	}

	public boolean onCollision(Graph obj) {
		return false;
	}

	public Drawable getValue() {
		return value;
	}

	public void setValue(Drawable value) {
		this.value = value;
	}

	public OWNER_TYPES getOwner() {
		return owner;
	}

	public void setOwner(OWNER_TYPES owner) {
		this.owner = owner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MindPolyGraph)) {
			return false;
		}
		MindPolyGraph other = (MindPolyGraph) obj;
		if (owner != other.owner) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	public LOGIC_TYPES getLogic() {
		return logic;
	}

	public void setLogic(LOGIC_TYPES logic) {
		this.logic = logic;
	}

	public LOGIC_FLAGS getFlags() {
		return flags;
	}

	public void setFlags(LOGIC_FLAGS flags) {
		this.flags = flags;
	}

}
