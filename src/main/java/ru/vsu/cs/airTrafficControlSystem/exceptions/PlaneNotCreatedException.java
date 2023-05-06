package ru.vsu.cs.airTrafficControlSystem.exceptions;

public class PlaneNotCreatedException extends RuntimeException {
    public PlaneNotCreatedException(String message) {
        super(message);
    }
}
