package com.jinyong.student.dto;

public class StudentDto {
    
    // 자동증가 id 데이터 저장 필드
    private int id;

    // 학생 이름 데이터 저장 필드
    private String name;
    
    // 학생 나이 데이터 저장 필드
    private int age;
    
    // 학생 성별 데이터 저장 필드
    private String gender;
    
    // 생성시간 데이터 저장 필드
    private String createdAt;
    
    //getter, setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    
}
