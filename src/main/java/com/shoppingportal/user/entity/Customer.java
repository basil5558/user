package com.shoppingportal.user.entity;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Customer {
	@Id
	@Column(nullable = false, unique = true, length = 30)
    private String mail;
     
    @Column(nullable = false, length = 30)
    private String password;
}

