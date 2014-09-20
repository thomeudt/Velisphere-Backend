--CREATE TABLE LOGQUEUE (
--
--   EXCHANGENAME VARCHAR(255),
--   MESSAGE VARCHAR(32768),
--   QUEUENAME VARCHAR(255) NOT NULL,
--   ROUTINGKEY VARCHAR(255),
--   IDENTIFIER VARCHAR(36) NOT NULL,
--   PRIMARY KEY (IDENTIFIER, QUEUENAME)
--);

-- PARTITION TABLE LOGQUEUE ON COLUMN QUEUENAME;

CREATE TABLE ENDPOINTPROPERTYLOG (
   ENTRYID VARCHAR(36) NOT NULL,
   ENDPOINTID VARCHAR(255) NOT NULL,
   PROPERTYID VARCHAR(255) NOT NULL,
   PROPERTYENTRY VARCHAR(4096),
   TIME_STAMP TIMESTAMP,
   PRIMARY KEY (ENTRYID, ENDPOINTID)
);

CREATE INDEX IDXtstamp ON ENDPOINTPROPERTYLOG (TIME_STAMP);
CREATE PROCEDURE FROM CLASS LGE_InsertEndpointPropertyLog;
CREATE PROCEDURE FROM CLASS UI_LastLogEntryForEndpointProperty;
PARTITION TABLE ENDPOINTPROPERTYLOG ON COLUMN ENDPOINTID;
PARTITION PROCEDURE LGE_InsertEndpointPropertyLog ON TABLE ENDPOINTPROPERTYLOG COLUMN ENDPOINTID;


