package ${basePackage}.${projectName}.dao;

/**
 * ${className} 数据操作层
 *
 * @author <a href="mailto:736779458@qq.com">736779458@qq.com</a>
 * @since ${currentDate?string("yyyy-MM-dd")}
 */
public interface ${className}Dao {

    int[] insertBatch(final List<${className}Bo> ${CodeCamelFirstLower}BoList);

    int[] deleteBatch(final List<${className}Bo> ${CodeCamelFirstLower}BoList);

    int[] updateBatch(final List<${className}Bo> ${CodeCamelFirstLower}BoList);
}
