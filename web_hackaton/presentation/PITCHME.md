---
# Java
2018
## Web hackaton

---

Отметьтесь на портале  
https://sphere.mail.ru/

---

### get ready
```bash
git fetch upstream
git checkout -b web_hackaton upstream/web_hackaton
cd web_hackaton
```

---

Разделитесь на группы по **3**

---

- Реализуйте сервер для **чата**
- Реализуйте как можно больше фич (следующий слайд)
Подумайте, за какие фичи стоит взяться, а какие вам не под силу
- 20.30 - заканчиваем реализацию и показываете нам **список фичей** и ссылку на сервер, по которой мы можем получить ваш **index.html**
- группы с наибольшим количеством фичей показывают свой сервис

---

**Features:**
1. chat/say, chat/chat, chat/logout - 4p
1. save history to file on server - 2p
1. message timestamp - 2p
1. different colors for name and timestamp - 2p
1. hrefs highlighting - 2p
1. anti-spam - 2p
1. anti-injection - 2p
1. test fixes - 2p
1. deploy to aws - 3p
1. test with server startup inside - 3p
1. list of online users in side panel - 3p
1. authentication - 3p
1. pretty frontend - 2p each feature

---

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
        400 - Already logged in
        400 - Too long name (longer than 30 symbols)
```

---

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

---

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

---

## Chat REST API. Say
say:
```
    Protocol: HTTP
    Path: chat/say
    Method: POST
    Body:
      name=my_name&msg=my message
    Host: {IP}:8080
    Response:
      Success code: 200
      Fail code:
        401 - Not logged in
```

---

## Chat REST API. Logout
logout:
```
    Protocol: HTTP
    Path: chat/logout
    Method: POST
    Body:
      name=my_name
    Host: {IP}:8080
    Response:
      Success code: 200
```

---

**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**