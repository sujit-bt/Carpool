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

    /**
     * Changes the cLocation attribute
     *
     * @param cLocation current location
     */
    public void setcLocation(String cLocation) {
        this.cLocation = cLocation;
    }

    /**
     * Gets the Current location attribute
     *
     * @return current location as a string
     */
    public String getcLocation() {
        return this.cLocation;
    }

    /**
     * Sets destination attribute
     *
     * @param destination destination
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Gets destination attribute
     *
     * @return destination attribute as a string
     */
    public String getDestination() {
        return this.destination;
    }

    /**
     * Sets phoneNumber attribute
     *
     * @param phoneNumber phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * gets phone number attribute
     *
     * @return phone number as a String
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * sets connected to attribute
     *
     * @param user_id inputted user ID as a string
     */
    public void setConnectedTo(String user_id) {
        this.connectedTo = user_id;
    }

    /**
     * gets the connectedTo attribute
     *
     * @return user ID stored in the connectedTo attribute as a String
     */
    public String getConnectedTo() {
        return this.connectedTo;
    }

    /**
     * Changes all attributes
     *
     * @param s String to which all attributes should be changed to
     */
    public void setAllAttributes(String s) {
        this.cLocation = s;
        this.destination = s;
        this.phoneNumber = s;
        this.connectedTo = s;
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
