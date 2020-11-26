-- DDL
DROP SEQUENCE IF EXISTS message_timeline_id;
CREATE SEQUENCE IF NOT EXISTS message_timeline_id;
DROP SEQUENCE IF EXISTS message_history_id;
CREATE SEQUENCE IF NOT EXISTS message_history_id;
DROP SEQUENCE IF EXISTS label_history_id;
CREATE SEQUENCE IF NOT EXISTS label_history_id;

ALTER TABLE public.message ALTER COLUMN timeline_id SET NOT NULL;
ALTER TABLE public.message ALTER COLUMN timeline_id SET DEFAULT NEXTVAL('message_timeline_id');
ALTER TABLE public.message ALTER COLUMN history_id SET NOT NULL;
ALTER TABLE public.message ALTER COLUMN history_id SET DEFAULT NEXTVAL('message_history_id');
ALTER TABLE public.label ALTER COLUMN history_id SET NOT NULL;
ALTER TABLE public.label ALTER COLUMN history_id SET DEFAULT NEXTVAL('label_history_id');

-- Data
INSERT INTO user_(username) VALUES ('igor@acme.org');
INSERT INTO user_(username) VALUES ('mike@acme.org');

INSERT INTO account(user_id, name) VALUES (1, 'izboran@gmail.com');
INSERT INTO account(user_id, name) VALUES (1, 'izboran@hotmail.com');
INSERT INTO account(user_id, name) VALUES (2, 'mike@gmail.com');
INSERT INTO account(user_id, name) VALUES (2, 'mike@hotmail.com');

INSERT INTO message(account_id, subject) VALUES (1, 'Hello!');
INSERT INTO message(account_id, subject) VALUES (1, 'Hello again!');
INSERT INTO message(account_id, subject) VALUES (2, 'Hi!');
INSERT INTO message(account_id, subject) VALUES (2, 'Hi again!');
INSERT INTO message(account_id, subject) VALUES (3, 'Holla!');
INSERT INTO message(account_id, subject) VALUES (3, 'Hola de nuevo!');
INSERT INTO message(account_id, subject) VALUES (4, 'Bonjour!');
INSERT INTO message(account_id, subject) VALUES (4, 'Rebonjour!');

INSERT INTO msg_tag(message_id, key, value) VALUES (1, 'INVOICE', 'Invoice');
INSERT INTO msg_tag(message_id, key, value) VALUES (1, 'PROJECT', 'Project');
INSERT INTO msg_tag(message_id, key, value) VALUES (1, 'CONTRACT', 'Contract');
