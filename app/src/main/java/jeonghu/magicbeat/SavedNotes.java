package jeonghu.magicbeat;

/**
 * Created by Jeonghu on 12/2/17.
 */

public class SavedNotes {
    public int[][][] savedNotes;
    public String[] names;
    public int[] bpm;

    SavedNotes(int[][][] savedNotes, String[] names, int[] bpm) {
        this.savedNotes = savedNotes;
        this.names = names;
        this.bpm = bpm;
    }
}
