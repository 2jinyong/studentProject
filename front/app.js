const express = require('express');
const path = require('path');
const app = express();

//정적폴더 지정 /public 생략가능 ex)/css/style.css
app.use(express.static('public'));

//라우터 등록
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'views', 'main.html'));
})

//서버 3000포트로 실행
app.listen(3000, () => {
    console.log(`서버가 http://localhost:3000 에서 실행 중입니다.`);
});