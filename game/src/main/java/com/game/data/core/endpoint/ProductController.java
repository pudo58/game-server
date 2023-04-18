package com.game.data.core.endpoint;

import com.game.data.base.AbstractControllerCRUD;
import com.game.data.core.entity.Product;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController extends AbstractControllerCRUD<Product,Integer> {
}
