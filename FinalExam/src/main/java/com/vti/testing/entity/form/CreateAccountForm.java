package com.vti.testing.entity.form;

import com.vti.testing.entity.Role;
import com.vti.testing.validate.account.AccountUsernameNotExists;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class CreateAccountForm {
    @NotBlank(message = "{Departments.createDepartment.form.username.NotBlank}")
    @Length(max = 50, message = "{Departments.createDepartment.form.username.Length}")
    @AccountUsernameNotExists
    private String username;

    @NotBlank
    @Length(max = 50)
    private String firstName;

    @NotBlank
    @Length(max = 50)
    private String lastName;

    @Pattern(regexp = "ADMIN|EMPLOYEE|MANAGER")
    private String role;

    @Positive
    // @DepartmentIdExists
    private int departmentId;
}
