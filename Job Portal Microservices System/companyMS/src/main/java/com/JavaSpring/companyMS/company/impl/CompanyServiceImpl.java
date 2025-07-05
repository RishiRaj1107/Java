package com.JavaSpring.companyMS.company.impl;

import com.JavaSpring.companyMS.company.Company;
import com.JavaSpring.companyMS.company.CompanyRepository;
import com.JavaSpring.companyMS.company.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

	private final CompanyRepository companyRepository;
	
	public CompanyServiceImpl(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	@Override
	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	@Override
	public Company getCompanyById(Long id) {
		return companyRepository.findById(id).orElse(null);
	}

	@Override
	public Company createCompany(Company company) {
		return companyRepository.save(company);
	}

	@Override
	public Company updateCompany(Long id, Company company) {
		if (companyRepository.existsById(id)) {
			company.setId(id);
			return companyRepository.save(company);
		}
		return null;
	}

	@Override
	public boolean deleteCompany(Long id) {
		if (companyRepository.existsById(id)) {
			companyRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
