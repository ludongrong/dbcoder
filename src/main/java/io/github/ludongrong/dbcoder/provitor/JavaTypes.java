package io.github.ludongrong.dbcoder.provitor;

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

    final static private Map<Integer, String> _SQL_TYPE_2_JAVA_TYPE = new HashMap<Integer, String>();

    static {
        _SQL_TYPE_2_JAVA_TYPE.put(Types.TINYINT, "Byte");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.SMALLINT, "Short");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.INTEGER, "Integer");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.BIGINT, "Long");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.REAL, "Float");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.FLOAT, "Double");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.DOUBLE, "Double");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.DECIMAL, "BigDecimal");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.NUMERIC, "BigDecimal");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.BIT, "Boolean");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.BOOLEAN, "Boolean");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.CHAR, "String");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.VARCHAR, "String");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.LONGVARCHAR, "String");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.BINARY, "byte[]");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.VARBINARY, "byte[]");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.LONGVARBINARY, "byte[]");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.DATE, "Date");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.TIME, "Time");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.TIMESTAMP, "Timestamp");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.CLOB, "Clob");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.BLOB, "Blob");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.ARRAY, "Array");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.REF, "Ref");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.STRUCT, "Object");
        _SQL_TYPE_2_JAVA_TYPE.put(Types.JAVA_OBJECT, "Object");
    }

    final static private Map<String, Integer> _MSSQL_SQL_TYPE = new HashMap<String, Integer>();

    static {
        _MSSQL_SQL_TYPE.put("ARRAY", java.sql.Types.ARRAY);
        _MSSQL_SQL_TYPE.put("BIGINT", java.sql.Types.BIGINT);
        _MSSQL_SQL_TYPE.put("BINARY", java.sql.Types.BINARY);
        _MSSQL_SQL_TYPE.put("BIT", java.sql.Types.BIT);
        _MSSQL_SQL_TYPE.put("BLOB", java.sql.Types.BLOB);
        _MSSQL_SQL_TYPE.put("BOOLEAN", java.sql.Types.BOOLEAN);
        _MSSQL_SQL_TYPE.put("CHAR", java.sql.Types.CHAR);
        _MSSQL_SQL_TYPE.put("CLOB", java.sql.Types.CLOB);
        _MSSQL_SQL_TYPE.put("DATALINK", java.sql.Types.DATALINK);
        _MSSQL_SQL_TYPE.put("DATE", java.sql.Types.DATE);
        _MSSQL_SQL_TYPE.put("DECIMAL", java.sql.Types.DECIMAL);
        _MSSQL_SQL_TYPE.put("DISTINCT", java.sql.Types.DISTINCT);
        _MSSQL_SQL_TYPE.put("DOUBLE", java.sql.Types.DOUBLE);
        _MSSQL_SQL_TYPE.put("FLOAT", java.sql.Types.FLOAT);
        _MSSQL_SQL_TYPE.put("INTEGER", java.sql.Types.INTEGER);
        _MSSQL_SQL_TYPE.put("JAVA_OBJECT", java.sql.Types.JAVA_OBJECT);
        _MSSQL_SQL_TYPE.put("LONGNVARCHAR", -16);
        _MSSQL_SQL_TYPE.put("LONGVARBINARY", java.sql.Types.LONGVARBINARY);
        _MSSQL_SQL_TYPE.put("LONGVARCHAR", java.sql.Types.LONGVARCHAR);
        _MSSQL_SQL_TYPE.put("NCHAR", -15);
        _MSSQL_SQL_TYPE.put("NCLOB", 2011);
        _MSSQL_SQL_TYPE.put("NULL", java.sql.Types.NULL);
        _MSSQL_SQL_TYPE.put("NUMERIC", java.sql.Types.NUMERIC);
        _MSSQL_SQL_TYPE.put("NVARCHAR", -9);
        _MSSQL_SQL_TYPE.put("OTHER", java.sql.Types.OTHER);
        _MSSQL_SQL_TYPE.put("REAL", java.sql.Types.REAL);
        _MSSQL_SQL_TYPE.put("REF", java.sql.Types.REF);
        _MSSQL_SQL_TYPE.put("ROWID", -8);
        _MSSQL_SQL_TYPE.put("SMALLINT", java.sql.Types.SMALLINT);
        _MSSQL_SQL_TYPE.put("SQLXML", 2009);
        _MSSQL_SQL_TYPE.put("STRUCT", java.sql.Types.STRUCT);
        _MSSQL_SQL_TYPE.put("TIME", java.sql.Types.TIME);
        _MSSQL_SQL_TYPE.put("TIME_WITH_TIMEZONE", 2013);
        _MSSQL_SQL_TYPE.put("TIMESTAMP", java.sql.Types.TIMESTAMP);
        _MSSQL_SQL_TYPE.put("TIMESTAMP_WITH_TIMEZONE", 2014);
        _MSSQL_SQL_TYPE.put("TINYINT", java.sql.Types.TINYINT);
        _MSSQL_SQL_TYPE.put("VARBINARY", java.sql.Types.VARBINARY);
    }

    /**
     * 数据库字段类型 对应 Java数据类型
     * @param dbType
     * @param columnType
     * @param size
     * @param decimalDigits
     * @return
     */
    public static String getPreferredJavaType(String dbType, String columnType, int size, int decimalDigits) {
        Integer sqlType = 0;

        if (dbType.equals(JdbcUtils.ORACLE)) {
            sqlType = getOracleSqlTypeByName(columnType);
        } else if (dbType.equals(JdbcUtils.SQL_SERVER)) {
            sqlType = getMssqlSqlTypeByName(columnType);
        } else if (dbType.equals(JdbcUtils.MYSQL)) {
            sqlType = getMysqlSqlTypeByName(columnType);
        } else {
            return "Object";
        }

        return getPreferredJavaType(sqlType, size, decimalDigits);
    }

    /**
     * 数据库字段类型 对应 Java数据类型
     * @param sqlType
     * @param size
     * @param decimalDigits
     * @return
     */
    public static String getPreferredJavaType(int sqlType, int size, int decimalDigits) {
        if ((sqlType == Types.DECIMAL || sqlType == Types.NUMERIC) && decimalDigits == 0) {
            if (size == 1) {
                return "Boolean";
            } else if (size < 3) {
                return "Byte";
            } else if (size < 5) {
                return "Short";
            } else if (size < 10) {
                return "Integer";
            } else if (size < 19) {
                return "Long";
            } else {
                return "BigDecimal";
            }
        }
        String result = _SQL_TYPE_2_JAVA_TYPE.get(sqlType);
        if (result == null) {
            result = "Object";
        }
        return result;
    }
    
    /**
     * MySql字段类型 对应 JdbcType
     * @param fullTypeName
     * @return
     */
    public static int getMysqlSqlTypeByName(String fullTypeName) {
        return MysqlType.getByName(fullTypeName).getJdbcType();
    }
    
    /**
     * MsSql字段类型 对应 JdbcType
     * @param fullTypeName
     * @return
     */
    public static int getMssqlSqlTypeByName(String fullTypeName) {
    	String typeName = StringUtil.subStringBeforeParenthesis(fullTypeName);
    	
        Integer sqlType = _MSSQL_SQL_TYPE.get(typeName);
        if (sqlType == null) {
        	return Types.JAVA_OBJECT;
		}
    	
    	return sqlType;
    }

    /**
     * Oracle字段类型 对应 JdbcType
     * @param fullTypeName
     * @return
     */
    public static int getOracleSqlTypeByName(String fullTypeName) {

        String typeName = StringUtil.subStringBeforeParenthesis(fullTypeName);
        
        if (StrUtil.indexOfIgnoreCase(typeName, OracleType.ANYDATA.getName()) != -1) {
            return OracleType.ANYDATA.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.ANYDATASET.getName()) != -1) {
            return OracleType.ANYDATASET.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.ANYTYPE.getName()) != -1) {
            return OracleType.ANYTYPE.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.BFILE.getName()) != -1) {
            return OracleType.BFILE.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.BINARY_DOUBLE.getName()) != -1) {
            return OracleType.BINARY_DOUBLE.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.BINARY_FLOAT.getName()) != -1) {
            return OracleType.BINARY_FLOAT.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.BLOB.getName()) != -1) {
            return OracleType.BLOB.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.CHAR.getName()) != -1) {
            return OracleType.CHAR.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.CLOB.getName()) != -1) {
            return OracleType.CLOB.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.DATE.getName()) != -1) {
            return OracleType.DATE.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.DBURITYPE.getName()) != -1) {
            return OracleType.DBURITYPE.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.FLOAT.getName()) != -1) {
            return OracleType.FLOAT.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.HTTPURITYPE.getName()) != -1) {
            return OracleType.HTTPURITYPE.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.INTERVAL_DAY_TO_SECOND.getName()) != -1) {
            return OracleType.INTERVAL_DAY_TO_SECOND.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.INTERVAL_YEAR_TO_MONTH.getName()) != -1) {
            return OracleType.INTERVAL_YEAR_TO_MONTH.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.LONG.getName()) != -1) {
            return OracleType.LONG.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.LONG_RAW.getName()) != -1) {
            return OracleType.LONG_RAW.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.NCHAR.getName()) != -1) {
            return OracleType.NCHAR.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.NCLOB.getName()) != -1) {
            return OracleType.NCLOB.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.NESTED_TABLE.getName()) != -1) {
            return OracleType.NESTED_TABLE.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.NUMBER.getName()) != -1) {
            return OracleType.NUMBER.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.NVARCHAR.getName()) != -1) {
            return OracleType.NVARCHAR.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.OBJECT.getName()) != -1) {
            return OracleType.OBJECT.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.ORDAUDIO.getName()) != -1) {
            return OracleType.ORDAUDIO.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.ORDDICOM.getName()) != -1) {
            return OracleType.ORDDICOM.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.ORDDOC.getName()) != -1) {
            return OracleType.ORDDOC.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.ORDIMAGE.getName()) != -1) {
            return OracleType.ORDIMAGE.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.ORDVIDEO.getName()) != -1) {
            return OracleType.ORDVIDEO.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.PLSQL_BOOLEAN.getName()) != -1) {
            return OracleType.PLSQL_BOOLEAN.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.RAW.getName()) != -1) {
            return OracleType.RAW.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.REF.getName()) != -1) {
            return OracleType.REF.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.ROWID.getName()) != -1) {
            return OracleType.ROWID.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.SDO_GEOMETRY.getName()) != -1) {
            return OracleType.SDO_GEOMETRY.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.SDO_GEORASTER.getName()) != -1) {
            return OracleType.SDO_GEORASTER.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.SDO_TOPO_GEOMETRY.getName()) != -1) {
            return OracleType.SDO_TOPO_GEOMETRY.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.SI_AVERAGE_COLOR.getName()) != -1) {
            return OracleType.SI_AVERAGE_COLOR.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.SI_COLOR.getName()) != -1) {
            return OracleType.SI_COLOR.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.SI_COLOR_HISTOGRAM.getName()) != -1) {
            return OracleType.SI_COLOR_HISTOGRAM.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.SI_FEATURE_LIST.getName()) != -1) {
            return OracleType.SI_FEATURE_LIST.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.SI_POSITIONAL_COLOR.getName()) != -1) {
            return OracleType.SI_POSITIONAL_COLOR.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.SI_STILL_IMAGE.getName()) != -1) {
            return OracleType.SI_STILL_IMAGE.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.SI_TEXTURE.getName()) != -1) {
            return OracleType.SI_TEXTURE.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.TIMESTAMP.getName()) != -1) {
            return OracleType.TIMESTAMP.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.TIMESTAMP_WITH_LOCAL_TIME_ZONE.getName()) != -1) {
            return OracleType.TIMESTAMP_WITH_LOCAL_TIME_ZONE.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.TIMESTAMP_WITH_TIME_ZONE.getName()) != -1) {
            return OracleType.TIMESTAMP_WITH_TIME_ZONE.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.UROWID.getName()) != -1) {
            return OracleType.UROWID.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.VARCHAR2.getName()) != -1) {
            return OracleType.VARCHAR2.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.VARRAY.getName()) != -1) {
            return OracleType.VARRAY.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.XDBURITYPE.getName()) != -1) {
            return OracleType.XDBURITYPE.getVendorTypeNumber();
        } else if (StrUtil.indexOfIgnoreCase(typeName, OracleType.XMLTYPE.getName()) != -1) {
            return OracleType.XMLTYPE.getVendorTypeNumber();
        }

        return Types.OTHER;
    }
}
