package com.ovo;

import com.ovo.domain.LoggingExample;
import com.ovo.domain.Person;
import com.ovo.domain.Team;

/**
 * Created by ovo on 17.2.17.
 */
public class App {

    public static void main(String [ ] args) {


        Person manager = new Person(123L);

        // cannot be used until lombok intelliJ plugin is installed
        // https://plugins.jetbrains.com/idea/plugin/6317-lombok-plugin
        manager.setName("boss");

        // toString example
        System.out.println(manager);
        // Person(id=null, name=boss, manager=null)

        // @NotNull example
        try {
            manager.setFirstNameLastName(null, "Surname");
            // Exception in thread "main" java.lang.NullPointerException: firstName
            // at com.ovo.domain.Person.setFirstNameLastName(Person.java:13)
        }
        catch (Exception e) {}


        Person joe = new Person(456L);
        joe.setName("joe");
        joe.setManager(manager);
        System.out.println(joe);
        // Person(id=456, name=joe, manager=Person(id=123, name=boss, manager=null))

        // equals example
        Person joe2 = new Person(456L);
        joe2.setName("joe");
        joe2.setManager(manager);
        System.out.println(joe.equals(joe2)); //true


        // logging example
        LoggingExample.method();
        // 2017-02-17 16:36:27 INFO  LoggingExample:9 - method called

        // builder example
        Team team = Team.builder().name("A Team").member(new Person(1L)).member(new Person(2L)).build();
        System.out.println(team);
    }
}
