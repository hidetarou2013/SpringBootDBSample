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

CREATE TABLE IF NOT EXISTS keyspaceA.departments
(
    department_id varint PRIMARY KEY,
    department_name text
);

CREATE TABLE IF NOT EXISTS keyspaceA.employees
(
    employee_id varint PRIMARY KEY,
    employee_name text,
    employee_phone text,
    employee_email text,
    department_id varint
);

CREATE INDEX keyspaceA_departments_department_name ON departments ( department_name );

CREATE INDEX keyspaceA_employees_employee_name ON employees ( employee_name );
CREATE INDEX keyspaceA_employees_employee_phone ON employees ( employee_phone );
CREATE INDEX keyspaceA_employees_employee_email ON employees ( employee_email );
CREATE INDEX keyspaceA_employees_department_id ON employees ( department_id );

INSERT INTO departments (department_id, department_name) VALUES (10, 'accounting');
INSERT INTO departments (department_id, department_name) VALUES (20, 'personal');
INSERT INTO departments (department_id, department_name) VALUES (30, 'general');
INSERT INTO departments (department_id, department_name) VALUES (40, 'development');
INSERT INTO departments (department_id, department_name) VALUES (50, 'secretary');

SELECT * FROM departments;

INSERT INTO employees (employee_id, employee_name,employee_phone,employee_email,department_id) VALUES (1, 'Taro Yamada','090-0000-9999','t-yamada@ken.jp',10);
INSERT INTO employees (employee_id, employee_name,employee_phone,employee_email,department_id) VALUES (2, 'Kenichi Suzuki','090-9999-0000','k-suzuki@ken.jp',20);
INSERT INTO employees (employee_id, employee_name,employee_phone,employee_email,department_id) VALUES (3, 'Ichiro Ueda','090-1234-5678','i-ueda@ken.jp',30);
INSERT INTO employees (employee_id, employee_name,employee_phone,employee_email,department_id) VALUES (4, 'Hideaki Ito','090-9988-7766','h-ito@ken.jp',40);
INSERT INTO employees (employee_id, employee_name,employee_phone,employee_email,department_id) VALUES (5, 'Syouji Inoue','090-8765-4321','s-inoue@ken.jp',50);

SELECT * FROM employees;

CREATE TABLE IF NOT EXISTS keyspaceA.greetings (
    user text,
    id timeuuid,
    greet text,
    creation_date timestamp,
    PRIMARY KEY (user, id)
) WITH CLUSTERING ORDER BY (id DESC);


CREATE INDEX keyspaceA_greetings_greet ON greetings ( greet );

INSERT INTO keyspaceA.greetings (user, id,greet,creation_date) VALUES ('yamada_1',now(),'greet message1','2016-12-13');
INSERT INTO keyspaceA.greetings (user, id,greet,creation_date) VALUES ('yamada_2',now(),'greet message2','2016-12-13');

SELECT * FROM greetings;
