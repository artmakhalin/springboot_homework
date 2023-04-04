package com.makhalin.springboot_homework.repository;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CPredicate {

    private final List<Predicate> predicates = new ArrayList<>();

    public static CPredicate builder() {
        return new CPredicate();
    }

    public <T> CPredicate add(T object, Function<T, Predicate> function) {
        if (object != null) {
            predicates.add(function.apply(object));
        }

        return this;
    }

    public Predicate[] getPredicateArray() {
        return predicates.toArray(Predicate[]::new);
    }
}
