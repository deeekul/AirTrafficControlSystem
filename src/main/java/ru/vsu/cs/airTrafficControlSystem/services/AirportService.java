package ru.vsu.cs.airTrafficControlSystem.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AirportService {
}
