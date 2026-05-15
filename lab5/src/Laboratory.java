public class Laboratory extends Auditorium {

    private String equipmentType;
    private int computerCount;

    public Laboratory(int number, int capacity, int floor, String equipmentType, int computerCount) {
        super(number, capacity, "laboratory", floor);
        this.equipmentType = equipmentType;
        this.computerCount = computerCount;
    }

    public String getEquipmentType()       { return equipmentType; }
    public void setEquipmentType(String e) { this.equipmentType = e; }
    public int getComputerCount()          { return computerCount; }
    public void setComputerCount(int c)    { this.computerCount = c; }

    @Override
    public String toString() {
        return "Laboratory{#" + getNumber() + ", cap=" + getCapacity() +
               ", floor=" + getFloor() + ", eq=" + equipmentType + ", pc=" + computerCount + "}";
    }
}
