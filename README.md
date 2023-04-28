Flight manager
Данное приложение позволяет вести учет полетного времени бортпроводников (БП).
Пользователь с ролью ADMIN (старший БП) добавляет, удаляет, редактирует рейсы, назначает на
каждый рейс БП, контролирует допуски БП на типы воздушных судов (ВС), а так же
сроки проверок каждого БП.
Пользователь с ролью USER может отслеживать статистику своих полетов (налет за
календарный месяц, год, за квалификационный период, срок проверки, географию
полетов, а так же налет по типам ВС).

Краское описание сущностей
Aircraft 
id (INTEGER) - PK
model (VARCHAR) - тип ВС

Airport
id (INTEGER) - PK
code (CHAR) - IATA код аэропорта, например, Шереметьево - SVO
city_id (INTEGER) - id города

City
id (INTEGER) - PK
name (VARCHAR) - город
country_id (INTEGER) - id страны

Country
id (INTEGER) - PK
name (VARCHAR) - страна

Crew
id (INTEGER) - PK
firstname (VARCHAR) - имя
lastname (VARCHAR) - фамилия
email (VARCHAR) - адрес электронной почты
password (VARCHAR) - пароль
birth_date (DATE) - дата рождения
employment_date (DATE) - дата приема на работу
mkk_date (DATE) - дата следующей ежегодной проверки
role (VARCHAR) - роль в системе (ADMIN, USER)

Flight
id (BIGINT) - PK
flight_no (VARCHAR) - номер рейса
departure_airport_id (INTEGER) - id аэропорта вылета
transit_airport_id (INTEGER) - id транзитного аэропорта (может быть null)
arrival_airport_id (INTEGER) - id аэропорта назначения
aircraft_id (INTEGER) - id типа ВС
time (BIGINT) - полетное время в секундах

CrewAircraft
id (BIGINT) - PK
crew_id (INT) - id БП
aircraft_id (INT) - id типа ВС
permit_date (DATE) - дата получения БП допуска на определенный тип ВС

FlightCrew
id (BIGINT) - PK
crew_id (INT) - id БП
flight_id (BIGINT) - id рейса
class_of_service (VARCHAR) - класс обслуживания, в котором БП работал в рейсе (BUSINESS, ECONOMY)
is_turnaround (BOOLEAN) - является ли рейс разворотным (если нет, то это командировка, и полет разбивается на несколько частей)
is_passenger (BOOLEAN) - в случае TRUE, БП выполнял рейс в качестве пассажира, полетное время не учитывается,
поле необходимо для статистики