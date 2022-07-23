package io.github.ludongrong.common.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.StrJoiner;
import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.exception.ExcelGenerateException;
import io.github.ludongrong.common.entity.DownloadBo;
import io.github.ludongrong.common.service.DownloadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Service("downloadService")
public class DownloadServiceImpl implements DownloadService {

    @Value("<#noparse>${config.sys.staticDownHome}</#noparse>")
    private String staticDownHome;

    private String tagExtension = ".tag";
    private String tmpExtension = ".tmp";
    private String failExtension = ".fail";

    @PostConstruct
    public void init() {
        File downHome = new File(staticDownHome);
        if (downHome.exists() == false) {
            downHome.mkdirs();
        }
    }

    @Override
    public File really(String fileName) {
        File tagFile = new File(staticDownHome, fileName + tagExtension);
        newFile(tagFile);

        File tmpFile = new File(staticDownHome, fileName + tmpExtension);
        return tmpFile;
    }

    private void newFile(File file) {
        try {
            if (file.exists() == false) {
                file.createNewFile();
            }
        } catch (IOException ex) {
            throw new ExcelGenerateException("File ‘" + file.getName() + "’ creation failed", ex);
        }
    }

    @Override
    public File reallyExcel(String fileName) {
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        String exportFileName = StrJoiner.of(null, null, null).append(fileName)
                .append("_")
                .append(yyyyMMdd.format(new Date()))
                .append("-")
                .append(IdUtil.randomUUID())
                .append(".xlsx").toString();
        return really(exportFileName);
    }

    @Override
    public String ok(File tmpFile, boolean writeFlag) {
        String fileName = FileUtil.mainName(tmpFile.getName());

        File outFile = new File(staticDownHome, fileName);

        if (writeFlag) {
            tmpFile.renameTo(outFile);
        } else {
            File failFile = new File(staticDownHome, fileName + failExtension);
            if (tmpFile.exists()) {
                tmpFile.renameTo(failFile);
            } else {
                newFile(failFile);
            }
        }

        return fileName;
    }

    @Override
    public DownloadBo judgeFile(String fileName) {
        File downFile = new File(staticDownHome, fileName);
        if (downFile.exists()) {
            return new DownloadBo(200, "文件生成成功");
        }

        File tmpFile = new File(staticDownHome, fileName + tmpExtension);
        if (tmpFile.exists()) {
            return new DownloadBo(501, "文件生成中");
        }

        File failFile = new File(staticDownHome, fileName + failExtension);
        if (failFile.exists()) {
            return new DownloadBo(500, "文件生成失败");
        }

        File tagFile = new File(staticDownHome, fileName + tagExtension);
        if (tagFile.exists()) {
            return new DownloadBo(501, "文件生成中");
        }

        return new DownloadBo(500, "没有文件");
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void delHistoryFile() {
        log.info("==delHistoryFile start ===");
        File staticDownFile = new File(staticDownHome);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        Date time = calendar.getTime();

        File[] files = staticDownFile.listFiles((f) -> {
            long l = f.lastModified();
            Date d = new Date();
            d.setTime(l);
            if (f.isFile() && d.before(time)) {
                return true;
            } else {
                return false;
            }
        });

        log.info("files size:" + files.length);
        if (null != files && files.length > 0) {
            Arrays.asList(files).forEach(x -> {
                x.delete();
            });
        }
        log.info("==delHistoryFile end ===");
    }

}
