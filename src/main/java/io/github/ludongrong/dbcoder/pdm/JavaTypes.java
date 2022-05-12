package io.github.ludongrong.dbcoder.pdm;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.druid.util.JdbcUtils;
import com.mysql.cj.MysqlType;

import cn.hutool.core.util.StrUtil;
import io.github.ludongrong.dbcoder.util.StringUtil;
import oracle.jdbc.OracleType;

/**
 * JavaTypesUtils
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since 2022-03-24
 */
public class JavaTypes {

    // JDBC 的 SQL_TYPE 对应 JAVA_TYPE
    final static private Map<Integer, String> _SQL_TYPE_2_JAVA_TYPE = new HashMap<Integer, String>();

    static {
        _SQL_TYPE_2_JAVA_TYPE.put(Types.TINYINT, Byte.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.SMALLINT, Short.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.INTEGER, Integer.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.BIGINT, Long.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.REAL, Float.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.FLOAT, Double.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.DOUBLE, Double.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.DECIMAL, BigDecimal.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.NUMERIC, BigDecimal.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.BIT, Boolean.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.BOOLEAN, Boolean.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.CHAR, String.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.VARCHAR, String.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.LONGVARCHAR, String.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.LONGNVARCHAR, String.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.NCHAR, String.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.NCLOB, java.sql.NClob.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.BINARY, "byte[]");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.VARBINARY, "byte[]");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.LONGVARBINARY, "byte[]");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.DATE, java.sql.Date.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.TIME, java.sql.Time.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.TIMESTAMP, java.sql.Timestamp.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.CLOB, java.sql.Clob.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.BLOB, Blob.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.ARRAY, Array.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.REF, Object.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.STRUCT, Object.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.JAVA_OBJECT, Object.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.DISTINCT, Object.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.NULL, Object.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.NVARCHAR, String.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.OTHER, Object.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.ROWID, Object.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.SQLXML, Object.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.TIME_WITH_TIMEZONE, java.time.OffsetTime.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(Types.TIMESTAMP_WITH_TIMEZONE, java.time.OffsetDateTime.class.getName());

        // microsoft-mssql-jdbc v4.4.0
        _SQL_TYPE_2_JAVA_TYPE.put(999, Object.class.getName());
        _SQL_TYPE_2_JAVA_TYPE.put(microsoft.sql.Types.DATETIMEOFFSET, microsoft.sql.DateTimeOffset.class.getName());

        // microsoft-mssql-jdbc v10.2.0
