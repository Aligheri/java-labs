import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class GenericRepository<T extends Auditorium> {

    private List<T> items = new ArrayList<>();

    public void add(T item) { items.add(item); }

    public T findFirst(Predicate<T> predicate) {
        return items.stream().filter(predicate).findFirst().orElse(null);
    }

    public List<T> filter(Predicate<T> predicate) {
        return items.stream().filter(predicate).collect(Collectors.toList());
    }

    public T findMax(Comparator<T> comparator) {
        return items.stream().max(comparator).orElse(null);
    }

    public List<T> sortBy(Comparator<T> comparator) {
        List<T> sorted = new ArrayList<>(items);
        sorted.sort(comparator);
        return sorted;
    }

    public double averageCapacity() {
        return items.stream().mapToInt(Auditorium::getCapacity).average().orElse(0.0);
    }

    public List<T> getAll() { return Collections.unmodifiableList(items); }

    public int size() { return items.size(); }
}
