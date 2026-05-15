public class Main {

    static void separator(String title) {
        System.out.println("\n=== " + title + " ===");
    }

    public static void main(String[] args) {

        Laboratory lab1 = new Laboratory(102, 25, 1, "computers",   15);
        Laboratory lab2 = new Laboratory(302, 20, 3, "electronics", 10);
        Laboratory lab3 = new Laboratory(303, 18, 3, "networking",  20);

        AuditoriumCollection col = new AuditoriumCollection();
        col.add(new Auditorium(101, 30,  "practical", 1));
        col.add(lab1);
        col.add(new Auditorium(201, 120, "lecture",   2));
        col.add(new Auditorium(202, 40,  "practical", 2));
        col.add(lab2);
        col.add(lab3);
        col.add(new Auditorium(101, 30,  "practical", 1));

        separator("Collection: findFirst type='lecture'");
        col.findFirst("lecture").printInfo();

        separator("Collection: getUnique (duplicate #101 removed)");
        col.getUnique().forEach(Auditorium::printInfo);

        separator("Collection: compareAndFindMax by capacity");
        col.compareAndFindMax((a, b) -> Integer.compare(a.getCapacity(), b.getCapacity())).printInfo();

        separator("Collection: compareAndFindMax by floor");
        col.compareAndFindMax((a, b) -> Integer.compare(a.getFloor(), b.getFloor())).printInfo();

        separator("Collection: filterByFloor(2)");
        col.filterByFloor(2).forEach(Auditorium::printInfo);

        System.out.printf("%n=== Average capacity: %.2f ===%n", col.averageCapacity());

        separator("Sort by capacity - anonymous class");
        col.sortByCapacityAnonymous().forEach(Auditorium::printInfo);

        separator("Sort by capacity - lambda");
        col.sortByCapacityLambda().forEach(Auditorium::printInfo);

        separator("Sort by capacity - method reference");
        col.sortByCapacityMethodRef().forEach(Auditorium::printInfo);

        AuditoriumMap map = new AuditoriumMap();
        col.getUnique().forEach(map::put);

        separator("Map: all rooms");
        map.printAll();

        separator("Map: filterByType('practical')");
        map.filterByType("practical").values().forEach(Auditorium::printInfo);

        map.getMap().get(101).occupy();
        map.getMap().get(201).occupy();

        separator("Map: before removeIfOccupied");
        map.printAll();

        map.removeIfOccupied();

        separator("Map: after removeIfOccupied");
        map.printAll();

        System.out.println("\nMap: total capacity = " + map.totalCapacity());

        AuditoriumMap map2 = new AuditoriumMap();
        col.getUnique().forEach(map2::put);

        separator("Sort by floor then capacity - anonymous class");
        map2.sortByFloorThenCapacityAnonymous().forEach(Auditorium::printInfo);

        separator("Sort by floor then capacity - lambda");
        map2.sortByFloorThenCapacityLambda().forEach(Auditorium::printInfo);

        separator("Sort by floor then capacity - method reference");
        map2.sortByFloorThenCapacityMethodRef().forEach(Auditorium::printInfo);
    }
}
