package com.practice.emp.employeedirectory.jsf;

import java.io.Serializable;

import javax.faces.annotation.ManagedProperty;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.practice.emp.employeedirectory.model.EmployeePojo;

@Named("newEmployee")
@ViewScoped
public class NewEmployeeView implements Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;

    private String firstName, lastName, email, title;
    private Double salary;
    private Integer empId;
    // TODO - add additional required fields

    @Inject
    @ManagedProperty("#{employeeController}")
    protected EmployeeController employeeController;

    public NewEmployeeView() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }



    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    public String getEmail() {
        return email;
    }



    public void setEmail(String email) {
        this.email = email;
    }



    public String getTitle() {
        return title;
    }



    public void setTitle(String title) {
        this.title = title;
    }



    public Double getSalary() {
        return salary;
    }



    public void setSalary(Double salary) {
        this.salary = salary;
    }



    public Integer getEmpId() {
        return empId;
    }



    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public void addEmployee() {
        if (allNotNullOrEmpty(firstName, lastName)) {
            EmployeePojo theNewEmployee = new EmployeePojo();
            theNewEmployee.setFirstName(firstName);
            theNewEmployee.setLastName(lastName);
            theNewEmployee.setEmail(email);
            theNewEmployee.setTitle(title);
            theNewEmployee.setSalary(salary);
            // TODO - additional fields

            // this Managed Bean does not know how to 'do' anything, ask controller
            employeeController.addNewEmployee(theNewEmployee);

            //clean up
            employeeController.toggleAdding();
            setFirstName(null);
            setLastName(null);
            setEmail(null);
            setTitle(null);
            setSalary(null);
        }
    }

    static boolean allNotNullOrEmpty(final Object... values) {
        if (values == null) {
            return false;
        }
        for (final Object val : values) {
            if (val == null) {
                return false;
            }
            if (val instanceof String) {
                String str = (String)val;
                if (str.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
}