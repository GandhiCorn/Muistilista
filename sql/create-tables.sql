Create table kayttaja (
kayttajaTunnus varchar (20) not null primary key unique, 
salasana varchar (20) not null);

create table luokka (
luokkaId serial primary key,
nimi varchar (40),
kayttaja varchar(20) references kayttaja(kayttajaTunnus) on delete cascade);

create table askare (
askareenId serial primary key,
tarkeysArvo varchar(3),
nimi varchar (40) ,
kayttaja varchar (20) references kayttaja(kayttajaTunnus) on delete cascade,
luokkaId integer references luokka(luokkaId) on delete cascade);