CREATE TABLE USER (

   USERID VARCHAR(36) NOT NULL,
   USERNAME VARCHAR(255) NOT NULL,
   USEREMAIL VARCHAR(255) NOT NULL,
   USERPWHASH VARCHAR(255),
   PLANID VARCHAR(36),
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
   PRIMARY KEY (LINKID, SPHEREID)
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
CREATE PROCEDURE FROM CLASS UI_UpdateSpherenameForSphereID;
CREATE PROCEDURE FROM CLASS UI_SelectAllSpheresForUserID;

CREATE TABLE ENDPOINTCLASS (

   ENDPOINTCLASSID VARCHAR(36) NOT NULL,
   ENDPOINTCLASSNAME VARCHAR(255) NOT NULL,
   ENDPOINTCLASSIMAGEURL VARCHAR(255),
   PRIMARY KEY (ENDPOINTCLASSID)
);

PARTITION TABLE ENDPOINTCLASS ON COLUMN ENDPOINTCLASSID;
CREATE PROCEDURE FROM CLASS UI_SelectAllEndpointClasses;
CREATE PROCEDURE FROM CLASS UI_SelectEndpointClassForEndpointClassID;
CREATE PROCEDURE FROM CLASS UI_UpdateEndpointClass;

CREATE TABLE ENDPOINT (

   ENDPOINTID VARCHAR(36) NOT NULL,
   ENDPOINTNAME VARCHAR(255) NOT NULL,
   ENDPOINTCLASSID VARCHAR(36) NOT NULL,
   PRIMARY KEY (ENDPOINTID)
);

CREATE INDEX EPEPC ON ENDPOINT (ENDPOINTCLASSID);
CREATE PROCEDURE FROM CLASS UI_SelectAllEndpoints;
CREATE PROCEDURE FROM CLASS UI_SelectEndpointsForSphere;
CREATE PROCEDURE FROM CLASS UI_SelectEndpointForEndpointID;
CREATE PROCEDURE FROM CLASS UI_UpdateEndpointnameForEndpointID;


CREATE TABLE PROPERTY (

   PROPERTYID VARCHAR(36) NOT NULL,
   PROPERTYNAME VARCHAR(255),
   PROPERTYCLASSID VARCHAR(36) NOT NULL,
   ENDPOINTCLASSID VARCHAR(36) NOT NULL,
   ACT TINYINT,
   SENSE TINYINT,
   STATUS TINYINT,
   CONFIGURABLE TINYINT,
   PRIMARY KEY (PROPERTYID)
);

CREATE INDEX PREPC ON PROPERTY (ENDPOINTCLASSID);
CREATE INDEX PRPRC ON PROPERTY (PROPERTYCLASSID);
CREATE PROCEDURE FROM CLASS UI_SelectAllProperties;
CREATE PROCEDURE FROM CLASS UI_SelectPropertyDetailsForEndpointClass;
CREATE PROCEDURE FROM CLASS UI_SelectPropertyClassForPropertyID;
CREATE PROCEDURE FROM CLASS UI_SelectPropertyNameForPropertyID;
CREATE PROCEDURE FROM CLASS UI_SelectActPropertiesForEndpoint;
CREATE PROCEDURE FROM CLASS UI_SelectSensePropertiesForEndpoint;

CREATE TABLE PROPERTYCLASS (

   PROPERTYCLASSID VARCHAR(36) NOT NULL,
   PROPERTYCLASSNAME VARCHAR(255),
   PROPERTYCLASSDATATYPE VARCHAR(32) NOT NULL,
   PROPERTYCLASSUNIT VARCHAR(32),
   PRIMARY KEY (PROPERTYCLASSID)
);

CREATE PROCEDURE FROM CLASS UI_SelectAllPropertyClasses;
CREATE PROCEDURE FROM CLASS UI_SelectPropertyClassForPropertyClassID;
CREATE PROCEDURE FROM CLASS UI_UpdatePropertyClass;

CREATE TABLE MESSAGE_ENDPOINT_LINK (

   LINKID VARCHAR(36) NOT NULL,
   ENDPOINTID VARCHAR(36) NOT NULL,
   MESSAGEID VARCHAR(36) NOT NULL,
   PRIMARY KEY (LINKID, ENDPOINTID)
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


CREATE TABLE EXPRESSION (

   EXPRESSIONID VARCHAR(36) NOT NULL,
   PROPERTYID VARCHAR(36) NOT NULL,
   PAYLOAD VARCHAR(4096),
   PRIMARY KEY (EXPRESSIONID)
);



CREATE TABLE CHECK (

   CHECKID VARCHAR(36) NOT NULL,
   ENDPOINTID VARCHAR(36) NOT NULL,
   PROPERTYID VARCHAR(36) NOT NULL,
   CHECKVALUE VARCHAR(4096),
   OPERATOR VARCHAR (32),
   STATE TINYINT,
   EXPIRED TINYINT,
   NAME VARCHAR(255),
   CHECKPATHID VARCHAR(36) NOT NULL,
   PRIMARY KEY (CHECKID, CHECKPATHID, ENDPOINTID)
);

CREATE INDEX CHEP ON CHECK (ENDPOINTID);
CREATE INDEX CHCOMB ON CHECK (ENDPOINTID, PROPERTYID, CHECKPATHID, STATE);
CREATE INDEX CHPR ON CHECK (PROPERTYID);
CREATE INDEX CHCP ON CHECK (CHECKPATHID);
CREATE INDEX CHST ON CHECK (STATE);
-- PARTITION TABLE CHECK ON COLUMN CHECKPATHID;
PARTITION TABLE CHECK ON COLUMN ENDPOINTID;
CREATE PROCEDURE FROM CLASS ImprovedFindMatchingChecksEqual;
CREATE PROCEDURE FROM CLASS UpdateChecks;
CREATE PROCEDURE FROM CLASS ResetChecks;
CREATE PROCEDURE FROM CLASS FindAllChecks;
CREATE PROCEDURE FROM CLASS FindChecksForCheckID;
CREATE PROCEDURE FROM CLASS BLE_ChecksForExpression;
-- PARTITION PROCEDURE BLE_ChecksForExpression ON TABLE CHECK COLUMN CHECKPATHID;
PARTITION PROCEDURE BLE_ChecksForExpression ON TABLE CHECK COLUMN ENDPOINTID;
CREATE PROCEDURE FROM CLASS BLE_AllChecksForExpression;
-- PARTITION PROCEDURE BLE_AllChecksForExpression ON TABLE CHECK COLUMN CHECKPATHID;
PARTITION PROCEDURE BLE_AllChecksForExpression ON TABLE CHECK COLUMN ENDPOINTID;
CREATE PROCEDURE FROM CLASS UI_SelectChecksForEndpoint;
CREATE PROCEDURE FROM CLASS UI_UpdateCheck;
CREATE PROCEDURE FROM CLASS UI_DeleteCheckState;
CREATE PROCEDURE FROM CLASS UI_DeleteCheck;
CREATE PROCEDURE FROM CLASS UI_SelectCheckForCheckID;


CREATE TABLE CHECKSTATE (

   CHECKID VARCHAR(36) NOT NULL,
   STATE TINYINT,
   CHECKPATHID VARCHAR(36) NOT NULL,
   PRIMARY KEY (CHECKID, CHECKPATHID)
);

PARTITION TABLE CHECKSTATE ON COLUMN CHECKPATHID;
CREATE PROCEDURE FROM CLASS BLE_UpdateCheckState;
PARTITION PROCEDURE BLE_UpdateCheckState ON TABLE CHECKSTATE COLUMN CHECKPATHID;

CREATE TABLE MULTICHECK (

   MULTICHECKID VARCHAR(36) NOT NULL,
   OPERATOR VARCHAR (32),
   STATE TINYINT,
   EXPIRED TINYINT,
   MULTICHECKNAME VARCHAR(255),
   CHECKPATHID VARCHAR(36) NOT NULL,
   PRIMARY KEY (MULTICHECKID, CHECKPATHID)
);
CREATE INDEX MCCP ON MULTICHECK (CHECKPATHID);
CREATE INDEX MCST ON MULTICHECK (STATE);
PARTITION TABLE MULTICHECK ON COLUMN CHECKPATHID;
CREATE PROCEDURE FROM CLASS UpdateMultiChecks;
CREATE PROCEDURE FROM CLASS FindMultiChecksForMultiCheckID;
CREATE PROCEDURE FROM CLASS BLE_IsMultiCheckTrue;
PARTITION PROCEDURE BLE_IsMultiCheckTrue ON TABLE MULTICHECK COLUMN CHECKPATHID;
CREATE PROCEDURE FROM CLASS BLE_IsCycleMultiCheckTrue;
PARTITION PROCEDURE BLE_IsCycleMultiCheckTrue ON TABLE MULTICHECK COLUMN CHECKPATHID;
CREATE PROCEDURE FROM CLASS UI_UpdateMulticheck;
CREATE PROCEDURE FROM CLASS UI_DeleteMulticheck;



CREATE TABLE MULTICHECK_CHECK_LINK (

   LINKID VARCHAR(36) NOT NULL,
   MULTICHECKID VARCHAR(36) NOT NULL,
   CHECKID VARCHAR(36) NOT NULL,
   CHECKPATHID VARCHAR(36) NOT NULL,
   PRIMARY KEY (LINKID, CHECKPATHID)
);


PARTITION TABLE MULTICHECK_CHECK_LINK ON COLUMN CHECKPATHID;
CREATE PROCEDURE FROM CLASS FindAllMultichecksForCheck;
CREATE PROCEDURE FROM CLASS ImprovedFindAllMultichecksForCheck;
CREATE PROCEDURE FROM CLASS FindChecksForMultiCheckID;
CREATE PROCEDURE FROM CLASS UI_DeleteMulticheckCheckLink;
CREATE PROCEDURE FROM CLASS UI_DeleteMulticheckMulticheckLink;
CREATE INDEX MCLMCidx ON MULTICHECK_CHECK_LINK (MULTICHECKID);
CREATE INDEX MCLCHidx ON MULTICHECK_CHECK_LINK (CHECKID);


CREATE TABLE MULTICHECK_MULTICHECK_LINK (

   LINKID VARCHAR(36) NOT NULL,
   MULTICHECKLID VARCHAR(36) NOT NULL,
   MULTICHECKRID VARCHAR(36) NOT NULL,
   CHECKPATHID VARCHAR(36) NOT NULL,
   PRIMARY KEY (LINKID, CHECKPATHID)
);
CREATE INDEX MCMCLR ON MULTICHECK_MULTICHECK_LINK (MULTICHECKRID);
CREATE INDEX MCMCLL ON MULTICHECK_MULTICHECK_LINK (MULTICHECKLID);
PARTITION TABLE MULTICHECK_MULTICHECK_LINK ON COLUMN CHECKPATHID;
CREATE PROCEDURE FROM CLASS BLE_MultiCheckParentForMultiCheck;
PARTITION PROCEDURE BLE_MultiCheckParentForMultiCheck ON TABLE MULTICHECK_MULTICHECK_LINK COLUMN CHECKPATHID;
CREATE PROCEDURE FROM CLASS FindLinkedMultiChecksForMultiCheckID;
CREATE PROCEDURE FROM CLASS FindParentMultiChecksForMultiCheckID;



CREATE TABLE CHECKPATH (

   CHECKPATHID VARCHAR(36) NOT NULL,
   CHECKPATHNAME VARCHAR(255),
   UIOBJECT VARCHAR(32768),
   OWNERID VARCHAR(36),
   PRIMARY KEY (CHECKPATHID)
);
PARTITION TABLE CHECKPATH ON COLUMN CHECKPATHID;
CREATE PROCEDURE FROM CLASS UI_UpdateCheckpath;
CREATE PROCEDURE FROM CLASS UI_SelectCheckpathForCheckpathID;
CREATE PROCEDURE FROM CLASS UI_SelectAllCheckpaths;
CREATE PROCEDURE FROM CLASS UI_UpdateCheckpathName;
CREATE INDEX CPOWNID ON CHECKPATH (OWNERID);

CREATE TABLE CHECKPATH_CHECK_LINK (

   LINKID VARCHAR(36) NOT NULL,
   CHECKPATHID VARCHAR(36) NOT NULL,
   CHECKID VARCHAR(36) NOT NULL,
   PRIMARY KEY (LINKID, CHECKPATHID)
);

PARTITION TABLE CHECKPATH_CHECK_LINK ON COLUMN CHECKPATHID;
-- CREATE PROCEDURE FROM CLASS BLE_CheckPathForChecks;
CREATE INDEX CHECKIDidx ON CHECKPATH_CHECK_LINK (CHECKID);
CREATE INDEX CHECKPATHIDidx ON CHECKPATH_CHECK_LINK (CHECKPATHID);
-- PARTITION PROCEDURE BLE_CheckPathForChecks ON TABLE CHECKPATH_CHECK_LINK COLUMN CHECKPATHID;



CREATE TABLE CHECKPATH_MULTICHECK_LINK (

   LINKID VARCHAR(36) NOT NULL,
   CHECKPATHID VARCHAR(36) NOT NULL,
   MULTICHECKID VARCHAR(36) NOT NULL,
   PRIMARY KEY (LINKID, CHECKPATHID)
);

PARTITION TABLE CHECKPATH_MULTICHECK_LINK ON COLUMN CHECKPATHID;
CREATE PROCEDURE FROM CLASS BLE_CheckPathForMultiChecks;
PARTITION PROCEDURE BLE_CheckPathForMultiChecks ON TABLE CHECKPATH_MULTICHECK_LINK COLUMN CHECKPATHID;
CREATE INDEX CPMCidx ON CHECKPATH_MULTICHECK_LINK (CHECKPATHID);


CREATE TABLE ACTION (

   ACTIONID VARCHAR(36) NOT NULL,
   ACTIONNAME VARCHAR(255) NOT NULL,
   TARGETENDPOINTID VARCHAR(36),
   TGTEPIDFROMINBOUNDPROP VARCHAR(36),
   EXPIRED TINYINT,
   CHECKID VARCHAR(36),
   MULTICHECKID VARCHAR(36),
   CHECKPATHID VARCHAR(36) NOT NULL,
   PRIMARY KEY (ACTIONID,CHECKPATHID)

);

CREATE PROCEDURE FROM CLASS BLE_FindActionsForCheckID;
PARTITION PROCEDURE BLE_FindActionsForCheckID ON TABLE ACTION COLUMN CHECKPATHID;
CREATE PROCEDURE FROM CLASS BLE_FindActionsForMultiCheckID;
PARTITION PROCEDURE BLE_FindActionsForMultiCheckID ON TABLE ACTION COLUMN CHECKPATHID;
CREATE INDEX RCHECK ON ACTION (CHECKID);
CREATE INDEX RMCHECK ON ACTION (MULTICHECKID);
PARTITION TABLE ACTION ON COLUMN CHECKPATHID;

CREATE TABLE OUTBOUNDPROPERTYACTION (

   OUTBOUNDPROPERTYACTIONID VARCHAR(36) NOT NULL,
   OUTBOUNDPROPERTYID VARCHAR(36),
   INBOUNDPROPERTYID VARCHAR(36),
   CURRENTSTATEPROPERTYID VARCHAR(36),
   CURRENTSTATEENDPOINTID VARCHAR(36),
   CUSTOMPAYLOAD VARCHAR(32768),
   ACTIONID VARCHAR(36) NOT NULL,
   CHECKPATHID VARCHAR(36) NOT NULL,
   PRIMARY KEY (OUTBOUNDPROPERTYACTIONID, CHECKPATHID)
);
PARTITION TABLE OUTBOUNDPROPERTYACTION ON COLUMN CHECKPATHID;

CREATE PROCEDURE FROM CLASS UI_SelectActionsForCheckID;
CREATE PROCEDURE FROM CLASS UI_UpsertActionsForCheckID;
CREATE PROCEDURE FROM CLASS UI_SelectActionsForMulticheckID;
CREATE PROCEDURE FROM CLASS UI_UpsertActionsForMulticheckID;
CREATE PROCEDURE FROM CLASS UI_DeleteAllActionsForCheckID;
CREATE PROCEDURE FROM CLASS UI_DeleteAllActionsForMulticheckID;
CREATE PROCEDURE FROM CLASS AME_DetailsForAction;
CREATE INDEX OUTBOUNDPROPACTION ON OUTBOUNDPROPERTYACTION (ACTIONID);
PARTITION PROCEDURE AME_DetailsForAction ON TABLE OUTBOUNDPROPERTYACTION COLUMN CHECKPATHID;
PARTITION PROCEDURE UI_SelectActionsForCheckID ON TABLE OUTBOUNDPROPERTYACTION COLUMN CHECKPATHID;
PARTITION PROCEDURE UI_SelectActionsForMulticheckID ON TABLE OUTBOUNDPROPERTYACTION COLUMN CHECKPATHID;


CREATE TABLE ITEMCOST (

   ITEMID VARCHAR(36) NOT NULL,
   PLANID VARCHAR(36),
   DESCRIPTION VARCHAR(128),
   COST FLOAT,
   PRIMARY KEY (ITEMID, PLANID)
);

CREATE TABLE PLAN (

   PLANID VARCHAR(36) NOT NULL,
   DESCRIPTION VARCHAR(128),
   PRIMARY KEY (PLANID)
);

CREATE TABLE UNPROVISIONED_ENDPOINT (

   UEPID VARCHAR(36) NOT NULL,
   IDENTIFIER VARCHAR(128),
   ENDPOINTCLASSID VARCHAR(36),
   TIME_STAMP TIMESTAMP,
   PRIMARY KEY (UEPID)
);

CREATE PROCEDURE FROM CLASS UI_SelectUnprovisionedEPforIdentifier;

CREATE TABLE VENDOR (

   VENDORID VARCHAR(36) NOT NULL,
   VENDORNAME VARCHAR(255) NOT NULL,
   VENDORIMAGEURL VARCHAR(255),
   PRIMARY KEY (VENDORID)
);

PARTITION TABLE VENDOR ON COLUMN VENDORID;
CREATE PROCEDURE FROM CLASS UI_SelectAllVendors;
CREATE PROCEDURE FROM CLASS UI_SelectVendorForVendorID;
CREATE PROCEDURE FROM CLASS UI_UpdateVendor;
