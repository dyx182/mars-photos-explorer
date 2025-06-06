# Mars Photos Explorer

Консольное приложение для поиска фотографий поверхности Марса по заданным параметрам, использующее официальное API NASA. Проект реализует кеширование данных в локальной БД H2 для уменьшения количества запросов к API.

## Установка и запуск
1. Клонировать репозиторий:
   git clone https://github.com/ваш-username/mars-photos-explorer.git
2. Запустить клаасс App в любой IDE

![](/docs/image/запуск.png)

3. Ввести в консоли данные, согласно подсказкам.

![](/docs/image/консоль.png)

4. Ссылку можно открыть в любом браузере

![](/docs/image/фото.png)
## Технологический стек
- **Java 17**
- **RestAssured** - для работы с NASA API
- **H2 Database** - встроенная СУБД для кеширования
- **Lombok** - для сокращения шблонного кода
- **Gson** - для работы с JSON 
- **JDBC** - для работы с базой данных

## Особенности реализации
- Кеширование полученных данных
- Обработка ошибок API и сетевых проблем
- Валидация пользовательского ввода
- Логирование ошибок в консоль

## Проект помогает в освоении:
1. Работы с внешними REST API
2. Организации кеширования данных
3. Принципов работы с JDBC
4. Обработки исключений

# Mars Photos Explorer
A console application for searching Mars surface photos by specified parameters using NASA's official API. The project implements data caching in a local H2 database to reduce API requests.

## Tech Stack
Java 17
RestAssured - for working with NASA API
H2 Database - embedded DBMS for caching
Lombok - for reducing boilerplate code
Gson - for JSON processing
JDBC - for database operations

## Implementation Features
Data caching
API error handling and network issue management
User input validation
Console error logging

## The project helps in mastering:


Working with external REST APIs
Implementing data caching
JDBC fundamentals
Exception handling

## Installation & Launch
Clone the repository:
Copy
git clone https://github.com/your-username/mars-photos-explorer.git
