import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Schedule {

    class Lesson {
        String day;
        int lessonNumber;
        int roomNumber;
        String subject;

        Lesson(String day, int lessonNumber, int roomNumber, String subject) {
            this.day          = day;
            this.lessonNumber = lessonNumber;
            this.roomNumber   = roomNumber;
            this.subject      = subject;
        }

        @Override
        public String toString() {
            return day + " | lesson " + lessonNumber + " | room #" + roomNumber + " | " + subject;
        }
    }

    private Auditorium[] rooms;
    private List<Lesson> lessons = new ArrayList<>();

    public Schedule(Auditorium[] rooms) {
        this.rooms = rooms;
    }

    public void addLesson(String day, int lessonNumber, int roomNumber, String subject) {
        lessons.add(new Lesson(day, lessonNumber, roomNumber, subject));
    }

    public Auditorium[] findFreeRooms(int lessonNumber, String day) {
        Set<Integer> busy = new HashSet<>();
        for (Lesson l : lessons)
            if (l.day.equals(day) && l.lessonNumber == lessonNumber)
                busy.add(l.roomNumber);
        List<Auditorium> free = new ArrayList<>();
        for (Auditorium a : rooms)
            if (!busy.contains(a.getNumber())) free.add(a);
        return free.toArray(new Auditorium[0]);
    }

    public void printSchedule() {
        System.out.println("=== Schedule ===");
        for (Lesson l : lessons) System.out.println("  " + l);
    }

    public void printRoomsByFloor(int floor) {
        class FloorFilter {
            boolean matches(Auditorium a) { return a.getFloor() == floor; }
        }
        FloorFilter filter = new FloorFilter();
        System.out.println("Rooms on floor " + floor + ":");
        for (Auditorium a : rooms)
            if (filter.matches(a)) a.printInfo();
    }

    public Auditorium findByNumber(int number) {
        for (Auditorium a : rooms)
            if (a.getNumber() == number) return a;
        return null;
    }

    public Auditorium findLargest() {
        Auditorium max = rooms[0];
        for (Auditorium a : rooms)
            if (a.getCapacity() > max.getCapacity()) max = a;
        return max;
    }

    public Auditorium[] sortByCapacity() {
        Auditorium[] sorted = Arrays.copyOf(rooms, rooms.length);
        Arrays.sort(sorted, (a, b) -> a.getCapacity() - b.getCapacity());
        return sorted;
    }

    public Auditorium[] getRooms() { return rooms; }
}
