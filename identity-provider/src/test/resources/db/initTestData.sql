INSERT INTO identitiy (id, created, updated, version, city, latitude, longitude, street, street_number, zip_code, auth_provider, email, email_verified, external_id, given_name, phone, surname)
VALUES
  (1, NOW(), NOW(), 1, NULL, 49.0, 8.4, NULL, NULL, 76133, 'empty', 'test@helfenkannjeder.de', 0, NULL, 'Given Name', '01234567', 'Surname'),
  (2, NOW(), NOW(), 1, NULL, 49.0, 8.41, NULL, NULL, 76133, 'empty', 'test2@helfenkannjeder.de', 0, NULL, 'Given Name 2', '01234568', 'Surname 2');
