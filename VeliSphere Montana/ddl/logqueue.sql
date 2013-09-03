CREATE TABLE LOGQUEUE (

   EXCHANGENAME VARCHAR(255),
   MESSAGE VARCHAR(1024),
   QUEUENAME VARCHAR(255) NOT NULL,
   ROUTINGKEY VARCHAR(255),
   IDENTIFIER VARCHAR(36) NOT NULL,
   PRIMARY KEY (IDENTIFIER, QUEUENAME)
);

PARTITION TABLE LOGQUEUE ON COLUMN QUEUENAME;

CREATE PROCEDURE FROM CLASS Insert;
CREATE PROCEDURE FROM CLASS Select;


CREATE TABLE USER (

   USERID VARCHAR(36) NOT NULL,
   USERNAME VARCHAR(255) NOT NULL,
   USEREMAIL VARCHAR(255) NOT NULL,
   PRIMARY KEY (USERID)
);

PARTITION TABLE USER ON COLUMN USERID;

CREATE PROCEDURE FROM CLASS SelectAllUsers;


CREATE TABLE ENDPOINT_USER_LINK (

   LINKID VARCHAR(36) NOT NULL,
   ENDPOINTID VARCHAR(36) NOT NULL,
   USERID VARCHAR(36) NOT NULL,
   PRIMARY KEY (LINKID)
);

PARTITION TABLE ENDPOINT_USER_LINK ON COLUMN USERID;

CREATE TABLE ENDPOINT_SPHERE_LINK (

   LINKID VARCHAR(36) NOT NULL,
   ENDPOINTID VARCHAR(36) NOT NULL,
   SPHEREID VARCHAR(36) NOT NULL,
   PRIMARY KEY (LINKID)
);

PARTITION TABLE ENDPOINT_SPHERE_LINK ON COLUMN SPHEREID;

CREATE TABLE SPHERE_USER_LINK (

   LINKID VARCHAR(36) NOT NULL,
   SPHEREID VARCHAR(36) NOT NULL,
   USERID VARCHAR(36) NOT NULL,
   PRIMARY KEY (LINKID)
);

PARTITION TABLE SPHERE_USER_LINK ON COLUMN SPHEREID;

CREATE TABLE SPHERE (

   SPHEREID VARCHAR(36) NOT NULL,
   SPHERENAME VARCHAR(255) NOT NULL,
   PUBLIC TINYINT,
   PRIMARY KEY (SPHEREID)
);

PARTITION TABLE SPHERE ON COLUMN SPHEREID;

CREATE TABLE ENDPOINTCLASS (

   ENDPOINTCLASSID VARCHAR(36) NOT NULL,
   ENDPOINTCLASSNAME VARCHAR(255) NOT NULL,
   PRIMARY KEY (ENDPOINTCLASSID)
);

PARTITION TABLE ENDPOINTCLASS ON COLUMN ENDPOINTCLASSID;

CREATE TABLE ENDPOINT (

   ENDPOINTID VARCHAR(36) NOT NULL,
   ENDPOINTNAME VARCHAR(255) NOT NULL,
   ENDPOINTCLASSID VARCHAR(36) NOT NULL,
   PRIMARY KEY (ENDPOINTID)
);

PARTITION TABLE ENDPOINT ON COLUMN ENDPOINTCLASSID;

CREATE TABLE PROPERTY (

   PROPERTYID VARCHAR(36) NOT NULL,
   PROPERTYNAME VARCHAR(255),
   PROPERTYDATATYPE VARCHAR(32) NOT NULL,
   PROPERTYUNIT VARCHAR(32),
   PRIMARY KEY (PROPERTYID)
);

PARTITION TABLE PROPERTY ON COLUMN PROPERTYDATATYPE;

CREATE TABLE MESSAGE_ENDPOINT_LINK (

   LINKID VARCHAR(36) NOT NULL,
   ENDPOINTID VARCHAR(36) NOT NULL,
   MESSAGEID VARCHAR(36) NOT NULL,
   PRIMARY KEY (LINKID)
);

PARTITION TABLE MESSAGE_ENDPOINT_LINK ON COLUMN ENDPOINTID;

CREATE TABLE MESSAGE (

   MESSAGEID VARCHAR(36) NOT NULL,
   EXPRESSIONCOUNT SMALLINT,
   TIME_STAMP TIMESTAMP NOT NULL,
   PRIMARY KEY (MESSAGEID)
);

PARTITION TABLE MESSAGE ON COLUMN MESSAGEID;


CREATE TABLE MESSAGE_EXPRESSION_LINK (

   LINKID VARCHAR(36) NOT NULL,
   MESSAGEID VARCHAR(36) NOT NULL,
   EXPRESSIONID VARCHAR(36) NOT NULL,
   PRIMARY KEY (LINKID)
);

PARTITION TABLE MESSAGE_EXPRESSION_LINK ON COLUMN MESSAGEID;

CREATE TABLE EXPRESSION (

   EXPRESSIONID VARCHAR(36) NOT NULL,
   PROPERTYID VARCHAR(36) NOT NULL,
   PAYLOAD VARCHAR(4096),
   PRIMARY KEY (EXPRESSIONID)
);

PARTITION TABLE EXPRESSION ON COLUMN PROPERTYID;

CREATE TABLE CHECK (

   CHECKID VARCHAR(36) NOT NULL,
   ENDPOINTID VARCHAR(36) NOT NULL,
   PROPERTYID VARCHAR(36) NOT NULL,
   CHECKVALUE VARCHAR(4096),
   OPERATOR VARCHAR (32),
   STATE TINYINT,
   EXPIRED TINYINT,
   PRIMARY KEY (CHECKID)
);

PARTITION TABLE CHECK ON COLUMN ENDPOINTID;

CREATE TABLE RULE_MULTICHECK_LINK (

   LINKID VARCHAR(36) NOT NULL,
   RULEID VARCHAR(36) NOT NULL,
   MULTICHECKID VARCHAR(36) NOT NULL,
   PRIMARY KEY (LINKID)
);

PARTITION TABLE RULE_MULTICHECK_LINK ON COLUMN RULEID;

CREATE TABLE RULE (

   RULEID VARCHAR(36) NOT NULL,
   RULENAME VARCHAR(255),
   STATE TINYINT,
   EXPIRED TINYINT,
   PRIMARY KEY (RULEID)
);

PARTITION TABLE RULE ON COLUMN RULEID;

CREATE TABLE MULTICHECK (

   MULTICHECKID VARCHAR(36) NOT NULL,
   OPERATOR VARCHAR (32),
   STATE TINYINT,
   EXPIRED TINYINT,
   PRIMARY KEY (MULTICHECKID)
);

PARTITION TABLE MULTICHECK ON COLUMN MULTICHECKID;

CREATE TABLE MULTICHECK_CHECK_LINK (

   LINKID VARCHAR(36) NOT NULL,
   MULTICHECKID VARCHAR(36) NOT NULL,
   CHECKID VARCHAR(36) NOT NULL,
   PRIMARY KEY (LINKID)
);

PARTITION TABLE MULTICHECK_CHECK_LINK ON COLUMN MULTICHECKID;

CREATE TABLE MULTICHECK_MULTICHECK_LINK (

   LINKID VARCHAR(36) NOT NULL,
   MULTICHECKLID VARCHAR(36) NOT NULL,
   MULTICHECKRID VARCHAR(36) NOT NULL,
   PRIMARY KEY (LINKID)
);

PARTITION TABLE MULTICHECK_MULTICHECK_LINK ON COLUMN MULTICHECKLID;

