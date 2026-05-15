import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class GenericUtils {

    public static Auditorium findLargestAuditorium(List<Auditorium> list) {
        Auditorium max = list.get(0);
        for (Auditorium a : list)
            if (a.getCapacity() > max.getCapacity()) max = a;
        return max;
    }

    public static <T> T findMax(List<T> list, Comparator<T> comparator) {
        return list.stream().max(comparator).orElse(null);
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

    public static <T> List<T> sort(List<T> list, Comparator<T> comparator) {
        List<T> sorted = new ArrayList<>(list);
        sorted.sort(comparator);
        return sorted;
    }

    public static <T extends Comparable<T>> T findMin(List<T> list) {
        return list.stream().min(Comparator.naturalOrder()).orElse(null);
    }

    public static void printAll(List<?> list) {
        list.forEach(System.out::println);
    }

    public static int sumCapacities(List<? extends Auditorium> list) {
        return list.stream().mapToInt(Auditorium::getCapacity).sum();
    }

    public static void fillWithDefaults(List<? super Auditorium> list, int count) {
        for (int i = 0; i < count; i++)
            list.add(new Auditorium(900 + i, 30, "lecture", 1));
    }
}
