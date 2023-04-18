package com.game.data.base;

import com.game.data.base.dto.PageRequest;
import com.game.data.constant.PageConstant;
import com.game.data.core.entity.BaseEntity;
import javax.transaction.Transactional;

import com.game.data.core.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;

/*
    Code by Tholv
 */

@Service
@Slf4j(topic = "AbstractServiceCRUD")
@Transactional(rollbackOn = RuntimeException.class)
@RequiredArgsConstructor
@SuppressWarnings("all")
public class AbstractServiceCRUD<T extends BaseEntity, ID extends Integer> implements ServiceCore<T, ID> {

    @Autowired
    private JpaRepository<T, ID> jpaRepository;

    @Override
    public T save(T bo) {
        try {
            log.info("saving data");
            return jpaRepository.save(bo);
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
            boolean isExist = jpaRepository.existsById(id);
            if (isExist) {
                log.info("updating data with id: {}", id);
                bo.preUpdate();
                return jpaRepository.save(bo);
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
                jpaRepository.deleteById(id);
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
        T bo = jpaRepository.findById(id).orElse(null);
        if (bo != null) {
            log.info("data with id: {} found", id);
            return bo;
        }
        return null;
    }

    @Override
    public ArrayDeque<T> findAll() {
        log.info("finding all data");
        return new ArrayDeque<>(jpaRepository.findAll());
    }

    @Override
    public Page<T> findAll(PageRequest model) {
        Integer pageDefault = PageConstant.PAGE_DEFAULT;
        Integer pageSizeDefault = PageConstant.PAGE_SIZE_DEFAULT;
        if (model.getSort() == null) {
            org.springframework.data.domain.PageRequest pageRequest = org.springframework.data.domain.PageRequest.of(model.getPage().orElse(pageDefault), pageSizeDefault);
            return jpaRepository.findAll(pageRequest);
        } else {
            org.springframework.data.domain.PageRequest pageRequest = org.springframework.data.domain.PageRequest.of(model.getPage().orElse(pageDefault), model.getSize(), model.getSort());
            return jpaRepository.findAll(pageRequest);
        }
    }
}