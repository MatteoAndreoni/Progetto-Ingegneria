DROP TABLE IF EXISTS Musician CASCADE;

CREATE TABLE Musician(
    id serial PRIMARY KEY,
    name varchar(150),
    genre varchar(150),
    birthdate DATE,
    instruments TEXT ARRAY
);

DROP TABLE IF EXISTS Products CASCADE;

CREATE TABLE Products (
	id serial PRIMARY KEY, 
	title VARCHAR(150), 
	tracklist TEXT ARRAY, 
	coverimage VARCHAR(60), 
	price NUMERIC(5,2),
	firstadded DATE,
	description VARCHAR(512), 
  artist NUMERIC,
	genre VARCHAR(20),
  involvedartists TEXT ARRAY,
	usedinstruments TEXT ARRAY, 
	productstocks integer
);

DROP TABLE IF EXISTS Utente CASCADE;

CREATE TABLE Utente(
	cf CHAR(16), 
	username VARCHAR(15) PRIMARY KEY, 
	password VARCHAR(20) NOT NULL, 
	name VARCHAR(20), 
	surname VARCHAR(20), 
	address VARCHAR(100), 
	telephone VARCHAR(15), 
	cellphone VARCHAR(15),
	isemployee BOOLEAN
);

DROP TABLE IF EXISTS Sale CASCADE;

CREATE TABLE Sale(
  id SERIAL PRIMARY KEY,
	username VARCHAR(15) REFERENCES Utente(username) ON UPDATE CASCADE ON DELETE SET DEFAULT ,
	products INTEGER ARRAY,
	price NUMERIC, 
	saledatetime TIMESTAMP with TIME ZONE , 
	ip VARCHAR(15), 
	paymenttype VARCHAR(20), 
	deliverytype VARCHAR(10)
);

INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Caparezza','rap','1973-10-09','{"voce"}');
INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Anastacia', 'pop','1968-09-17', '{"voce"}');
INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Pink Floyd', 'rock','1965-01-01', '{"voce, chitarra, batteria, percussioni, basso, tastiera, pianoforte, organo, sintetizzatore"}');
INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Jimi Hendrix','rock','1942-11-27','{"voce, chitarra, basso, pianoforte"}');
INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Noel Redding','rock','1945-12-25','{"voce, basso"}');
INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Mitch Mitchell','rock','1947-07-09','{"batteria, percussioni"}');
INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Michael Jackson','pop','1958-08-29','{"voce"}');


INSERT INTO Products (title,tracklist,coverimage,price,firstadded,description,artist,genre,involvedartists,usedinstruments,productstocks)
VALUES('Prisoners 709','{"Prosopagnosia,Prisoner 709 ,La caduta di Atlante, Forever Jung, Confusianesimo, Il testo che avrei voluto scrivere,Una chiave, Ti fa stare bene, Migliora la tua memoria con un click, Larsen, Sogno di potere, La guardia, Minimoog, La finestra, Autoipnotica, Prosopagno sia"}','resources/Prisoner.png',18.99,'2017-09-15','Settimo album in studio del rapper italiano Caparezza',1,'rap rock','{"Caparezza, Max Gazzè, DMC"}','{chitarra, voce, basso, violoncello, tastiera, pianoforte, batteria, violino, viola, tromba, sassofono, trombone, corno}',100);
INSERT INTO Products (title,tracklist,coverimage,price,firstadded,description,artist,genre,involvedartists,usedinstruments,productstocks)
VALUES('Evolution','{"Caught in The Middle, Redlight, Stamina, Boxer, My Everything, Nobody Loves Me Better, Reckless, Not Coming Down, Before, Pain, Why, Boomerang, Higher Livin"}','resources/Evolution.png',17.99,'2017-09-15','Settimo album in studio della cantautrice statunitense Anastacia',2,'pop','{"Anastacia"}','{voce}',100);
INSERT INTO Products (title,tracklist,coverimage,price,firstadded,description,artist,genre,involvedartists,usedinstruments,productstocks)
VALUES('The Dark Side of the Moon','{"Breathe, On the Run, Time, The Great Gig in the Sky, Money, Us and Them, Any Colour You Like, Brain Damage, Eclipse"}','resources/Dark.png',17.99,'2016-02-01','Ottavo album in studio del gruppo musicale britannico Pink Floyd',3,'rock','{"Pink Floyd"}','{voce, chitarra, basso, pianoforte, organo, batteria, sassofono}',100);
INSERT INTO Products (title,tracklist,coverimage,price,firstadded,description,artist,genre,involvedartists,usedinstruments,productstocks)
VALUES ('Are You Experienced', '{"Foxy Lady, Manic Depression, Red House, Can You See Me, Love or Confusion, May This Be Love, Fire, Third Stone from the Sun, Remember"}','resources/Experience.png', 16.99, '2016-02-01', 'Album di debutto della band inglese/americana The Jimi Hendrix Experience',4,'rock','{"Jimi Hendrix, Noel Redding, Mitch Mitchell"}','{voce,chitarra,pianoforte,basso,batteria,percussioni}', 100);
INSERT INTO Products (title,tracklist,coverimage,price,firstadded,description,artist,genre,involvedartists,usedinstruments,productstocks)
VALUES('This is it','{"FILM DVD"}','resources/This.png',7.25,'2016-02-01','Il film documenta le prove del grande show a cui Michael stava lavorando prima della sua improvvisa scomparsa',7,'pop','{"Michael Jackson"}','{voce}',100);

INSERT INTO Utente (cf, username, password, name, surname, address, telephone, cellphone, isemployee)  VALUES ('aaaaaaaaaaaaaaaa','john','a','john','jonhyllo','hello city','1234567','12345456', FALSE );

INSERT INTO Utente (cf, username, password, name, surname, address, telephone, cellphone, isemployee)  VALUES ('aaaaaaaaaaaaaaaa','admin','admin','admin','admin','hello city','1234567','12345456', TRUE );

INSERT INTO Sale (username, products, price,saledatetime) VALUES ('john','{1,2}',1,'2016-06-22 22:10:25-04');
