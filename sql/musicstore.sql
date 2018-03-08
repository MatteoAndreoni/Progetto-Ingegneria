DROP TABLE IF EXISTS Musician CASCADE;

--PRESENTI SOLO I MUSICISTI E BAND PRINCIPALI
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
	coverimage VARCHAR(100),
	price NUMERIC(5, 2),
	firstadded DATE,
	description VARCHAR(512), 
    artist INTEGER REFERENCES Musician(id) ON UPDATE CASCADE ON DELETE SET DEFAULT,
	genre VARCHAR(50),
    involvedartists TEXT ARRAY,
	usedinstruments TEXT ARRAY, 
	productstocks INTEGER
);

DROP TABLE IF EXISTS Utente CASCADE;

CREATE TABLE Utente(
	cf CHAR(16), 
	username VARCHAR(15) PRIMARY KEY, 
	password VARCHAR(20) NOT NULL, 
	name VARCHAR(30),
	surname VARCHAR(30),
	address VARCHAR(100), 
	telephone VARCHAR(11),
	cellphone VARCHAR(11),
	isemployee BOOLEAN,
	ispremium BOOLEAN DEFAULT FALSE
);

DROP TABLE IF EXISTS Sale CASCADE;

CREATE TABLE Sale(
    id SERIAL PRIMARY KEY,
    username VARCHAR(15) REFERENCES Utente(username) ON UPDATE CASCADE ON DELETE SET DEFAULT ,
	products INTEGER ARRAY,
	price NUMERIC,
	saledatetime TIMESTAMP with TIME ZONE,
	ip VARCHAR(15), 
	paymenttype VARCHAR(20), 
	deliverytype VARCHAR(10)
);

--INSERIMENTO MUSICISTI E BAND
INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Adele','pop','1988-05-05','{"voce"}');

