
package com.practice.emp.employeedirectory.dao;

import java.util.List;

import com.practice.emp.employeedirectory.model.EmployeePojo;

/**
 * Description: API for the database C-R-U-D operations
 */
public interface EmployeeDao {

    // C
    public EmployeePojo createEmployee(EmployeePojo employee);
    // R
    public EmployeePojo readEmployeeById(int employeeId);
    public List<EmployeePojo> readAllEmployees();
    // U
    public EmployeePojo updateEmployee(EmployeePojo employee);
    // D
    public void deleteEmployeeById(int employeeId);

}