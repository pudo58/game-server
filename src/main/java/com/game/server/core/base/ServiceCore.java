package com.game.server.core.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;

@Service
public interface ServiceCore<T extends BaseEntity, ID extends Integer> {
    T save(T bo);

    T update(T bo, ID id);

    T delete(T bo);

    T findById(ID id);

    ArrayDeque<T> findAll();

    Page<T> findAll(Pageable pageable);
}
