#HSLIDE
# Java
hackaton
## Web service

#HSLIDE
### get ready
```bash
> git fetch upstream
> git checkout -b web_hackaton upstream/web_hackaton
```

#HSLIDE
Разделитесь на группы по **3**

#HSLIDE
- Реализуйте сервер для **чата** (как в лекции 4) 
- Реализуйте как можно больше фич (следующий слайд)
Подумайте, за какие фичи стоит взяться, а какие вам не под силу
- 20.20 - заканчиваем реализацию и показываете нам **список фичей** и ссылку на сервер, по которой мы можем получить ваш **index.html**
- группы с наибольшим количеством фичей показывают свой сервис

#HSLIDE
**Features:**
1. chat/login, chat/say, chat/chat - 5p
1. chat/logout - 3p
1. save history to file on server - 3p
1. message timestamp - 2p
1. different colors for name and timestamp - 3p
1. hrefs highlighting - 3p
1. anti-spam - 3p
1. pretty frontend - 3p
1. test coverage 50% 2p
1. test coverage 80% 4p
1. authentication back - 5
1. authentication front - 4

#HSLIDE
## Chat REST API. Login
login:
```
    Protocol: HTTP
    Path: chat/login
    Method: POST
    PathParam: name
    Host: {IP}:8080
    Response:
      Success code: 200
      Fail code:
        400 - Already logined
        400 - Too long name (longer than 30 symbols)
```

#HSLIDE
## Chat REST API. View chat
online:
```
    Protocol: HTTP
    Path: chat/chat
    Method: GET
    Host: {IP}:8080
    Response:
      Success code: 200
```

#HSLIDE
## Chat REST API. View online users
online:
```
    Protocol: HTTP
    Path: chat/online
    Method: GET
    Host: {IP}:8080
    Response:
      Success code: 200
```

#HSLIDE
## Chat REST API. Say
say:
```
    Protocol: HTTP
    Path: chat/say
    Method: POST
    PathParam: name
    Body:
      msg="my message"
    Host: {IP}:8080
    Response:
      Success code: 200
      Fail code:
        401 - Not logined
        400 - Too long message (longer than 140 symbols)
```

#HSLIDE
## Chat REST API. Logout
logout:
```
    Protocol: HTTP
    Path: chat/logout
    Method: POST
    PathParam: name
    Host: {IP}:8080
    Response:
      Success code: 200
```

#HSLIDE
**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**