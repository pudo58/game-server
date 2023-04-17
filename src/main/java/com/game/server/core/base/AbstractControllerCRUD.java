package com.game.server.core.base;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayDeque;
@RequiredArgsConstructor
public class AbstractControllerCRUD<T extends BaseEntity, ID extends Integer> {
    @Autowired
    private  ServiceCore<T, ID> serviceCore;

    @GetMapping("/find/{id}")
    public T findById(@PathVariable ID id) {
        return serviceCore.findById(id);
    }

    @GetMapping("/findAll")
    public ArrayDeque<T> findAll() {
        return serviceCore.findAll();
    }

    @GetMapping("/findAllPage")
    public Page<T> findAllPage(@RequestBody Pageable pageable) {
        return serviceCore.findAll(pageable);
    }

    @PostMapping("/insert")
    public T save(@RequestBody T bo) {
        return serviceCore.save(bo);
    }

    @PostMapping("/update/{id}")
    public T update(@RequestBody T bo,@PathVariable ID id) {
        return serviceCore.update(bo, id);
    }
}
