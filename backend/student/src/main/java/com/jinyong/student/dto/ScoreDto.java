package com.jinyong.student.dto;

public class ScoreDto {
	
	// 자동증가 id
	private int id;
	
	// 왜래키 학생정보와 연결
	private int student_id;
	
	//국어점수
	private int korean;
	
	//영어점수
	private int english;
	
	//수학점수
	private int math;
	
	//평균점수
	private int avg;
	
	//국어 등급
	private String korean_grade;
	
	//영어 등급
	private String english_grade;
	
	//수학 등급
	private String math_grade;

	//getter,setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStudent_id() {
		return student_id;
	}
	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}
	public int getKorean() {
		return korean;
	}
	public void setKorean(int korean) {
		this.korean = korean;
	}
	public int getEnglish() {
		return english;
	}
	public void setEnglish(int english) {
		this.english = english;
	}
	public int getMath() {
		return math;
	}
	public void setMath(int math) {
		this.math = math;
	}
	public int getAvg() {
		return avg;
	}
	public void setAvg(int avg) {
		this.avg = avg;
	}
	public String getKorean_grade() {
		return korean_grade;
	}
	public void setKorean_grade(String korean_grade) {
		this.korean_grade = korean_grade;
	}
	public String getEnglish_grade() {
		return english_grade;
	}
	public void setEnglish_grade(String english_grade) {
		this.english_grade = english_grade;
	}
	public String getMath_grade() {
		return math_grade;
	}
	public void setMath_grade(String math_grade) {
		this.math_grade = math_grade;
	}

}
