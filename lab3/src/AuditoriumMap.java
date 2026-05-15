import java.util.*;
import java.util.stream.*;

public class AuditoriumMap {

    private Map<Integer, Auditorium> rooms;

    public AuditoriumMap() {
        this.rooms = new HashMap<>();
    }

    public void put(Auditorium a) {
        rooms.put(a.getNumber(), a);
    }

    public Map<Integer, Auditorium> filterByType(String type) {
        return rooms.entrySet().stream()
                .filter(e -> e.getValue().getType().equals(type))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void removeIfOccupied() {
        rooms.entrySet().removeIf(e -> e.getValue().isOccupied());
    }

    public int totalCapacity() {
        return rooms.values().stream()
                .mapToInt(Auditorium::getCapacity)
                .sum();
    }

    public List<Auditorium> sortByFloorThenCapacityAnonymous() {
        List<Auditorium> list = new ArrayList<>(rooms.values());
        Collections.sort(list, new Comparator<Auditorium>() {
            @Override
            public int compare(Auditorium a, Auditorium b) {
                int byFloor = Integer.compare(a.getFloor(), b.getFloor());
                return byFloor != 0 ? byFloor : Integer.compare(a.getCapacity(), b.getCapacity());
            }
        });
        return list;
    }

    public List<Auditorium> sortByFloorThenCapacityLambda() {
        List<Auditorium> list = new ArrayList<>(rooms.values());
        list.sort((a, b) -> {
            int byFloor = Integer.compare(a.getFloor(), b.getFloor());
            return byFloor != 0 ? byFloor : Integer.compare(a.getCapacity(), b.getCapacity());
        });
        return list;
    }

    public List<Auditorium> sortByFloorThenCapacityMethodRef() {
        List<Auditorium> list = new ArrayList<>(rooms.values());
        list.sort(Comparator.comparingInt(Auditorium::getFloor)
                            .thenComparingInt(Auditorium::getCapacity));
        return list;
    }

    public void printAll() {
        rooms.values().forEach(Auditorium::printInfo);
    }

    public Map<Integer, Auditorium> getMap() {
        return rooms;
    }
}