--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Paul McCartney','pop','1942-06-18','{"voce, basso"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('John Lennon','pop','1940-10-09','{"voce, chitarra"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('George Harrison','pop','1943-02-25','{"voce, chitarra"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Ringo Starr','pop','1940-07-07','{"voce, batteria"}');
INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('The Beatles','pop','1957-07-06','{"voce, chitarra, basso, batteria"}');

INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Herbie Hancock','jazz','1940-04-12','{"piano, tastiera"}');

INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Ed Sheeran','pop','1991-02-17','{"voce, chitarra"}');

INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Lady Gaga','pop','1986-03-28','{"voce"}');

INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Miles Davis','jazz','1926-05-26','{"tromba"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('John Coltrane','jazz','1926-09-23','{"sassofono"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Cannonball Adderley','jazz','1928-09-15','{"sassofono"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Bill Evans','jazz','1929-08-16','{"piano"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Paul Chambers','jazz','1935-04-22','{"contrabbasso"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Jimmy Cobb','jazz','1929-01-20','{"batteria"}');

INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('ACDC','rock','1973-12-31','{"voce, chitarra, basso, batteria"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Angus Young','rock','1955-03-31','{"chitarra"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Malcolm Young','rock','1953-01-06','{"chitarra"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Bon Scott','rock','1946-07-09','{"voce"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Mark Evans','rock','1956-03-02','{"basso"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Phil Rudd','rock','1954-05-19','{"batteria"}');

INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Deep Purple','rock','1968-03-01','{"voce, chitarra, basso, tastiera, batteria"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Ian Gillan','rock','1945-08-19','{"voce"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Ritchie Blackmore','rock','1945-04-14','{"chitarra"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Roger Glover','rock','1945-11-30','{"basso"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Jon Lord','rock','1941-06-09','{"tastiera"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Ian Paice','rock','1948-06-29','{"batteria"}');

INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Metallica','metal','1981-10-28','{"voce, chitarra, basso, batteria"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('James Hetfield','metal','1963-08-03','{"voce, chitarra"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Kirk Hammett','metal','1962-11-18','{"chitarra"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Cliff Burton','metal','1962-02-10','{"basso"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Lars Ulrich','metal','1963-12-26','{"batteria"}');

INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Iron Maiden','metal','1975-12-25','{"voce, chitarra, basso, batteria"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Bruce Dickinson','metal','1958-08-07','{"voce"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Steve Harris','metal','1956-03-12','{"basso"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Adrian Smith','metal','1957-02-27','{"chitarra"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Dave Murray','metal','1956-12-23','{"chitarra"}');
--INSERT INTO Musician (name,genre,birthdate,instruments) VALUES ('Nicko McBrain','metal','1952-06-05','{"batteria"}');

--INSERIMENTO PRODOTTI
INSERT INTO Products (title,tracklist,coverimage,price,firstadded,description,artist,genre,involvedartists,usedinstruments,productstocks)
VALUES('25','{"Hello, Send My Love (To Your New Lover), I Miss You, When We Were Young, Remedy, Water Under the Bridge, River Lea, Love in the Dark, Million Years Ago, All I Ask, Sweetest Devotion"}','resources/25.jpg',16.99,'2017-09-15','Terzo album in studio della cantante britannica Adele',1,'pop soul','{"Adele"}','{chitarra, voce, basso, archi, tastiera, pianoforte, batteria}',10);
INSERT INTO Products (title,tracklist,coverimage,price,firstadded,description,artist,genre,involvedartists,usedinstruments,productstocks)
VALUES('Sgt. Pepper''s Lonely Hearts Club Band','{"Sgt. Pepper''s Lonely Hearts Club Band, With a Little Help from My Friends, Lucy in the Sky with Diamonds, Getting Better, Fixing a Hole, She''s Leaving Home, Being for the Benefit of Mr. Kite!, Within You Without You, When I''m Sixty-Four, Lovely Rita, Good Morning Good Morning, Sgt. Pepper''s Lonely Hearts Club Band (Reprise), A Day in the Life"}','resources/beatles.jpg',9.99,'2017-09-15','Ottavo album in studio della band inglese The Beatles',2,'rock','{"John Lennon, Paul McCartney, George Harrison, Ringo Starr"}','{chitarra, voce, basso, batteria}',10);
INSERT INTO Products (title,tracklist,coverimage,price,firstadded,description,artist,genre,involvedartists,usedinstruments,productstocks)
VALUES('Divide','{"Eraser, Castle on the Hill, Dive, Shape of You, Perfect, Galway Girl, Happier, New Man, Hearts Don''t Break Around Here, What Do I Know?, How Would You Feel? (Paean), Supermarket Flowers"}','resources/divide.jpg',17.99,'2017-09-15','Terzo album in studio del cantautore britannico Ed Sheeran',3,'pop','{"Ed Sheeran"}','{chitarra, voce, basso, batteria, tastiere, fiati}',10);
INSERT INTO Products (title,tracklist,coverimage,price,firstadded,description,artist,genre,involvedartists,usedinstruments,productstocks)
VALUES('Head Hunters','{"Chameleon, Watermelon Man, Sly, Vein Melter"}','resources/headhunters.jpg',8.99,'2017-09-15','Dodicesimo album in studio del musicista statunitense Herbie Hancock',4,'jazz funk','{"Herbie Hancock"}','{basso, batteria, tastiere, sassofono, fiati}',10);
INSERT INTO Products (title,tracklist,coverimage,price,firstadded,description,artist,genre,involvedartists,usedinstruments,productstocks)
VALUES('Joanne','{"Diamond Heart, A-Yo, Joanne, John Wayne, Dancin'' in Circles, Perfect Illusion, Million Reasons, Sinner''s Prayer, Come to Mama, Hey Girl, Angel Down"}','resources/joanne.jpeg',15.99,'2017-09-15','Quinto album in studio della cantautrice statunitense Lady Gaga',5,'pop rock','{"Lady Gaga"}','{chitarra, voce, basso, batteria, tastiere, fiati}',10);
INSERT INTO Products (title,tracklist,coverimage,price,firstadded,description,artist,genre,involvedartists,usedinstruments,productstocks)
VALUES('Kind of Blue','{"So What, Freddie Freeloader, Blue in Green, All Blues, Flamenco Sketches"}','resources/kind.jpg',9.99,'2017-09-15','Album realizzato dal trombettista statunitense Miles Davis nel 1959',6,'jazz','{"Miles Davis, John Coltrane, Cannonball Adderley, Jimmy Cobb, Bill Evans, Paul Chambers"}','{tromba, sax tenore, sax contralto, pianoforte, contrabbasso, batteria}',10);
INSERT INTO Products (title,tracklist,coverimage,price,firstadded,description,artist,genre,involvedartists,usedinstruments,productstocks)
VALUES('Let There Be Rock','{"Go Down, Dog Eat Dog, Let There Be Rock, Bad Boy Boogie, Problem Child, Overdose, Hell Ain''t a Bad Place to Be, Whole Lotta Rosie"}','resources/let.jpg',7.99,'2017-09-15','Quarto album in studio della band australiana AC/DC',7,'rock','{"Bon Scott, Angus Young, Malcolm Young, Mark Evans, Phil Rudd"}','{chitarra, voce, basso, batteria}',10);
INSERT INTO Products (title,tracklist,coverimage,price,firstadded,description,artist,genre,involvedartists,usedinstruments,productstocks)
VALUES('Machine Head','{"Highway Star, Maybe I''m a Leo Pictures of Home, Never Before, Smoke on the Water, Lazy, Space Truckin''"}','resources/machine.jpg',7.99,'2017-09-15','Sesto album in studio della band britannica Deep Purple',8,'rock','{"Ian Gillan, Ritchie Blackmore, Roger Glover, Jon Lord, Ian Paice"}','{chitarra, voce, basso, batteria, tastiere}',10);
INSERT INTO Products (title,tracklist,coverimage,price,firstadded,description,artist,genre,involvedartists,usedinstruments,productstocks)
VALUES('Master of Puppets','{"Battery, Master of Puppets, The Thing That Should not Be, Welcome Home (Sanitarium), Disposable Heroes, Leper Messiah, Orion, Damage Inc."}','resources/master.jpg',10.99,'2017-09-15','Terzo album in studio della band americana Metallica',9,'metal','{"James Hetfield, Kirk Hammett, Cliff Burton, Lars Ulrich"}','{chitarra, voce, basso, batteria}',10);
INSERT INTO Products (title,tracklist,coverimage,price,firstadded,description,artist,genre,involvedartists,usedinstruments,productstocks)
VALUES('Powerslave','{"Aces High, 2 Minutes to Midnight, Losfer Words (Big ''Orra), Flash of the Blade, The Duellists, Back in the Village, Powerslave, Rime of the Ancient Mariner"}','resources/powerslave.jpg',9.99,'2017-09-15','Quarto album in studio della band britannica Iron Maiden',10,'metal','{"Bruce Dickinson, Steve Harris, Adrian Smith, Dave Murray, Nicko McBrain"}','{chitarra, voce, basso, batteria}',10);

--INSERIMENTO UTENTI
INSERT INTO Utente (cf, username, password, name, surname, address, telephone, cellphone, isemployee)  VALUES ('ABCDEF12G34H567I','mario','abcd','mario','rossi','milano','1234567','12345456', FALSE );
INSERT INTO Utente (cf, username, password, name, surname, address, telephone, cellphone, isemployee, ispremium) VALUES ('ABCDEF98G76H543I','luca','ciao','luca','bianchi','verona','574250724','23479852', FALSE, TRUE );

INSERT INTO Utente (cf, username, password, name, surname, address, telephone, cellphone, isemployee)  VALUES ('CKWOFK98W76C543X','admin','admin','admin','admin','admin','1234567','12345456', TRUE );
