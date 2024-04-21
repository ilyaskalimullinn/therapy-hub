package ru.itis.therapy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.therapy.model.Speciality;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
}
