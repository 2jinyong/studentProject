$(function(){
    //각 페이지별 정보전송 버튼
    const studentBtn = $('#studentBtn');
    const scoreBtn = $('#scoreBtn');

    // 빈값이 있는지 체크하는 공통함수
    function isEmpty(val) {
        return val.toString().trim() === ''
    }

    //학생정보 등록 로직 fetch방식 사용
    studentBtn.click(function(){
        const name = $('#name').val();
        const age = $('#age').val();
        const gender = $('input[name="gender"]:checked').val();

        //공통함수로 빈값이 있는지 확인 후 있을 시 함수 바로 종료
        if(isEmpty(name) || isEmpty(age) || isEmpty(gender)){
            alert("값을 입력하세요!")
            return;
        }

        //입력한 데이터를 객체형식으로 담음
        const studentData = { name, age, gender }
        console.log(studentData)

        //fetch를 사용하여 서버로 데이터 전송
        fetch('http://localhost:8080/student-data',{
            method : 'post',
            headers : {
                'Content-Type' : 'application/json; charset=utf-8',
                'Accept': 'application/json; charset=UTF-8'
            },
            body : JSON.stringify(studentData)
        })
        .then(res=> res.text())
        .then(data => alert(data))
    })

    //점수등록 로직 fetch방식 사용
    scoreBtn.click(function(){
        // 1. 선택된 학생 ID를 가져옵니다 (아까 .selectBtn 클릭 시 #selectedStudentId에 넣어둠)
        const student_id = $('#selectedStudentId').val();

        // 2. 학생이 선택되었는지 체크
        if(isEmpty(student_id)) {
            alert("먼저 학생 목록에서 '선택' 버튼을 눌러 학생을 지정해주세요!");
            return;
        }

        const korean = $('#korean').val();
        const english = $('#english').val();
        const math = $('#math').val();

        if(isEmpty(korean) || isEmpty(english) || isEmpty(math)){
            alert("값을 입력하세요!");
            return;
        }

        // 3. 전송할 데이터 객체에 studentId를 포함시킵니다!
        const scoreData = { student_id, korean, english, math }
        console.log("전송할 데이터:", scoreData); // 확인용

        fetch('http://localhost:8080/score-data',{
            method : 'post',
            headers : {
                'Content-Type' : 'application/json; charset=utf-8',
                'Accept': 'application/json; charset=UTF-8'
            },
            body : JSON.stringify(scoreData) // 이제 여기에 studentId가 포함됨
        })
        .then(res=> res.text())
        .then(data => alert(data))
    })

    //등록된 학생정보를 디비에서 가져오기
    fetch('http://localhost:8080/get-students')
    .then(res => res.json())
    .then(data => {
        let html = '<table border="1">';
        html += '<thead><tr><th>이름</th><th>나이</th><th>성별</th><th>학생 등록일</th><th>선택</th><th>수정</th><th>삭제</th></tr></thead>';
        html += '<tbody>';
        
        data.forEach(student => {
            // 모든 정보를 표에 뿌림
            html += `<tr>
                        <td>${student.name}</td>
                        <td>${student.age}</td>
                        <td>${student.gender}</td>
                        <td>${student.createdAt}</td>
                        <td><button type="button" class="selectBtn" data-id="${student.id}">선택</button></td>
                        <td><button type="button" class="editStudentBtn" data-id="${student.id}">수정</button></td>
                        <td><button type="button" class="deleteBtn" data-id="${student.id}">삭제</button></td>
                    </tr>`;
        });
        
        html += '</tbody></table>';
        $('#studentList').html(html);
    });

    // 학생정보 테이블표 안의 '수정' 버튼 클릭 시
    $('#studentList').on('click', '.editStudentBtn', function() {
        const id = $(this).data('id');
        const name = prompt("수정할 이름을 입력하세요");
        const age = prompt("수정할 나이를 입력하세요");
        const gender = prompt("수정할 성별을 입력하세요 (man/woman)");

        if(!name || !age || !gender) return;

        const studentData = { name, age, gender };

        fetch(`http://localhost:8080/student-data/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(studentData)
        })
        .then(res => res.text())
        .then(data => {
            alert(data);
            location.reload();
        });
    });

    // 학생정보 테이블표 안의 '선택' 버튼 클릭 시 선택된 학생고유아이디 등록
    $('#studentList').on('click', '.selectBtn', function() {
        const id = $(this).data('id'); // 버튼에 숨겨둔 ID를 가져옴
        $('#selectedStudentId').val(id); // 숨겨진 인풋박스에 넣음
        
        // 시각적 피드백 (선택된 줄 강조)
        $('tr').css('background-color', 'white'); // 초기화
        $(this).closest('tr').css('background-color', '#e0f7fa'); // 선택된 줄 강조
        
        alert("학생이 선택되었습니다. 점수를 입력하고 등록하세요!");
    });

     // 학생정보 테이블 안의 삭제 버튼 클릭 시 학생 고유아이디를 가져와서 백엔드로 삭제요청 
    $('#studentList').on('click', '.deleteBtn', function() {
        // 1. 해당 행에 저장된 학생 ID 가져오기
        // (선택 버튼에서 사용했던 것처럼 data-id를 삭제 버튼에도 추가해야 합니다)
        const id = $(this).closest('tr').find('.selectBtn').data('id');

        if (!confirm("정말 삭제하시겠습니까?")) return;

        // 2. fetch로 DELETE 요청 보내기
        fetch(`http://localhost:8080/student-data/${id}`, {
            method: 'DELETE',
            headers : {
                'Content-Type' : 'application/json; charset=utf-8',
                'Accept': 'application/json; charset=utf-8'
            },
            
        })
        .then(res => res.text())
        .then(data => {
            alert(data);
            // 삭제 성공 후 리스트 다시 불러와서 화면 갱신
            location.reload(); // 또는 전체 학생 리스트 불러오는 함수 재호출
        })
        .catch(err => console.error("삭제 실패:", err));
    });

    //점수조회 테이블안의 삭제 버튼 클릭시 학생 고유 아이디를 가져와서 백엔드로 삭제요청
    $('#scoreListTable').on('click', '.deleteScoreBtn', function() {
        const id = $(this).data('id');
        if (!confirm("정말 삭제하시겠습니까?")) return;

        fetch(`http://localhost:8080/score-data/${id}`, { 
            method: 'DELETE' ,
            headers : {
                'Content-Type' : 'application/json; charset=utf-8',
                'Accept': 'application/json; charset=utf-8'
            }
        })
        .then(res => res.text())
        .then(data => { alert(data); loadScoreList(); });
    });

    // 페이지 로딩 시 실행 (성적 리스트 조회)
    function loadScoreList() {
        fetch('http://localhost:8080/get-scores') // JOIN 쿼리 결과 받는 주소
        .then(res => res.json())
        .then(data => {
            let html = '<table border="1">';
            html += '<thead><tr><th>이름</th><th>나이</th><th>성별</th><th>국어</th><th>영어</th><th>수학</th><th>평균</th><th>국어등급</th><th>영어등급</th><th>수학등급</th><th>점수 등록시간</th><th>수정</th><th>삭제</th></tr></thead>';
            html += '<tbody>';
            
            data.forEach(item => {
                // JOIN된 결과는 1줄에 모든 정보가 다 들어있습니다.
                html += `<tr>
                            <td>${item.name}</td>
                            <td>${item.age}</td>
                            <td>${item.gender}</td>
                            <td>${item.korean}</td>
                            <td>${item.english}</td>
                            <td>${item.math}</td>
                            <td>${item.avg}</td>
                            <td>${item.korean_grade}</td>
                            <td>${item.english_grade}</td>
                            <td>${item.math_grade}</td>
                            <td>${item.created_at}</td>
                            <td><button type="button" class="editScoreBtn" data-id="${item.id}">수정</button></td>
                            <td><button type="button" class="deleteScoreBtn" data-id="${item.id}">삭제</button></td>
                        </tr>`;
            });
            
            html += '</tbody></table>';
            $('#scoreListTable').html(html); // 표를 뿌려줄 div 또는 태그
        })
        .catch(err => console.error("데이터 조회 에러:", err));
    }

    // 성적 수정 버튼 클릭 시
    $('#scoreListTable').on('click', '.editScoreBtn', function() {
        const id = $(this).data('id');
        const korean = prompt("수정할 국어 점수를 입력하세요");
        const english = prompt("수정할 영어 점수를 입력하세요");
        const math = prompt("수정할 수학 점수를 입력하세요");

        if(!korean || !english || !math) return;

        const scoreData = { korean, english, math };

        fetch(`http://localhost:8080/score-data/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(scoreData)
        })
        .then(res => res.text())
        .then(data => {
            alert(data);
            loadScoreList();
        });
    });

    // 화면이 다 그려지면 자동으로 호출
    $(function() {
        loadScoreList();
    });
})