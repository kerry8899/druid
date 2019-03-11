CREATE COLUMN MASTER KEY MyCMK  
WITH (  
     KEY_STORE_PROVIDER_NAME = N'MSSQL_CERTIFICATE_STORE',   
     KEY_PATH = 'Current User/Personal/f2260f28d909d21c642a3d8e0b45a830e79a1420'  
   );  

CREATE COLUMN MASTER KEY MyCMK
WITH (
    KEY_STORE_PROVIDER_NAME = N'MSSQL_CNG_STORE',
    KEY_PATH = N'My HSM CNG Provider/AlwaysEncryptedKey'
);
CREATE COLUMN MASTER KEY MyCMK
WITH (
    KEY_STORE_PROVIDER_NAME = N'AZURE_KEY_VAULT',
    KEY_PATH = N'https://myvault.vault.azure.net:443/keys/
        MyCMK/4c05f1a41b12488f9cba2ea964b6a700');
CREATE COLUMN MASTER KEY MyCMK
WITH (
    KEY_STORE_PROVIDER_NAME = 'CUSTOM_KEY_STORE',
    KEY_PATH = 'https://contoso.vault/sales_db_tce_key'
);
