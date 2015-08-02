Create table kayttaja (
kayttajaId serial primary key,
kayttajaTunnus varchar (10) unique, 
salasana varchar (20));

create table luokka (
luokkaId serial primary key,
nimi varchar (40),
kayttajaId integer references kayttaja(kayttajaId) on delete cascade);

create table askare (
askareenId serial primary key,
tarkeysArvo varchar(3),
nimi varchar (40) ,
kayttajaId integer references kayttaja(kayttajaId) on delete cascade,
luokkaId integer references luokka(luokkaId) on delete cascade);