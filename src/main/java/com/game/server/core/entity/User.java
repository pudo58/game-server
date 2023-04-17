package com.game.server.core.entity;

import com.game.server.core.base.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class User extends BaseEntity implements Serializable {
    private String name;
    private String password;
}
