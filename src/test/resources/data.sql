-- users
INSERT INTO users (login, password) VALUES
                                        ('alice',  'password_hash_1'),
                                        ('bob',    'password_hash_2'),
                                        ('charlie','password_hash_3');

-- locations
INSERT INTO locations (name, userid, latitude, longitude) VALUES
                                                              ('Moscow',      1, 55.7558, 37.6173),
                                                              ('SaintPeters', 1, 59.9343, 30.3351),
                                                              ('Berlin',      2, 52.5200, 13.4050),
                                                              ('Paris',       2, 48.8566, 2.3522),
                                                              ('Tokyo',       3, 35.6895, 139.6917);

-- session
INSERT INTO session (id, userid, expiresat) VALUES
                                                ('11111111-1111-1111-1111-111111111111', 1, TIMESTAMP '2030-01-01 12:00:00'),
                                                ('22222222-2222-2222-2222-222222222222', 1, TIMESTAMP '2020-01-01 12:00:00'),
                                                ('33333333-3333-3333-3333-333333333333', 2, TIMESTAMP '2030-06-01 08:30:00'),
                                                ('44444444-4444-4444-4444-444444444444', 3, TIMESTAMP '2029-12-31 23:59:59');
