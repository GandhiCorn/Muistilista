CREATE TABLE Askareet
(
id SERIAL PRIMARY KEY,
Tarkeys integer,
Nimi varchar(255) NOT NULL,
luokka_id INTEGER REFERENCES Luokat(id)
);

CREATE TABLE Luokat
(
id SERIAL PRIMARY KEY,
Nimi varchar(255) NOT NULL,
askare_id INTEGER REFERENCES Askareet(id)
);