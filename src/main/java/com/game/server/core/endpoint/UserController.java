package com.game.server.core.endpoint;

import com.game.server.core.base.AbstractControllerCRUD;
import com.game.server.core.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController extends AbstractControllerCRUD<User,Integer> {

}
