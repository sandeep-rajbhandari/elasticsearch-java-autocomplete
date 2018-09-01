package com.search.demo.repository;

public class BaseRepository {
    protected String[] indices;
    protected String[] types;

    public BaseRepository(String[] indices,String[] types) {
        this.indices=indices;
        this.types=types;
    }
}
