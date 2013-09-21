CREATE TABLE LOGQUEUE (

   EXCHANGENAME VARCHAR(255),
   MESSAGE VARCHAR(32768),
   QUEUENAME VARCHAR(255) NOT NULL,
   ROUTINGKEY VARCHAR(255),
   IDENTIFIER VARCHAR(36) NOT NULL,
   PRIMARY KEY (IDENTIFIER, QUEUENAME)
);

PARTITION TABLE LOGQUEUE ON COLUMN QUEUENAME;

CREATE TABLE ENDPOINTPROPERTYLOG (
   ENTRYID VARCHAR(36) NOT NULL,
   ENDPOINTID VARCHAR(255) NOT NULL,
   PROPERTYID VARCHAR(255) NOT NULL,
   PROPERTYENTRY VARCHAR(255),
   TIME_STAMP TIMESTAMP NOT NULL,
   PRIMARY KEY (ENTRYID)
);

PARTITION TABLE ENDPOINTPROPERTYLOG ON COLUMN ENDPOINTID;
CREATE PROCEDURE FROM CLASS LGE_InsertEndpointPropertyLog;


CREATE PROCEDURE FROM CLASS Insert;
CREATE PROCEDURE FROM CLASS Select;
CREATE PROCEDURE FROM CLASS SelectAllLogEntries;

CREATE TABLE USER (

   USERID VARCHAR(36) NOT NULL,
   USERNAME VARCHAR(255) NOT NULL,
   USEREMAIL VARCHAR(255) NOT NULL,
   USERPWHASH VARCHAR(255),
   PRIMARY KEY (USERID)
);

PARTITION TABLE USER ON COLUMN USERID;

CREATE PROCEDURE FROM CLASS SelectAllUsers;
CREATE PROCEDURE FROM CLASS UI_FindUserForEmail;


CREATE TABLE ENDPOINT_USER_LINK (

   LINKID VARCHAR(36) NOT NULL,
   ENDPOINTID VARCHAR(36) NOT NULL,
   USERID VARCHAR(36) NOT NULL,
   PRIMARY KEY (LINKID)
);

CREATE PROCEDURE FROM CLASS UI_SelectEndpointsForUser;


CREATE TABLE ENDPOINT_SPHERE_LINK (

   LINKID VARCHAR(36) NOT NULL,
   ENDPOINTID VARCHAR(36) NOT NULL,
   SPHEREID VARCHAR(36) NOT NULL,
   PRIMARY KEY (LINKID)
);

CREATE PROCEDURE FROM CLASS UI_DeleteEndpointFromSphere;

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
CREATE PROCEDURE FROM CLASS UI_SelectAllSpheres;

CREATE TABLE ENDPOINTCLASS (

   ENDPOINTCLASSID VARCHAR(36) NOT NULL,
   ENDPOINTCLASSNAME VARCHAR(255) NOT NULL,
   PRIMARY KEY (ENDPOINTCLASSID)
);

PARTITION TABLE ENDPOINTCLASS ON COLUMN ENDPOINTCLASSID;
CREATE PROCEDURE FROM CLASS UI_SelectAllEndpointClasses;
CREATE PROCEDURE FROM CLASS UI_SelectEndpointClassForEndpointClassID;

CREATE TABLE ENDPOINT (

   ENDPOINTID VARCHAR(36) NOT NULL,
   ENDPOINTNAME VARCHAR(255) NOT NULL,
   ENDPOINTCLASSID VARCHAR(36) NOT NULL,
   PRIMARY KEY (ENDPOINTID)
);

PARTITION TABLE ENDPOINT ON COLUMN ENDPOINTID;
CREATE PROCEDURE FROM CLASS UI_SelectAllEndpoints;
CREATE PROCEDURE FROM CLASS UI_SelectEndpointsForSphere;
CREATE PROCEDURE FROM CLASS UI_SelectEndpointForEndpointID;

CREATE TABLE PROPERTY (

   PROPERTYID VARCHAR(36) NOT NULL,
   PROPERTYNAME VARCHAR(255),
   PROPERTYCLASSID VARCHAR(36) NOT NULL,
   ENDPOINTCLASSID VARCHAR(36) NOT NULL,
   PRIMARY KEY (PROPERTYID)
);

PARTITION TABLE PROPERTY ON COLUMN ENDPOINTCLASSID;
CREATE PROCEDURE FROM CLASS UI_SelectAllProperties;


CREATE TABLE PROPERTYCLASS (

   PROPERTYCLASSID VARCHAR(36) NOT NULL,
   PROPERTYCLASSNAME VARCHAR(255),
   PROPERTYCLASSDATATYPE VARCHAR(32) NOT NULL,
   PROPERTYCLASSUNIT VARCHAR(32),
   PRIMARY KEY (PROPERTYCLASSID)
);


CREATE PROCEDURE FROM CLASS UI_SelectAllPropertyClasses;

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
CREATE PROCEDURE FROM CLASS ImprovedFindMatchingChecksEqual;
PARTITION PROCEDURE ImprovedFindMatchingChecksEqual ON TABLE CHECK COLUMN ENDPOINTID;
CREATE PROCEDURE FROM CLASS UpdateChecks;
CREATE PROCEDURE FROM CLASS ResetChecks;
CREATE PROCEDURE FROM CLASS FindAllChecks;
CREATE PROCEDURE FROM CLASS FindChecksForCheckID;
CREATE PROCEDURE FROM CLASS BLE_ChecksForExpression;
CREATE PROCEDURE FROM CLASS BLE_AllChecksForExpression;

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
   CHECKID VARCHAR(36),
   MULTICHECKID VARCHAR(36),
   PRIMARY KEY (RULEID)
);
CREATE PROCEDURE FROM CLASS FindRulesForCheckID;
CREATE PROCEDURE FROM CLASS FindRulesForMultiCheckID;

