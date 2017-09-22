
### Что у нас есть?
Tinder-Клиент и Tinder-Сервер, общающиеся через HTTP. 
Про HTTP можно почитать тут: https://habrahabr.ru/post/215117/

### Пример общения по HTTP
Клиент - ваш браузер
Сервер - Яндекс.Погода - расположен по адресу (URL): http://yandex.ru/pogoda/moscow - в этом адресе:
- http - протокол соединения
- yandex.ru - имя хоста
- /pogoda/moscow - путь до нужного ресурса
	
Вы хотите посмотреть погоду в Москве.
    
1. Клиент отправляет Серверу запрос по HTTP:
Запрос выглядит так:

        GET /pogoda/moscow HTTP/1.1
        Host: yandex.ru
    
	- GET - метод
	- HTTP/1.1 - версия протокола
	- Host - наименование(имя) хоста

2. Сервер отправляет Клиенту ответ:

        HTTP/1.1 200 OK
        Content-Type: text/html; charset=utf-8
        Date: Fri, 07 Oct 2016 08:54:17 GMT
        ... (еще некоторые заголовки)
        <html> 
            код страницы
        </html

Таким образом Клиенту была доставлена html страница.
В зависимости от запроса(спецификации сервиса) в ответы могут приходить в разных типах,
например, *Content-Type: application/json* и других.

### Tinder-Сервер
- код сервера находится в ru.atom.server
- поднят на сетевом адресе (при локальном тестировании localhost:8080)
- предоставляет публичный API:
	- для регистрации пользователя auth/register
	- для авторизации пользователя auth/login
	- для получения выборки людей по половому признаку data/personsbatch
- реализован полностью, модифицировать его не нужно
		
### Tinder-Клиент
- код клиента находится в ru.atom.client
- расположен на локальной машине студента
- хочет делать запросы через сеть на сервер:
	- хочет зарегистрироваться
	- хочет получать выборки людей по половому признаку
- реализация некоторых запросов *отсутствует*

### Задача
Реализовать недостающие методы клиента:

    RestClientImpl::login(String user, String password)
    RestClientImpl::getBatch(Long token, Gender gender)
    
которые реализуют общение с API сервера. (В обновленных материалах я реализовал эти методы)

### Зачем?
В реальных приложениях очень часто встречается использование публичных API.
Например: 

    Вы хотите разместить баннер с прогнозом погоды на главной странице портала, но данных у вас нет.
    Самым простым способом их достать будет запрос на общедоступный сервис с прогнозом погоды.

### Пример публичного API
https://api.github.com - на этой странице есть набор запросов, которые предоставляют открытую информацию.

Воспользуемся "https://api.github.com/users/{user}" чтобы узнать информацию о пользователе.
Допустим, нам интересен пользователь *Al-p-i*.

Сделаем HTTP GET запрос на https://api.github.com/users/Al-p-i
Будем использовать curl для отправки запроса.
Небольшой мануал по сurl: http://osxh.ru/terminal/command/curl
\+ всегда можно вызвать *curl --help*

    curl -i -X GET "https://api.github.com/users/Al-p-i"

И вот что мы получим:

    HTTP/1.1 200 OK
    Server: GitHub.com
    Date: Fri, 07 Oct 2016 09:22:43 GMT
    Content-Type: application/json; charset=utf-8
    Content-Length: 1236
    Status: 200 OK
    X-RateLimit-Limit: 60
    X-RateLimit-Remaining: 44
    X-RateLimit-Reset: 1475834812
    Cache-Control: public, max-age=60, s-maxage=60
    Vary: Accept
    ETag: "22d9113baa8ac948df339d0903e74dc2"
    Last-Modified: Mon, 26 Sep 2016 17:49:56 GMT
    X-GitHub-Media-Type: github.v3
    Access-Control-Expose-Headers: ETag, Link, X-GitHub-OTP, X-RateLimit-Limit, X-RateLimit-Remaining, X-RateLimit-Reset, X-OAuth-Scopes, X-Accepted-OAuth-Scopes,
    X-Poll-Interval
    Access-Control-Allow-Origin: *
    Content-Security-Policy: default-src 'none'
    Strict-Transport-Security: max-age=31536000; includeSubdomains; preload
    X-Content-Type-Options: nosniff
    X-Frame-Options: deny
    X-XSS-Protection: 1; mode=block
    Vary: Accept-Encoding
    X-Served-By: 8a5c38021a5cd7cef7b8f49a296fee40
    X-GitHub-Request-Id: B906F58A:1597:45A3A3A:57F76963
    
    {
      "login": "Al-p-i",
      "id": 3051314,
      "avatar_url": "https://avatars.githubusercontent.com/u/3051314?v=3",
      "gravatar_id": "",
      "url": "https://api.github.com/users/Al-p-i",
      "html_url": "https://github.com/Al-p-i",
      "followers_url": "https://api.github.com/users/Al-p-i/followers",
      "following_url": "https://api.github.com/users/Al-p-i/following{/other_user}",
      "gists_url": "https://api.github.com/users/Al-p-i/gists{/gist_id}",
      "starred_url": "https://api.github.com/users/Al-p-i/starred{/owner}{/repo}",
      "subscriptions_url": "https://api.github.com/users/Al-p-i/subscriptions",
      "organizations_url": "https://api.github.com/users/Al-p-i/orgs",
      "repos_url": "https://api.github.com/users/Al-p-i/repos",
      "events_url": "https://api.github.com/users/Al-p-i/events{/privacy}",
      "received_events_url": "https://api.github.com/users/Al-p-i/received_events",
      "type": "User",
      "site_admin": false,
      "name": "Alexander",
      "company": "Allods Team",
      "blog": null,
      "location": "Moscow",
      "email": "Alpieex@gmail.com",
      "hireable": null,
      "bio": null,
      "public_repos": 22,
      "public_gists": 0,
      "followers": 5,
      "following": 11,
      "created_at": "2012-12-15T18:28:35Z",
      "updated_at": "2016-09-26T17:49:56Z"
    }

