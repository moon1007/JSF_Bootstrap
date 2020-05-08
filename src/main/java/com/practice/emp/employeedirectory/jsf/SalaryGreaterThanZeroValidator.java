package com.practice.emp.employeedirectory.jsf;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("com.practice.emp.employeedirectory.jsf.SalaryValidator")
public class SalaryGreaterThanZeroValidator implements Validator {
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        // TODO Auto-generated method stub
        String sal = value.toString();
        Double salary = Double.parseDouble(sal);
        
        if (value == null || salary <= 0) {
            FacesMessage msg = new FacesMessage("Salary should be greater than Zero and not to be null");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);            
        }
   
    }
}