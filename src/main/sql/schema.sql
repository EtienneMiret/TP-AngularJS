create table CONTACT (
	ID bigint generated always as identity primary key,
	FIRSTNAME varchar(50),
	LASTNAME varchar(50),
	CREATION timestamp,
	MODIFICATION timestamp
);

create table FIELD (
	ID bigint generated always as identity primary key,
	CONTACT bigint not null references CONTACT(ID),
	ORDER_ int not null,
	TYPE_ varchar(50) not null,
	VALUE_ varchar(50) not null
);
