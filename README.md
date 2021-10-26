# 맞춤법 맞추미
> 한글날을 기념하여 만든 간단한 맞춤법 검사기입니다.

네이버 맞춤법 검사기를 사용했으며, 문제가 발생할 시 바로 내리도록 하겠습니다.

링크에서 바로 확인 가능합니다. > 
http://zifori.me:3000/

기본 화면이며, 왼쪽에 텍스트를 입력하시면 자동으로 맞춤법을 검사해서 우측 textarea에 뿌려줍니다.

전체 검색은 하단의 버튼을 누를 시 전체 검색이 되며, 단일 검색과 동일하게 ctrl + `을 누를 시 우측의 맞춤법으로 변경됩니다.

![화면 캡처 2021-10-17 231000](https://user-images.githubusercontent.com/19691052/137631047-34050926-5682-4888-a8a2-8da9fd89b397.png)


사용한 프레임워크 및 언어
1. spring boot + kotlin
2. reactJS

webflux를 사용했으며 
api 서버 : graphql non blocking 방식으로 변경 및 test code 작성
view 서버 : log list 출력
기능만 남았습니다
