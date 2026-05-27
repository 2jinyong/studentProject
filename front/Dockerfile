# 어떤 환경에서 시작할지 정합니다.
FROM node:24-alpine

# 컨테이너 내부의 작업 디렉토리를 만듭니다. (컴퓨터의 폴더 생성과 같음)
WORKDIR /app

# 프로젝트의 의존성 목록(package.json)만 먼저 복사합니다.
# 왜냐하면, 소스 코드가 바뀔 때마다 npm install을 다시 하면 너무 느리기 때문입니다.
COPY package*.json ./

# 필요한 라이브러리를 설치합니다.
RUN npm install

# 이제 나머지 모든 소스 코드를 복사합니다.
COPY . .

# 컨테이너가 3000번 포트를 사용할 것이라고 선언합니다.
EXPOSE 3000

# 컨테이너가 실행될 때 자동으로 실행할 명령어를 지정합니다.
CMD ["node", "app.js"]