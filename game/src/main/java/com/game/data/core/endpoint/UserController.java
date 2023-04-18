package com.game.data.core.endpoint;

import com.game.data.base.AbstractControllerCRUD;
import com.game.data.core.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController extends AbstractControllerCRUD<User,Integer> {
}
