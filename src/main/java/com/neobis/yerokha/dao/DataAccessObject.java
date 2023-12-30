package com.neobis.yerokha.dao;

import java.sql.Connection;
import java.util.List;

public abstract class DataAccessObject <T extends DataTransferObject> {

    protected final Connection connection;

    public DataAccessObject(Connection connection) {
        this.connection = connection;
    }

    public abstract void create(T dto);
    public abstract T findById(Long id);
    public abstract List<T> findAll();
    public abstract T update(T dto);
    public abstract int deleteById(Long id);
}
