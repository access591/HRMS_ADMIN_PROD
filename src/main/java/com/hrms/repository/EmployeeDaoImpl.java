package com.hrms.repository;


import org.springframework.stereotype.Repository;

import com.hrms.dao.AbstractGenericDao;
import com.hrms.model.Employee;


@Repository
public class EmployeeDaoImpl extends AbstractGenericDao<Employee> implements EmployeeDao {



}
