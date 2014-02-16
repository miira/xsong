package cz.versarius.xsong;

import java.util.ArrayList;
import java.util.List;

import cz.versarius.xchords.ChordBag;

/**
 * Song itself.
 * Identification info, song structure (parts) and list of used chords.
 * 
 * @author miira
 *
 */
public class Song {
	private String title;
	private String interpret;
	private String info;
	private List<Part> parts = new ArrayList<Part>();
	/** List of all used chords. */
	private ChordBag usedChords = new ChordBag();
	/** used in UI, as flag */
	private transient boolean changed;
	/** default songs for RoboTar flag */
	private transient boolean robotarDefault;
	/** path from where it was loaded */
	private transient String path;
	
	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

	public void addPart(Part part) {
		parts.add(part);
	}
	
	public boolean hasAnyChords() {
		for (Part p : parts) {
			if (p.hasAnyChords()) {
				return true;
			}
		}
		return false;
	}
	
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getInterpret() {
		return interpret;
	}

	public void setInterpret(String interpret) {
		this.interpret = interpret;
	}

	public String getTitle() {
		return title;
	}

	public String getTitleWithMark() {
		return (changed ? title + " *": title);
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getFullTitle() {
		StringBuilder sb = new StringBuilder();
		sb.append(title);
		sb.append(" - ");
		sb.append(interpret);
		return sb.toString();
	}

	public ChordBag getUsedChords() {
		return usedChords;
	}

	public void setUsedChords(ChordBag chords) {
		this.usedChords = chords;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public boolean isRobotarDefault() {
		return robotarDefault;
	}

	public void setRobotarDefault(boolean robotarDefault) {
		this.robotarDefault = robotarDefault;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Line getLine(int lineIdx) {
		int idx = 0;
		for (Part part : parts) {
			// check empty lines
			for (Line line : part.getLines()) {
				if (lineIdx == idx) {
					return line;
				} else {
					idx++;
				}
			}
		}
		return null;
	}

	public void deleteChord(int lineIdx, ChordRef chordRef) {
		Line line = getLine(lineIdx);
		line.remove(chordRef);
	}
	
	
}
