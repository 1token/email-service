-- DDL
DROP SEQUENCE IF EXISTS message_timeline_id;
CREATE SEQUENCE IF NOT EXISTS message_timeline_id;
DROP SEQUENCE IF EXISTS message_history_id;
CREATE SEQUENCE IF NOT EXISTS message_history_id;
DROP SEQUENCE IF EXISTS label_history_id;
CREATE SEQUENCE IF NOT EXISTS label_history_id;
DROP SEQUENCE IF EXISTS filter_history_id;
CREATE SEQUENCE IF NOT EXISTS filter_history_id;


ALTER TABLE public.message ALTER COLUMN timeline_id SET NOT NULL;
ALTER TABLE public.message ALTER COLUMN timeline_id SET DEFAULT NEXTVAL('message_timeline_id');
ALTER TABLE public.message ALTER COLUMN history_id SET NOT NULL;
ALTER TABLE public.message ALTER COLUMN history_id SET DEFAULT NEXTVAL('message_history_id');
ALTER TABLE public.label ALTER COLUMN history_id SET NOT NULL;
ALTER TABLE public.label ALTER COLUMN history_id SET DEFAULT NEXTVAL('label_history_id');
ALTER TABLE public.filter ALTER COLUMN history_id SET NOT NULL;
ALTER TABLE public.filter ALTER COLUMN history_id SET DEFAULT NEXTVAL('filter_history_id');

-- Data
INSERT INTO user_(username) VALUES ('igor@acme.org');
INSERT INTO user_(username) VALUES ('mike@acme.org');

INSERT INTO account(user_id, display_name, username) VALUES (1, 'Igor Zboran', 'izboran@gmail.com');
INSERT INTO account(user_id, display_name, username) VALUES (1, 'Igor Zboran', 'izboran@hotmail.com');
INSERT INTO account(user_id, username) VALUES (2, 'mike@gmail.com');
INSERT INTO account(user_id, username) VALUES (2, 'mike@hotmail.com');

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
INSERT INTO label(user_id, name) VALUES (1, 'Invoices');
INSERT INTO label(user_id, parent_id, name) VALUES (1, 9, 'O2');
INSERT INTO label(user_id, parent_id, name) VALUES (1, 9, 'Orange');

INSERT INTO labels_messages(message_id, label_id) VALUES (1, 1);
INSERT INTO labels_messages(message_id, label_id) VALUES (1, 2);
INSERT INTO labels_messages(message_id, label_id) VALUES (2, 1);
INSERT INTO labels_messages(message_id, label_id) VALUES (3, 3);
INSERT INTO labels_messages(message_id, label_id) VALUES (4, 3);
INSERT INTO labels_messages(message_id, label_id) VALUES (5, 5);
INSERT INTO labels_messages(message_id, label_id) VALUES (6, 5);
INSERT INTO labels_messages(message_id, label_id) VALUES (7, 7);
INSERT INTO labels_messages(message_id, label_id) VALUES (8, 7);

INSERT INTO msg_recipient_to(message_id, fullname, email_address) VALUES (1, 'John Doe', 'jdoe@acme.org');
INSERT INTO msg_recipient_to(message_id, fullname, email_address) VALUES (1, 'Mary Doe', 'mdoe@acme.org');
INSERT INTO msg_recipient_to(message_id, fullname, email_address) VALUES (2, 'Theo Brown', 'tbrown@acme.org');
INSERT INTO msg_recipient_to(message_id, fullname, email_address) VALUES (5, 'Alice Williams', 'awilliams@acme.org');
INSERT INTO msg_recipient_cc(message_id, fullname, email_address) VALUES (1, 'Theo Brown', 'tbrown@acme.org');
INSERT INTO msg_recipient_bcc(message_id, fullname, email_address) VALUES (2, 'Alice Williams', 'awilliams@acme.org');

INSERT INTO msg_attachment(message_id, filename, mimetype) VALUES (1, 'Contract.pdf', 'application/pdf');
INSERT INTO msg_attachment(message_id, filename, mimetype) VALUES (1, 'Template.docx', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document');
INSERT INTO msg_attachment(message_id, filename, mimetype) VALUES (2, 'Invoice.pdf', 'application/pdf');
INSERT INTO msg_attachment(message_id, filename, mimetype) VALUES (5, 'Forecast.xlsx', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');

INSERT INTO filter(user_id, name) VALUES (1, 'O2 Invoice');
INSERT INTO filter(user_id, name) VALUES (1, 'Orange Invoice');

INSERT INTO filters_labels(label_id, filter_id) VALUES (10, 1);
INSERT INTO filters_labels(label_id, filter_id) VALUES (11, 2);

INSERT INTO contact(user_id, first_name, last_name, email_address) VALUES (1, 'Mary', 'Doe', 'jdoe@acme.org');
INSERT INTO contact(user_id, first_name, last_name, email_address) VALUES (1, 'John', 'Doe', 'mdoe@acme.org');
INSERT INTO contact(user_id, first_name, last_name, email_address) VALUES (1, 'Theo', 'Brown', 'tbrown@acme.org');
INSERT INTO contact(user_id, first_name, last_name, email_address) VALUES (2, 'Alice', '', 'awilliams@acme.org');
