package com.example.carpool;

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
    boolean reached;

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
     * @param reached boolean value whether the user has reached destination or not
     */
    public PersonInfo(String cLocation, String destination, String phoneNumber, boolean reached) {
        this.cLocation = cLocation;
        this.destination = destination;
        this.phoneNumber = phoneNumber;
        this.reached = reached;
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

    public void setReached(boolean reached) {
        this.reached = reached;
    }

    public boolean getReached() {
        return this.reached;
    }
}
