CREATE TABLE `AUTHORITY`
(
    `ID`   BIGINT NOT NULL auto_increment,
    `NAME` VARCHAR(255) UNIQUE,
    PRIMARY KEY (`ID`)
);

CREATE TABLE `USERS`
(
    `ID`                  BIGINT NOT NULL auto_increment,
    `PASSWORD`            VARCHAR(255),
    `USER_NAME`           VARCHAR(255) UNIQUE,
    `ACCOUNT_EXPIRED`     BOOLEAN,
    `ACCOUNT_LOCKED`      BOOLEAN,
    `CREDENTIALS_EXPIRED` BOOLEAN,
    `ENABLED`             BOOLEAN,
    PRIMARY KEY (`ID`)
);

CREATE TABLE `USERS_AUTHORITIES`
(
    `USER_ID`      BIGINT NOT NULL,
    `AUTHORITY_ID` BIGINT NOT NULL,
    PRIMARY KEY (USER_ID, AUTHORITY_ID),
    CONSTRAINT `fk_users_authorities_authority`
        FOREIGN KEY (AUTHORITY_ID) REFERENCES AUTHORITY (ID),
    CONSTRAINT `fk_users_authorities_users`
        FOREIGN KEY (USER_ID) REFERENCES USERS (ID)
);

