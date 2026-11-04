package pl.kurs.basics;

import java.util.List;
import java.util.Map;

/**
 * School grade book: grades 1..6 of students in subjects.
 * Choose the fields yourself (hint: a map of maps, or a map with a record key).
 */
public class GradeBook {

    // TODO: fields

    /** Adds a grade. Grade outside 1..6 -> IllegalArgumentException. */
    public void addGrade(String student, String subject, int grade) {
        throw new UnsupportedOperationException("TODO");
    }

    /** Grades of the student in the subject, in the order they were added. No grades -> empty list. */
    public List<Integer> grades(String student, String subject) {
        throw new UnsupportedOperationException("TODO");
    }

    /** Average of all the student's grades (all subjects). Unknown student -> IllegalArgumentException. */
    public double average(String student) {
        throw new UnsupportedOperationException("TODO");
    }

    /** Average per subject for the student, subjects in alphabetical order. Unknown student -> empty map. */
    public Map<String, Double> averagesBySubject(String student) {
        throw new UnsupportedOperationException("TODO");
    }

    /** All students who have at least one grade, alphabetically. */
    public List<String> students() {
        throw new UnsupportedOperationException("TODO");
    }

    /** How many times each grade was given (all students, all subjects). Only grades that occur, ascending. */
    public Map<Integer, Integer> histogram() {
        throw new UnsupportedOperationException("TODO");
    }
}
