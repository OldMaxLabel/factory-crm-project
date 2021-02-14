INSERT INTO usr (id, username, password, active)
VALUES (1, 'admin', 'admin', 'true');

INSERT INTO user_role (user_id, roles)
VALUES (1, 'ADMIN');

CREATE EXTENSION IF NOT EXISTS pgcrypto;

UPDATE usr SET password = crypt(password, gen_salt('bf', 8));