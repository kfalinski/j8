import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by K on 2015-07-17.
 */
public class IntStreamTest {

    private final static List<String> stringList = Lists.newArrayList("1", "2", "3", "4", "5");

    private final static List<Person> persons = Lists.newArrayList();

    @Before
    public void setUp() throws Exception {
        try (Stream<String> stream = new BufferedReader(new InputStreamReader(Main.class.getClassLoader().getResourceAsStream("people.txt"))).lines()) {
            stream.forEach(line -> {
                String[] s = line.split(" ");
                Person p = new Person(s[0].trim(), Integer.parseInt(s[1]), s[2].trim());
                persons.add(p);
            });
        }
    }

    @Test
    public void test1() {
        ArrayList<String> list = Lists.newArrayList();
        IntStream.range(1, stringList.size()).forEach(i -> list.add(stringList.get(i)));
        System.out.println(list);
        List<Integer> naturals = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13);
        naturals.stream()
                .map(n -> n * 2)
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    @Test
    public void test2() throws Exception {
        List<Map<Integer, String>> mapList = new ArrayList<>();

        Map<Integer, String> map1 = new HashMap<>();
        map1.put(1, "String1");
        mapList.add(map1);

        Map<Integer, String> map2 = new HashMap<>();
        map2.put(2, "String2");
        mapList.add(map2);

        Map<Integer, String> map3 = new HashMap<>();
        map3.put(1, "String3");
        mapList.add(map3);

        Map<Integer, String> map4 = new HashMap<>();
        map4.put(2, "String4");
        mapList.add(map4);

        Map<Integer, List<String>> response = mapList.stream()
                .flatMap(map -> map.entrySet().stream())
                .collect(
                        Collectors.groupingBy(
                                Map.Entry::getKey,
                                Collectors.mapping(
                                        Map.Entry::getValue,
                                        Collectors.toList()
                                )
                        )
                );
        response.forEach((i, list) -> {
            System.out.println("Integer: " + i + " / List: " + list);
        });

    }

    @Test
    public void testMap() throws Exception {
        persons.forEach(System.out::println);
    }

    @Test
    public void testBiMap() throws Exception {
        persons.add(new Person());
        persons.add(new Person());
        Map<Integer, Map<String, List<Person>>> bimap = new HashMap<>();

        persons.forEach(
                person ->
                        bimap.computeIfAbsent(
                                person.getAge(),
                                HashMap::new
                        ).merge(
                                person.getGender(),
                                new ArrayList<>(Arrays.asList(person)),
                                (l1, l2) -> {
                                    l1.addAll(l2);
                                    return l1;
                                }
                        )
        );
        System.out.println("bimap:");
        bimap.forEach(
                (age, m) -> System.out.println(age + " ->" + m));
    }
}