package pl.kurs.basics;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Small helpers for collections. Written "before generics": everything is Object and raw types.
 * The logic is correct, the types are not.
 */
public class CollectionUtils {

    private CollectionUtils() {
    }

    /** The largest element according to the comparator. Empty collection -> NoSuchElementException. */
    public static Object max(Collection items, Comparator comparator) {
        Iterator it = items.iterator();
        if (!it.hasNext()) {
            throw new NoSuchElementException("Pusta kolekcja");
        }
        Object best = it.next();
        while (it.hasNext()) {
            Object candidate = it.next();
            if (comparator.compare(candidate, best) > 0) {
                best = candidate;
            }
        }
        return best;
    }

    /** Adds all elements of source to the end of target. */
    public static void copyAll(List source, List target) {
        for (Object element : source) {
            target.add(element);
        }
    }

    /** Sum of any numbers (Integer, Long, Double...) as double. */
    public static double sum(List<Number> numbers) {
        double sum = 0;
        for (Number n : numbers) {
            sum += n.doubleValue();
        }
        return sum;
    }

    /** Swaps keys with values: {"a"=1, "b"=2} -> {1="a", 2="b"}. */
    public static Map invert(Map map) {
        Map result = new HashMap();
        for (Object o : map.entrySet()) {
            Map.Entry e = (Map.Entry) o;
            result.put(e.getValue(), e.getKey());
        }
        return result;
    }
}
