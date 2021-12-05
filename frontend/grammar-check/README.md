# Fronend 구조
> redux를 사용하지 않았기에 component로만 구성되어있습니다. 
> 경로는 dotenv로 제어하고 있으며 apolloClient를 사용했습니다.

+ [components](https://github.com/tlgj255/spellChecker/tree/master/frontend/grammar-check/src/components)
  + [common](https://github.com/tlgj255/spellChecker/tree/master/frontend/grammar-check/src/components/common)
    > 여러 곳에 공통으로 쓰이는 모듈로 Button이 있습니다.
  + [grammar](https://github.com/tlgj255/spellChecker/tree/master/frontend/grammar-check/src/components/grammar)
    > 오타 수정 기능이 있는 화면들로 GrammarArea를 중심으로 InputTextArea에서는 사용자 입력부분 ResultTextArea에서는 결과 출력 부분으로 구성되어있습니다.
  + [header](https://github.com/tlgj255/spellChecker/tree/master/frontend/grammar-check/src/components/header)
    > 해더 디자인입니다.
  + [history](https://github.com/tlgj255/spellChecker/tree/master/frontend/grammar-check/src/components/history)
    > 오타 확인 후 사용자가 수정한 로그들을 보여주는 화면입니다. LodArea를 중심으로 LogTextArea는 디자인, SentenceLog, WordLog는 서버에 조회하는 기능 부분으로 분할되어있습니다.
+ [graphql](https://github.com/tlgj255/spellChecker/tree/master/frontend/grammar-check/src/graphql)
  > graphql의 쿼리가 들어있는 파일들 입니다.
+ [lib](https://github.com/tlgj255/spellChecker/tree/master/frontend/grammar-check/src/lib)
  > 디자인에 필요한 유틸들을 모아두었습니다. 직접 개발한 코드는 아닙니다.
+ [pages](https://github.com/tlgj255/spellChecker/tree/master/frontend/grammar-check/src/pages)
+ [static](https://github.com/tlgj255/spellChecker/tree/master/frontend/grammar-check/src/static)
  > 폰트 및 이미지들이 들어있는 파일입니다. 이미지들은 저작권은 최화영 디자이너님에게 있습니다.

