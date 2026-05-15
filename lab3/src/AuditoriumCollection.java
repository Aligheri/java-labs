import java.util.*;
import java.util.stream.*;

public class AuditoriumCollection {

    private Collection<Auditorium> rooms;

    public AuditoriumCollection() {
        this.rooms = new ArrayList<>();
    }

    public void add(Auditorium a) {
        rooms.add(a);
    }

    public Auditorium findFirst(String type) {
        return rooms.stream()
                .filter(a -> a.getType().equals(type))
                .findFirst()
                .orElse(null);
    }

    public Set<Auditorium> getUnique() {
        return new HashSet<>(rooms);
    }

    public Auditorium compareAndFindMax(Comparator<Auditorium> comparator) {
        return rooms.stream().max(comparator).orElse(null);
    }

    public List<Auditorium> filterByFloor(int floor) {
        return rooms.stream()
                .filter(a -> a.getFloor() == floor)
                .collect(Collectors.toList());
    }

    public double averageCapacity() {
        return rooms.stream()
                .mapToInt(Auditorium::getCapacity)
                .average()
                .orElse(0.0);
    }

    public List<Auditorium> sortByCapacityAnonymous() {
        List<Auditorium> list = new ArrayList<>(rooms);
        Collections.sort(list, new Comparator<Auditorium>() {
            @Override
            public int compare(Auditorium a, Auditorium b) {
                return Integer.compare(a.getCapacity(), b.getCapacity());
            }
        });
        return list;
    }

    public List<Auditorium> sortByCapacityLambda() {
        List<Auditorium> list = new ArrayList<>(rooms);
        list.sort((a, b) -> Integer.compare(a.getCapacity(), b.getCapacity()));
        return list;
    }

    public List<Auditorium> sortByCapacityMethodRef() {
        List<Auditorium> list = new ArrayList<>(rooms);
        list.sort(Comparator.comparingInt(Auditorium::getCapacity));
        return list;
    }

    public Collection<Auditorium> getRooms() {
        return rooms;
    }
}
