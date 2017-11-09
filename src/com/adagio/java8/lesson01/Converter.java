package com.adagio.java8.lesson01;

@FunctionalInterface
public interface Converter<F, T> {
    T convert(F from);
}
