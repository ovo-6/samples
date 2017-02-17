package com.ovo.domain;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

import java.util.List;

@Data
@Builder
public class Team {

    private String name;

    @Singular
    private List<Person> members;

}
