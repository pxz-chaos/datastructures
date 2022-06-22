package Day03.Tree.BST;

/**
 * @ClassName Person
 * @Description TODO
 * @Author Zhang Peixin
 * @Date 2021/11/30 10:34
 * @Version 1.0
 */
public class Person implements Comparable<Person> {
    int age;
    String name;

    public Person(int age) {
        this.age = age;
    }

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public int compareTo(Person p) {
       /* if (this.age > p.age) return 1;
        if (this.age < p.age) return -1;
        return 0;*/
        return this.age-p.age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +name+
                '}';
    }
}
