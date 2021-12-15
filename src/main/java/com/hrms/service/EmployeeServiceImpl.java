package com.hrms.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hrms.model.Employee;
import com.hrms.repository.EmployeeDao;
@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public List<Employee> getAllEmployees() {
		
		return employeeDao.findAll();
	}
}
