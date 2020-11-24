-- SET timezone = 'UTC';

-- INSERT INTO msg_tag(key, value, timestamp) VALUES ('INVOICE', 'Invoice', Now());
-- INSERT INTO msg_tag(key, value, timestamp) VALUES ('PROJECT', 'Project', Now());
-- INSERT INTO msg_tag(key, value, timestamp) VALUES ('CONTRACT', 'Contract', Now());

INSERT INTO message(owner, subject) VALUES ('jdoe@.acme.org', 'Hello.');
INSERT INTO message(owner, subject) VALUES ('jdoe@.acme.org', 'Hello again.');

INSERT INTO msg_tag(message_id, key, value) VALUES (1, 'INVOICE', 'Invoice');
INSERT INTO msg_tag(message_id, key, value) VALUES (1, 'PROJECT', 'Project');
INSERT INTO msg_tag(message_id, key, value) VALUES (1, 'CONTRACT', 'Contract');
