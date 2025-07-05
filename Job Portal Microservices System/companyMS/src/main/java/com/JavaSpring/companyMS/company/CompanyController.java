package com.JavaSpring.companyMS.company;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/companies")
public class CompanyController {
	
	private final CompanyService companyService;

	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@GetMapping
	@Cacheable(value = "companies", key = "'allCompanies'")
	public List<Company> getAllCompanies() {
		return companyService.getAllCompanies();
	}
	
	@GetMapping("/{id}")
	@Cacheable(value = "companies", key = "#id")
	public Company getCompanyById(@PathVariable Long id) {
		return companyService.getCompanyById(id);
	}
	
	@PostMapping
	@CacheEvict(value = "companies", allEntries = true)
	public ResponseEntity<Company> createCompany(@RequestBody Company company) {
		Company createdCompany = companyService.createCompany(company);
		return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@CacheEvict(value = "companies", allEntries = true)
	public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody Company company) {
		Company updatedCompany = companyService.updateCompany(id, company);
		if (updatedCompany != null) {
			return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	@CacheEvict(value = "companies", allEntries = true)
	public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
		if (companyService.deleteCompany(id)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
