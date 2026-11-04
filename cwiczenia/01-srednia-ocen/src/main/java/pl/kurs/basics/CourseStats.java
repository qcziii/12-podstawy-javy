package pl.kurs.basics;

/**
 * Statistics for the course panel.
 */
public class CourseStats {

    /**
     * Average of the grades, e.g. [4, 5] -> 4.5. For an empty array returns 0.0.
     */
    public static double average(int[] grades) {
        if (grades.length == 0) {
            return 0.0;
        }
        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return sum / grades.length;
    }

    /**
     * Total watch time of all videos in milliseconds.
     *
     * @param watchSecondsPerVideo total watch time of each video (all viewers together) in seconds
     */
    public static long totalWatchMillis(int[] watchSecondsPerVideo) {
        int total = 0;
        for (int seconds : watchSecondsPerVideo) {
            total += seconds * 1000;
        }
        return total;
    }
}
