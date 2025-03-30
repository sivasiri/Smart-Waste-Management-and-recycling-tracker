package com.akatsuki.project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private int age;
    private String sex;

    @CreatedDate
    private LocalDateTime createdAt;

    // Constructors
    public User() {
        this.createdAt = LocalDateTime.now();
    }

    public User(String firstName, String lastName, String email, String password,
                String phone, String address, int age, String sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.age = age;
        this.sex = sex;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public int getAge() { return age; }

    public void setAge(int age) { this.age = age; }

    public String getSex() { return sex; }

    public void setSex(String sex) { this.sex = sex; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
