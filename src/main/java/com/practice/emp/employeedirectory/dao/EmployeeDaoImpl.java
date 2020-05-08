
package com.practice.emp.employeedirectory.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import com.practice.emp.employeedirectory.ejb.EmployeeService;
import com.practice.emp.employeedirectory.model.EmployeePojo;

/**
 * Description: Implements the C-R-U-D API for the database
 */
@Named
@ApplicationScoped
public class EmployeeDaoImpl implements EmployeeDao, Serializable {
    /** explicitly set serialVersionUID */
    private static final long serialVersionUID = 1L;

    protected ServletContext sc;

    @EJB
    protected EmployeeService employeeService;

    @Inject
    public EmployeeDaoImpl(ServletContext sc) {
        super();
        this.sc = sc;
    }

    // delegate all C-R-U-D operations to EmployeeService

    @Override
    public List<EmployeePojo> readAllEmployees() {
        sc.log("reading all employees");
        List<EmployeePojo> employees = new ArrayList<>();
        try {
            employees = employeeService.findAllEmployees();
        } catch (Exception e) {
            sc.log("something went wrong finding all employees: " + e.getLocalizedMessage());
        }
        return employees;
    }

    @Override
    public EmployeePojo createEmployee(EmployeePojo employee) {
        sc.log("creating an employee");
        try {
            employee = employeeService.persistEmployee(employee);
        } catch (Exception e) {
            sc.log("something went wrong persisting employee: " + e.getLocalizedMessage());
        }
        return employee;
    }

    @Override
    public EmployeePojo readEmployeeById(int employeeId) {
        EmployeePojo employee = null;
        try {
            employee = employeeService.findEmployeeByPrimaryKey(employeeId);
        } catch (Exception e) {
            sc.log("something went wrong finding employee: " + employeeId + " " + e.getLocalizedMessage());
        }
        return employee;
    }

    @Override
    public EmployeePojo updateEmployee(EmployeePojo employee) {
        sc.log("updating specific employee");
        EmployeePojo updatedEmployee = null;
        try {
            updatedEmployee = employeeService.mergeEmployee(employee);
        } catch (Exception e) {
            sc.log("something went wrong merging changes to employee: " + e.getLocalizedMessage());
        }
        return updatedEmployee;
    }

    @Override
    public void deleteEmployeeById(int employeeId) {
        sc.log("deleting a specific employee");
        try {
            employeeService.removeEmployee(employeeId);
        } catch (Exception e) {
            sc.log("something went wrong removing employee: " + employeeId + " " + e.getLocalizedMessage());
        }
    }

}