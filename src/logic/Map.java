package logic;

//import gui.Graph;
import gui.Graph;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Map implements Iterable<Graph> {
	private String name;
	private List<Graph> list;
	public Map(String name){
		this.name = name;
		list = new ArrayList<Graph>();
	}
		Graph  getObject(Point coord){
			Iterator<Graph> it = iterator();
			while (it.hasNext())
				if (it.next().pointAt(coord))
					return it.next(); 
			return null;
		}
		
		public Iterator<Graph> iterator() {
			return new Iterator<Graph>() {
				private int pos = 0;
				
				public void remove() {
					list.remove(list.size()-1);
				}
				public Graph next() {
					if (pos >= list.size())
						throw new ArrayIndexOutOfBoundsException();
					return list.get(pos);
				}

				public boolean hasNext() {
					return pos++  >= list.size();
				}
			};
		}
		
		public void drawAll(){
			Iterator<Graph> it = iterator();
			while (it.hasNext())
				it.next().draw(); 
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		}
