package com.sand.ibsmis.dao.inf;

import java.util.List;

import com.sand.ibsmis.bean.Company;

public interface CompanyMapper {
	public Company getCompanyById(String companyId);  
	public List<Company> findCompanys();
	public int queryCompanyCount();
}
