import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        Laboratory lab1 = new Laboratory(102, 25, 1, "computers",   15);
        Laboratory lab2 = new Laboratory(302, 20, 3, "electronics", 10);
        Laboratory lab3 = new Laboratory(303, 18, 3, "networking",  20);

        Auditorium[] allRooms = {
            new Auditorium(101, 30,  "practical", 1),
            lab1,
            new Auditorium(201, 120, "lecture",   2),
            new Auditorium(202, 40,  "practical", 2),
            lab2,
            lab3,
        };

        System.out.println("=== All rooms (parent array with subclass objects) ===");
        for (Auditorium a : allRooms) a.printInfo();
        System.out.println("Total created (static field): " + Auditorium.getTotalCount());

        System.out.println("\n=== instanceof + downcast ===");
        for (Auditorium a : allRooms) {
            if (a instanceof Laboratory) {
                Laboratory l = (Laboratory) a;
                System.out.println("Lab #" + l.getNumber() + " | equipment: " + l.getEquipmentType()
                        + " | computers: " + l.getComputerCount());
            }
        }

        Schedule schedule = new Schedule(allRooms);
        schedule.addLesson("Monday",  1, 101, "Math");
        schedule.addLesson("Monday",  1, 102, "Programming");
        schedule.addLesson("Monday",  2, 201, "Physics");
        schedule.addLesson("Tuesday", 1, 201, "History");
        schedule.printSchedule();

        System.out.println("\n=== Free rooms: Monday, lesson 1 (variant 2.5) ===");
        Auditorium[] free = schedule.findFreeRooms(1, "Monday");
        for (Auditorium a : free) a.printInfo();

        System.out.println("\n=== Local class: rooms on floor 3 ===");
        schedule.printRoomsByFloor(3);

        System.out.println("\n=== Largest room ===");
        schedule.findLargest().printInfo();

        System.out.println("\n=== Find by number: 202 ===");
        schedule.findByNumber(202).printInfo();

        System.out.println("\n=== Sorted by capacity ===");
        for (Auditorium a : schedule.sortByCapacity()) a.printInfo();

        System.out.println("\n=== Copy of rooms array ===");
        Auditorium[] copy = Arrays.copyOf(allRooms, allRooms.length);
        System.out.println("Copy length: " + copy.length);

        System.out.println("\n=== Best equipped lab (static method) ===");
        Laboratory.findBestEquipped(new Laboratory[]{lab1, lab2, lab3}).printInfo();

        System.out.println();
        Auditorium.printStats(allRooms);
    }
}
