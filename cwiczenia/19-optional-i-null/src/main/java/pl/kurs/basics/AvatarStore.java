package pl.kurs.basics;

/**
 * Avatar storage. Loading the default avatar is slow (it is downloaded from a CDN), so it should
 * be loaded only when it is really needed. Do not change this class.
 */
public class AvatarStore {

    private int defaultLoads;

    public String loadDefaultAvatar() {
        defaultLoads++;
        return "default.png";
    }

    public int defaultLoads() {
        return defaultLoads;
    }
}
