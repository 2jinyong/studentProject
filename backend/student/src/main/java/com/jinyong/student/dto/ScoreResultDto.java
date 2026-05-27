package com.jinyong.student.dto;

public class ScoreResultDto {
	private Long id;

	private String name;
    private int age;
    private String gender;
    private int korean;
    private int english;
    private int math;
    private double avg;
    private String korean_grade;
    private String english_grade;
    private String math_grade;
    private String created_at;

    
    // Getter & Setter (이게 있어야 스프링이 데이터를 넣고 뺄 수 있습니다)
    public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public int getKorean() { return korean; }
    public void setKorean(int korean) { this.korean = korean; }

    public int getEnglish() { return english; }
    public void setEnglish(int english) { this.english = english; }

    public int getMath() { return math; }
    public void setMath(int math) { this.math = math; }

    public double getAvg() { return avg; }
    public void setAvg(double avg) { this.avg = avg; }

    public String getKorean_grade() { return korean_grade; }
    public void setKorean_grade(String korean_grade) { this.korean_grade = korean_grade; }

    public String getEnglish_grade() { return english_grade; }
    public void setEnglish_grade(String english_grade) { this.english_grade = english_grade; }

    public String getMath_grade() { return math_grade; }
    public void setMath_grade(String math_grade) { this.math_grade = math_grade; }

    public String getCreated_at() { return created_at; }
    public void setCreated_at(String created_at) { this.created_at = created_at; }
}