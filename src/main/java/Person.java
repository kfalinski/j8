import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by K on 2015-07-17.
 */
@Getter
@NoArgsConstructor
public class Person {

    private String name;

    private int age;

    private String gender;

    public Person(String name, int age,String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String toString() {
        return "Person [" + this.name + ", " + this.age + "]";
    }
}