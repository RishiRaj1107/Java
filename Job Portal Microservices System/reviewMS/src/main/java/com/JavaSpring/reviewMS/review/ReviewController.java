package com.JavaSpring.reviewMS.review;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

	private ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@GetMapping
	public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId) {
		List<Review> reviews = reviewService.getAllReviews(companyId);
		return new ResponseEntity<>(reviews, HttpStatus.OK);
	}

	@PostMapping
	@CacheEvict(value = "reviews", key = "#companyId")
	public ResponseEntity<String> addReview(@RequestParam Long companyId, @RequestBody Review review) {
		boolean isReviewSaved = reviewService.addReview(companyId, review);
		if (isReviewSaved) {
			return new ResponseEntity<>("Review Added", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Review Not Added", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{reviewId}")
	public ResponseEntity<Review> getReview(@PathVariable Long reviewId) {
		Review review = reviewService.getReview(reviewId);
		return new ResponseEntity<>(review, HttpStatus.OK);
	}

	@PutMapping("/{reviewId}")
	@CacheEvict(value = "reviews", allEntries = true)
	public ResponseEntity<String> updateReview(@PathVariable Long reviewId, @RequestBody Review review) {
		boolean isReviewUpdated = reviewService.updateReview(reviewId, review);
		if (isReviewUpdated) {
			return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Updation failed", HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{reviewId}")
	@CacheEvict(value = "reviews", allEntries = true)
	public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
		boolean isDeletedReview = reviewService.deleteReview(reviewId);
		if (isDeletedReview) {
			return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Deletion failed", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/averageRating")
	public Double getAverageReview(@RequestParam Long companyId) {
		List<Review> reviewList = reviewService.getAllReviews(companyId);
		return reviewList.stream().mapToDouble(Review::getRating).average().orElse(0.0);
	}
}