# test-gis

Технологии: spring cloud, project reactor, r2dbc, java2d
Предпочтительно СУБД: postgresql

Задание:
1. Реализовать основу микросесервисной архитектуры - gateway, discovery, config
2. Реализовать микросервис рендеринга данных
с одним get контроллером со следующими параметрами
* width ширина изображения
* height высота изображения
* bbox: минимальная и максимальная координата прямоугольной области, в которой находятся объекты, которые нужно
  отобразить (обычно в координатах 3857)

3. Использование ProjectReactor и spring cloud ОБЯЗАТЕЛЬНО
4. Плюсом будет браузерный веб клиент на основе openlayer или leafleat

Пояснение:
В БД 1 таблица. -
Объекты - идентификатор, геометрия, цветоопределяющая характеристика

## Дополнительные пояснения

Геометрия - это пространственные данные

В PostgreSQL для этого есть расширение постгис
Если пользуетесь докером, то https://hub.docker.com/r/postgis/postgis

Для простоты будем работать с каким-либо определенным типом геометрии, например линии
Для этого и сделаем таблицу для наших сушностей в базе

CREATE TABLE entity (
id serial NOT NULL,
geom geometry(linestring, 4326) not null,
color text not null,
CONSTRAINT entity_pkey PRIMARY KEY (id)
);

Геометрия из базы должна читаться в соответствующую структуру в JAVA, обычно это классы библиотеки JTS
https://mvnrepository.com/artifact/org.locationtech.jts/jts-core/1.19.0

В итоге наше тестовое задание должно уметь отбирать сущности, находящиеся в заданном прямоугольном фрагменте и
рендерить (отрисовывать) их
Если скомбинировать вместе с клиентом должно получится что то
вроде https://openlayers.org/en/latest/examples/wms-image.html
Только рисоваться должны не штаты, а линии

Чтоб много не думалось, как отбирать сущности с помощью запросов - есть в постгис функции ST_intersects и
ST_makeenvelope
https://postgis.net/docs/ST_MakeEnvelope.html
https://postgis.net/docs/ST_Intersects.html

С помощью первой мы можем сформировать область, которую задал пользователь, с помощью второй - отфильтровать сущности(
линии), находящиеся в ней
После этого все эти линии мы и отрисовываем

Не забывайте, что должны быть применены правильно Spring Cloud и Project Reactor
Пример проекта, где построен скелет клауд приложения: https://github.com/spring-cloud-samples/spring-cloud-intro-demo
Но у нас там даже чуточку проще должно быть, поскольку нет разграничения по правам.
В итоге нужно толтько оставить config, discovery, gateway и сам микросервис рендеринга

Если видите, что нужно что то очень уж много писать, значит идете по неправильному пути, изобретаете велдосипед, задание
больше на умение пользоваться технологиями