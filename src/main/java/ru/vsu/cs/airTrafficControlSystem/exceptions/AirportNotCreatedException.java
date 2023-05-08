package ru.vsu.cs.airTrafficControlSystem.exceptions;

public class AirportNotCreatedException extends RuntimeException {
    public AirportNotCreatedException(String message) {
        super(message);
    }
}
