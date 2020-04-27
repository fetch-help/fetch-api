package com.fetch.web.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fetch.web.model.Cart;
import com.fetch.web.model.CartItem;
import org.junit.Test;

import java.util.Arrays;

public class CartTest {

    @Test
    public void test()throws Exception{
        Cart c = new Cart();
        CartItem ci = new CartItem();
        ci.setProductId(20);
        ci.setQuantity(1);
        c.addItem(ci);

        ObjectMapper om = new ObjectMapper();
        System.out.println(om.writeValueAsString(c));
    }
}
