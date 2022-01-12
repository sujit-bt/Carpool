package com.example.carpool;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * An object a user.
 * Holds information about the user's current location, destination, phone number, and if the destination is reached.
 *
 * @author Sujit Patil
 * @since openJDK 11.0.11
 * @version 1.0
 */
public class PersonInfo {
    String cLocation;
    String destination;
    String phoneNumber;
    String connectedTo;

    /**
     * Empty constructor so that this class can be used to connect to Realtime Database.
     * Do NOT delete.
     */
    public PersonInfo() {
    }

    /**
     * Constructor for PersonInfo class
     *
     * @param cLocation the user's current location
     * @param destination the user's destination
     * @param phoneNumber the user's phone number
     */
    public PersonInfo(String cLocation, String destination, String phoneNumber) {
        this.cLocation = cLocation;
        this.destination = destination;
        this.phoneNumber = phoneNumber;
        this.connectedTo = "";
    }

    public void setcLocation(String cLocation) {
        this.cLocation = cLocation;
    }

    public String getcLocation() {
        return this.cLocation;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setConnectedTo(String user_id) {
        this.connectedTo = user_id;
    }

    public String getConnectedTo() {
        return this.connectedTo;
    }

    public void logOutUser() {
        this.cLocation = "logout";
        this.destination = "logout";
        this.phoneNumber = "logout";
        this.connectedTo = "logout";
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("cLocation", cLocation);
        result.put("destination", destination);
        result.put("phoneNumber", phoneNumber);
        result.put("connectedTo", connectedTo);

        return result;
    }
}