//        _SQL_TYPE_2_JAVA_TYPE.put(microsoft.sql.Types.MONEY, BigDecimal.class.getName());
//        _SQL_TYPE_2_JAVA_TYPE.put(microsoft.sql.Types.SMALLMONEY, BigDecimal.class.getName());
//        _SQL_TYPE_2_JAVA_TYPE.put(microsoft.sql.Types.STRUCTURED, Object.class.getName());
//        _SQL_TYPE_2_JAVA_TYPE.put(microsoft.sql.Types.DATETIME, java.sql.Timestamp.class.getName());
//        _SQL_TYPE_2_JAVA_TYPE.put(microsoft.sql.Types.SMALLDATETIME, java.sql.Timestamp.class.getName());
//        _SQL_TYPE_2_JAVA_TYPE.put(microsoft.sql.Types.GUID, String.class.getName());
//        _SQL_TYPE_2_JAVA_TYPE.put(microsoft.sql.Types.SQL_VARIANT, Object.class.getName());
//        _SQL_TYPE_2_JAVA_TYPE.put(microsoft.sql.Types.GEOMETRY, Object.class.getName());
//        _SQL_TYPE_2_JAVA_TYPE.put(microsoft.sql.Types.GEOGRAPHY, Object.class.getName());
//        _SQL_TYPE_2_JAVA_TYPE.put(java.sql.Types.TIMESTAMP, LocalDateTime.class.getName());
    }

    // JDBC 的 SQL_TYPE 对应 JDBC 的 SQL_TYPE_NAME
    final static private Map<Integer, String> _SQL_TYPE_CODE_2_NAME = new HashMap<Integer, String>();

    static {
        _SQL_TYPE_CODE_2_NAME.put(Types.TINYINT, "TINYINT");
        _SQL_TYPE_CODE_2_NAME.put(Types.SMALLINT, "SMALLINT");
        _SQL_TYPE_CODE_2_NAME.put(Types.INTEGER, "INTEGER");
        _SQL_TYPE_CODE_2_NAME.put(Types.BIGINT, "BIGINT");
        _SQL_TYPE_CODE_2_NAME.put(Types.REAL, "REAL");
        _SQL_TYPE_CODE_2_NAME.put(Types.FLOAT, "FLOAT");
        _SQL_TYPE_CODE_2_NAME.put(Types.DOUBLE, "DOUBLE");
        _SQL_TYPE_CODE_2_NAME.put(Types.DECIMAL, "DECIMAL");
        _SQL_TYPE_CODE_2_NAME.put(Types.NUMERIC, "NUMERIC");
        _SQL_TYPE_CODE_2_NAME.put(Types.BIT, "BIT");
        _SQL_TYPE_CODE_2_NAME.put(Types.BOOLEAN, "BOOLEAN");
        _SQL_TYPE_CODE_2_NAME.put(Types.CHAR, "CHAR");
        _SQL_TYPE_CODE_2_NAME.put(Types.VARCHAR, "VARCHAR");
        _SQL_TYPE_CODE_2_NAME.put(Types.LONGVARCHAR, "LONGVARCHAR");
        _SQL_TYPE_CODE_2_NAME.put(Types.LONGNVARCHAR, "LONGNVARCHAR");
        _SQL_TYPE_CODE_2_NAME.put(Types.NCHAR, "NCHAR");
        _SQL_TYPE_CODE_2_NAME.put(Types.NCLOB, "NCLOB");
        _SQL_TYPE_CODE_2_NAME.put(Types.BINARY, "BINARY");
        _SQL_TYPE_CODE_2_NAME.put(Types.VARBINARY, "VARBINARY");
        _SQL_TYPE_CODE_2_NAME.put(Types.LONGVARBINARY, "LONGVARBINARY");
        _SQL_TYPE_CODE_2_NAME.put(Types.DATE, "DATE");
        _SQL_TYPE_CODE_2_NAME.put(Types.TIME, "TIME");
        _SQL_TYPE_CODE_2_NAME.put(Types.TIMESTAMP, "TIMESTAMP");
        _SQL_TYPE_CODE_2_NAME.put(Types.CLOB, "CLOB");
        _SQL_TYPE_CODE_2_NAME.put(Types.BLOB, "BLOB");
        _SQL_TYPE_CODE_2_NAME.put(Types.ARRAY, "ARRAY");
        _SQL_TYPE_CODE_2_NAME.put(Types.REF, "REF");
        _SQL_TYPE_CODE_2_NAME.put(Types.STRUCT, "STRUCT");
        _SQL_TYPE_CODE_2_NAME.put(Types.JAVA_OBJECT, "JAVA_OBJECT");
        _SQL_TYPE_CODE_2_NAME.put(Types.DISTINCT, "DISTINCT");
        _SQL_TYPE_CODE_2_NAME.put(Types.NULL, "NULL");
        _SQL_TYPE_CODE_2_NAME.put(Types.NVARCHAR, "NVARCHAR");
        _SQL_TYPE_CODE_2_NAME.put(Types.OTHER, "OTHER");
        _SQL_TYPE_CODE_2_NAME.put(Types.ROWID, "ROWID");
        _SQL_TYPE_CODE_2_NAME.put(Types.SQLXML, "SQLXML");

        // microsoft-mssql-jdbc v4.4.0
        _SQL_TYPE_CODE_2_NAME.put(microsoft.sql.Types.DATETIMEOFFSET, "DATETIMEOFFSET");
    }

    // Microsoft Sql Server 数据类型 对应 JDBC 的 SQL_TYPE
    final static private Map<String, Integer> _MSSQL_SQL_TYPE = new HashMap<String, Integer>();

    /**
     * @see com.microsoft.sqlserver.jdbc.JDBCType
     */
    static {
        _MSSQL_SQL_TYPE.put("UNKNOWN", 999);
        _MSSQL_SQL_TYPE.put("ARRAY", Types.ARRAY);
        _MSSQL_SQL_TYPE.put("BIGINT", Types.BIGINT);
        _MSSQL_SQL_TYPE.put("BINARY", Types.BINARY);
        _MSSQL_SQL_TYPE.put("BIT", Types.BIT);
        _MSSQL_SQL_TYPE.put("BLOB", Types.BLOB);
        _MSSQL_SQL_TYPE.put("BOOLEAN", Types.BOOLEAN);
        _MSSQL_SQL_TYPE.put("CHAR", Types.CHAR);
        _MSSQL_SQL_TYPE.put("CLOB", Types.CLOB);
        _MSSQL_SQL_TYPE.put("DATALINK", Types.DATALINK);
        _MSSQL_SQL_TYPE.put("DATE", Types.DATE);
        _MSSQL_SQL_TYPE.put("DATETIMEOFFSET", microsoft.sql.Types.DATETIMEOFFSET);

        _MSSQL_SQL_TYPE.put("DECIMAL", Types.DECIMAL);
        _MSSQL_SQL_TYPE.put("DISTINCT", Types.DISTINCT);
        _MSSQL_SQL_TYPE.put("DOUBLE", Types.DOUBLE);
        _MSSQL_SQL_TYPE.put("FLOAT", Types.FLOAT);
        _MSSQL_SQL_TYPE.put("INTEGER", Types.INTEGER);
        _MSSQL_SQL_TYPE.put("JAVA_OBJECT", Types.JAVA_OBJECT);

        _MSSQL_SQL_TYPE.put("LONGNVARCHAR", -16);
        _MSSQL_SQL_TYPE.put("LONGVARBINARY", Types.LONGVARBINARY);
        _MSSQL_SQL_TYPE.put("LONGVARCHAR", Types.LONGVARCHAR);
        _MSSQL_SQL_TYPE.put("NCHAR", Types.NCHAR);
        _MSSQL_SQL_TYPE.put("NCLOB", Types.NCLOB);

        _MSSQL_SQL_TYPE.put("NULL", Types.NULL);
        _MSSQL_SQL_TYPE.put("NUMERIC", Types.NUMERIC);
        _MSSQL_SQL_TYPE.put("NVARCHAR", Types.NVARCHAR);
        _MSSQL_SQL_TYPE.put("OTHER", Types.OTHER);
        _MSSQL_SQL_TYPE.put("REAL", Types.REAL);
        _MSSQL_SQL_TYPE.put("REF", Types.REF);

        _MSSQL_SQL_TYPE.put("ROWID", Types.ROWID);
        _MSSQL_SQL_TYPE.put("SMALLINT", Types.SMALLINT);
        _MSSQL_SQL_TYPE.put("SQLXML", 2009);
        _MSSQL_SQL_TYPE.put("STRUCT", Types.STRUCT);
        _MSSQL_SQL_TYPE.put("TIME", Types.TIME);
        _MSSQL_SQL_TYPE.put("TIME_WITH_TIMEZONE", 2013);
        _MSSQL_SQL_TYPE.put("TIMESTAMP", Types.TIMESTAMP);
        _MSSQL_SQL_TYPE.put("TIMESTAMP_WITH_TIMEZONE", 2014);
        _MSSQL_SQL_TYPE.put("TINYINT", Types.TINYINT);
        _MSSQL_SQL_TYPE.put("VARBINARY", Types.VARBINARY);
        _MSSQL_SQL_TYPE.put("VARCHAR", java.sql.Types.VARCHAR);

        // v10.2.0
//        _MSSQL_SQL_TYPE.put("MONEY", microsoft.sql.Types.MONEY);
//        _MSSQL_SQL_TYPE.put("SMALLMONEY", microsoft.sql.Types.SMALLMONEY);
//        _MSSQL_SQL_TYPE.put("TVP", microsoft.sql.Types.STRUCTURED);
//        _MSSQL_SQL_TYPE.put("DATETIME", microsoft.sql.Types.DATETIME);
//        _MSSQL_SQL_TYPE.put("SMALLDATETIME", microsoft.sql.Types.SMALLDATETIME);
//        _MSSQL_SQL_TYPE.put("GUID", microsoft.sql.Types.GUID);
//        _MSSQL_SQL_TYPE.put("SQL_VARIANT", microsoft.sql.Types.SQL_VARIANT);
//        _MSSQL_SQL_TYPE.put("GEOMETRY", microsoft.sql.Types.GEOMETRY);
//        _MSSQL_SQL_TYPE.put("GEOGRAPHY", microsoft.sql.Types.GEOGRAPHY);
//        _MSSQL_SQL_TYPE.put("LOCALDATETIME", java.sql.Types.TIMESTAMP);
    }

    /**
     * PostgreSQL 的基础 数据类型
     */
    final static private Map<String, Integer> _PGSQL_SQL_TYPE = new HashMap<String, Integer>();

    /**
     * @see org.postgresql.jdbc.TypeInfoCache
     */
    static {
        _PGSQL_SQL_TYPE.put("int2", Types.SMALLINT);
        _PGSQL_SQL_TYPE.put("int4", Types.INTEGER);
        _PGSQL_SQL_TYPE.put("int8", Types.BIGINT);

        _PGSQL_SQL_TYPE.put("smallint", Types.SMALLINT);
        _PGSQL_SQL_TYPE.put("int", Types.INTEGER);
        _PGSQL_SQL_TYPE.put("integer", Types.INTEGER);
        _PGSQL_SQL_TYPE.put("long", Types.BIGINT);
        _PGSQL_SQL_TYPE.put("bigint", Types.BIGINT);
        _PGSQL_SQL_TYPE.put("oid", Types.BIGINT);

        _PGSQL_SQL_TYPE.put("numeric", Types.NUMERIC);
        _PGSQL_SQL_TYPE.put("decimal", Types.NUMERIC);
        _PGSQL_SQL_TYPE.put("numeric", Types.NUMERIC);

        _PGSQL_SQL_TYPE.put("float4", Types.REAL);
        _PGSQL_SQL_TYPE.put("float8", Types.DOUBLE);
        _PGSQL_SQL_TYPE.put("float", Types.REAL);
        _PGSQL_SQL_TYPE.put("double", Types.DOUBLE);
        _PGSQL_SQL_TYPE.put("money", Types.DOUBLE);

        _PGSQL_SQL_TYPE.put("char", Types.CHAR);
        _PGSQL_SQL_TYPE.put("bpchar", Types.CHAR);
        _PGSQL_SQL_TYPE.put("varchar", Types.VARCHAR);
        _PGSQL_SQL_TYPE.put("text", Types.VARCHAR);
        _PGSQL_SQL_TYPE.put("name", Types.VARCHAR);
        _PGSQL_SQL_TYPE.put("bytea", Types.BINARY);

        _PGSQL_SQL_TYPE.put("bool", Types.BIT);
        _PGSQL_SQL_TYPE.put("bit", Types.BIT);
        _PGSQL_SQL_TYPE.put("boolean", Types.BIT);

        _PGSQL_SQL_TYPE.put("date", Types.DATE);
        _PGSQL_SQL_TYPE.put("time", Types.TIME);
        _PGSQL_SQL_TYPE.put("timetz", Types.TIME);
        _PGSQL_SQL_TYPE.put("timestamp", Types.TIMESTAMP);
        _PGSQL_SQL_TYPE.put("timestamptz", Types.TIMESTAMP);
        _PGSQL_SQL_TYPE.put("refcursor", Types.REF_CURSOR);

        _PGSQL_SQL_TYPE.put("json", Types.OTHER);
        _PGSQL_SQL_TYPE.put("point", Types.OTHER);
    }

    /**
     * 数据库字段类型 对应 Java数据类型
     *
     * @param dbType
     * @param columnType
     * @param size
     * @param decimalDigits
     * @return
     */
    public static String getJavaType(String dbType, String columnType, int size, int decimalDigits) {
        int sqlType = getSqlType(dbType, columnType);

        return getJavaType(sqlType, size, decimalDigits);
    }

    /**
     * 数据库字段类型 对应 Java数据类型
     *
     * @param sqlType
     * @param size
     * @param decimalDigits
     * @return
     */
    public static String getJavaType(int sqlType, int size, int decimalDigits) {
        if ((sqlType == Types.DECIMAL || sqlType == Types.NUMERIC) && decimalDigits == 0) {
            if (size == 1) {
                sqlType = Types.BOOLEAN;
            } else if (size < 3) {
                sqlType = Types.TINYINT;
            } else if (size < 5) {
                sqlType = Types.SMALLINT;
            } else if (size < 10) {
                sqlType = Types.INTEGER;
            } else if (size < 19) {
                sqlType = Types.BIGINT;
            }
        }

        String result = _SQL_TYPE_2_JAVA_TYPE.get(sqlType);
        if (result == null) {
            result = _SQL_TYPE_2_JAVA_TYPE.get(Types.OTHER);
            ;
        }
        return result;
    }

    /**
     * 数据库字段类型 对应 Jdbc数据类型
     *
     * @param dbType
     * @param columnType
     * @return
     */
    public static String getJdbcType(String dbType, String columnType) {
        int sqlType = getSqlType(dbType, columnType);

        return getJdbcType(sqlType);
    }

    /**
     * 数据库字段类型 对应 Jdbc数据类型
     *
     * @param sqlType
     * @return
     */
    public static String getJdbcType(int sqlType) {
        String result = _SQL_TYPE_CODE_2_NAME.get(sqlType);
        if (result == null) {
            result = _SQL_TYPE_CODE_2_NAME.get(Types.OTHER);
        }
        return result;
    }

    private static int getSqlType(String dbType, String columnType) {
        int sqlType = Types.OTHER;

        if (dbType.equals(JdbcUtils.ORACLE)) {
            sqlType = getOracleSqlTypeByName(columnType);
        } else if (dbType.equals(JdbcUtils.SQL_SERVER)) {
            sqlType = getMssqlSqlTypeByName(columnType);
        } else if (dbType.equals(JdbcUtils.MYSQL)) {
            sqlType = getMysqlSqlTypeByName(columnType);
        } else if (dbType.equalsIgnoreCase(JdbcUtils.POSTGRESQL)) {
            sqlType = getPostgreSqlTypeByName(columnType);
        }

        return sqlType;
    }

    /**
     * MySql字段类型 对应 JdbcType
     *
     * @param fullTypeName
     * @return
     */
    public static int getMysqlSqlTypeByName(String fullTypeName) {
        return MysqlType.getByName(fullTypeName).getJdbcType();
    }

    /**
     * MsSql字段类型 对应 JdbcType
     *
     * @param fullTypeName
     * @return
     */
    public static int getMssqlSqlTypeByName(String fullTypeName) {
        return getSqlTypeByName(fullTypeName, _MSSQL_SQL_TYPE);
    }

    /**
     * Oracle字段类型 对应 JdbcType
     *
     * @param fullTypeName
     * @return
     */
    public static int getOracleSqlTypeByName(String fullTypeName) {

        String typeName = StringUtil.subStringBeforeParenthesis(fullTypeName);

        if (StrUtil.equalsIgnoreCase(typeName, OracleType.ANYDATA.getName())) {
            return OracleType.ANYDATA.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.ANYDATASET.getName())) {
            return OracleType.ANYDATASET.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.ANYTYPE.getName())) {
            return OracleType.ANYTYPE.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.BFILE.getName())) {
            return OracleType.BFILE.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.BINARY_DOUBLE.getName())) {
            return OracleType.BINARY_DOUBLE.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.BINARY_FLOAT.getName())) {
            return OracleType.BINARY_FLOAT.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.BLOB.getName())) {
            return OracleType.BLOB.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.VARCHAR2.getName())) {
            return OracleType.VARCHAR2.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.CHAR.getName())) {
            return OracleType.CHAR.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.CLOB.getName())) {
            return OracleType.CLOB.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.DATE.getName())) {
            return OracleType.DATE.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.DBURITYPE.getName())) {
            return OracleType.DBURITYPE.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.FLOAT.getName())) {
            return OracleType.FLOAT.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.HTTPURITYPE.getName())) {
            return OracleType.HTTPURITYPE.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.INTERVAL_DAY_TO_SECOND.getName())) {
            return OracleType.INTERVAL_DAY_TO_SECOND.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.INTERVAL_YEAR_TO_MONTH.getName())) {
            return OracleType.INTERVAL_YEAR_TO_MONTH.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.LONG.getName())) {
            return OracleType.LONG.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.LONG_RAW.getName())) {
            return OracleType.LONG_RAW.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.NCHAR.getName())) {
            return OracleType.NCHAR.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.NCLOB.getName())) {
            return OracleType.NCLOB.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.NESTED_TABLE.getName())) {
            return OracleType.NESTED_TABLE.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.NUMBER.getName())) {
            return OracleType.NUMBER.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.NVARCHAR.getName())) {
            return OracleType.NVARCHAR.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.OBJECT.getName())) {
            return OracleType.OBJECT.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.ORDAUDIO.getName())) {
            return OracleType.ORDAUDIO.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.ORDDICOM.getName())) {
            return OracleType.ORDDICOM.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.ORDDOC.getName())) {
            return OracleType.ORDDOC.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.ORDIMAGE.getName())) {
            return OracleType.ORDIMAGE.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.ORDVIDEO.getName())) {
            return OracleType.ORDVIDEO.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.PLSQL_BOOLEAN.getName())) {
            return OracleType.PLSQL_BOOLEAN.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.RAW.getName())) {
            return OracleType.RAW.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.REF.getName())) {
            return OracleType.REF.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.ROWID.getName())) {
            return OracleType.ROWID.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.SDO_GEOMETRY.getName())) {
            return OracleType.SDO_GEOMETRY.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.SDO_GEORASTER.getName())) {
            return OracleType.SDO_GEORASTER.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.SDO_TOPO_GEOMETRY.getName())) {
            return OracleType.SDO_TOPO_GEOMETRY.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.SI_AVERAGE_COLOR.getName())) {
            return OracleType.SI_AVERAGE_COLOR.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.SI_COLOR.getName())) {
            return OracleType.SI_COLOR.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.SI_COLOR_HISTOGRAM.getName())) {
            return OracleType.SI_COLOR_HISTOGRAM.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.SI_FEATURE_LIST.getName())) {
            return OracleType.SI_FEATURE_LIST.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.SI_POSITIONAL_COLOR.getName())) {
            return OracleType.SI_POSITIONAL_COLOR.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.SI_STILL_IMAGE.getName())) {
            return OracleType.SI_STILL_IMAGE.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.SI_TEXTURE.getName())) {
            return OracleType.SI_TEXTURE.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.TIMESTAMP.getName())) {
            return OracleType.TIMESTAMP.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.TIMESTAMP_WITH_LOCAL_TIME_ZONE.getName())) {
            return OracleType.TIMESTAMP_WITH_LOCAL_TIME_ZONE.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.TIMESTAMP_WITH_TIME_ZONE.getName())) {
            return OracleType.TIMESTAMP_WITH_TIME_ZONE.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.UROWID.getName())) {
            return OracleType.UROWID.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.VARRAY.getName())) {
            return OracleType.VARRAY.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.XDBURITYPE.getName())) {
            return OracleType.XDBURITYPE.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, OracleType.XMLTYPE.getName())) {
            return OracleType.XMLTYPE.getVendorTypeNumber();
        } else if (StrUtil.equalsIgnoreCase(typeName, "integer")) {
            return Types.INTEGER;
        }

        return Types.OTHER;
    }

    /**
     * PostgreSQL字段类型 对应 JdbcType
     *
     * @param fullTypeName
     * @return
     */
    public static int getPostgreSqlTypeByName(String fullTypeName) {
        return getSqlTypeByName(fullTypeName, _PGSQL_SQL_TYPE);
    }

    private static int getSqlTypeByName(String fullTypeName, Map<String, Integer> sqlTypeMap) {
        String typeName = StringUtil.subStringBeforeParenthesis(fullTypeName);

        Integer sqlType = sqlTypeMap.get(typeName);
        if (sqlType == null) {
            return Types.OTHER;
        }

        return sqlType;
    }
}
