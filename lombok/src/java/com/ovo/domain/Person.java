package com.ovo.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
// implicit @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor
public class Person {

    private final Long id;
    private String name;
    private Person manager;

    public void setFirstNameLastName(@NonNull  String firstName, @NonNull String lastName ) {
        this.name = firstName + " " + lastName;
    }

}
