public class Main {

    public static void main(String[] args) {

        // -------------------------------------------------------
        // 1. Array of objects — multiple constructors demonstrated
        // -------------------------------------------------------
        Auditorium[] rooms = {
            new Auditorium(),                                        // default constructor
            new Auditorium(101, 30,  "practical",  1),              // 4-arg constructor
            new Auditorium(102, 25,  "laboratory", 1),
            new Auditorium(201, 120, "lecture",    2),
            new Auditorium(202, 40,  "practical",  2),
            new Auditorium(301, 15,  "laboratory", 3, true),        // full constructor (already occupied)
        };

        System.out.println("=== Initial state ===");
        for (Auditorium a : rooms)
            a.printInfo();

        System.out.println("\nTotal auditoriums created (static field): " + Auditorium.getTotalCount());

        // -------------------------------------------------------
        // 2. Using instance methods
        // -------------------------------------------------------
        System.out.println("\n=== Occupying rooms 101 and 201 ===");
        rooms[1].occupy();   // 101
        rooms[3].occupy();   // 201
        rooms[3].occupy();   // already occupied — shows message

        System.out.println("\n=== State after occupy ===");
        for (Auditorium a : rooms)
            a.printInfo();

        // -------------------------------------------------------
        // 3. canFit — find a free room for a group
        // -------------------------------------------------------
        int groupSize = 28;
        System.out.println("\n=== Looking for a free room for " + groupSize + " students ===");
        for (Auditorium a : rooms) {
            if (a.canFit(groupSize))
                System.out.println("  Suitable: auditorium #" + a.getNumber()
                        + " (capacity " + a.getCapacity() + ")");
        }

        // -------------------------------------------------------
        // 4. Freeing a room
        // -------------------------------------------------------
        System.out.println("\n=== Freeing room 201 ===");
        rooms[3].free();
        rooms[3].printInfo();

        // -------------------------------------------------------
        // 5. Overloaded printInfo
        // -------------------------------------------------------
        System.out.println("\n=== printInfo overload (with floor flag) ===");
        for (Auditorium a : rooms)
            a.printInfo(true);

        // -------------------------------------------------------
        // 6. Static method — aggregate stats
        // -------------------------------------------------------
        System.out.println();
        Auditorium.printStats(rooms);

        // -------------------------------------------------------
        // 7. Encapsulation demo — change capacity via setter
        // -------------------------------------------------------
        System.out.println("\n=== Encapsulation: setCapacity on room 202 ===");
        System.out.println("Before: " + rooms[4]);
        rooms[4].setCapacity(50);
        System.out.println("After : " + rooms[4]);

        // -------------------------------------------------------
        // 8. Class without explicit constructor — compile demo
        // -------------------------------------------------------
        // If Auditorium had no constructors defined, Java would
        // provide a default one automatically: new Auditorium()
        // would still compile, but fields would be 0/false/null.
    }
}
