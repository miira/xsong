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

	/**
	 * Count all lines in song.
	 * @return
	 */
	public int getLinesCount() {
		int count = 0;
		for (Part part : parts) {
			// check empty lines
			count += part.getLines().size();
		}
		return count;
	}

	/**
	 * Delete chord at the line.
	 * Chord equality is defined by position and name {@link ChordRef}
	 * @param lineIdx
	 * @param chordRef
	 */
	public void deleteChord(int lineIdx, ChordRef chordRef) {
		Line line = getLine(lineIdx);
		line.remove(chordRef);
	}

	/**
	 * Insert new line to the song.
	 * @param lineIdx -1 put as first line, otherwise 0-based index
	 */
	public void insertLineAfter(int lineIdx) {
		if (lineIdx == -1) {
			parts.get(0).getLines().add(0, new Line());
		} else {
			PartSearchResult res = findPart(lineIdx);
			int newidx = lineIdx - res.idx + 1;
			res.part.getLines().add(newidx, new Line());
		}
	}
	
	/**
	 * Insert new part to the song.
	 * @param line -1 create part before other parts, otherwise 0-based index of lines
	 */
	public void insertPartAfter(int lineIdx, PartType type) {
		Part newpart;
		if (PartType.VERSE == type) {
			newpart = new Verse();
		} else {
			newpart = new Chorus();
		}
		if (lineIdx == -1) {
			parts.add(0, newpart);
		} else {
			PartSearchResult res = findPart(lineIdx);
			// after current
			parts.add(res.partIdx + 1, newpart);
			if (res.lastLine) {
				// adding new part right after the end of the existing part
				newpart.addLine(new Line());
			} else {
				// inserting new part at the middle of the existing part
				// split lines between old and new part
				for (int i = res.idx; i < res.idx + res.linesInPart; i++) {
					Line line = res.part.getLines().remove(res.idx); // it shrinks during iteration
					newpart.addLine(line);
				}
			}
		}
	}
	
	private PartSearchResult findPart(int lineIdx) {
		int idx = 0;
		int partIdx = 0;
		for (Part part : parts) {
			int currSize = part.getLinesCount();
			if (idx + currSize < lineIdx + 1) {
				idx += currSize;
				partIdx++;
				continue;
			}
			PartSearchResult res = new PartSearchResult();
			res.idx = idx;
			res.linesInPart = currSize;
			res.partIdx = partIdx;
			res.lastLine = (idx + currSize == lineIdx + 1);
			res.part = part;
			return res;
		}
		return null;	
	}

	class PartSearchResult {
		Part part;
		int partIdx;
		int idx;
		int linesInPart;
		boolean lastLine;
	}
	
}
