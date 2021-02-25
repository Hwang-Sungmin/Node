# Mongoose ORM 라이브러리를 사용한 Node.js 데이터 처리

	- Monggoose는 Node.js와 MongoDB를 위한 ORM(객체 관계형 매핑) 라이브러리.

# 맥에서 몽고디비 실행하기
	- 설치
		Update brew
		$ brew update

		Tab mongodb
		$ brew tap mongodb/brew

		Install mongodb
		$ brew install mongodb-community


	- DB폴더 생성
		$ mkdir ~/data
		$ mkdir ~/data/db

	- /usr/local/etc/mongod.conf에 dbpath 추가

	- 실행
		$ brew services start mongodb-community
		$ ps -df|grep mongo

	- 종료
		$ brew services stop mongodb