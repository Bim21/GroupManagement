package com.vti.testing.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginInforDTO {
    private int id;
    private String username;
    private String fullName;
    private String departmentName;
}
