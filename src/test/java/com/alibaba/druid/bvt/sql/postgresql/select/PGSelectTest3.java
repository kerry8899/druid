/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.druid.bvt.sql.postgresql.select;

import java.util.List;

import org.junit.Assert;

import com.alibaba.druid.sql.PGTest;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.postgresql.parser.PGSQLStatementParser;
import com.alibaba.druid.sql.dialect.postgresql.visitor.PGSchemaStatVisitor;

public class PGSelectTest3 extends PGTest {

    public void test_0() throws Exception {
        String sql = "SELECT TITLE_ID,WEB_ID,MENU_TYPE_ID,MENU_ID,TITLE" + //
                     ",SOURCE,INFO,RECOMMEND_FLAG,CREATE_TIME,CREATE_IP,CREATE_ACCENDANT_ID" + //
                     ",LAST_UPD_TIME,LAST_UPD_IP,LAST_UPD_ACCENDANT_ID,'http://cmp2.test.com/cmps' AS STR_BASE_URL" + //
                     ",'html' AS STR_SUFFIX FROM WEB_DATA_TITLE_LIST" + //
                     " WHERE WEB_ID=? AND MENU_ID=? AND MENU_TYPE_ID=? ORDER BY RECOMMEND_FLAG DESC ,LAST_UPD_TIME DESC  OFFSET ? LIMIT ?";

        PGSQLStatementParser parser = new PGSQLStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        SQLStatement statemen = statementList.get(0);
//        print(statementList);

        Assert.assertEquals(1, statementList.size());

        PGSchemaStatVisitor visitor = new PGSchemaStatVisitor();
        statemen.accept(visitor);

//        System.out.println("Tables : " + visitor.getTables());
//        System.out.println("fields : " + visitor.getColumns());
//        System.out.println("coditions : " + visitor.getConditions());

        Assert.assertEquals(14, visitor.getColumns().size());
        Assert.assertEquals(1, visitor.getTables().size());
    }

}

// select categoryId , offerIds from cnres.function_select_get_spt_p4p_offer_list (' 1031918 , 1031919 , 1037004 ') as
// a(categoryId numeric,offerIds character varying(4000))
// select memberId , offerIds from cnres.function_select_get_seller_hot_offer_list('\'gzyyd168\'') as a(memberId
// character varying(20),offerIds character varying(4000))

