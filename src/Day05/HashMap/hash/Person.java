package Day05.HashMap.hash;

import java.util.Objects;
@SuppressWarnings("unused")
public class Person {
    int age;
    double height;
    String name;

    public Person(int age, double height, String name) {
        this.age = age;
        this.height = height;
        this.name = name;
    }

    public Person() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;//内存地址是一样的，铁定就是一样的
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Double.compare(person.height, height) == 0 && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
      /*  int hashCode=1;
        hashCode= hashCode*31+Integer.hashCode(age);
        hashCode = hashCode * 31 + Double.hashCode(height);
        hashCode = hashCode * 31 + (name != null ? name.hashCode() : 0);
        return hashCode;*/
        return Objects.hash(age, height, name);
//        return toHashCode(age, height, name);
    }

    private int toHashCode(Object... values) {
        return hashCode1(values);
    }

    private static int hashCode1(Object[] a) {
        if (a == null) {
            return 0;
        }

        int result = 1;

        for (Object element : a) {
            result = 31 * result + (element == null ? 0 : element.hashCode());
        }

        return result;
    }

}
