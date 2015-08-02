INSERT INTO kayttaja (tunnus, salasana) VALUES
    ('kayttaja', 'salasana');
INSERT INTO luokka (nimi, kayttajaId) VALUES
    ('koti', (select kayttajaId from kayttaja where tunnus = 'kayttaja'));
INSERT INTO askare (nimi, tarkeysArvo, kayttajaId,  luokkaId) VALUES
    ('imuroi', 1, (select kayttajaId from kayttaja where tunnus = 'kayttaja'), (select luokkaId from luokka where nimi = 'koti' limit 1));
