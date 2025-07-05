package com.JavaSpring.companyMS.company;

import java.util.List;

public interface CompanyService {

	List<Company> getAllCompanies();
	
	Company getCompanyById(Long id);
	
	Company createCompany(Company company);
	
	Company updateCompany(Long id, Company company);
	
	boolean deleteCompany(Long id);
}
