drop table if exists authorities;
drop table if exists users;

create table if not exists `users` (
	`username` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
	`password` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
	`enabled` CHAR(1) NULL DEFAULT '1',
	PRIMARY KEY (`username`) USING BTREE
)
COLLATE='utf8_general_ci'
ENGINE=INNODB;

create table if not exists `authorities` (
	`username` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
	`authority` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
	CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES `tacodb`.`users` (`username`)
)
COLLATE='utf8_general_ci'
ENGINE=INNODB;

create unique index ix_auth_username  on `tacodb`.`authorities` (username, authority);

