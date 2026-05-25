const express = require('express');
const path = require('path');
const app = express();

//정적폴더 지정 /public 생략가능 ex)/css/style.css
app.use(express.static('public'));

//메인페이지 라우터
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'views', 'main.html'));
})

// 학생정보 등록 페이지
app.get('/student', (req, res) => {
    res.sendFile(path.join(__dirname, 'views', 'student.html'));
})

// 점수등록 페이지
app.get('/score', (req, res) => {
    res.sendFile(path.join(__dirname, 'views', 'score.html'));
})

// 점수조회 페이지
app.get('/list', (req, res) => {
    res.sendFile(path.join(__dirname, 'views', 'list.html'));
})

//서버 3000포트로 실행
app.listen(3000, () => {
    console.log(`서버가 http://localhost:3000 에서 실행 중입니다.`);
});