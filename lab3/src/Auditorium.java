import java.util.Objects;

public class Auditorium {

    private static int totalCount = 0;

    private int number;
    private int capacity;
    private String type;
    private int floor;
    private boolean occupied;

    public Auditorium() {
        this.number   = 0;
        this.capacity = 0;
        this.type     = "lecture";
        this.floor    = 1;
        this.occupied = false;
        totalCount++;
    }

    public Auditorium(int number, int capacity, String type, int floor) {
        this.number   = number;
        this.capacity = capacity;
        this.type     = type;
        this.floor    = floor;
        this.occupied = false;
        totalCount++;
    }

    public Auditorium(int number, int capacity, String type, int floor, boolean occupied) {
        this.number   = number;
        this.capacity = capacity;
        this.type     = type;
        this.floor    = floor;
        this.occupied = occupied;
        totalCount++;
    }

    public int getNumber()         { return number; }
    public void setNumber(int n)   { this.number = n; }
    public int getCapacity()       { return capacity; }
    public void setCapacity(int c) { this.capacity = c; }
    public String getType()        { return type; }
    public void setType(String t)  { this.type = t; }
    public int getFloor()          { return floor; }
    public void setFloor(int f)    { this.floor = f; }
    public boolean isOccupied()    { return occupied; }

    public void occupy() {
        if (!occupied) occupied = true;
        else System.out.println("Auditorium " + number + " is already occupied.");
    }

    public void free() {
        if (occupied) occupied = false;
        else System.out.println("Auditorium " + number + " is already free.");
    }

    public boolean canFit(int students) {
        return !occupied && capacity >= students;
    }

    public void printInfo() {
        System.out.printf("Auditorium #%-4d | capacity: %-3d | type: %-12s | floor: %d | %s%n",
                number, capacity, type, floor, occupied ? "OCCUPIED" : "FREE");
    }

    public static int getTotalCount() { return totalCount; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Auditorium)) return false;
        return number == ((Auditorium) o).number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "Auditorium{#" + number + ", cap=" + capacity + ", type=" + type +
               ", floor=" + floor + ", occupied=" + occupied + "}";
    }
}
