public class Laboratory extends Auditorium {

    private String equipmentType;
    private int computerCount;

    public Laboratory(int number, int capacity, int floor, String equipmentType, int computerCount) {
        super(number, capacity, "laboratory", floor);
        this.equipmentType = equipmentType;
        this.computerCount = computerCount;
    }

    public Laboratory(int number, int capacity, int floor, String equipmentType) {
        super(number, capacity, "laboratory", floor);
        this.equipmentType = equipmentType;
        this.computerCount = 0;
    }

    public String getEquipmentType()       { return equipmentType; }
    public void setEquipmentType(String e) { this.equipmentType = e; }
    public int getComputerCount()          { return computerCount; }
    public void setComputerCount(int c)    { this.computerCount = c; }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.printf("             equipment: %-15s | computers: %d%n", equipmentType, computerCount);
    }

    public static Laboratory findBestEquipped(Laboratory[] labs) {
        Laboratory best = labs[0];
        for (Laboratory l : labs)
            if (l.computerCount > best.computerCount) best = l;
        return best;
    }

    @Override
    public String toString() {
        return "Laboratory{#" + getNumber() + ", cap=" + getCapacity() +
               ", floor=" + getFloor() + ", eq=" + equipmentType + ", pc=" + computerCount + "}";
    }
}
