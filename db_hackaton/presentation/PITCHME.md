## DB-hackaton (RK2)

### Database authentication HTTP-server

#HSLIDE
### Intro
На РК \#1 мы сделали In-memory authentication HTTP-server
1. Первая задача на хакатон - миграция **аккаунт-сервера** из памяти в базу данных. 
2. Вторая задача - реализация **матч мейкера** с сохранением результатов матчей в базу.

#HSLIDE
### Задача на хакатон
- вы работаете в командах. Кто не нашел команду - сделайте это прямо сейчас
- Реализуйте как можно больше фич (следующий слайд)
Подумайте, за какие фичи стоит взяться, а какие вам не под силу
- 20.30 - заканчиваем реализацию и показываете нам **список фичей** и ссылку на сервер, по которой мы можем получить ваш **index.html**
- группы с наибольшим количеством фичей показывают результат

#HSLIDE
### Учимся работать вместе
Выберите лучшую реализацию **rk1** в команде и работайте с ней.  
Теперь вы работаете вместе над одним кодом - работайте в одном репозитории.
Ваша реализация должна быть основана на результатах **РК \#1**. Поэтому автор лучшего rk1 должен вычекать ветку **db_hackaton** и вмержить ее в rk1
```bash
> git fetch upstream
> git checkout -b db_hackaton upstream/db_hackaton
> git merge rk1
> git push origin db_hackaton
```
Проследите, что мерж прошел успешно и не возникло **конфликтов**. Если возникли конфликты, разберитесь как их разрешать.  
После того, как автор лучшего rk1 запушил изменения, остальные участники команды должны выкачать **его** репозиторий и работать с ним
```bash
> git clone https://github.com/BEST_RK1_AUTHOR/atom.git
> git checkout db_hackaton
... (make your changes, add and commit)
> git push origin db_hackaton
```

#HSLIDE
### Features
1. /auth/register persistence - 2
1. /auth/login persistence - 2
1. /auth/logout persistence - 2
1. /mm/join resource method - 2
1. /mm/finish resource method - 2
1. /mm/finish persistence - 3
1. Saving hashed password - 3
1. tests 40% - 2
1. tests 80% - 4

#HSLIDE
### 1. /auth/register persistence
см. rk1 **/auth/register**  
Успешная регистрация должна сохранять в базе данных
- login
- password
- registration date

#HSLIDE
### 2. /auth/login persistence
см. rk1 **/auth/logout**  
Успешный логин должен сохранять в базе **токен**. Пользователь считается залогиненым тогда и только тогда когда у нго есть токен

#HSLIDE
### 3. /auth/login persistence
см. rk1 **/auth/logout**  
Успешный логаут удаляет токен у пользователя

#HSLIDE
### Match maker service
**Match maker** (mm) - сервис, производящий маршрутизацию и балансировку запросов на старт игры, а также сбор результатов окончания игры  
**mm** обладает следующим интерфейсом:  
- **GET /mm/join** - запрос на старт игры, возвращающий url, по которому **front-end** игры будет общаться с игровым сервером (**gs**)  
Таким образом за интерфейсом **mm** могут скрываться несколько игровых серверов, а самм **mm** может осуществлять балансировку  
- **POST /mm/result** - сохранение результата игры. Результат содержит информацию об очках, набранных каждым участником игры.  
> Обратите внимание, что mm - это отдельный сервис с отдельной точкой входа   
Подробное описание API mm дольше

#HSLIDE
### /mm/join resource method
  ```
  Protocol: HTTP
  Path: mm/join
  Method: GET
  Host: {IP}:8080 (IP = localhost при локальном тестрировании сервера)
  Headers:
      Content-Type: application/x-www-form-urlencoded
  Body:
      name={}token={}

  Response: 
      Code: 200
      Content-Type: text/plain
      Body: wtfis.ru:8090/gs/12345
  ```
В ответе пока возвращаем произвольный URL (gs нами еще не реализован)

#HSLIDE
### /mm/finish resource method
Метод вызывается игровым сервером после окончания игры. Метод принимает json с результатом игры
  ```
  Protocol: HTTP
  Path: mm/finish
  Method: POST
  Host: {IP}:8080 (IP = localhost при локальном тестрировании сервера)
  Headers:
      Content-Type: application/x-www-form-urlencoded
  Body:
      {id='12345', 'result':{'user1'=10, 'user2'=15}}

  Response: 
      Code: 200
  ```

#HSLIDE
### /mm/finish persistence
Успешный запрос на mm/finish должен сохранять в базу информацию о состоявшейся игры:
- id игры
- количество очков, набранное каждым игроком

#HSLIDE
### Saving hashed password
Хранение незашифрованного пароля в базе - плохая практика. Компроментация базы данных в хтом случае приведет к утечке пароля и возможной компроментации пользователя на других сервисах. Реализуйте систему, при которой в базу сохраняется **хэш** пароля, а не сам пароль


#HSLIDE
### Замечания
- Примеры общения с базой есть в **лекциях 6 и 7**.    
- Вы сами можете выбрать, использовать **Hibernate** или **JDBC**.  
- Разпаботка схемы бд - ваша задача  
- Как и раньше, публичные базы доступны по адресу **wtfis.ru** [Пост с раздачей](https://atom.mail.ru/blog/topic/view/8603/)
