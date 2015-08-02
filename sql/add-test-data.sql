INSERT INTO kayttaja (tunnus, salasana) VALUES
    ('kayttaja', 'salasana');
INSERT INTO tarkeys (arvo, kayttajaId, selite) VALUES
    (7, (select kayttajaId from kayttaja where tunnus = 'kayttaja'), 'Tarkea');
INSERT INTO luokka (nimi, kayttajaId) VALUES
    ('koti', (select kayttajaId from kayttaja where tunnus = 'kayttaja'));
INSERT INTO askare (nimi, kayttajaId, tarkeysId, luokkaId) VALUES
    ('imuroi', (select kayttajaId from kayttaja where tunnus = 'kayttaja'), (select tarkeysId from tarkeys where selite = 'Tarkea'),(select luokkaId from luokka where nimi = 'koti' limit 1));
