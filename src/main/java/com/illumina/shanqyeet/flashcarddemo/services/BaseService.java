package com.illumina.shanqyeet.flashcarddemo.services;

public interface BaseService<T, U> {
    U execute(T request) throws Exception;
}
