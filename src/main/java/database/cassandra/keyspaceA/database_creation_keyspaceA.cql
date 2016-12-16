CREATE KEYSPACE IF NOT EXISTS keyspaceA WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = true;
USE keyspaceA;
CREATE TABLE IF NOT EXISTS keyspaceA.memobox (
    id timeuuid,
    name text,
    memo text,
    date timestamp,
    PRIMARY KEY (id)
) ;

CREATE INDEX keyspaceA_memobox_name ON memobox ( name );
CREATE INDEX keyspaceA_memobox_memo ON memobox ( memo );

INSERT INTO memobox (id,name,memo,date) VALUES (now(),'tanaka','greet message1','2016-12-13');
INSERT INTO memobox (id,name,memo,date) VALUES (now(),'yamamoto','greet message2','2016-12-13');
