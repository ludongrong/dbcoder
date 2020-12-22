package io.github.ludongrong.dbcoder.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.map.MapUtil;
import io.github.ludongrong.dbcoder.dao.IPdFileDao;
import io.github.ludongrong.dbcoder.entity.PdFileBo;
import io.github.ludongrong.dbcoder.service.IPdFileService;
import io.github.ludongrong.dbcoder.sql.ConditionGroup;
import io.github.ludongrong.dbcoder.sql.DynamicCondition;

@Service("dbcoder-PdFileServiceImpl")
public class PdFileServiceImpl implements IPdFileService {

    static final public DynamicCondition _DEFAULT_CONDITION = new DynamicCondition(ConditionGroup.AND);
    static {
        _DEFAULT_CONDITION.eq(PdFileBo._ID, "id");
        _DEFAULT_CONDITION.eq(PdFileBo._CREATE_TIME, "createTime");
        _DEFAULT_CONDITION.eq(PdFileBo._BASE_PACKAGE, "basePackage");
        _DEFAULT_CONDITION.eq(PdFileBo._PROJECT_NAME, "projectName");
        _DEFAULT_CONDITION.eq(PdFileBo._DB_TYPE, "dbType");
        _DEFAULT_CONDITION.eq(PdFileBo._NAME, "name");
        _DEFAULT_CONDITION.eq(PdFileBo._GENERATE_PATH, "generatePath");
        _DEFAULT_CONDITION.eq(PdFileBo._TEMPLATE_PATH, "templatePath");
    }

    private IPdFileDao pdFileDao;

    @Autowired(required = true)
    public void setBaseDao(@Qualifier("dbcoder-PdFileDaoImpl") IPdFileDao baseDao) {
        pdFileDao = baseDao;
    }

    @Transactional
    @Override
    public boolean create(PdFileBo pdFile) {

        int result = pdFileDao.create(pdFile);
        if (result != 1 && result != -2) {
            return false;
        }

        return true;
    }

    @Transactional
    @Override
    public int delete(DynamicCondition dyc, Map<String, Object> param) {

        int result = pdFileDao.delete(dyc, param);
        if (result < 0) {
            return 0;
        }
        return result;
    }

    @Transactional
    @Override
    public int delete(Map<String, Object> param) {

        return delete(_DEFAULT_CONDITION, param);
    }

    @Transactional
    @Override
    public int delete(String id) {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        return delete(_DEFAULT_CONDITION, param);
    }

    @Transactional
    @Override
    public int update(Map<String, Object> input, DynamicCondition dyc, Map<String, Object> param) {
        if (MapUtil.isEmpty(input)) {
            return 0;
        }

        int result = pdFileDao.update(input, dyc, param);
        if (result < 0) {
            return 0;
        }
        return result;
    }

    @Transactional
    @Override
    public int update(Map<String, Object> input, Map<String, Object> param) {

        return update(input, _DEFAULT_CONDITION, param);
    }

    @Transactional
    @Override
    public int update(PdFileBo pdFile, String id) {
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("_CREATE_TIME", pdFile.getCreateTime());
        input.put("_BASE_PACKAGE", pdFile.getBasePackage());
        input.put("_PROJECT_NAME", pdFile.getProjectName());
        input.put("_DB_TYPE", pdFile.getDbType());
        input.put("_NAME", pdFile.getName());
        input.put("_GENERATE_PATH", pdFile.getGeneratePath());
        input.put("_TEMPLATE_PATH", pdFile.getTemplatePath());

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);

        return update(input, _DEFAULT_CONDITION, param);
    }

    @Override
    public long count(DynamicCondition dyc, Map<String, Object> param) {

        return pdFileDao.count(dyc, param);
    }

    @Override
    public long count(Map<String, Object> param) {

        return pdFileDao.count(_DEFAULT_CONDITION, param);
    }

    @Override
    public List<PdFileBo> list(DynamicCondition dyc, Map<String, Object> param) {

        return pdFileDao.query(dyc, param);
    }

    @Override
    public List<PdFileBo> list(Map<String, Object> param) {

        return pdFileDao.query(_DEFAULT_CONDITION, param);
    }

    @Override
    public List<PdFileBo> list(int offset, int limit, DynamicCondition dyc, Map<String, Object> param) {

        return pdFileDao.query(offset, limit, dyc, param);
    }

    @Override
    public List<PdFileBo> list(int offset, int limit, Map<String, Object> param) {

        return pdFileDao.query(offset, limit, _DEFAULT_CONDITION, param);
    }

    @Override
    public PdFileBo get(String id) {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);

        List<PdFileBo> pdFileList = pdFileDao.query(_DEFAULT_CONDITION, param);
        if (pdFileList.size() > 0) {
            return pdFileList.get(0);
        }
        return null;
    }
}
