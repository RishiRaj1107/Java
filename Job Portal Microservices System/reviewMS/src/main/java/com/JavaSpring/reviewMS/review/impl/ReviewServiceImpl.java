package com.JavaSpring.reviewMS.review.impl;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.JavaSpring.reviewMS.review.Review;
import com.JavaSpring.reviewMS.review.ReviewRepository;
import com.JavaSpring.reviewMS.review.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;

	public ReviewServiceImpl(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}

	@Override
	@Cacheable(value = "reviews", key = "#companyId")
	public List<Review> getAllReviews(Long companyId) {
		return reviewRepository.findByCompanyId(companyId);
	}

	@Override
	@CacheEvict(value = "reviews", key = "#companyId")
	public boolean addReview(Long companyId, Review review) {
		if (companyId != null && review != null) {
			review.setCompanyId(companyId);
			reviewRepository.save(review);
			return true;
		}
		return false;
	}

	@Override
	@Cacheable(value = "reviews", key = "#reviewId")
	public Review getReview(Long reviewId) {
		return reviewRepository.findById(reviewId).orElse(null);
	}

	@Override
	@CacheEvict(value = "reviews", allEntries = true)
	public boolean updateReview(Long reviewId, Review updatedReview) {
		Review review = reviewRepository.findById(reviewId).orElse(null);
		if (review != null) {
			review.setTitle(updatedReview.getTitle());
			review.setDescription(updatedReview.getDescription());
			review.setRating(updatedReview.getRating());
			review.setCompanyId(updatedReview.getCompanyId());
			reviewRepository.save(review);
			return true;
		}
		return false;
	}

	@Override
	@CacheEvict(value = "reviews", allEntries = true)
	public boolean deleteReview(Long reviewId) {
		Review review = reviewRepository.findById(reviewId).orElse(null);
		if (review != null) {
			reviewRepository.delete(review);
			return true;
		}
		return false;
	}

}
