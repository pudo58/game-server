package com.game.server.core.base;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;

@Service
@Slf4j(topic = "AbstractServiceCRUD")
@Transactional(rollbackOn = RuntimeException.class)
@RequiredArgsConstructor
public class AbstractServiceCRUD<T extends BaseEntity, ID extends Integer> implements ServiceCore<T, ID> {
    private final DaoCore<T, ID> daoCore;

    @Override
    public T save(T bo) {
        try {
            log.info("saving data");
            return daoCore.save(bo);
        } catch (Exception e) {
            log.error("error saving data", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public T update(T bo, ID id) {
        try {
            if (!(id instanceof Integer)) {
                log.info("id is not instance Integer");
                return null;
            }
            boolean isExist = daoCore.existsById(id);
            if (isExist) {
                log.info("updating data with id: {}", id);
                bo.preUpdate();
                return daoCore.save(bo);
            }
            log.info("data with id: {} not found", id);
            return null;
        } catch (Exception e) {
            log.error("error updating data", e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public T delete(T bo) {
        try {
            ID id = (ID) bo.getId();
            if (id != null) {
                log.info("deleting data with id: {}", id);
                daoCore.deleteById(id);
                return bo;
            }
            log.info("data with id: {} not found", id);
            return null;
        } catch (Exception e) {
            log.error("error deleting data", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public T findById(ID id) {
        log.info("finding data with id: {}", id);
        return daoCore.findById(id).orElse(null);
    }

    @Override
    public ArrayDeque<T> findAll() {
        log.info("finding all data");
        return new ArrayDeque<>(daoCore.findAll());
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        log.info("finding all data with page {} and size {}", pageable.getPageNumber(), pageable.getPageSize());
        return daoCore.findAll(pageable);
    }
}