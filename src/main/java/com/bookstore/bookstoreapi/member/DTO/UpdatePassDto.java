package com.bookstore.bookstoreapi.member.DTO;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UpdatePassDto{
    private String oldPassword;
    private String newPassword;
}
