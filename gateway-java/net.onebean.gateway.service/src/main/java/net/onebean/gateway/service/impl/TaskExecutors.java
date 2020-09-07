package net.onebean.gateway.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池公用类
 * 
 * @author Icc
 * @since 1.0.0
 */
public class TaskExecutors {
	
	/**
	 * uag-conf项目使用
	 */
	public static final ExecutorService UAGTHREADPOOL = Executors.newFixedThreadPool(20);

}
