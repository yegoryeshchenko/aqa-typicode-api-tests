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
}/*{
        "id": 1,
        "name": "Leanne Graham",
        "username": "Bret",
        "email": "Sincere@april.biz",
        "address": {
            "street": "Kulas Light",
            "suite": "Apt. 556",
            "city": "Gwenborough",
            "zipcode": "92998-3874",
            "geo": {
                "lat": "-37.3159",
                "lng": "81.1496"
            }
        },
        "phone": "1-770-736-8031 x56442",
        "website": "hildegard.org",
        "company": {
            "name": "Romaguera-Crona",
            "catchPhrase": "Multi-layered client-server neural-net",
            "bs": "harness real-time e-markets"
        }
    }*/
