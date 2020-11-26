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

INSERT INTO label(user_id, name) VALUES (1, 'INBOX');
INSERT INTO label(user_id, name) VALUES (1, 'SNOOZED');
INSERT INTO label(user_id, name) VALUES (1, 'SENT');
INSERT INTO label(user_id, name) VALUES (1, 'DRAFTS');
INSERT INTO label(user_id, name) VALUES (2, 'INBOX');
INSERT INTO label(user_id, name) VALUES (2, 'SNOOZED');
INSERT INTO label(user_id, name) VALUES (2, 'SENT');
INSERT INTO label(user_id, name) VALUES (2, 'DRAFTS');

INSERT INTO labels_messages(message_id, label_id) VALUES (1, 1);
INSERT INTO labels_messages(message_id, label_id) VALUES (1, 2);
INSERT INTO labels_messages(message_id, label_id) VALUES (2, 1);
INSERT INTO labels_messages(message_id, label_id) VALUES (3, 3);
INSERT INTO labels_messages(message_id, label_id) VALUES (4, 3);
INSERT INTO labels_messages(message_id, label_id) VALUES (5, 5);
INSERT INTO labels_messages(message_id, label_id) VALUES (6, 5);
INSERT INTO labels_messages(message_id, label_id) VALUES (7, 7);
INSERT INTO labels_messages(message_id, label_id) VALUES (8, 7);
