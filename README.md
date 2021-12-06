# 맞춤법 맞추미
> 한글날을 기념하여 만든 간단한 맞춤법 검사기입니다. WebFlux와 react를 사용하여 간단한 웹 프로젝트를 개발하고 싶어서 만들었습니다.

네이버 맞춤법 검사기를 사용했으며, 문제가 발생할 시 바로 내리도록 하겠습니다.

링크에서 바로 확인 가능합니다. > 
https://www.grammar-checker.site/

Spring Framework + Kotlin, ReactJS를 사용하여 Backend, Frontend를 개발

최화영 디자이너님이 사이트 디자인을 해주었습니다.
https://hdesigner.creatorlink.net/

현재 개인 linux 서버에 올려두었으며 AWS로 옮기면서 pipeline을 구축하고 있습니다. 
이에 현재 Dockerfile, jenkinsfile, k8s는 아직 미완입니다.

## 호환성

|Chrome|Firefox|Edge|IE11|
|---|---|---|---|
|latest check|latest check|latest check|no support|

## [Fronend 구조](https://github.com/tlgj255/spellChecker/tree/master/frontend/grammar-check)

## Backend 구조

+ [config](https://github.com/tlgj255/spellChecker/tree/master/src/main/kotlin/com/grammer/grammerchecker/config)
  > WebFlux의 기본 설정들입니다. cors및 라우팅 작업을 해주고 있습니다.
+ [errors](https://github.com/tlgj255/spellChecker/tree/master/src/main/kotlin/com/grammer/grammerchecker/errors)
  > exception handeler이며 성공 시와 동일하게 데이터를 전달하고자 생성했습니다.
+ [graphql](https://github.com/tlgj255/spellChecker/tree/master/src/main/kotlin/com/grammer/grammerchecker/graphql)
  > graphql로 들어오는 API를 처리하는 부분입니다. 
+ [handlers](https://github.com/tlgj255/spellChecker/tree/master/src/main/kotlin/com/grammer/grammerchecker/handlers)
  > 기본 web API를 처리하는 부분입니다. 내부에 controller, service, repository가 구현되어있습니다.
  + [repository](https://github.com/tlgj255/spellChecker/tree/master/src/main/kotlin/com/grammer/grammerchecker/handlers/repository)
    > generic type을 사용하여 하나의 repository로 구성하려 하였으나, interface 상속 없이는 bean 생성이 안 되며, select, update가 정상적으로 이루어지지 않아서 동일한 작업을 하지만 두 개로 구성되어있습니다.
  + [service](https://github.com/tlgj255/spellChecker/tree/master/src/main/kotlin/com/grammer/grammerchecker/handlers/service)
    > service를 interface, impl로 구분할 필요는 없지만 두 개가 같은 작업을 진행하기에 추후 유지보수를 위해 기능들을 추상화하여 interface, impl 구조로 생성했습니다.
    + [impl](https://github.com/tlgj255/spellChecker/tree/master/src/main/kotlin/com/grammer/grammerchecker/handlers/service/impl)
      > 추상화된 interface의 구현체 실제 interface 내부에서 sort 변수를 생성했기에 abstract class를 상속받아서 구현 중입니다. 
+ [model](https://github.com/tlgj255/spellChecker/tree/master/src/main/kotlin/com/grammer/grammerchecker/model)
  > client, server, db 간 통신을 위한 데이터 객체
  + [domain](https://github.com/tlgj255/spellChecker/tree/master/src/main/kotlin/com/grammer/grammerchecker/model/domain)
    > server와 db 간의 데이터 객체로 db table과 같은 구조로 되어있습니다.
  + [dto](https://github.com/tlgj255/spellChecker/tree/master/src/main/kotlin/com/grammer/grammerchecker/model/dto)
    > client와 server 간의 데이터 객체로 client -> server는 request, server -> client는 dto로 네임을 생성해서 관리하고 있습니다.
+ [utils](https://github.com/tlgj255/spellChecker/tree/master/src/main/kotlin/com/grammer/grammerchecker/utils)
  > 별도의 추가 작업들이 있는 폴더입니다. ApiUtils은 response 형식입니다. GrammarChecker은 네이버 맞춤법 api 서버에 데이터를 보낸 후 데이터를 정제하는 작업을 하고 있습니다. RegularExpression은 object mapper는 blocking code이기에 non blocking을 지향하고자 api에서 받아온 값을 정규식으로 원하는 값만 가져오려고 만든 모듈입니다.
+ [validator](https://github.com/tlgj255/spellChecker/tree/master/src/main/kotlin/com/grammer/grammerchecker/validator)
  > validation에 들어가는 값을 통일시키고 에러처리까지 해주는 템플릿 메소드 패턴을 사용하였습니다. ValidationText는 에러 발생 시 text를 모아둔 파일입니다.
  + [impl](https://github.com/tlgj255/spellChecker/tree/master/src/main/kotlin/com/grammer/grammerchecker/validator/impl)
    > 실제 validation 체크를 하는 부분입니다.
  

## api 구조
에러 발생 시
```
{
    "success": false,
    "response": null,
    "error": 
    {
      "message": error massage,
      "status": 400
    }
}
```

### 맞춤법 검사 | GET /api/check 
#### Request Parameter
|Parameter|Types|Description|
|---|---|---|
|grammar|String|한글만 검사를 하며 500자 이하의 String만 검색이 가능합니다.|
#### Response
성공 예제
```
{
    "success": true,
    "response": [
        {
            "errorText": "되요",
            "fixedText": "돼요"
        }
    ],
    "error": null
}
```
수정 데이터 없을 시
```
{
    "success": true,
    "response": [],
    "error": null
}
```

### log 검색 | GET /api/log
> 시간대는 UTC 사용 중
#### Request Parameter
None
#### Response
성공 예제
```
{
    "success": true,
    "response": [
        {
            "error": "되요",
            "fixed": "돼요",
            "count": 1,
            "fixedTime": "2021-12-05T19:08:17"
        },
        .
        .
        .
    ],
    "error": null
}
```

### log 등록 | POST /api/log
### Request Body
|Fields|Types|Description|
|---|---|---|
|errorText|String|기존 에러 텍스트 500자 이하의 글자만 입력 가능|
|fixedText|String|수정된 텍스트 500자 이하의 글자만 입력 가능|
|ip|String|사용자의 ip 500자 이하의 글자만 입력 가능|
#### Response
성공 예제
```
{
    "success": true,
    "response": true,
    "error": null
}
```
