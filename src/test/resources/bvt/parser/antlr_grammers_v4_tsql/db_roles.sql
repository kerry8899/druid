ALTER ROLE buyers WITH NAME = purchasing;  
ALTER ROLE Sales ADD MEMBER Barry;
ALTER ROLE Sales DROP MEMBER Barry;
CREATE ROLE buyers AUTHORIZATION BenMiller;
GO
CREATE ROLE auditors AUTHORIZATION db_securityadmin;
GO