PARTITION TABLE RULE ON COLUMN RULEID;

CREATE TABLE MULTICHECK (

   MULTICHECKID VARCHAR(36) NOT NULL,
   OPERATOR VARCHAR (32),
   STATE TINYINT,
   EXPIRED TINYINT,
   PRIMARY KEY (MULTICHECKID)
);

PARTITION TABLE MULTICHECK ON COLUMN MULTICHECKID;
CREATE PROCEDURE FROM CLASS UpdateMultiChecks;
CREATE PROCEDURE FROM CLASS FindMultiChecksForMultiCheckID;
CREATE PROCEDURE FROM CLASS BLE_IsMultiCheckTrue;
CREATE PROCEDURE FROM CLASS BLE_IsCycleMultiCheckTrue;
CREATE PROCEDURE FROM CLASS BLE_MultiCheckParentForMultiCheck;


CREATE TABLE MULTICHECK_CHECK_LINK (

   LINKID VARCHAR(36) NOT NULL,
   MULTICHECKID VARCHAR(36) NOT NULL,
   CHECKID VARCHAR(36) NOT NULL,
   PRIMARY KEY (LINKID)
);

PARTITION TABLE MULTICHECK_CHECK_LINK ON COLUMN MULTICHECKID;
CREATE PROCEDURE FROM CLASS FindAllMultichecksForCheck;
CREATE PROCEDURE FROM CLASS ImprovedFindAllMultichecksForCheck;
CREATE PROCEDURE FROM CLASS FindChecksForMultiCheckID;

CREATE TABLE MULTICHECK_MULTICHECK_LINK (

   LINKID VARCHAR(36) NOT NULL,
   MULTICHECKLID VARCHAR(36) NOT NULL,
   MULTICHECKRID VARCHAR(36) NOT NULL,
   PRIMARY KEY (LINKID)
);

PARTITION TABLE MULTICHECK_MULTICHECK_LINK ON COLUMN MULTICHECKLID;
CREATE PROCEDURE FROM CLASS FindLinkedMultiChecksForMultiCheckID;
CREATE PROCEDURE FROM CLASS FindParentMultiChecksForMultiCheckID;


CREATE TABLE CHECKPATH (

   CHECKPATHID VARCHAR(36) NOT NULL,
   PRIMARY KEY (CHECKPATHID)
);


CREATE TABLE CHECKPATH_CHECK_LINK (

   LINKID VARCHAR(36) NOT NULL,
   CHECKPATHID VARCHAR(36) NOT NULL,
   CHECKID VARCHAR(36) NOT NULL,
   PRIMARY KEY (LINKID)
);

CREATE PROCEDURE FROM CLASS BLE_CheckPathForChecks;
PARTITION TABLE CHECKPATH_CHECK_LINK ON COLUMN CHECKID;

CREATE TABLE CHECKPATH_MULTICHECK_LINK (

   LINKID VARCHAR(36) NOT NULL,
   CHECKPATHID VARCHAR(36) NOT NULL,
   MULTICHECKID VARCHAR(36) NOT NULL,
   PRIMARY KEY (LINKID)
);

CREATE PROCEDURE FROM CLASS BLE_CheckPathForMultiChecks;
PARTITION TABLE CHECKPATH_MULTICHECK_LINK ON COLUMN MULTICHECKID;

CREATE TABLE ACTION (

   ACTIONID VARCHAR(36) NOT NULL,
   ACTIONNAME VARCHAR(255) NOT NULL,
   TARGETENDPOINTID VARCHAR(36),
   TGTEPIDFROMINBOUNDPROP VARCHAR(36),
   EXPIRED TINYINT,
   PRIMARY KEY (ACTIONID)
);



CREATE TABLE OUTBOUNDPROPERTYACTION (

   OUTBOUNDPROPERTYACTIONID VARCHAR(36) NOT NULL,
   OUTBOUNDPROPERTYID VARCHAR(36),
   INBOUNDPROPERTYID VARCHAR(36),
   CURRENTSTATEPROPERTYID VARCHAR(36),
   CUSTOMPAYLOAD VARCHAR(32768),
   ACTIONID VARCHAR(36) NOT NULL,
   PRIMARY KEY (OUTBOUNDPROPERTYACTIONID)
);
PARTITION TABLE OUTBOUNDPROPERTYACTION ON COLUMN ACTIONID;

CREATE PROCEDURE FROM CLASS AME_DetailsForAction;

CREATE TABLE RULE_ACTION_LINK (

   LINKID VARCHAR(36) NOT NULL,
   RULEID VARCHAR(36) NOT NULL,
   ACTIONID VARCHAR(36) NOT NULL,
   PRIMARY KEY (LINKID)
);

PARTITION TABLE RULE_ACTION_LINK ON COLUMN RULEID;
CREATE PROCEDURE FROM CLASS AME_ActionsForRule;
