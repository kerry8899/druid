ALTER SYMMETRIC KEY JanainaKey043   
    ADD ENCRYPTION BY PASSWORD = '<enterStrongPasswordHere>';  
-- Now remove encryption by the certificate.  
ALTER SYMMETRIC KEY JanainaKey043   
    DROP ENCRYPTION BY CERTIFICATE Shipping04;  
CREATE SYMMETRIC KEY JanainaKey09   
WITH ALGORITHM = AES_256  
ENCRYPTION BY CERTIFICATE Shipping04;  
GO  
CREATE SYMMETRIC KEY #MarketingXXV
WITH ALGORITHM = AES_128,
KEY_SOURCE
     = 'The square of the hypotenuse is equal to the sum of the squares of the sides',
IDENTITY_VALUE = 'Pythagoras'
ENCRYPTION BY CERTIFICATE Marketing25;
GO
CREATE SYMMETRIC KEY MySymKey
AUTHORIZATION User1
FROM PROVIDER EKMProvider
WITH
PROVIDER_KEY_NAME='KeyForSensitiveData',
CREATION_DISPOSITION=OPEN_EXISTING;
GO
