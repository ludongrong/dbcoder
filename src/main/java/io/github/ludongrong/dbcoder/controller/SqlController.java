package io.github.ludongrong.dbcoder.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLCreateTableStatement;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;
import com.mchange.lang.IntegerUtils;

import io.github.ludongrong.dbcoder.controller.dto.IndexRequestDto;
import io.github.ludongrong.dbcoder.controller.dto.IndexResponseDto;

@Controller
@RequestMapping("/sql")
public class SqlController {

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public IndexResponseDto create(@RequestBody IndexRequestDto indexRequestDto) {
        
        IndexResponseDto indexResponseDto = new IndexResponseDto();

        List<SQLStatement> stmtList = null;
        try {
            stmtList = SQLUtils.parseStatements(indexRequestDto.getContent(), JdbcConstants.SQL_SERVER);
        } catch (Exception e) {
            indexResponseDto.setCode("-1");
            indexResponseDto.setMessage("please input error sql!");
            return indexResponseDto;
        }

        if (stmtList.size() > 1) {
            indexResponseDto.setCode("-1");
            indexResponseDto.setMessage("please input right content!");
            return indexResponseDto;
        }

        SQLStatement stmt = stmtList.get(0);

        try {
            SchemaStatVisitor visitor = SQLUtils.createSchemaStatVisitor(JdbcConstants.SQL_SERVER);
            stmt.accept(visitor);

            SQLCreateTableStatement stmt1 = (SQLCreateTableStatement) stmt;
            List<SQLTableElement> sqlTableElements = stmt1.getTableElementList();

            String tableName = stmt1.getName().toString().toLowerCase();
        } catch (Exception e) {
            indexResponseDto.setCode("-1");
            indexResponseDto.setMessage("please input right column!");
            return indexResponseDto;
        }

        indexResponseDto.setCode("0");
        indexResponseDto.setMessage("successfull");
        return indexResponseDto;
    }

    public void createDbdTableColumn(String tableId, List<SQLTableElement> sqlTableElements, int i) {
        for (SQLTableElement sqlTableElement : sqlTableElements) {
            SQLColumnDefinition sqlColumnDefinition = (SQLColumnDefinition) sqlTableElement;

            String columnName = sqlColumnDefinition.getName().toString();
            String dataType = sqlColumnDefinition.getDataType().getName();

            int length = 0;
            int precision = 0;

            List<SQLExpr> sqlExprs = sqlColumnDefinition.getDataType().getArguments();
            if (sqlExprs.size() > 0) {
                SQLExpr sqlExpr = sqlExprs.get(0);
                if (sqlExpr == null) {
                    length = 0;
                } else {
                    length = IntegerUtils.parseInt(sqlExpr.toString(), 0);
                }
            }

            if (length < 1) {
                length = -1;
            }

            if (sqlExprs.size() > 1) {
                SQLExpr sqlExpr = sqlExprs.get(1);
                if (sqlExpr == null) {
                    precision = 0;
                } else {
                    precision = IntegerUtils.parseInt(sqlExpr.toString(), 0);
                }
            }

            if (precision < 1) {
                precision = -1;
            }
        }
    }
}