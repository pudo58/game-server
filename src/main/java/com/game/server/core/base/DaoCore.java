package com.game.server.core.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaoCore<T extends BaseEntity,ID extends Integer> extends JpaRepository<T,ID> {
}
