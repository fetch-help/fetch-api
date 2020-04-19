package com.fetch.persist;


import com.fetch.persist.model.*;
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
    public void generateMerchant()throws Exception{
        Merchant c = new Merchant();
        c.setName("Joes Groceries");
        c.setContactName("Joe Merchant");
        System.out.println(TypeLookup.writeJson(c));
    }

    @Test
    public void generateProduct()throws Exception{
        Product c = new Product();
        c.setName("Milk");
        c.setPrice(3.00);
        c.setMerchantId(3L);

        System.out.println(TypeLookup.writeJson(c));
    }

    @Test
    public void generateBankAccount()throws Exception{
        BankAccount c = new BankAccount();
        c.setAccountHolder("holder");
        String json = TypeLookup.writeJson(c);
        System.out.println(json);
        c = TypeLookup.getObject(json, BankAccount.class);
        System.out.println(c);
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
