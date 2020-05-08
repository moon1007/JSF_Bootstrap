
package com.practice.emp.employeedirectory.jsf;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Session;
import javax.servlet.ServletContext;

import com.practice.emp.employeedirectory.dao.EmployeeDao;
import com.practice.emp.employeedirectory.model.EmployeePojo;
import com.practice.emp.employeedirectory.model.EmployeePojoListener;

/**
 * Description: Responsible for collection of Employee DTO's in XHTML (list)
 * <h:dataTable> </br>
 * Delegates all C-R-U-D behaviour to DAO
 */
@Named("employeeController")
@SessionScoped
public class EmployeeController implements Serializable {
    private static final long serialVersionUID = 1L;

    protected FacesContext facesContext;
    protected ServletContext sc;
    protected EmployeeDao employeeDao;
    protected List<EmployeePojo> employees;
    private boolean isshowAdd = false;

    private String empFirstName, empLastName, empEmail, empTitle;
    private Double empSalary;
    private Integer empId;
    private LocalDateTime empCreatedDate, empUpdatedDate;
    private Integer empVersion;

    public String getFirstName() {
        return empFirstName;
    }

    public void setFirstName(String firstName) {
        this.empFirstName = firstName;
    }

    public String getlastName() {
        return empLastName;
    }

    public void setlastName(String lastName) {
        this.empLastName = lastName;
    }

    public String getEmail() {
        return empEmail;
    }

    public void setEmail(String email) {
        this.empEmail = email;
    }

    public String getTitle() {
        return empTitle;
    }

    public void setTitle(String title) {
        this.empTitle = title;
    }

    public Double getSalary() {
        return empSalary;
    }

    public void setSalary(Double salary) {
        this.empSalary = salary;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public LocalDateTime getEmpCreatedDate() {
        return empCreatedDate;
    }

    public void setEmpCreatedDate(LocalDateTime createdDate) {
        this.empCreatedDate = createdDate;
    }

    public LocalDateTime getEmpUpdatedDate() {
        return empUpdatedDate;
    }

    public void setEmpUpdatedDate(LocalDateTime updatedDate) {
        this.empUpdatedDate = updatedDate;
    }

    public Integer getEmpVersion() {
        return empVersion;
    }

    public void setEmpVersion(Integer version) {
        this.empVersion = version;
    }

    @Inject
    public EmployeeController(FacesContext facesContext, ServletContext sc, EmployeeDao employeeDao) {
        this.facesContext = facesContext;
        this.sc = sc;
        this.employeeDao = employeeDao;
    }

    public void loadEmployees() {
        sc.log("refreshing employees");
        employees = employeeDao.readAllEmployees();
        setEmployees(employees);
    }

    public List<EmployeePojo> getEmployees() {
        return this.employees;
    }

    public void setEmployees(List<EmployeePojo> employees) {
        this.employees = employees;
    }

    /**
     * Toggles the add employee mode which determines whether the addEmployee form
     * is rendered
     */

    public void toggleAdding() {
        setShowAdd(!isshowAdd);
    }

    // This getter and setter verfies if the new employee form displayed or not.
    // Controller works with form
    public boolean getShowAdd() {
        return isshowAdd;
    }

    public void setShowAdd(boolean isshowAdd) {
        this.isshowAdd = isshowAdd;
    }

    public void editEmployee(EmployeePojo employee) {
        EmployeePojo foundEmp = employeeDao.readEmployeeById(employee.getId());
        String foundEmpFirstName = foundEmp.getFirstName();
        System.out.println(employee.getId() + " : " + foundEmpFirstName);

        for (EmployeePojo existing : getEmployees()) {
            existing.setEditable(false);
        }
        employee.setEditable(true);

    }

    public String updateEmployee(EmployeePojo employee) {

        EmployeePojo employeeToUpdateEmployeed = employeeDao.readEmployeeById(employee.getId());
        if (employeeToUpdateEmployeed == null) {
            // someone else deleted it
            facesContext.addMessage(null,
                    new FacesMessage(SEVERITY_ERROR, "Employee record missing, please refresh", null));
        } else {
            employeeToUpdateEmployeed = employeeDao.updateEmployee(employee);
            if (employeeToUpdateEmployeed == null) {
                // OptimisticLockException - someone else altered it 'faster' than we could hit
                // the 'save' button

                facesContext.addMessage(null,
                        new FacesMessage(SEVERITY_ERROR, "Employee record out-of-date, please refresh", null));
            } else {
                employeeToUpdateEmployeed.setEditable(false);
                int idx = employees.indexOf(employee);
                employees.remove(idx);
                employees.add(idx, employeeToUpdateEmployeed);
            }
        }
        return null; // current page
    }

    public String cancelUpdate(EmployeePojo employee) {
        System.out.println("Cancel Edit");

        employee.setEditable(false);
        loadEmployees();
        return null; // current page
    }

    public void deleteEmployee(int empId) {
        EmployeePojo employeePojoToBeRemoted = employeeDao.readEmployeeById(empId);
        if (employeePojoToBeRemoted != null) {
            employeeDao.deleteEmployeeById(empId);
            employees.remove(employeePojoToBeRemoted);
        }
    }

    public void addNewEmployee(EmployeePojo theNewEmployee) {
        // TODO
        EmployeePojo employeeToCreate = employeeDao.createEmployee(theNewEmployee);

        employeeToCreate.setFirstName(empFirstName);
        employeeToCreate.setLastName(empLastName);
        employeeToCreate.setEmail(empEmail);
        employeeToCreate.setTitle(empTitle);
        employeeToCreate.setSalary(empSalary);
        // employeeToCreate.setCreatedDate();
        // employeeToCreate.setUpdatedDate(empUpdatedDate);
        // employeeToCreate.setVersion(empVersion);

        employees.add(employeeToCreate);
        loadEmployees();
    }
}