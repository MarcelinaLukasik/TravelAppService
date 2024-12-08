package com.example.travel_app_client;

import java.io.Serializable;
import java.util.Date;

public class Offer implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String country;
    private String city;
    private double price;
    private String hotelName;
    private Date startDate;
    private Date endDate;
    private String imageUrl;
    private String insurance;


    public Offer(int id, String country, String city, double price, String hotelName, Date startDate, Date endDate, String imageUrl, String insurance) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.price = price;
        this.hotelName = hotelName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.insurance = insurance;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public double getPrice() {
        return price;
    }

    public String getHotelName() {
        return hotelName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public String getInsurance() {
        return insurance;
    }


}
