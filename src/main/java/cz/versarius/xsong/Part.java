package cz.versarius.xsong;

import java.util.ArrayList;
import java.util.List;

/**
 * Part of song, consists of lines.
 * Can be @see Chorus or @see Verse.
 * 
 * @author miira
 *
 * TODO inconsistent api with song and parts - the list is created there....
 */
public abstract class Part {
	private List<Line> lines;

	public List<Line> getLines() {
		return lines;
	}

	public void setLines(List<Line> lines) {
		this.lines = lines;
	}
	
	public void addLine(Line line) {
		if (lines == null) { // TODO just for now, shortcut
			lines = new ArrayList<Line>();
		}
		lines.add(line);
	}
	
	public boolean hasAnyChords() {
		for (Line line : lines) {
			if (line.hasAnyChords()) {
				return true;
			}
		}
		return false;
	}
	
	public int getLinesCount() {
		return lines.size();
	}
	
	public abstract String getTypeName();
}
