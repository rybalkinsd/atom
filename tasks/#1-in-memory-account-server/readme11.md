# Рубежный контроль

API:
    `join(name: String)`
	User opens game webpage (localhost:8080) and see the landing page (index page)
	with the only button [Play] and a text form to enter his nickname.



Under the hood:
1. Matchmaker service.
	Matchmaker should handle `play` button request from user and provide a valid game session id to the user
	User is waiting until Matchmaker will respond with the `game id`  

	Specification  
	  ```
	    Protocol: HTTP
	    Path: matchmaker/join
	    Method: POST
	    Host: {IP}:8080 (IP = localhost for local server tests)
	    Headers:
	        Content-Type: application/x-www-form-urlencoded
	    Body:
	        name={}
	    Response: 
	        Code: 200
	        Сontent-Type: text/plain
	        Body: game id
	  ```

  [mm.pic]

	Matchmaker is creating a new games when necessary.
	Matchmaker provides same gameId to N client connections(players) 
	Matchmaking algorithm was described in lectures.

Bonus: 
	Monitoring - how many players are in queue and other interesting data 

2. Game service

API:
	`gameId create(playerCount: int): long`
	Creates 
	
	`connect(name: String, gameId: long)`

	`start(gameId: long)`

Under the hood:
At some point Matchmaker asks Game service to `create` a new game for `playerCount` users.
After that Matchmaker will provide this `gameId` to clients and clients will
`connect` to the exact game using `gameId` and `name`

At some poing Matchmaker starts the game with `gameId`.
In general it should be when nuber of connected players equals to number of players that should play in one game.

	Specification  
	  ```
	    Protocol: HTTP
	    Path: game/create
	    Method: POST
	    Host: {IP}:8090 (IP = localhost for local server tests)
	    Headers:
	        Content-Type: application/x-www-form-urlencoded
	    Body:
	        playerCount={}
	    Response: 
	        Code: 200
	        Сontent-Type: text/plain
	        Body: game id
	  ```

	  ```
	    Protocol: HTTP
	    Path: game/start
	    Method: POST
	    Host: {IP}:8090 (IP = localhost for local server tests)
	    Headers:
	        Content-Type: application/x-www-form-urlencoded
	    Body:
	        gameId={}
	    Response: 
	        Code: 200
	        Сontent-Type: text/plain
	        Body: game id
	  ```

	  ```
	    Protocol: WS
	    Path: game/connect?gameId={}&name={}
	    Host: {IP}:8090 (IP = localhost for local server tests)
	    
	    Result: 
	        WS connection established
	  ```




## Технологический стек:
### Spring & Spring-boot
**[Srping]( @TODO )** - самый популярный фреймворк в мире java, в него входит в частности web-mvc.
**[Spring-boot]( @TODO)** - фреймворк, позволяющий очень качественно и быстро конфигурировать java/spring приложения, 
в том числе и web-mvc.
**[Spring mvc]** - реализация концепции mvc на уровне фреймворка Spring.

**Spring mvc реализует модель thread per request** [[link]](http://stackoverflow.com/questions/15217524/what-is-the-difference-between-thread-per-connection-vs-thread-per-request)  
Это значит, что каждый запрос будет обработан в своем потоке:
![](thread_per_request.jpg)


## Форма и сроки сдачи
- `git checkout -b rk1 upstream/rk1` после этого работаете в ветки
- Срок сдачи: **29 марта** на лекции. Целое занятие будет посвящено сдаче этого задания.
- Задание выполняется индивидуально
- Формат сдачи:
    1. вы показываете свой красивый PR с выполненным билдом
    1. смотрим тесты и полноту тестового покрытия
    1. вы поднимаете свой AuthServer
    1. вы запускаете тесты на каждый из ресурсных методов
    1. мы делаем запросы к вашему сервису 
    1. смотрим код AuthServer'а и обсуждаем его    
- Если нам что-то не нравится на одном из этапов - попросим внести коррективы прямо на паре
- Максимальное количество баллов, которое можно получить за первый рубежный контроль - **16**
- Обращаю внимание, что вы должны понимать (уметь объяснить) для чего нужна каждая строчка вашего кода.

## Критерии оценки
1. Корректная реализация поставленной задачи
1. Зеленый билд (пройденный чекстайл)
1. Качественные тесты
1. Покрытие кода > 60%
1. Грамотное логгирование
1. Разворачивание сервера не из Idea, а из командной строки
1. Понимание происходящего


title Game infrastructure

participant Alice
participant Bob

Alice->Matchmaker: join(name=Alice)
note right of Alice: POST matchmaker/join
note right of Matchmaker: Matchmaker doesn't have vacant games
note right of Matchmaker: Matchmaker has to ask for a new one
Matchmaker->GameService: create(playerCount=2)
note right of Matchmaker: POST crea
note right of GameService: GameService creates new game 
GameService->Matchmaker: gameId: 42
Matchmaker-> Alice: gameId: 42
Alice-> GameService: connect(gameId=42, name=Alice)
note right of Alice: Alice is connected to GameService via websocket 
note right of Matchmaker: now game 42 has 1 out of 2 players
Bob->Matchmaker:  join(name=Bob)
note right of Matchmaker: Matchmaker has a vacant place in game 42 
Matchmaker->Bob: gameId=42
Bob-> GameService: connect(gameId=42, name=Bob)
note right of Bob: Bob is connected to GameService via websocket 

note right of Matchmaker: now game 42 has 2 out of 2 players
note right of Matchmaker: time to ask GameService to start game 42
Matchmaker->GameService: start(gameId=42)

