package ru.itis.therapy.service;

import ru.itis.therapy.dto.request.CreateAppointmentRequest;
import ru.itis.therapy.model.Appointment;

import java.util.List;

public interface AppointmentService {
    void create(CreateAppointmentRequest request, Long clientId);
    void approve(Long appointmentId, Long specialistId);
    List<Appointment> getAllByUserId(Long userId);
}
