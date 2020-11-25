INSERT INTO user_(username) VALUES ('igor@acme.org');
INSERT INTO user_(username) VALUES ('mike@acme.org');

/* INSERT INTO account(name) VALUES ('izboran@gmail.com');
INSERT INTO account(name) VALUES ('izboran@hotmail.com');
INSERT INTO user__account(user_id, accounts_id) VALUES (1, 1);
INSERT INTO user__account(user_id, accounts_id) VALUES (1, 2);

INSERT INTO account(name) VALUES ('mike@gmail.com');
INSERT INTO account(name) VALUES ('mike@hotmail.com');
INSERT INTO user__account(user_id, accounts_id) VALUES (2, 3);
INSERT INTO user__account(user_id, accounts_id) VALUES (2, 4); */

INSERT INTO account(user_id, name) VALUES (1, 'izboran@gmail.com');
INSERT INTO account(user_id, name) VALUES (1, 'izboran@hotmail.com');
INSERT INTO account(user_id, name) VALUES (2, 'mike@gmail.com');
INSERT INTO account(user_id, name) VALUES (2, 'mike@hotmail.com');

/* INSERT INTO message(subject) VALUES ('Hello!');
INSERT INTO message(subject) VALUES ('Hello again!');
INSERT INTO account_message(account_id, messages_id) VALUES (1, 1);
INSERT INTO account_message(account_id, messages_id) VALUES (1, 2);

INSERT INTO message(subject) VALUES ('Hi!');
INSERT INTO message(subject) VALUES ('Hi again!');
INSERT INTO account_message(account_id, messages_id) VALUES (2, 3);
INSERT INTO account_message(account_id, messages_id) VALUES (2, 4);

INSERT INTO message(subject) VALUES ('Holla!');
INSERT INTO message(subject) VALUES ('Hola de nuevo!');
INSERT INTO account_message(account_id, messages_id) VALUES (3, 5);
INSERT INTO account_message(account_id, messages_id) VALUES (3, 6);

INSERT INTO message(subject) VALUES ('Bonjour!');
INSERT INTO message(subject) VALUES ('Rebonjour!');
INSERT INTO account_message(account_id, messages_id) VALUES (4, 7);
INSERT INTO account_message(account_id, messages_id) VALUES (4, 8); */

INSERT INTO message(account_id, subject) VALUES (1, 'Hello!');
INSERT INTO message(account_id, subject) VALUES (1, 'Hello again!');
INSERT INTO message(account_id, subject) VALUES (2, 'Hi!');
INSERT INTO message(account_id, subject) VALUES (2, 'Hi again!');
INSERT INTO message(account_id, subject) VALUES (3, 'Holla!');
INSERT INTO message(account_id, subject) VALUES (3, 'Hola de nuevo!');
INSERT INTO message(account_id, subject) VALUES (4, 'Bonjour!');
INSERT INTO message(account_id, subject) VALUES (4, 'Rebonjour!');

/* INSERT INTO msg_tag(key, value) VALUES ('INVOICE', 'Invoice');
INSERT INTO msg_tag(key, value) VALUES ('PROJECT', 'Project');
INSERT INTO msg_tag(key, value) VALUES ('CONTRACT', 'Contract');
INSERT INTO message_msg_tag(message_id, tags_id) VALUES (1, 1);
INSERT INTO message_msg_tag(message_id, tags_id) VALUES (1, 2);
INSERT INTO message_msg_tag(message_id, tags_id) VALUES (1, 3); */

INSERT INTO msg_tag(message_id, key, value) VALUES (1, 'INVOICE', 'Invoice');
INSERT INTO msg_tag(message_id, key, value) VALUES (1, 'PROJECT', 'Project');
INSERT INTO msg_tag(message_id, key, value) VALUES (1, 'CONTRACT', 'Contract');
