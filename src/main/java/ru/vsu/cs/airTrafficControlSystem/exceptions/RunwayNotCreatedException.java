package ru.vsu.cs.airTrafficControlSystem.exceptions;

public class RunwayNotCreatedException extends RuntimeException{
    public RunwayNotCreatedException(String message) {
        super(message);
    }
}
