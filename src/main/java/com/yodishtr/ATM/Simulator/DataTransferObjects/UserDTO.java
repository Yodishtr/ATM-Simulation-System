package com.yodishtr.ATM.Simulator.DataTransferObjects;

import java.util.List;

public class UserDTO {

    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> accounts;
    private List<String> cards;

    public UserDTO(Long userId, String username, String firstName, String lastName, String email,
                   List<String> accounts, List<String> cards) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accounts = accounts;
        this.cards = cards;
    }
}
