
package com.practice.emp.employeedirectory.model;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class EmployeePojoListener {

    @PrePersist
    public void setCreatedOnDate(EmployeePojo emp) {
        LocalDateTime now = LocalDateTime.now();
        emp.setCreatedDate(now);
        emp.setUpdatedDate(now);
    }

    @PreUpdate
    public void setUpdatedDate(EmployeePojo emp) {
        emp.setUpdatedDate(LocalDateTime.now());
    }

}