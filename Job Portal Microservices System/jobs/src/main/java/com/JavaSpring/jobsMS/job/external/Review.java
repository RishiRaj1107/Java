package com.JavaSpring.jobsMS.job.external;

public class Review {

	private Long id;
	private String title;
	private String description;
	private Double rating;
	private Company company;
	private Review review;
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public Review getReview() {
		return review;
	}
	
	public void setReview(Review review) {
		this.review = review;
	}
	
	public Double getRating() {
		return rating;
	}
}