Нам пришел ответ, содержащий в себе json, о чем нам говорит *Content-Type*.


### Публичный API Tinder-Сервера
Формальное описание

1. Для регистрации пользователя.
    
	    Protocol: HTTP
		Path: auth/register
		Method: POST
		Host: {IP}:8080 (IP = localhost при локальном тестрировании сервера)
		Headers:
			Content-Type: application/x-www-form-urlencoded
		Body:
			login={}&password={}

		Response: 
			Code: 200
			Body: сообщение об успехе

	Пример запроса в curl:

		curl -i 
			 -X POST 
			 -H "Content-Type: application/x-www-form-urlencoded" 
			 -H "Host: localhost:8080" 
			 -d "login=superman&password=qwerty" 
		"localhost:8080/auth/register"

2. Для авторизации пользователя.

	Авторизация позволяет по паре (login, password) получить token, если пользователь зарегистрирован.
	Чтобы иметь доступ к другим запросам, требующих авторизации(предоставления token при запросе)

		Protocol: HTTP
		Path: auth/login
		Method: POST
		Host: {IP}:8080 (IP = localhost при локальном тестрировании сервера)
		Headers:
			Content-Type: application/x-www-form-urlencoded
		Body:
			login={}&password={}
		Response: 
			Code: 200
			Body: token

	Пример запроса в curl:

	    curl -i
			 -X POST
		 -H "Content-Type: application/x-www-form-urlencoded"
		 -H "Host: localhost:8080"
		 -d "login=superman&password=qwerty"
	    "http://localhost:8080/auth/login" 

3. Для получения выборки людей по половому признаку:

	 **! Для этого запроса необходим токен !**

		Protocol: HTTP
		Path: data/personsbatch
		Method: POST
		Host: {IP}:8080 (IP = localhost при локальном тестрировании сервера)
		Headers:
			Authorization: Bearer {token}
		Body:
			"gender={ MALE | FEMALE }"
		Response:
			Code: 200
			Body: json вида {"persons" : [{Person1}, {Person2}, ... ]}

	Пример запроса в curl:		

		curl -i
		 -X POST
		 -H "Authorization: Bearer 123124253"
		 -H "Content-Type: application/x-www-form-urlencoded"
		 -H "Host: localhost:8080"
		 -d "gender=FEMALE"
	     "http://localhost:8080/data/personsbatch"

Нам нужно реализовать общение с этим API Tinder-Cервера.

Сейчас в репозитории обновлены материалы третьего семинара.
Можно самим все попробовать, покопаться в кишках отправки запросов.
Сначала нужно поднять сервер, чтобы было куда слать свои запросы.
Чтобы стартовать на своей машине Tinder-Сервер нужно запустить метод *main* в классе *ru.atom.server.Server*

Тесты клиента находятся в пакете ru.atom.client - RestClientImplTest
Они работают и их стоит попробовать дебажить.

Спасибо, что дочитали до этого места.
Надеюсь, эта статья была полезна.

## Хочу копнуть глубже, что делать?
В классе ru.atom.client.Controller есть два метода:

	public List<? extends Person> findYoungerThan29(Gender gender)
	public Map<Location, List<? extends Person>> groupByLocation(Gender gender)

Попробуйте их реализовать самостоятельно и написать на них тесты.
