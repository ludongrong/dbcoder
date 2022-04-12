package io.github.ludongrong.dbcoder.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import io.github.ludongrong.dbcoder.dao.IPdFileDao;
import io.github.ludongrong.dbcoder.entity.PdFileBo;
import io.github.ludongrong.dbcoder.sql.DynamicCondition;

@Repository("dbcoder-PdFileDaoImpl")
public class PdFileDaoImpl extends NamedParameterJdbcDaoSupport implements IPdFileDao {

    static final public RowMapper<PdFileBo> _DEFAULT_MAPPER = new RowMapper<PdFileBo>() {

        public PdFileBo mapRow(ResultSet rs, int rowNum) throws SQLException {
            PdFileBo pdFile = new PdFileBo();

            pdFile.setId(rs.getString(PdFileBo._ID));
            pdFile.setCreateTime(rs.getTimestamp(PdFileBo._CREATE_TIME));
            if (rs.wasNull()) {
                pdFile.setCreateTime(null);
            }
            pdFile.setBasePackage(rs.getString(PdFileBo._BASE_PACKAGE));
            pdFile.setProjectName(rs.getString(PdFileBo._PROJECT_NAME));
            pdFile.setDbType(rs.getString(PdFileBo._DB_TYPE));
            pdFile.setName(rs.getString(PdFileBo._NAME));
            pdFile.setGeneratePath(rs.getString(PdFileBo._GENERATE_PATH));
            pdFile.setTemplatePath(rs.getString(PdFileBo._TEMPLATE_PATH));

            return pdFile;
        }
    };

    @Autowired
    PdFileDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    @Override
    public int create(PdFileBo pdFile) {

        final String SQL = "INSERT INTO PD_FILE "
                + "(ID, CREATE_TIME, BASE_PACKAGE, PROJECT_NAME, DB_TYPE, NAME, GENERATE_PATH, TEMPLATE_PATH) "
                + "VALUES                                "
                + "(:id, :createTime, :basePackage, :projectName, :dbType, :name, :generatePath, :templatePath)";
        return getNamedParameterJdbcTemplate().update(SQL, new BeanPropertySqlParameterSource(pdFile));
    }

    @Override
    public int delete(DynamicCondition dyc, Map<String, Object> param) {

        final String _sql = "DELETE FROM PD_FILE";

        List<Object> args = new ArrayList<Object>();
        return getJdbcTemplate().update(DynamicCondition.generate(_sql, dyc, param, args), args.toArray());
    }

    @Override
    public int update(Map<String, Object> input, DynamicCondition dyc, Map<String, Object> param) {

        List<Object> args = new ArrayList<Object>();

        if (input.isEmpty()) {
            return -2;
        }

        StringBuilder strBuilder = new StringBuilder("UPDATE PD_FILE SET");
        for (Map.Entry<String, Object> entry : input.entrySet()) {
            strBuilder.append(entry.getKey());
            args.add(entry.getValue());
        }
        strBuilder.delete(0, strBuilder.length());

        return getJdbcTemplate().update(DynamicCondition.generate(strBuilder.toString(), dyc, param, args),
                args.toArray());
    }

    @Override
    public long count(DynamicCondition dyc, Map<String, Object> param) {

        final String _sql = "SELECT COUNT(1) FROM PD_FILE";

        List<Object> args = new ArrayList<Object>();
        return getJdbcTemplate().queryForObject(DynamicCondition.generate(_sql, dyc, param, args), Long.class,
                args.toArray());
    }

    @Override
    public List<PdFileBo> query(DynamicCondition dyc, Map<String, Object> param) {

        final String _sql = "SELECT ID, CREATE_TIME, BASE_PACKAGE, PROJECT_NAME, DB_TYPE, NAME, GENERATE_PATH, TEMPLATE_PATH FROM PD_FILE";

        List<Object> args = new ArrayList<Object>();
        return getJdbcTemplate().query(DynamicCondition.generate(_sql, dyc, param, args), _DEFAULT_MAPPER,
                args.toArray());
    }

    @Override
    public List<PdFileBo> query(int offset, int limit, DynamicCondition dyc, Map<String, Object> param) {

        List<Object> args = new ArrayList<Object>();

        final String _inside_sql = "select ID FROM PD_FILE ";
        StringBuilder inSqlBuf = new StringBuilder(DynamicCondition.generate(_inside_sql, dyc, param, args))
                .append(" LIMIT ?, ? ");

        args.add(offset);
        args.add(limit);

        final String _outside_sql = "SELECT A.ID, A.CREATE_TIME, A.BASE_PACKAGE, A.PROJECT_NAME, A.DB_TYPE, A.NAME, A.GENERATE_PATH, A.TEMPLATE_PATH FROM PD_FILE AS A";
        StringBuilder outSqlBuf = new StringBuilder(_outside_sql).append(" RIGHT JOIN ").append("(").append(inSqlBuf)
                .append(") AS B ON ");

        outSqlBuf.append(" A.ID=B.ID ");

        outSqlBuf.append(" LIMIT ?");

        args.add(limit);

        return getJdbcTemplate().query(_outside_sql.toString(), _DEFAULT_MAPPER, args.toArray());
    }
}
