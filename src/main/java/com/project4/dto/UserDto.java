package com.project4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate birthday;

    private String phone;

    private String email;

    private String password;

    private String address;

    private String status;

    private String role;
}
