import RecursiveBST.*;

import java.util.Random;
import java.util.Set;
import java.util.HashSet;


public class Person implements Comparable<Person> {
    // fields
    private String name;
    private String surname;

    // constructor
    Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    // compareTo (Comparable)
    @Override
    public int compareTo(Person p) {
        int surRes = surname.compareTo(p.surname);
        return ( (surRes != 0) ? surRes : name.compareTo(p.name));
    }

    // toString (Object)
    @Override
    public String toString() {
        return surname + " " + name;
    }



    public static void main(String[] args) {
        // some names, surnames
        String[] names = {"Kamil", "Piotr", "Paweł", "Wojciech", "Zbigniew", "Tomasz", "Roman", "Michał", "Marcin", "Andrzej"};
        String[] surnames = {"Kowalik", "Nowak", "Kowalski", "Kwiatkowski", "Kwiatek", "Nowakowski"};

        // set for Person objects
        int n = 20;
        Set<Person> personSet = new HashSet<>(n);

        Random gen = new Random();

        // generate some random Person objects
        for (int i=0; i<n; i++)
            personSet.add( new Person( names[(int)(gen.nextFloat() * names.length)], surnames[(int)(gen.nextFloat() * surnames.length)] ) );

        /* ... */

        // tree object
        RecursiveBST<Person> tree = new RecursiveBST<Person>();

        // create tree from set
        personSet.forEach( tree::insert );

        /* ... */

        // print
        tree.printTree();

        System.out.println("============");

        // max/min
        tree.printMin();
        tree.printMax();
    }
}
