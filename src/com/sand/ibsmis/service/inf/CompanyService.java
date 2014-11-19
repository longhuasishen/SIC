package com.sand.ibsmis.service.inf;

import java.util.List;

import com.sand.ibsmis.bean.Company;
import com.sand.ibsmis.bean.Role;

public interface CompanyService {
	public List<Company> findCompanys();
	public int queryCompanyCount();
}
