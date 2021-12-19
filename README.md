# 맞춤법 맞추미
> 한글날을 기념하여 만든 간단한 맞춤법 검사기입니다. WebFlux와 react를 사용하여 간단한 웹 프로젝트를 개발하고 싶어서 만들었습니다.

네이버 맞춤법 검사기를 사용했으며, 문제가 발생할 시 바로 내리도록 하겠습니다.

링크에서 바로 확인 가능합니다. > 
www.grammar-checker.link

Spring Framework + Kotlin, ReactJS를 사용하여 Backend, Frontend를 개발

[최화영 디자이너님](https://hdesigner.creatorlink.net/)이 사이트 디자인을 해주었습니다.

frontend server는 aws의 amplify를 사용했으며, backend server는 aws의 elastic beanstack을 사용하였습니다.

## 호환성

|Chrome|Safari|Firefox|Edge|IE11|
|---|---|---|---|---|
|latest check|latest check|latest check|latest check|no support|

## [Fronend 구조](https://github.com/tlgj255/spellCheckerFrontend)

## Backend 구조
### 전체 구조
``` bash
📦grammerchecker
 ┣ 📂config > cors 등 서버 관련 설정
 ┣ 📂errors > 에러 발생 시 일정한 포맷이로 response를 내기 위한 Exception 처리
 ┣ 📂handlers > 모든 비즈니스 로직이 들어가 있는 폴더
 ┃ ┣ 📂graphql > graphql mutation, query controller 폴더
 ┃ ┣ 📂model > data model들을 정의
 ┃ ┃ ┣ 📂domain > server <> database 통신을 담당하는 data model
 ┃ ┃ ┣ 📂dto > server <> client 통신을 담당하는 data model(server -> client : dto, client -> server : request) 
 ┃ ┣ 📂repository
 ┃ ┣ 📂service > service model들을 정의
 ┃ ┃ ┣ 📂impl
 ┃ ┣ 📂validator
 ┗ 📂utils > 출력 포맷 및 공통으로 쓰이는 기능
```
### 상세 코드 및 구조
#### 비즈니스 로직(handlers)
- graphql
  > graphql 관련 처리를 위한 폴더 Resolver는 select, Mutation은 insert, update, delete를 처리하고 있습니다.
- model
  - domain
    > server <> database 통신을 담당하는 data model
  - dto
    > server <> client 통신을 담당하는 data model(server -> client : dto, client -> server : request) 
- repository
  > R2DBC를 사용하고 있습니다.
- service
  > 두 개의 서비스가 동일한 로직에 다른 repository를 사용하기에 같은 기능의 service라는 것을 표기하기 위하여 interface를 사용했습니다.
    - impl
      > Transactional처리 및 실제 비즈니스 로직을 수행하고 있습니다.
      ``` kotlin
          @Transactional(readOnly = true)
          override fun findAll() =
              logRepository.findAll(sort)
                  .flatMap {
                      Flux.just(LogDto(it))
                  }

          @Transactional
          override fun logSave(log: LogRequest) =
              logRepository.save(GrammarSentenceLog(log))
                  .flatMap {
                      Mono.just(LogDto(it))
                  }
      
      ```
- validator
  > 서비스상 입력되면 안 되거나 잘못 입력된 부분을 체크 후 해당하는 ValidationText와 함께 throw IllegalStateException 합니다.
- handler
  > request의 validation 처리, 에러 처리(주로 valudation 처리 내부에 있습니다), 실제 기능, 반환으로 이루어져 있습니다. 
  ``` kotlin
      fun insertLog(req: ServerRequest): Mono<ServerResponse> = ServerResponse.ok()
          .body(
              req.bodyToMono(LogRequest::class.java)
                  .switchIfEmpty(Mono.empty())
                  .flatMap { log ->
                      //validation 처리
                      validator.validationCheck(log)
                      //실제 기능
                      service.logSave(log)
                      //반환
                      Mono.just(ApiUtils(response = true))
                  }
          )
  ```
#### 비즈니스 로직 외(config, errors, utils)
- config
  > cors, router등록 등 서버 관련 설정 
- errors
  > error 발생 시 response 포맷을 통일 시켜주기 위해 생성한 handler
- utils
  > ApiUtils success, error response 포맷 정의
  ``` kotlin
  data class ApiUtils<T>(
    val success: Boolean = true,
    val response: T? = null,
    val error: ErrorUtils? = null
  )

  data class ErrorUtils(
      val message: String = "",
      val status: Int
  )
  ```
  > GrammarChecker 실제 text를 naver 맞춤법 탐색기를 통해 검사하는 기능
  > RegularExpression ObjectMapper는 bolcking code 이기에 정규식을 사용해서 분리
  > ValidationText error text

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
