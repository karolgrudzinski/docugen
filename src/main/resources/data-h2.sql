INSERT INTO CUSTOMERS(ID, ADDRESS, EMAIL, NAME, PHONE) VALUES
(1, STRINGDECODE('ul. Prosta 1/3, 16-400 Suwa\u0142ki'), 'antek@o2.pl', STRINGDECODE('Antoni Nowak, Gra\u017cyna Kowalska'), '123-456-789'),
(2, STRINGDECODE('ul. Krzywa 5/8, 16-300 August\u00f3w'), 'ala@op.pl', STRINGDECODE('Alicja Jaka\u015btam, Andrzej Ma\u0142olepszy'), '789-564-123');

INSERT INTO WEDDING_CEREMONY(ID, CREATION_TIME, MODIFICATION_TIME, ADVANCE, CHARGE, CHURCH, DATE_TIME_OF_EVENT, DATE_OF_SIGNING, PERFORMER, PLACE_OF_EVENT, PLACE_OF_SIGNING, SERVICE_PACKAGE, PAYMENT_METHOD, CUSTOMER_ID, DOCUMENT_SHORT_NAME) VALUES
(1, TIMESTAMP '2019-01-12 00:00:00', TIMESTAMP '2019-01-12 00:00:00', 100.00, 300.00, STRINGDECODE('św Kazimierza'), TIMESTAMP '2019-06-29 16:30:00', DATE '2019-01-12', STRINGDECODE('Pawe\u0142 Grudzi\u0144ski'), 'Suwałkach', 'Suwałkach', 'Organista', 'CASH', 1, '20190629 UMOWA Nowak Suwałki');
INSERT INTO WEDDING_CEREMONY(ID, CREATION_TIME, MODIFICATION_TIME, ADVANCE, CHARGE, CHURCH, DATE_TIME_OF_EVENT, DATE_OF_SIGNING, PERFORMER, PLACE_OF_EVENT, PLACE_OF_SIGNING, SERVICE_PACKAGE, PAYMENT_METHOD, CUSTOMER_ID, DOCUMENT_SHORT_NAME) VALUES
(2, TIMESTAMP '2019-01-15 11:30:00', TIMESTAMP '2019-01-15 11:30:00', 100.00, 500.00, 'św Antoniego',
 TIMESTAMP '2019-08-17 17:15:00', DATE '2019-01-15', STRINGDECODE('Pawe\u0142 Grudzi\u0144ski'), 'Suwałkach', 'Suwałkach', 'Organista, skrzypce', 'TRANSFER', 2, '20190817 UMOWA Jakastam Suwałki');