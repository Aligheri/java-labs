public class Auditorium {

    // --- static field: shared counter for all objects ---
    private static int totalCount = 0;

    // --- instance fields ---
    private int number;
    private int capacity;
    private String type;      // "lecture", "practical", "laboratory"
    private int floor;
    private boolean occupied;

    // --- default constructor ---
    public Auditorium() {
        this.number   = 0;
        this.capacity = 0;
        this.type     = "lecture";
        this.floor    = 1;
        this.occupied = false;
        totalCount++;
    }

    // --- parameterized constructor ---
    public Auditorium(int number, int capacity, String type, int floor) {
        this.number   = number;
        this.capacity = capacity;
        this.type     = type;
        this.floor    = floor;
        this.occupied = false;
        totalCount++;
    }

    // --- full constructor ---
    public Auditorium(int number, int capacity, String type, int floor, boolean occupied) {
        this.number   = number;
        this.capacity = capacity;
        this.type     = type;
        this.floor    = floor;
        this.occupied = occupied;
        totalCount++;
    }

    // --- getters / setters (encapsulation) ---
    public int getNumber()          { return number; }
    public void setNumber(int n)    { this.number = n; }

    public int getCapacity()        { return capacity; }
    public void setCapacity(int c)  { this.capacity = c; }

    public String getType()         { return type; }
    public void setType(String t)   { this.type = t; }

    public int getFloor()           { return floor; }
    public void setFloor(int f)     { this.floor = f; }

    public boolean isOccupied()     { return occupied; }

    // --- instance methods ---
    public void occupy() {
        if (!occupied)
            occupied = true;
        else
            System.out.println("Auditorium " + number + " is already occupied.");
    }

    public void free() {
        if (occupied)
            occupied = false;
        else
            System.out.println("Auditorium " + number + " is already free.");
    }

    public boolean canFit(int students) {
        return !occupied && capacity >= students;
    }

    // --- overloaded method (same name, different params) ---
    public void printInfo() {
        System.out.printf("Auditorium #%-4d | capacity: %-3d | type: %-12s | floor: %d | %s%n",
                number, capacity, type, floor, occupied ? "OCCUPIED" : "FREE");
    }

    public void printInfo(boolean showFloor) {
        if (showFloor)
            System.out.printf("Auditorium #%d (floor %d) — %s%n", number, floor, occupied ? "OCCUPIED" : "FREE");
        else
            System.out.printf("Auditorium #%d — %s%n", number, occupied ? "OCCUPIED" : "FREE");
    }

    // --- static method ---
    public static int getTotalCount() {
        return totalCount;
    }

    public static void printStats(Auditorium[] rooms) {
        int free = 0, occ = 0, totalCap = 0;
        for (Auditorium a : rooms) {
            if (a.isOccupied()) occ++; else free++;
            totalCap += a.getCapacity();
        }
        System.out.println("--- Stats ---");
        System.out.println("Total auditoriums : " + rooms.length);
        System.out.println("Free              : " + free);
        System.out.println("Occupied          : " + occ);
        System.out.println("Total capacity    : " + totalCap);
    }

    @Override
    public String toString() {
        return "Auditorium{#" + number + ", cap=" + capacity +
               ", type=" + type + ", floor=" + floor +
               ", occupied=" + occupied + "}";
    }
}
