package com.akatsuki.project.dto;

public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private int age;
    private String sex;
    //private byte[] profilePicture;  // ⬅️ Changed from String to byte[]

    // Getters & Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    //public byte[] getProfilePicture() { return profilePicture; }
    //public void setProfilePicture(byte[] profilePicture) { this.profilePicture = profilePicture; }
}
