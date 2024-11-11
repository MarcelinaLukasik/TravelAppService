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


    public Offer(int id, String country, String city, double price, String hotelName, Date startDate, Date endDate) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.price = price;
        this.hotelName = hotelName;
        this.startDate = startDate;
        this.endDate = endDate;
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


    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", price=" + price +
                ", hotelName='" + hotelName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
