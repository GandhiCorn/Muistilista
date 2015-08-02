CREATE TABLE Askareet
(
askareId serial NOT NULL PRIMARY KEY,
tarkeysArvo integer,
askareenNimi varchar(255) NOT NULL,
luokanId int references Luokat(luokkaId)
);

CREATE TABLE Luokat
(
luokkaId serial NOT NULL PRIMARY KEY,
luokanNimi varchar(255) NOT NULL,
askareenId int references Askareet(askareId)
);