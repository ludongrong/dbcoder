package io.github.ludongrong.common.service;

import io.github.ludongrong.common.entity.DownloadBo;

import java.io.File;

public interface DownloadService {

    File really(String fileName);

    File reallyExcel(String fileName);

    String ok(File tmpFile, boolean writeFlag);

    DownloadBo judgeFile(String fileName);

}
