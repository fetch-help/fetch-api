package com.fetch.persist;


import com.fetch.persist.model.Address;
import com.fetch.persist.model.Customer;
import com.fetch.persist.service.TypeLookup;
import org.junit.Test;

import java.util.Arrays;

public class GenerateJsonTest {

    @Test
    public void generateAddress()throws Exception{

        Address a = new Address();
        a.setLine1("Address line 1");
        a.setLine2("Address line 2");
        a.setLine3("Address line 3");
        a.setPostalCode("TW32B");
        a.setCountry("UK");

        System.out.println(TypeLookup.writeJson(a));
    }

    @Test
    public void generateCustomer()throws Exception{

        Customer c = new Customer("John", "Doe");
        System.out.println(TypeLookup.writeJson(c));
    }

    @Test
    public void generateCustomerAddress()throws Exception{

        Customer c = new Customer("John", "Doe");
        Address a = new Address();
        a.setLine1("Address line 1");
        a.setLine2("Address line 2");
        a.setLine3("Address line 3");
        a.setPostalCode("TW32B");
        a.setCountry("UK");

        c.setAddress(a);
        System.out.println(TypeLookup.writeJson(c));
    }

    @Test
    public void testList()throws Exception{
        Customer c1 = new Customer("John", "Doe");
        Customer c2 = new Customer("John", "Does");

        System.out.println(TypeLookup.writeJson(Arrays.asList(c1, c2)));
    }

}
