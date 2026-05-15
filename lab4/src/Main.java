import java.util.*;

public class Main {

    static void sep(String title) { System.out.println("\n=== " + title + " ==="); }

    public static void main(String[] args) {

        sep("1. Generic class Pair<A,B> - lecture example");
        Pair<String, Integer> p1 = new Pair<>("Auditorium 101", 30);
        Pair<Integer, Boolean> p2 = new Pair<>(201, true);
        System.out.println(p1);
        System.out.println(p2);

        Laboratory lab1 = new Laboratory(102, 25, 1, "computers",   15);
        Laboratory lab2 = new Laboratory(302, 20, 3, "electronics", 10);
        Laboratory lab3 = new Laboratory(303, 18, 3, "networking",  20);

        List<Auditorium> auditoriums = new ArrayList<>(Arrays.asList(
            new Auditorium(101, 30,  "practical", 1),
            lab1,
            new Auditorium(201, 120, "lecture",   2),
            new Auditorium(202, 40,  "practical", 2),
            lab2
        ));

        List<Laboratory> labs = new ArrayList<>(Arrays.asList(lab1, lab2, lab3));

        sep("2a. Regular method findLargestAuditorium (non-generic, Lab 2)");
        GenericUtils.findLargestAuditorium(auditoriums).printInfo();

        sep("2b. Generic method findMax - same result for Auditorium list");
        GenericUtils.findMax(auditoriums, Comparator.comparingInt(Auditorium::getCapacity)).printInfo();

        sep("2c. Generic method findMax - works for Laboratory list too");
        GenericUtils.findMax(labs, Comparator.comparingInt(Laboratory::getComputerCount)).printInfo();

        sep("2d. Generic method findMax - works for String list (impossible with regular method)");
        List<String> words = Arrays.asList("apple", "zebra", "mango");
        System.out.println("Max string: " + GenericUtils.findMax(words, Comparator.naturalOrder()));

        sep("2e. Generic filter - free auditoriums");
        GenericUtils.filter(auditoriums, a -> !a.isOccupied()).forEach(Auditorium::printInfo);

        sep("2f. Generic sort by floor");
        GenericUtils.sort(auditoriums, Comparator.comparingInt(Auditorium::getFloor))
                    .forEach(a -> System.out.println("#" + a.getNumber() + " floor=" + a.getFloor()));

        sep("2g. Generic findMin on Comparable list");
        System.out.println("Min word: " + GenericUtils.findMin(words));

        sep("3a. GenericRepository<Auditorium>");
        GenericRepository<Auditorium> audRepo = new GenericRepository<>();
        auditoriums.forEach(audRepo::add);
        System.out.println("Size: " + audRepo.size());
        audRepo.findFirst(a -> a.getType().equals("lecture")).printInfo();
        System.out.printf("Avg capacity: %.2f%n", audRepo.averageCapacity());
        audRepo.sortBy(Comparator.comparingInt(Auditorium::getCapacity))
               .forEach(a -> System.out.println("  #" + a.getNumber() + " cap=" + a.getCapacity()));

        sep("3b. GenericRepository<Laboratory> - same class, different type");
        GenericRepository<Laboratory> labRepo = new GenericRepository<>();
        labs.forEach(labRepo::add);
        System.out.println("Size: " + labRepo.size());
        labRepo.findMax(Comparator.comparingInt(Laboratory::getComputerCount)).printInfo();
        labRepo.filter(l -> l.getComputerCount() >= 15).forEach(Laboratory::printInfo);

        sep("4. List<?> wildcard method - printAll");
        System.out.println("-- auditoriums --");
        GenericUtils.printAll(auditoriums);
        System.out.println("-- words --");
        GenericUtils.printAll(words);

        sep("4. List<? extends Auditorium> - sumCapacities");
        System.out.println("Sum (auditoriums): " + GenericUtils.sumCapacities(auditoriums));
        System.out.println("Sum (labs only):   " + GenericUtils.sumCapacities(labs));

        sep("4. List<? super Auditorium> - fillWithDefaults");
        List<Auditorium> extra = new ArrayList<>();
        GenericUtils.fillWithDefaults(extra, 3);
        extra.forEach(Auditorium::printInfo);

        sep("5. Wildcard in Java standard library");

        List<Integer> ints = Arrays.asList(5, 3, 8, 1);
        List<Integer> dest  = new ArrayList<>(Arrays.asList(0, 0, 0, 0));
        Collections.sort(ints);
        System.out.println("Collections.sort(List<T extends Comparable>): " + ints);

        Collections.copy(dest, ints);
        System.out.println("Collections.copy(List<? super T>, List<? extends T>): " + dest);

        List<Integer> unmod = Collections.unmodifiableList(ints);
        System.out.println("Collections.unmodifiableList(List<? extends T>): " + unmod);

        Optional<String> opt = words.stream().filter(s -> s.startsWith("m")).findFirst();
        System.out.println("Stream<T>.filter + findFirst -> Optional<T>: " + opt.orElse("none"));

        Comparator<Auditorium> byFloorThenCap =
            Comparator.comparingInt(Auditorium::getFloor)
                      .thenComparingInt(Auditorium::getCapacity);
        GenericUtils.sort(auditoriums, byFloorThenCap)
                    .forEach(a -> System.out.printf("  floor=%d cap=%d%n", a.getFloor(), a.getCapacity()));
    }
}
