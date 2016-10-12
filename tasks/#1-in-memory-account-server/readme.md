### Рубежный контроль \#1

#### In-memory authentication HTTP-server

На третьей лекции мы писали HTTP-client, регистрировались и аутентифицировались с его помощью.
Теперь самое время перейти на другую сторону.

Нам нужно разработать **In-memory authentication HTTP-server** (далее Сервер).

Такой Сервер может быть использован на любом ресурсе для проверки подлинности идентификатора пользователя.
Для нас Сервер будет частью реализации нашей игры.

#### Аутентификация
[Аутентификация](https://ru.wikipedia.org/wiki/%D0%90%D1%83%D1%82%D0%B5%D0%BD%D1%82%D0%B8%D1%84%D0%B8%D0%BA%D0%B0%D1%86%D0%B8%D1%8F_%D0%B2_%D0%98%D0%BD%D1%82%D0%B5%D1%80%D0%BD%D0%B5%D1%82%D0%B5) - 
это проверка подлинности предъявленного пользователем идентификатора.
Мы будем пользоваться аутентификацией с помощью токена.

![](auth_schema.jpg)

#### In-memory

#### API
Строить наш Сервер будем исходя из предположения, что **все поступающие в него запросы строго последовательны**.
Это значит, что **не нужно заботиться о разделении потоками ресурсов**.

#### Базовые структуры данных

#### Технологический стек
##### Servlet

##### Jetty
Jetty - контейнер сервлетов. Jetty можно себе представлять как коллекцию, содержащюю в себе набор сервлетов.
При получении HTTP-запроса Jetty своими внутренними методами осуществляет обращение к методу соответсвующего запросу сервлета.

**Jetty реализует модель thread per request.** [link](http://stackoverflow.com/questions/15217524/what-is-the-difference-between-thread-per-connection-vs-thread-per-request) 

Это значит, что каждый запрос будет обработан в своем потоке. Эти потоки берутся из некоторого `thread-pool`'a. 

[Quick start](https://www.eclipse.org/jetty/documentation/current/quick-start.html)

Пример поднятия сервера Jetty.

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


##### Jax-rs
##### Jersey

#### Форма и сроки сдачи

