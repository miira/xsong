package cz.versarius.xsong;

import cz.versarius.xchords.Chord;

/**
 * Chord reference. 
 * position - above which character in line of text should be the chord placed
 * chordId - complete identification of chord (library-chordname)
 * chord - computed field, will be filled
 * 
 * @author miira
 *
 */
public class ChordRef {
	private int position;
	private String chordId;
	/**
	 * Chord object will be found in chord library, based on chordId, read from song XML.
	 */
	private transient Chord chord;
	
	public ChordRef() {
	}
	
	public ChordRef(String chordId, int position) {
		this.chordId = chordId;
		this.position = position;
	}
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getChordId() {
		return chordId;
	}

	public void setChordId(String chordId) {
		this.chordId = chordId;
	}

	public Chord getChord() {
		return chord;
	}

	public void setChord(Chord chord) {
		this.chord = chord;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof ChordRef)) return false;
		ChordRef other = (ChordRef)obj;
		if ((other.position == this.position) 
			&& (other.chordId.equals(this.chordId))) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		// position itself is unique, this may be an average line length, 
		// and if not, don't care, it's THE ANSWER
		return position / 42;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(10);
		sb.append(chordId).append(":").append(position);
		return sb.toString();
	}
}
