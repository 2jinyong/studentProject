package com.jinyong.student.service;

import org.springframework.stereotype.Service;

import com.jinyong.student.dto.ScoreDto;

@Service
public class ScoreService {

    // 1. 점수를 받아 등급을 구하는 핵심 로직 (반복되는 부분은 따로 뺌)
    private String calcGrade(int score) {
        if (score >= 90) return "A";
        else if (score >= 80) return "B";
        else if (score >= 70) return "C";
        else if (score >= 60) return "D";
        else return "F";
    }

    // 2. 전체 점수를 받아 3과목 등급을 한 번에 세팅하는 메서드
    public void processGrades(ScoreDto dto) {
        // 국어 등급 세팅
        dto.setKorean_grade(calcGrade(dto.getKorean()));
        // 영어 등급 세팅
        dto.setEnglish_grade(calcGrade(dto.getEnglish()));
        // 수학 등급 세팅
        dto.setMath_grade(calcGrade(dto.getMath()));
        
     // 평균 계산 후 정수로 변환하여 저장
        int average = (int)((dto.getKorean() + dto.getEnglish() + dto.getMath()) / 3.0);
        dto.setAvg(average);
    }
}
