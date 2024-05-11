package ru.itis.therapy.service;

import ru.itis.therapy.dto.request.CreateReviewRequest;
import ru.itis.therapy.model.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllByClientId(Long clientId);
    void create(CreateReviewRequest request, Long clientId, Long specialistId);
}
