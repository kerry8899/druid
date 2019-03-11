package com.alibaba.druid.bvt.sql.oracle.visitor;

import com.alibaba.druid.sql.ast.SQLParameter;
import com.alibaba.druid.sql.ast.expr.SQLGroupingSetExpr;
import com.alibaba.druid.sql.ast.expr.SQLTimestampExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.ast.statement.SQLMergeStatement.MergeInsertClause;
import com.alibaba.druid.sql.ast.statement.SQLMergeStatement.MergeUpdateClause;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleDataTypeIntervalDay;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleDataTypeIntervalYear;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.CycleClause;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.ModelClause;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.ModelClause.CellAssignment;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.ModelClause.CellAssignmentItem;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.ModelClause.MainModelClause;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.ModelClause.ModelColumn;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.ModelClause.ModelColumnClause;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.ModelClause.ModelRulesClause;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.ModelClause.QueryPartitionClause;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.ModelClause.ReturnRowsClause;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.OracleReturningClause;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.OracleStorageClause;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.OracleWithSubqueryEntry;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.PartitionExtensionClause;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.SampleClause;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.SearchClause;
import com.alibaba.druid.sql.dialect.oracle.ast.expr.OracleAnalytic;
import com.alibaba.druid.sql.dialect.oracle.ast.expr.OracleAnalyticWindowing;
import com.alibaba.druid.sql.dialect.oracle.ast.expr.OracleArgumentExpr;
import com.alibaba.druid.sql.dialect.oracle.ast.expr.OracleBinaryDoubleExpr;
import com.alibaba.druid.sql.dialect.oracle.ast.expr.OracleBinaryFloatExpr;
import com.alibaba.druid.sql.dialect.oracle.ast.expr.OracleCursorExpr;
import com.alibaba.druid.sql.ast.expr.SQLDateExpr;
import com.alibaba.druid.sql.dialect.oracle.ast.expr.OracleDatetimeExpr;
import com.alibaba.druid.sql.dialect.oracle.ast.expr.OracleDbLinkExpr;
import com.alibaba.druid.sql.dialect.oracle.ast.expr.OracleIntervalExpr;
import com.alibaba.druid.sql.dialect.oracle.ast.expr.OracleIsSetExpr;
import com.alibaba.druid.sql.dialect.oracle.ast.expr.OracleRangeExpr;
import com.alibaba.druid.sql.dialect.oracle.ast.expr.OracleSizeExpr;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleAlterIndexStatement;
import com.alibaba.druid.sql.ast.statement.SQLAlterProcedureStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleAlterSessionStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleAlterSynonymStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleAlterTableDropPartition;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleAlterTableModify;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleAlterTableMoveTablespace;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleAlterTableSplitPartition;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleAlterTableTruncatePartition;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleAlterTablespaceAddDataFile;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleAlterTablespaceStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleAlterTriggerStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleAlterViewStatement;
import com.alibaba.druid.sql.ast.statement.SQLCommitStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleCreateDatabaseDbLinkStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleCreateIndexStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleCreateTableStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleDeleteStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleDropDbLinkStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleExceptionStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleExitStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleExplainStatement;
import com.alibaba.druid.sql.ast.statement.SQLExprStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleFileSpecification;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleForStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleGotoStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleInsertStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleLabelStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleLockTableStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleMultiInsertStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleMultiInsertStatement.ConditionalInsertClause;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleMultiInsertStatement.ConditionalInsertClauseItem;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleMultiInsertStatement.InsertIntoClause;
import com.alibaba.druid.sql.ast.statement.SQLScriptCommitStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OraclePrimaryKey;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectPivot;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectQueryBlock;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectRestriction.CheckOption;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectRestriction.ReadOnly;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectTableReference;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectUnPivot;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSetTransactionStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleUpdateStatement;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitorAdapter;

import junit.framework.TestCase;

public class OracleASTVisitorAdapterTest extends TestCase {

