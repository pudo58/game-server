package com.game.data.base;
import com.game.data.base.dto.PageRequest;
import com.game.data.core.entity.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayDeque;

/*
    Code by Tholv
 */
@RestController
@RequiredArgsConstructor
@SuppressWarnings("all")
public class AbstractControllerCRUD<T extends BaseEntity, ID extends Integer> implements ServiceCore<T, ID>{
    @Autowired
    private AbstractServiceCRUD<T, ID> abstractServiceCRUD;


    @PostMapping("/insert")
    @Override
    public T save(@RequestBody T bo) {
        return abstractServiceCRUD.save(bo);
    }

    @PostMapping("/update/{id}")
    @Override
    public T update( @RequestBody T bo,@PathVariable ID id) {
        return abstractServiceCRUD.update(bo, id);
    }

    @GetMapping("/delete/{id}")
    @Override
    public T delete(@RequestBody T bo) {
        return abstractServiceCRUD.delete(bo);
    }

    @GetMapping("/find/{id}")
    @Override
    public T findById(@PathVariable ID id) {
        return abstractServiceCRUD.findById(id);
    }

    @GetMapping("/findAll")
    @Override
    public ArrayDeque<T> findAll() {
        return abstractServiceCRUD.findAll();
    }

    @GetMapping("/findAllPage")
    @Override
    public Page<T> findAll(@RequestBody PageRequest pageable) {
        return abstractServiceCRUD.findAll(pageable);
    }
}
