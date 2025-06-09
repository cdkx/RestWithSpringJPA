package ru.eremin.restwithspringjpa.mapper;


public interface AbstractMapper<E, D> {
    E toEntity(D dto);

    D toDto(E entity);
}
