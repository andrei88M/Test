package by.group;

import java.io.*;
import java.util.*;

public class Group {
    public static void main(String[] args) {
        List<Person> group = new ArrayList<>();
        setGroup(group);

        Comparator<Person> comparatorName = Comparator.comparing(Person::getName);
        Comparator<Person> comparatorSurname = Comparator.comparing(Person::getSurname);
        Set<Person> group1 = new TreeSet<>(comparatorSurname.thenComparing(comparatorName));
        group.stream()
                .filter(person -> person.getAge() < 21)
                .peek(System.out::println)
                .forEach(group1::add);

        File file = new File("1.txt");
        writeGroup(group1, file);

        List<String> list = getList(file);
        System.out.println(list);


    }

    private static List<String> getList(File file) {
        List<String> list = new ArrayList<>();
        Arrays.stream(Objects.requireNonNull(getGroupFromFile(file)))
                .forEach(x -> list.add(x.getSurname() + x.getName()));
        return list;
    }

    private static Person[] getGroupFromFile(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            return (Person[]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void writeGroup(Set<Person> group1, File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            oos.writeObject(group1.toArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setGroup(List<Person> group) {
        for (int i = 0; i < 100; i++) {
            String name = "name" + (int) (Math.random() * 9 + 1);
            String surname = "surname" + (int) (Math.random() * 9 + 1);
            int age = (int) (Math.random() * 16 + 15);
            group.add(new Person(name, surname, age));
        }
    }
}
