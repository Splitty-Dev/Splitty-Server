## 스플릿티 ㅣ 함께 나누는 똑똑한 소비
### 고물가 시대, 소규모 가구를 위한 대용량 제품 소분 거래 플랫폼
<img width="800" alt="1" src="https://github.com/user-attachments/assets/eb66c0bf-328f-4686-ba5b-48271bfb2ee6" />

### Tech Stack

#### Frontend
<img src="https://img.shields.io/badge/TypeScript-3178C6?style=for-the-badge&logo=TypeScript&logoColor=white"> <img src="https://img.shields.io/badge/Next.js-000000?style=for-the-badge&logo=Next.js&logoColor=white">

#### Backend
<img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white"> <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">

#### Database
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/Amazon%20RDS-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white"> <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white">

#### Infrastructure
<img src="https://img.shields.io/badge/Amazon%20EC2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white"> <img src="https://img.shields.io/badge/Amazon%20S3-569A31?style=for-the-badge&logo=amazons3&logoColor=white"> <img src="https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white"> <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/Vercel-000000?style=for-the-badge&logo=vercel&logoColor=white">

### Service Architecture
<img width="600" alt="system_architecture" src="https://github.com/user-attachments/assets/2eb2fb94-c4ea-4bda-b89e-f5e390855678" />

### Key Features
#### 📍 거리 기반 소분 거래 탐색
* 사용자 위치 기반 반경 내 물품 조회 기능 제공

#### 💬 거래 전용 채팅
* 거래 사용자 간 1:1 채팅 기능 제공

#### ⚡ 상품 자동완성 시스템
* 외부 상품 DB 구축을 통한 상품 정보(사진, 상품명, 수량 등) 자동완성 제공


### Key Contribution
- Pessimistic Lock을 통한 동시성 제어로 소분 거래 참여 시 데이터 정합성 확보
- Redis GeoSpatial을 활용한 사용자 위치 기반 제한 거리 내 물품 탐색
- Redis Sorted Set을 활용한 상품명 자동완성 성능 49% 개선
- Cursor 기반 페이지네이션과 복합 인덱스 적용을 통한 채팅 메시지 조회 성능 90% 개선
