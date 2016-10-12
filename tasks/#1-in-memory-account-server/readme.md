## Рубежный контроль \#1

### In-memory authentication HTTP-server

На лекции 4 мы рассмотрели примеры HTTP серверов:
Echo server, использующий простой сервлет DummyServlet, который возвращает на запрос HTML страничку с информацией о сервере
https://github.com/rybalkinsd/atom/tree/master/lecture4/source/src/main/java/ru/atom/servlets/examples
И вкратце разобрали более production-ready пример использования сервлетов для реализации сервера Tinder
https://github.com/rybalkinsd/atom/tree/master/lecture4/source/src/main/java/ru/atom/tinder/server

Основываясь на этих примерах, вам нужно разработать **In-memory authentication HTTP-server** (далее AuthServer).

Такой AuthServer может быть использован на любом ресурсе для проверки подлинности идентификатора пользователя.
В нашем случае AuthServer будет использован для того, чтобы контролировать доступ игрока на игровой сервер.

### Аутентификация
[Аутентификация](https://ru.wikipedia.org/wiki/%D0%90%D1%83%D1%82%D0%B5%D0%BD%D1%82%D0%B8%D1%84%D0%B8%D0%BA%D0%B0%D1%86%D0%B8%D1%8F_%D0%B2_%D0%98%D0%BD%D1%82%D0%B5%D1%80%D0%BD%D0%B5%D1%82%D0%B5) - 
это проверка подлинности предъявленного пользователем идентификатора.
Мы будем пользоваться аутентификацией с помощью токена.

![](auth_schema.jpg)

### In-memory
На данном этапе мы не будем думать о сохранении данных о регистрации пользователей в базе данных. 

### API


### Технологический стек
#### Servlet
Класс - обработчик HTTP запросов
Простейший пример сервлета, возращающего html с текстом "Hello, World!":

```
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HelloWorld extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter().println("<h1>Hello, World!</h1>");
  }
}
```

#### Jetty
Jetty - контейнер сервлетов. Jetty можно себе представлять как коллекцию, содержащюю в себе набор сервлетов.
При получении HTTP-запроса Jetty своими внутренними методами осуществляет обращение к методу соответсвующего запросу сервлета.

**Jetty реализует модель thread per request.** [link](http://stackoverflow.com/questions/15217524/what-is-the-difference-between-thread-per-connection-vs-thread-per-request) 
Это значит, что каждый запрос будет обработан в своем потоке. Эти потоки берутся из некоторого `thread-pool`'a. 
![](thread_per_request.jpg)

[Quick start tutorial](https://www.eclipse.org/jetty/documentation/current/quick-start.html).

Пример поднятия сервера Jetty

``` java
public class HelloWorld extends AbstractHandler
{
    @Override
    public void handle( String target,
                        Request baseRequest,
                        HttpServletRequest request,
                        HttpServletResponse response ) throws IOException, ServletException {
                        
        // Declare response encoding and types
        response.setContentType("text/html; charset=utf-8");

        // Declare response status code
        response.setStatus(HttpServletResponse.SC_OK);

        // Write back response
        response.getWriter().println("<h1>Hello World</h1>");

        // Inform jetty that this request has now been handled
        baseRequest.setHandled(true);
    }
    
    public static void main( String[] args ) throws Exception {
        Server server = new Server(PORT);
        server.setHandler(new HelloWorld());

        server.start();
        server.join();
    }
}
```


#### Jax-rs
The Java API for RESTful Web Services - Это [набор спецификаций](https://jcp.org/en/jsr/detail?id=311), который вы вряд ли когда-либо полностью прочитаете, но в некоторых случаях полезно открыть.

Из того, что важно понимать: Jax-rs - это предоставляемый нам интерфейс для простого описания REST-сервисов.

Пример использования Jax-rs
``` java
    import javax.ws.rs.*;
    
    @Get
    @Path("hello")
    @Produces("text/html; charset=UTF-8")
    public class HelloWorld {
        public Response hello()
            return Response.ok("<h1>Hello World</h1>").build();
        }
    }
```

Полезные статьи про Jax-rs:
- [на Хабре](https://habrahabr.ru/post/140181/)
- [в блоге IBM](https://www.ibm.com/developerworks/ru/library/wa-jaxrs/)
- [туториал от Oracle](http://docs.oracle.com/javaee/6/tutorial/doc/gilik.html)

#### Jersey
[Project homepage](https://jersey.java.net/).

Jersey для нас - это тулкит(библиотека) для упрощенной работы с Jax-rs.

#### Форма и сроки сдачи
Срок сдачи - 25 октября на лекции. Целое занятие будет посвящено сдаче этого задания
Задание выполняется индивидуально
Сдача происходит следующим образом:
Вы поднимаете свой сервер, мы делаем к нему запросы, после этого смотрим код сервера и обсуждаем его
Максимальное количество баллов, которое можно получить за первый рубежный контроль - 16
