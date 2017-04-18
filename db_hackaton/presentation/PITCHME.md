## DB-hackaton (RK2)

### Database authentication HTTP-server and match-maker

#HSLIDE
### Intro
На РК \#1 мы сделали In-memory authentication HTTP-server
1. Первая задача на хакатон - миграция **аккаунт-сервера** из памяти в базу данных. 
2. Вторая задача - реализация **матч мейкера** с сохранением результатов матчей в базу.

#HSLIDE
### Задача на хакатон
- вы работаете в командах. Кто не нашел команду - сделайте это прямо сейчас
- Реализуйте как можно больше фич (далее)
Подумайте, за какие фичи стоит взяться, а какие вам не под силу
- 20.30 - заканчиваем реализацию и показываете нам **список фичей** и ссылку на сервер, по которой мы можем получить ваш **index.html**
- группы с наибольшим количеством фичей показывают результат

#HSLIDE
### Учимся работать вместе
Выберите лучшую реализацию **rk1** в команде и работайте с ней.  
Теперь вы работаете вместе над одним кодом - работайте в одном репозитории.  
Весь код игры далее вы будете разрабатывать в этом репозитории

#HSLIDE
### Инструкции для владельца репозитория
Ваша реализация должна быть основана на результатах **РК \#1**
1. автор лучшего rk1 должен вычекать ветку **db_hackaton**
```bash
> git fetch upstream
> git checkout -b db_hackaton upstream/db_hackaton
```
2. вмержить ее в rk1. Разрешите конфликты, если необходимо
```bash
> git merge rk1
> git push origin db_hackaton
```
3. Добавьте остальных участников группы в коллабораторы  
[https://github.com/BEST_RK1_AUTHOR/atom/settings/collaboration](https://github.com/BEST_RK1_AUTHOR/atom/settings/collaboration)  

#HSLIDE
### Инструкции для остальных членов команды
остальные участники команды должны склонировать репозиторий **** и дальше при разработке игры работать с ним
```bash
> git clone https://github.com/BEST_RK1_AUTHOR/atom.git
```
Обратите внимание, что после клонирования репозитория, удаленный репозиторий будет доступен как **origin**
```bash
> git fetch origin
> git checkout db_hackaton
... (make your changes, add and commit)
> git push origin db_hackaton
```
Не забываем рефрешить gradle проект.

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
1. frontend improvements (error handling, usability, ...) - 2

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
### Match-maker service
**Match-maker** (mm) - сервис, производящий маршрутизацию и балансировку запросов на старт игры, а также сбор результатов окончания игры. **mm** обладает следующим интерфейсом:  
- **GET /mm/join** - запрос на старт игры, возвращающий url, по которому **front-end** игры будет общаться с игровым сервером.   
Таким образом за интерфейсом **mm** могут скрываться несколько игровых серверов, а сам **mm** может осуществлять балансировку  
- **POST /mm/result** - сохранение результата игры. Результат содержит информацию об очках, набранных каждым участником игры.  
Обратите внимание, что **mm** - это отдельный сервис с отдельной точкой входа. Подробное описание API mm дальше

#HSLIDE
### /mm/join resource method
  ```
  Protocol: HTTP
  Path: mm/join
  Method: POST
  Host: {IP}:{PORT} (IP = localhost при локальном тестрировании сервера)
  Headers:
      Content-Type: application/x-www-form-urlencoded
  Body:
      token={}

  Response: 
      Code: 200
      Content-Type: text/plain
      Body: wtfis.ru:8090/gs/12345
  ```
В ответе пока возвращаем произвольный URL (игровой сервер нами еще не реализован)

#HSLIDE
### /mm/finish resource method
Метод вызывается игровым сервером после окончания игры. Метод принимает json с результатом игры
  ```
  Protocol: HTTP
  Path: mm/finish
  Method: POST
  Host: {IP}:{PORT} (IP = localhost при локальном тестрировании сервера)
  Headers:
      Content-Type: application/x-www-form-urlencoded
  Body:
      {"id":12345, "result":{"user1":10, "user2":15}}

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
Хранение незашифрованного пароля в базе - плохая практика. Компроментация базы данных в этом случае приведет к утечке 
пароля и возможной компроментации пользователя на других сервисах. 
Реализуйте систему, при которой в базу сохраняется **хэш** пароля, а не сам пароль


#HSLIDE
### Замечания 1
- Примеры общения с базой есть в **лекциях 6 и 7**.    
- Вы сами можете выбрать, использовать **Hibernate** или **JDBC**.  
- Разработка схемы бд - ваша задача  
- Как и раньше, публичные базы доступны по адресу **wtfis.ru**  
[Пост с раздачей баз](https://atom.mail.ru/blog/topic/view/8603/)

#HSLIDE
### Замечания 2
- Нет игры, MM, чего-то еще - делайте моки, заглушки
- Матчмейкер долен подниматься на своем порту, Auth на своем
- работа с JS - google-driven-development
- Cookies - это такие печеньки
- jquery
- ajax

