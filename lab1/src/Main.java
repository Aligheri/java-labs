public class Main {

    public static void main(String[] args) {

        Auditorium[] rooms = {
            new Auditorium(),
            new Auditorium(101, 30,  "practical",  1),
            new Auditorium(102, 25,  "laboratory", 1),
            new Auditorium(201, 120, "lecture",    2),
            new Auditorium(202, 40,  "practical",  2),
            new Auditorium(301, 15,  "laboratory", 3, true),
        };

        System.out.println("=== Initial state ===");
        for (Auditorium a : rooms)
            a.printInfo();

        System.out.println("\nTotal created (static field): " + Auditorium.getTotalCount());

        System.out.println("\n=== Occupying rooms 101 and 201 ===");
        rooms[1].occupy();
        rooms[3].occupy();
        rooms[3].occupy();

        System.out.println("\n=== State after occupy ===");
        for (Auditorium a : rooms)
            a.printInfo();

        int groupSize = 28;
        System.out.println("\n=== Free rooms for " + groupSize + " students ===");
        for (Auditorium a : rooms)
            if (a.canFit(groupSize))
                System.out.println("  Suitable: #" + a.getNumber() + " (capacity " + a.getCapacity() + ")");

        System.out.println("\n=== Freeing room 201 ===");
        rooms[3].free();
        rooms[3].printInfo();

        System.out.println("\n=== printInfo overload ===");
        for (Auditorium a : rooms)
            a.printInfo(true);

        System.out.println();
        Auditorium.printStats(rooms);

        System.out.println("\n=== Encapsulation: setCapacity on room 202 ===");
        System.out.println("Before: " + rooms[4]);
        rooms[4].setCapacity(50);
        System.out.println("After : " + rooms[4]);
    }
}
