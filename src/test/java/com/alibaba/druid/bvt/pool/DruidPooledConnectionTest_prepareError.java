package com.alibaba.druid.bvt.pool;

import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.TestCase;

import org.junit.Assert;

import com.alibaba.druid.filter.FilterAdapter;
import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.proxy.jdbc.CallableStatementProxy;
import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import com.alibaba.druid.proxy.jdbc.PreparedStatementProxy;

public class DruidPooledConnectionTest_prepareError extends TestCase {

    private DruidDataSource dataSource;

    protected void setUp() throws Exception {
        dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mock:xxx");
        dataSource.setTestOnBorrow(false);
        dataSource.setFilters("stat");
        dataSource.setPoolPreparedStatements(true);
        dataSource.getProxyFilters().add(new FilterAdapter() {

            @Override
            public PreparedStatementProxy connection_prepareStatement(FilterChain chain, ConnectionProxy connection,
                                                                      String sql) throws SQLException {
                throw new SQLException();
            }

            @Override
            public PreparedStatementProxy connection_prepareStatement(FilterChain chain, ConnectionProxy connection,
                                                                      String sql, int autoGeneratedKeys)
                                                                                                        throws SQLException {
                throw new SQLException();
            }

            @Override
            public PreparedStatementProxy connection_prepareStatement(FilterChain chain, ConnectionProxy connection,
                                                                      String sql, int resultSetType,
                                                                      int resultSetConcurrency) throws SQLException {
                throw new SQLException();
            }

            @Override
            public PreparedStatementProxy connection_prepareStatement(FilterChain chain, ConnectionProxy connection,
                                                                      String sql, int resultSetType,
                                                                      int resultSetConcurrency, int resultSetHoldability)
                                                                                                                         throws SQLException {
                throw new SQLException();
            }

            @Override
            public PreparedStatementProxy connection_prepareStatement(FilterChain chain, ConnectionProxy connection,
                                                                      String sql, int[] columnIndexes)
                                                                                                      throws SQLException {
                throw new SQLException();
            }

            @Override
            public PreparedStatementProxy connection_prepareStatement(FilterChain chain, ConnectionProxy connection,
                                                                      String sql, String[] columnNames)
                                                                                                       throws SQLException {
                throw new SQLException();
            }

            @Override
            public CallableStatementProxy connection_prepareCall(FilterChain chain, ConnectionProxy connection,
                                                                 String sql) throws SQLException {
                throw new SQLException();
            }

            @Override
            public CallableStatementProxy connection_prepareCall(FilterChain chain, ConnectionProxy connection,
                                                                 String sql, int resultSetType, int resultSetConcurrency)
                                                                                                                         throws SQLException {
                throw new SQLException();
            }

            @Override
            public CallableStatementProxy connection_prepareCall(FilterChain chain, ConnectionProxy connection,
                                                                 String sql, int resultSetType,
                                                                 int resultSetConcurrency, int resultSetHoldability)
                                                                                                                    throws SQLException {
                throw new SQLException();
            }

        });
    }

    protected void tearDown() throws Exception {
        dataSource.close();
    }

    public void test_prepare_error() throws Exception {
        Connection conn = dataSource.getConnection();

        Assert.assertEquals(0, dataSource.getErrorCount());

        Exception error = null;
        try {
            conn.prepareStatement("select 1");
        } catch (Exception e) {
            error = e;
        }

        Assert.assertNotNull(error);

        Assert.assertEquals(1, dataSource.getErrorCount());
    }

    public void test_prepare_error_1() throws Exception {
        Connection conn = dataSource.getConnection();

        Assert.assertEquals(0, dataSource.getErrorCount());

        Exception error = null;
        try {
            conn.prepareStatement("select 1", 0, 0);
        } catch (Exception e) {
            error = e;
        }

        Assert.assertNotNull(error);

        Assert.assertEquals(1, dataSource.getErrorCount());
    }

    public void test_prepare_error_2() throws Exception {
        Connection conn = dataSource.getConnection();

        Assert.assertEquals(0, dataSource.getErrorCount());

        Exception error = null;
        try {
            conn.prepareStatement("select 1", 0, 0, 0);
        } catch (Exception e) {
            error = e;
        }

        Assert.assertNotNull(error);

        Assert.assertEquals(1, dataSource.getErrorCount());
    }

    public void test_prepare_error_3() throws Exception {
        Connection conn = dataSource.getConnection();

        Assert.assertEquals(0, dataSource.getErrorCount());

        Exception error = null;
        try {
            conn.prepareStatement("select 1", 0);
        } catch (Exception e) {
            error = e;
        }

        Assert.assertNotNull(error);

        Assert.assertEquals(1, dataSource.getErrorCount());
    }

    public void test_prepare_error_4() throws Exception {
        Connection conn = dataSource.getConnection();

        Assert.assertEquals(0, dataSource.getErrorCount());

        Exception error = null;
        try {
            conn.prepareStatement("select 1", new int[0]);
        } catch (Exception e) {
            error = e;
        }

        Assert.assertNotNull(error);

        Assert.assertEquals(1, dataSource.getErrorCount());
    }

    public void test_prepare_error_5() throws Exception {
        Connection conn = dataSource.getConnection();

        Assert.assertEquals(0, dataSource.getErrorCount());

        Exception error = null;
        try {
            conn.prepareStatement("select 1", new String[0]);
        } catch (Exception e) {
            error = e;
        }

        Assert.assertNotNull(error);

        Assert.assertEquals(1, dataSource.getErrorCount());
    }
    
    public void test_prepareCall_error_1() throws Exception {
        Connection conn = dataSource.getConnection();
        
        Assert.assertEquals(0, dataSource.getErrorCount());
        
        Exception error = null;
        try {
            conn.prepareCall("select 1");
        } catch (Exception e) {
            error = e;
        }
        
        Assert.assertNotNull(error);
        
        Assert.assertEquals(1, dataSource.getErrorCount());
    }
    
    public void test_prepareCall_error_2() throws Exception {
        Connection conn = dataSource.getConnection();
        
        Assert.assertEquals(0, dataSource.getErrorCount());
        
        Exception error = null;
        try {
            conn.prepareCall("select 1", 0, 0);
        } catch (Exception e) {
            error = e;
        }
        
        Assert.assertNotNull(error);
        
        Assert.assertEquals(1, dataSource.getErrorCount());
    }
    
    public void test_prepareCall_error_3() throws Exception {
        Connection conn = dataSource.getConnection();
        
        Assert.assertEquals(0, dataSource.getErrorCount());
        
        Exception error = null;
        try {
            conn.prepareCall("select 1", 0, 0, 0);
        } catch (Exception e) {
            error = e;
        }
        
        Assert.assertNotNull(error);
        
        Assert.assertEquals(1, dataSource.getErrorCount());
    }
}
