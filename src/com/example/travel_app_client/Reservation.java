package com.example.travel_app_client;

import java.io.Serializable;
import java.util.Date;

public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int offerId;
    private String username;
    private int isPaid;

    public Reservation(int id, int offerId, String username, int isPaid) {
        this.id = id;
        this.offerId = offerId;
        this.username = username;
        this.isPaid = isPaid;

    }


}
