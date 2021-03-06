
CREATE TABLE GUIDES (
   ID         BIGSERIAL      PRIMARY KEY
  ,INPUTDATE  TIMESTAMP      WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
  ,TITLE      VARCHAR(255)
);


CREATE TABLE PLACES (
   ID          BIGSERIAL     PRIMARY KEY
  ,INPUTDATE   TIMESTAMP     WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
  ,TITLE       VARCHAR(255)
  ,NAME        VARCHAR(255)
);


CREATE TABLE ACTIVITIES (
   ID          BIGSERIAL     PRIMARY KEY
  ,INPUTDATE   TIMESTAMP     WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
  ,TITLE       VARCHAR(255)
);


CREATE TABLE CATEGORIES (
   ID          BIGSERIAL     PRIMARY KEY
  ,INPUTDATE   TIMESTAMP     WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
  ,TITLE       VARCHAR(255)
);


CREATE TABLE COMMENTS (
   ID          BIGSERIAL     PRIMARY KEY
  ,INPUTDATE   TIMESTAMP     WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
  ,TITLE       VARCHAR(255)
  ,USER        BIGINT        REFERENCES USERS(ID)
  ,COMMENT     VARCHAR(4000)
);


CREATE TABLE USERS (
   ID          BIGSERIAL     PRIMARY KEY
  ,INPUTDATE   TIMESTAMP     WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
  ,NAME        VARCHAR(255)
  ,EMAIL       VARCHAR(255)
);


CREATE TABLE RESOURCES (
   ID          BIGSERIAL     PRIMARY KEY
  ,INPUTDATE   TIMESTAMP     WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
  ,TITLE       VARCHAR(255)
  ,RESOURCE    
);