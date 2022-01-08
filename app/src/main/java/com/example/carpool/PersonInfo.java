package com.example.carpool;

public class PersonInfo {
    String clocation = null;
    String destination = null;
    boolean reached = true;

    public PersonInfo() {
    }

    public PersonInfo(String clocation, String destination, boolean reached) {
        this.clocation = clocation;
        this.destination = destination;
        this.reached = reached;
    }

    public void setClocation(String clocation) {
        this.clocation = clocation;
    }

    public String getClocation() {
        return this.clocation;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setReached(boolean reached) {
        this.reached = reached;
    }

    public boolean getReached() {
        return this.reached;
    }
}
