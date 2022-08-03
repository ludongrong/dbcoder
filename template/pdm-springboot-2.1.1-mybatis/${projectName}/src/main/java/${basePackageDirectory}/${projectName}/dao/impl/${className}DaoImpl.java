package ${basePackage}.${projectName}.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * ${className} 数据操作实现层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
@Slf4j
@Repository("${CodeCamelFirstLower}Dao")
public class ${className}DaoImpl extends SqlSessionDaoSupport implements ${className}Dao {

    public static final RowMapper<${className}Bo> MAPPER = new RowMapper<${className}Bo>() {

        public ${className}Bo mapRow(ResultSet rs, int rowNum) throws SQLException {
            ${className}Bo ${CodeCamelFirstLower} = new ${className}Bo();

        <#list Columns as column>
            ${CodeCamelFirstLower}.set${column.CodeCamelFirstUpper}(rs.get${column.JavaType}(${className}Bo.PK_${column.CodeUpper}));
            if (rs.wasNull()) {
                ${CodeCamelFirstLower}.set${column.CodeCamelFirstUpper}(null);
            }
        </#list>

            return ${CodeCamelFirstLower};
        }
    };

<#--
引入 JdbcTemplate 兼容旧项目。适配在要用原生 jdbc 的场景。
-->
    @Nullable
    private JdbcTemplate jdbcTemplate;

    @Autowired(required = true)
    @Qualifier("sqlSessionFactory")
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
        this.jdbcTemplate = new JdbcTemplate(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource());
    }
<#--
样例：
    @Override
    public ${className}Bo getTest(Long id) {
        return this.getSqlSession().getMapper(${className}Mapper.class).select_primary(id);
    }
-->

    final String INSERT_BATCH_SQL = "INSERT INTO ${Code} ("+
        "<#list Columns as column> ${column.Code}<#sep>, </#sep></#list>"+
        ") VALUES ("+
        "<#list Columns as column>?<#sep>, </#sep></#list>)";

<#--
适合执行小批量操作
如果是 oracle 数据库 返回的结果是 -2，驱动问题
这里没有判断单个对象执行情况，而是整理执行情况，只要没抛出异常，则都认为操作成功
-->
    @Override
    public int[] insertBatch(final List<${className}Bo> ${CodeCamelFirstLower}List) {

        return this.jdbcTemplate.batchUpdate(INSERT_BATCH_SQL, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ${className}Bo ${CodeCamelFirstLower} = ${CodeCamelFirstLower}List.get(i);
            <#assign i = 0>
            <#list Columns as column>
                <#assign i++>
                ps.set${column.JavaType}(${i}, ${CodeCamelFirstLower}.get${column.CodeCamelFirstUpper}());
            </#list>
            }

            @Override
            public int getBatchSize() {
                return ${CodeCamelFirstLower}List.size();
            }
        });
    }
    
    final String DELETE_BATCH_SQL = "DELETE FROM ${Code} WHERE <#list Columns?filter(x -> x.PrimaryKey == "1") as column>${column.Code} = ?<#sep>AND </#sep></#list>";
    
    @Override
    public int[] deleteBatch(final List<${className}Bo> ${CodeCamelFirstLower}List) {

        return this.jdbcTemplate.batchUpdate(DELETE_BATCH_SQL, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ${className}Bo ${CodeCamelFirstLower} = ${CodeCamelFirstLower}List.get(i);
            <#assign i = 0>
            <#list Columns?filter(x -> x.PrimaryKey == "1") as column>
                <#assign i++>
                ps.set${column.JavaType}(${i}, ${CodeCamelFirstLower}.get${column.CodeCamelFirstUpper}());
            </#list>
            }

            @Override
            public int getBatchSize() {
                return ${CodeCamelFirstLower}List.size();
            }
        });
    }
<#if HasPrimaryKey == '1'>

    final String UPDATE_BATCH_SQL = "UPDATE ${Code} SET <#list Columns?filter(x -> x.PrimaryKey != "1") as column>${column.Code} = ?<#sep>, </#sep></#list> "+
        " WHERE <#list Columns?filter(x -> x.PrimaryKey == "1") as column>${column.Code} = ?<#sep>AND </#sep></#list>";

    @Override
    public int[] updateBatch(final List<${className}Bo> ${CodeCamelFirstLower}List) {

        return this.getJdbcTemplate().batchUpdate(UPDATE_BATCH_SQL, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ${className}Bo ${CodeCamelFirstLower} = ${CodeCamelFirstLower}List.get(i);
            <#assign i = 0>
            <#list Columns?filter(x -> x.PrimaryKey != "1") as column>
                <#assign i++>
                ps.set${column.JavaType}(${i}, ${CodeCamelFirstLower}.get${column.CodeCamelFirstUpper}());
            </#list>
            <#list Columns?filter(x -> x.PrimaryKey == "1") as column>
                <#assign i++>
                ps.set${column.JavaType}(${i}, ${CodeCamelFirstLower}.get${column.CodeCamelFirstUpper}());
            </#list>
            }

            @Override
            public int getBatchSize() {
                return ${CodeCamelFirstLower}List.size();
            }
        });
    }
</#if>
}
