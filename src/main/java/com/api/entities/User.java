package com.api.entities;

import lombok.Data;

@Data
public class User {

    public int id;
    public String name;
    public String username;
    public String email;
    public Address address;
    public String phone;
    public String website;
    public Company company;

    @Data
    public static class Address {
        public String street;
        public String suite;
        public String city;
        public String zipcode;
        public Geo geo;
    }

    @Data
    public static class Company {
        public String name;
        public String catchPhrase;
        public String bs;
    }

    @Data
    public static class Geo {
        public String lat;
        public String lng;
    }
}