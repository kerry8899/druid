CREATE TABLE druid_springmethod (
	id bigint(20) AUTO_INCREMENT NOT NULL, 
	domain varchar(45) NOT NULL, 
	app varchar(45) NOT NULL, 
	cluster varchar(45) NOT NULL, 
	host varchar(128), 
	pid int(10) NOT NULL, 
	collectTime datetime NOT NULL, 
	className varchar(256), 
	signature varchar(256), 
	runningCount int(10), 
	concurrentMax int(10), 
	executeCount bigint(20), 
	executeErrorCount bigint(20), 
	executeTimeNano bigint(20), 
	jdbcFetchRowCount bigint(20), 
	jdbcUpdateCount bigint(20), 
	jdbcExecuteCount bigint(20), 
	jdbcExecuteErrorCount bigint(20), 
	jdbcExecuteTimeNano bigint(20), 
	jdbcCommitCount bigint(20), 
	jdbcRollbackCount bigint(20), 
	jdbcPoolConnectionOpenCount bigint(20), 
	jdbcPoolConnectionCloseCount bigint(20), 
	jdbcResultSetOpenCount bigint(20), 
	jdbcResultSetCloseCount bigint(20), 
	lastErrorClass varchar(256), 
	lastErrorMessage varchar(256), 
	lastErrorStackTrace varchar(256), 
	lastErrorTimeMillis bigint(20), 
	h1 bigint(20), 
	h10 bigint(20), 
	h100 bigint(20), 
	h1000 bigint(20), 
	h10000 int(10), 
	h100000 int(10), 
	h1000000 int(10), 
	hmore int(10), 
	PRIMARY KEY (id)
);

CREATE INDEX druid_springmethod_index ON druid_springmethod (collectTime, domain, app);