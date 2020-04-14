package com.fetch.persist;


import com.fetch.persist.model.Address;
import com.fetch.persist.model.Customer;
import com.fetch.persist.model.Product;
import com.fetch.persist.model.Supplier;
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
    public void generateSupplier()throws Exception{
        Supplier c = new Supplier();
        c.setName("Joes Groceries");
        c.setContactName("Joe Supplier");
        System.out.println(TypeLookup.writeJson(c));
    }

    @Test
    public void generateProduct()throws Exception{
        Product c = new Product();
        c.setName("Milk");
        c.setPrice(3.00);
        c.setSupplierId(3L);

        System.out.println(TypeLookup.writeJson(c));
    }



    @Test
    public void generateCustomer()throws Exception{

        Customer c = new Customer();
        c.setFirstName("John");
        c.setLastName("Dane");
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
    public void generateCustomerAddress()throws Exception{

        Customer c = new Customer();
        c.setFirstName("John");
        c.setLastName("Dane");
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
        Customer c1 = new Customer();
        c1.setFirstName("John");
        c1.setLastName("Dane");
        Customer c2 = new Customer();
        c2.setFirstName("John");
        c2.setLastName("J");

        System.out.println(TypeLookup.writeJson(Arrays.asList(c1, c2)));
    }

}
