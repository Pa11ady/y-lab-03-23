package io.ylab.intensive.lesson05.eventsourcing.db;

import io.ylab.intensive.lesson05.eventsourcing.Person;

public interface DbClient {
    void insert(Person person);

    void update(Person person);

    void delete(Long personId);

    boolean containsPerson(Long personId);
}
