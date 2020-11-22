--SET timezone = 'UTC';

--INSERT INTO msg_tag(key, value, timestamp) VALUES ('INVOICE', 'Invoice', Now());
--INSERT INTO msg_tag(key, value, timestamp) VALUES ('PROJECT', 'Project', Now());
--INSERT INTO msg_tag(key, value, timestamp) VALUES ('CONTRACT', 'Contract', Now());

INSERT INTO message(owner, subject) VALUES ('jdoe@.acme.org', 'Hello message.');

INSERT INTO msg_tag(key, value) VALUES ('INVOICE', 'Invoice');
INSERT INTO msg_tag(key, value) VALUES ('PROJECT', 'Project');
INSERT INTO msg_tag(key, value) VALUES ('CONTRACT', 'Contract');
