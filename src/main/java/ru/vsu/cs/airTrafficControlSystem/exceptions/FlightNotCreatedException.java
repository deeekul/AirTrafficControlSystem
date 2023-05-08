package ru.vsu.cs.airTrafficControlSystem.exceptions;

public class FlightNotCreatedException extends RuntimeException {
    public FlightNotCreatedException(String msg) {
        super(msg);
    }
}
