package ru.itis.therapy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.therapy.model.Appointment;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllBySpecialistId(Long specialistId);
    List<Appointment> findAllByClientId(Long clientId);
}
