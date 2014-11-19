package com.sand.ibsmis.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sand.ibsmis.bean.Company;
import com.sand.ibsmis.bean.Role;
import com.sand.ibsmis.dao.inf.CompanyMapper;
import com.sand.ibsmis.dao.inf.RoleMapper;
import com.sand.ibsmis.service.inf.CompanyService;
import com.sand.ibsmis.service.inf.RoleService;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
	private static Log logger = LogFactory.getLog(CompanyServiceImpl.class);
	private CompanyMapper companyMapper;
	@Autowired
	public void setCompanyMapper(CompanyMapper companyMapper) {
		this.companyMapper = companyMapper;
	}
	public List<Company> findCompanys() {
		return companyMapper.findCompanys();
	}
	public int queryCompanyCount() {
		return companyMapper.queryCompanyCount();
	}
	
	
}
