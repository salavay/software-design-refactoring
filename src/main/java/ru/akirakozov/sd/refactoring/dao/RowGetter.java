package ru.akirakozov.sd.refactoring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface RowGetter<T> {

    T apply(ResultSet rs) throws SQLException;

}
