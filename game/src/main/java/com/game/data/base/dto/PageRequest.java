package com.game.data.base.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@Getter
@Setter
public class PageRequest {
    private Optional<Integer> page;
    private int size;
    private Sort sort;
}
