INSERT INTO kayttaja (kayttajatunnus, salasana) VALUES
    ('kayttaja', 'salasana');
INSERT INTO kayttaja (kayttajatunnus, salasana) VALUES
    ('kayttaja1', 'salasana1');
INSERT INTO luokka (nimi, kayttaja) VALUES
    ('koti', (select kayttajaTunnus from kayttaja where kayttajatunnus = 'kayttaja'));
INSERT INTO luokka (nimi, kayttaja) VALUES
    ('koti1', (select kayttajatunnus from kayttaja where kayttajatunnus = 'kayttaja1'));
INSERT INTO askare (nimi, tarkeysArvo, kayttaja,  luokkaId) VALUES
    ('imuroi', 1, (select kayttajatunnus from kayttaja where kayttajatunnus = 'kayttaja'), (select luokkaId from luokka where nimi = 'koti' limit 1));
INSERT INTO askare (nimi, tarkeysArvo, kayttaja,  luokkaId) VALUES
    ('imuroi1', 2, (select kayttajatunnus from kayttaja where kayttajatunnus = 'kayttaja1'), (select luokkaId from luokka where nimi = 'koti1' limit 1));
