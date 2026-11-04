package pl.kurs.basics;

import java.util.ArrayList;
import java.util.List;

/**
 * Playlist: track ids in the order of playing. The same track can be on the list several times.
 */
public class Playlist {

    private final List<Integer> trackIds = new ArrayList<>();

    public void add(int trackId) {
        trackIds.add(trackId);
    }

    /** Removes the first occurrence of the track. Returns false if the track is not on the list. */
    public boolean removeTrack(int trackId) {
        if (!trackIds.contains(trackId)) {
            return false;
        }
        trackIds.remove(trackId);
        return true;
    }

    /** Removes the track at the given position (0 = first). */
    public void removeAt(int position) {
        trackIds.remove(position);
    }

    /** Tracks in the order of playing. Only Playlist methods can change the playlist. */
    public List<Integer> trackIds() {
        return trackIds;
    }
}
