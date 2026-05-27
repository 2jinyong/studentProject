package com.jinyong.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    
    // 점수 조회 컨트롤러
    @GetMapping("/get-scores")
    @ResponseBody
    public List<ScoreResultDto> getScores() {
        // b.id를 그대로 가져옵니다.
        String sql = "SELECT a.name, a.age, a.gender, b.korean, b.english, b.math, b.avg, " +
                     "b.id, b.korean_grade, b.english_grade, b.math_grade, b.created_at " +
                     "FROM students a " +
                     "JOIN scores b ON a.id = b.student_id";
                     
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ScoreResultDto.class));
    }
    
    // 학생정보 삭제 컨트롤러
    @DeleteMapping("/student-data/{id}")
    @ResponseBody
    public String deleteStudentData(@PathVariable Long id) {
        String sql = "delete from students where id = ?";
        
        jdbcTemplate.update(sql, id);

        return "학생정보 삭제성공!";
    }

    // 점수 삭제 컨트롤러
    @DeleteMapping("/score-data/{id}")
    @ResponseBody
    public String deleteScoreData(@PathVariable Long id) {
        // 본인의 성적 테이블 기본키 컬럼명에 맞춰 수정하세요
        jdbcTemplate.update("delete from scores where id = ?", id);
        return "성적정보 삭제성공!";
    }

    // 학생정보 수정 컨트롤러
    @PutMapping("/student-data/{id}")
    @ResponseBody
    public String updateStudentData(@PathVariable Long id, @RequestBody StudentDto student) {
        String sql = "UPDATE students SET name = ?, age = ?, gender = ? WHERE id = ?";
        jdbcTemplate.update(sql, student.getName(), student.getAge(), student.getGender(), id);
        return "학생정보 수정성공!";
    }

    // 점수정보 수정 컨트롤러
    @PutMapping("/score-data/{id}")
    @ResponseBody
    public String updateScoreData(@PathVariable Long id, @RequestBody ScoreDto scoreDto) {
        // 등급 및 평균 재계산
        scoreService.processGrades(scoreDto);

        String sql = "UPDATE scores SET korean = ?, english = ?, math = ?, avg = ?, korean_grade = ?, english_grade = ?, math_grade = ? WHERE id = ?";
        jdbcTemplate.update(sql, 
            scoreDto.getKorean(), 
            scoreDto.getEnglish(), 
            scoreDto.getMath(), 
            scoreDto.getAvg(),
            scoreDto.getKorean_grade(),
            scoreDto.getEnglish_grade(),
            scoreDto.getMath_grade(),
            id
        );
        return "성적정보 수정성공!";
    }
    
}
