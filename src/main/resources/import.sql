
DROP TABLE IF EXISTS phone;
DROP TABLE IF EXISTS contact;
DROP TABLE IF EXISTS address;

CREATE TABLE contact
(
    id          INTEGER NOT NULL AUTO_INCREMENT,
    first_name  VARCHAR(20),
    middle_name VARCHAR(20),
    last_name   VARCHAR(20),
    dob         DATE,
    death       DATE,
    PRIMARY KEY (id)
) ENGINE=INNODB;

CREATE TABLE address
(
    id          INTEGER NOT NULL AUTO_INCREMENT,
    address_1   VARCHAR(20) NOT NULL,
    address_2   VARCHAR(20) NULL,
    city        VARCHAR(20) NOT NULL,
    state       VARCHAR(20) NOT NULL,
    zipcode     VARCHAR(5) NOT NULL,
    country_id  int NOT NULL,
    contact_id  int NOT NULL,
    PRIMARY KEY (id),
    INDEX zip_idx     (zipcode),
    FOREIGN KEY (zipcode) REFERENCES zipcodes(zip),
    INDEX country_idx    (country_id),
    FOREIGN KEY (country_id) REFERENCES countries(id),
    INDEX contact_idx    (contact_id),
    FOREIGN KEY (contact_id) REFERENCES contact(id)
) ENGINE=INNODB;

CREATE TABLE phone
(
    id          INTEGER NOT NULL AUTO_INCREMENT,
    phone       NUMERIC(12) NOT NULL,
    type        VARCHAR(20) NULL,
    contact_id  int NOT NULL,
    PRIMARY KEY (id),
    INDEX contact_idx    (contact_id),
    FOREIGN KEY (contact_id) REFERENCES contact(id)
) ENGINE=INNODB;

INSERT INTO contact(first_name,middle_name,last_name,dob,death) VALUES ('John','Henry','Doe','1981-12-31','2021-10-31');
INSERT INTO address(address_1,address_2,city,state,zipcode,country_id,contact_id)
VALUES ('123 Sesame Street','Suite A','New York','NY',(SELECT zip from zipcodes WHERE state = 'NY' and city = 'New York' order by rand() limit 1),(SELECT id FROM countries WHERE country_code = 'US'),(SELECT id FROM contact WHERE dob = '1981-12-31'));
INSERT INTO phone(phone,type,contact_id) VALUES ('14043387744','Primary',(SELECT id FROM contact WHERE dob = '1981-12-31'));
INSERT INTO phone(phone,type,contact_id) VALUES ('14046683377','Cell',(SELECT id FROM contact WHERE dob = '1981-12-31'));
INSERT INTO phone(phone,type,contact_id) VALUES ('16789887421','Fax',(SELECT id FROM contact WHERE dob = '1981-12-31'));

INSERT INTO contact(first_name,middle_name,last_name,dob,death) VALUES ('John','Bastard','Snow','1988-02-29','2018-11-17');
INSERT INTO address(address_1,address_2,city,state,zipcode,country_id,contact_id)
VALUES ('8569 Shadow Keep','','Christiansted','VI','00824',(SELECT id FROM countries WHERE country_code = 'VI'),(SELECT id FROM contact WHERE dob = '1988-02-29'));
INSERT INTO phone(phone,type,contact_id) VALUES ('16128759824','Primary',(SELECT id FROM contact WHERE dob = '1988-02-29'));
INSERT INTO phone(phone,type,contact_id) VALUES ('16519842677','Cell',(SELECT id FROM contact WHERE dob = '1988-02-29'));

INSERT INTO contact(first_name,middle_name,last_name,dob,death) VALUES ('Freddy',null,'Krueger','1941-01-13',null);
INSERT INTO address(address_1,address_2,city,state,zipcode,country_id,contact_id)
VALUES ('1428 Elm Street',null,'Springwood','OH',(SELECT zip from zipcodes WHERE state = 'OH' and city = 'Valley City'),(SELECT id FROM countries WHERE country_code = 'US'),(SELECT id FROM contact WHERE dob = '1941-01-13'));
INSERT INTO address(address_1,address_2,city,state,zipcode,country_id,contact_id)
VALUES ('415 S Broad Street',null,'Alpharetta','GA','30009',(SELECT id FROM countries WHERE country_code = 'US'),(SELECT id FROM contact WHERE dob = '1941-01-13'));
INSERT INTO phone(phone,type,contact_id) VALUES ('19102249983','Cell',(SELECT id FROM contact WHERE dob = '1941-01-13'));
