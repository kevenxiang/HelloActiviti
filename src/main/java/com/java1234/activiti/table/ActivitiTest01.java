package com.java1234.activiti.table;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;

public class ActivitiTest01 {
	
	/**
	 * 生成Activiti需要的25张表
	 */
	public static void testCreateTable(){
		//获取流程引擎配置
		ProcessEngineConfiguration pec = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		//配置驱动
		pec.setJdbcDriver("com.mysql.jdbc.Driver");
		//配置连接地址
		pec.setJdbcUrl("jdbc:mysql://localhost:3306/db_activiti");
		pec.setJdbcUsername("root");  //用户名
		pec.setJdbcPassword("");  //密码
		
		//配置模式  true 自动创建和更新表
		pec.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
		
		//获取流程引擎对象
		ProcessEngine pe = pec.buildProcessEngine();
		
	}
	
	/**
	 * 生成Activiti需要的25表 使用配置文件
	 */
	public static void testCreateTableWithXml() {
		 // 引擎配置
	    ProcessEngineConfiguration pec=ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
	    // 获取流程引擎对象
	    ProcessEngine processEngine=pec.buildProcessEngine();
	}
	
	public static void main(String[] args) {
//		testCreateTable();
		testCreateTableWithXml();
	}

}
