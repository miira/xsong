package cz.versarius.xsong;

import java.util.List;

/**
 * Single line of text of song.
 * With list of all chord references, that should be displayed above this line of text.
 * 
 * @author miira
 *
 */
public class Line {
	private String text;
	private List<ChordRef> chords;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<ChordRef> getChords() {
		return chords;
	}

	public void setChords(List<ChordRef> chords) {
		this.chords = chords;
	}
	
	public boolean hasAnyChords() {
		return !((chords == null) || (chords.isEmpty()));
	}
	
	public boolean hasAnyText() {
		return !((text == null || text.trim().isEmpty()));
	}
	
	public int getChordLineLength() {
		ChordRef last = getLastChord();
		if (last == null) {
			return 0;
		}
		return (last.getPosition() + last.getChord().getName().length());
	}
	
	public void remove(ChordRef ref) {
		chords.remove(ref);
	}

	public ChordRef getLastChord() {
		if (chords.isEmpty()) {
			return null;
		}
		return chords.get(chords.size() - 1);
	}
}
