package ru.itis.therapy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.therapy.model.Specialty;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
}