    public void test_adapter() throws Exception {
        OracleASTVisitorAdapter adapter = new OracleASTVisitorAdapter();

        new SQLScriptCommitStatement().accept(adapter);
        new OracleAnalytic().accept(adapter);
        new OracleAnalyticWindowing().accept(adapter);
        new SQLDateExpr().accept(adapter);
        new OracleDbLinkExpr().accept(adapter);
        new OracleSelectPivot.Item().accept(adapter);
        new OracleSelectPivot().accept(adapter);
        new CheckOption().accept(adapter);
        new ReadOnly().accept(adapter);
        new OracleSelectUnPivot().accept(adapter);
        new SQLTimestampExpr().accept(adapter);
        new PartitionExtensionClause().accept(adapter);
        new SQLGroupingSetExpr().accept(adapter);
        new OracleWithSubqueryEntry().accept(adapter);
        new OracleFileSpecification().accept(adapter);
        new OracleAlterTablespaceAddDataFile().accept(adapter);
        new OracleAlterTablespaceStatement().accept(adapter);
        new SQLCreateSequenceStatement().accept(adapter);
        new SQLLoopStatement().accept(adapter);
        new OracleIntervalExpr().accept(adapter);
        new OracleDeleteStatement().accept(adapter);
        new OracleUpdateStatement().accept(adapter);
        new SampleClause().accept(adapter);
        new OracleSelectTableReference().accept(adapter);
        new SearchClause().accept(adapter);
        new CycleClause().accept(adapter);
        new OracleBinaryFloatExpr().accept(adapter);
        new OracleBinaryDoubleExpr().accept(adapter);
        new OracleCursorExpr().accept(adapter);
        new OracleIsSetExpr().accept(adapter);
        new ReturnRowsClause().accept(adapter);
        new ModelClause().accept(adapter);
        new MainModelClause().accept(adapter);
        new ModelColumnClause().accept(adapter);
        new QueryPartitionClause().accept(adapter);
        new ModelColumn().accept(adapter);
        new ModelRulesClause().accept(adapter);
        new CellAssignmentItem().accept(adapter);
        new CellAssignment().accept(adapter);
        new SQLMergeStatement().accept(adapter);
        new MergeUpdateClause().accept(adapter);
        new MergeInsertClause().accept(adapter);
        new SQLErrorLoggingClause().accept(adapter);
        new OracleReturningClause().accept(adapter);
        new OracleInsertStatement().accept(adapter);
        new InsertIntoClause().accept(adapter);
        new OracleMultiInsertStatement().accept(adapter);
        new ConditionalInsertClause().accept(adapter);
        new ConditionalInsertClauseItem().accept(adapter);
        new OracleSelectQueryBlock().accept(adapter);
        new SQLBlockStatement().accept(adapter);
        new OracleLockTableStatement().accept(adapter);
        new OracleAlterSessionStatement().accept(adapter);
        new SQLExprStatement().accept(adapter);
        new OracleDatetimeExpr().accept(adapter);
        new OracleExceptionStatement().accept(adapter);
        new OracleExceptionStatement.Item().accept(adapter);
        new OracleArgumentExpr().accept(adapter);
        new OracleSetTransactionStatement().accept(adapter);
        new SQLDropSequenceStatement().accept(adapter);
        new OracleDataTypeIntervalDay().accept(adapter);
        new OracleDataTypeIntervalYear().accept(adapter);
        new OracleDropDbLinkStatement().accept(adapter);
        new OracleCreateDatabaseDbLinkStatement().accept(adapter);
        new SQLCreateProcedureStatement().accept(adapter);
        new SQLFetchStatement().accept(adapter);
        new OracleExitStatement().accept(adapter);
        new OracleExplainStatement().accept(adapter);
        new SQLAlterProcedureStatement().accept(adapter);
        new OracleAlterTableDropPartition().accept(adapter);
        new OracleAlterTableTruncatePartition().accept(adapter);
        new OracleAlterTableSplitPartition.TableSpaceItem().accept(adapter);
        new OracleAlterTableSplitPartition.UpdateIndexesClause().accept(adapter);
        new OracleAlterTableSplitPartition.NestedTablePartitionSpec().accept(adapter);
        new OracleAlterTableSplitPartition().accept(adapter);
        new OracleAlterTableModify().accept(adapter);
        new OracleCreateIndexStatement().accept(adapter);
        new OracleAlterIndexStatement().accept(adapter);
        new OracleForStatement().accept(adapter);
        new OracleAlterIndexStatement().accept(adapter);
        new OracleRangeExpr().accept(adapter);
        new OraclePrimaryKey().accept(adapter);
        new OracleCreateTableStatement().accept(adapter);
        new SQLAlterTableRename().accept(adapter);
        new OracleStorageClause().accept(adapter);
        new OracleGotoStatement().accept(adapter);
        new OracleLabelStatement().accept(adapter);
        new SQLParameter().accept(adapter);
        new SQLCommitStatement().accept(adapter);
        new OracleAlterTriggerStatement().accept(adapter);
        new OracleAlterSynonymStatement().accept(adapter);
        new OracleAlterViewStatement().accept(adapter);
        new OracleAlterTableMoveTablespace().accept(adapter);
        new OracleSizeExpr().accept(adapter);
    }
}
