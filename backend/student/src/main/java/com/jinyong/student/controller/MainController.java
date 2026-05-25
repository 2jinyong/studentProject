package com.jinyong.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jinyong.student.dto.ScoreDto;
import com.jinyong.student.dto.ScoreResultDto;
import com.jinyong.student.dto.StudentDto;
import com.jinyong.student.service.ScoreService;

@Controller
public class MainController {

	//jdbc 템플릿사용
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //학생정보 등록 컨트롤러
    @PostMapping("/student-data")
    @ResponseBody
    public String insertStudentData(@RequestBody StudentDto student) {
        String sql = "insert into students (name,age,gender) values (?,?,?)";
        jdbcTemplate.update(sql, student.getName(), student.getAge(), student.getGender());

        return "학생정보 등록성공!";
    }
    
    //스코어 서비스 가져오기
    @Autowired
    private ScoreService scoreService;
    
    //점수등록 컨트롤러
    @PostMapping("/score-data")
    @ResponseBody
    public String registerScore(@RequestBody ScoreDto scoreDto) {
    	System.out.println("넘어온 학생 ID: " + scoreDto.getStudent_id());
        // 1. 등급 계산 로직 호출 (이미 만든 서비스 사용)
        scoreService.processGrades(scoreDto);

        // 2. JdbcTemplate으로 DB에 직접 꽂아넣기
        String sql = "INSERT INTO scores (student_id, korean, english, math, korean_grade, english_grade, math_grade, avg) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, 
            scoreDto.getStudent_id(),
            scoreDto.getKorean(), 
            scoreDto.getEnglish(), 
            scoreDto.getMath(), 
            scoreDto.getKorean_grade(),
            scoreDto.getEnglish_grade(), 
            scoreDto.getMath_grade(),
            scoreDto.getAvg()
        );

        return "성적과 등급이 성공적으로 등록되었습니다!";
    }
    
    //학생정보 조회 컨트롤러
    @GetMapping("/get-students")
    @ResponseBody
    public List<StudentDto> getStudents() {
        String sql = "SELECT * FROM students";
        // SQL 결과와 DTO 필드명을 스프링이 알아서 1:1 매칭해줌!
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(StudentDto.class));
    }
    
    @GetMapping("/get-scores")
    @ResponseBody
    public List<ScoreResultDto> getScores() {
        String sql = "SELECT a.name, a.age, a.gender, b.korean, b.english, b.math, b.avg, " +
                     "b.korean_grade, b.english_grade, b.math_grade, " +
                     "b.created_at " +
                     "FROM students a " +
                     "JOIN scores b ON a.id = b.student_id";
                     
        // BeanPropertyRowMapper가 SQL 결과를 ScoreResultDto에 자동으로 매핑해줍니다.
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ScoreResultDto.class));
    }
    
}
