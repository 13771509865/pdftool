package com.neo.task;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.neo.commons.cons.RedisConsts;
import com.neo.commons.cons.SysConstant;
import com.neo.commons.util.SysLog4JUtils;
import com.neo.config.SysConfig;
import com.neo.service.cache.CacheManager;
import com.neo.service.cache.CacheService;
import com.neo.service.file.FileService;

/*
 * spring.xml 配置的 任务调度类
 */
/**
 * 定期清理文件
 * @author zhouf
 * @create 2018-12-10 21:20
 */
@Component
public class ClearFileTask {
	@Autowired
	private FileService fileService;
	
	@Autowired
	private SysConfig config;
	
	@Autowired
	private CacheService<String> cacheService;
	
	private static final int PERIOD_DAY  = 24*3600*1000;
	
	private final String fileInfoKey = RedisConsts.FileInfoKey;
	
	private final String htmlFolder = SysConstant.FILENAME+".files";
	
	private void clearFile(String path,final Long clearTime){
		final Path clearPath = Paths.get(path);
		final Long currentTime = System.currentTimeMillis();
		SysLog4JUtils.info("=======================================开始清理文件=======================================");
		try {
			Files.walkFileTree(clearPath
					, new SimpleFileVisitor<Path>()
				{
				// 访问文件失败
					@Override
					public FileVisitResult visitFileFailed(Path file,
							IOException exc) throws IOException {
						return FileVisitResult.CONTINUE;
					}
					// 访问文件时候触发该方法
					@Override
					public FileVisitResult visitFile(Path file
						, BasicFileAttributes attrs) throws IOException
					{
						if (currentTime - clearTime > file.toFile().lastModified()) {
							fileService.deleteDir(file.toFile());
							File parentFile = file.toFile().getParentFile();
							String hashKey = parentFile.getName();
							if(parentFile.isDirectory() && !htmlFolder.equals(hashKey)) {   //删除文件的同时删除redis中记录
								CacheManager<String> cacheManager = cacheService.getCacheManager();
								cacheManager.deleteHashKey(fileInfoKey, hashKey);
							}
						}
						return FileVisitResult.CONTINUE;
					}
					// 开始访问目录时触发该方法
					@Override
					public FileVisitResult preVisitDirectory(Path dir
						, BasicFileAttributes attrs) throws IOException
					{
						if(dir.toFile().list().length==0){
							if(dir == clearPath){
								return FileVisitResult.CONTINUE;
							}
							dir.toFile().delete();
							return FileVisitResult.SKIP_SUBTREE;
						}else{
							return FileVisitResult.CONTINUE;
						}
					}
				});
		} catch (Exception e) {
			SysLog4JUtils.info("清理过期文件线程异常");
		}
	}
	
	//TODO 删除生成文件的时候,redis记录要不要删除
	public void clearDestFile(){
		String destPath = config.getOutputDir();
		Integer clearDay = config.getClearDay();
		Long clearTime =(long) (clearDay*PERIOD_DAY);
		clearFile(destPath,clearTime);
	}
	
	public void clearSrcFile(){
		String srcPath = config.getInputDir();
		Integer clearDay = config.getClearDay();
		Long clearTime =(long) (clearDay*PERIOD_DAY);
		clearFile(srcPath,clearTime);
	}
}