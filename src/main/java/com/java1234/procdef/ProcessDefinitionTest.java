package com.java1234.procdef;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.junit.Test;


public class ProcessDefinitionTest {
	
	//myFitstProcess
	
	/**
	 * 获取默认流程引擎实例，会自动读取activiti.cfg.xml文件
	 */
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 查询流程定义 返回流程定义集合  对应表 act_re_procdef
	 */
	@Test
	public void list(){
		String processDefinitionKey = "myFitstProcess";
		List<ProcessDefinition> pdList = processEngine.getRepositoryService()  //获取service
				.createProcessDefinitionQuery()   //创建流程定义查询
				.processDefinitionKey(processDefinitionKey)  //通过key查询
				.list();   //返回一个集合
		for(ProcessDefinition pd: pdList) {
			System.out.println("ID_"+pd.getId());
			System.out.println("NAME_"+pd.getName());
			System.out.println("KEY_"+pd.getKey());
			System.out.println("VERSION_"+pd.getVersion());
			System.out.println("===============");
		}
	}
	
	
//	myFitstProcess:2:7504
	
	/**
	 * 通过ID查询某个流程定义
	 */
	@Test
	public void getById() {
		String processDefinitionId = "myFitstProcess:2:7504";
		ProcessDefinition pd = processEngine.getRepositoryService()  //获取service
				.createProcessDefinitionQuery()  //创建流程定义查询
				.processDefinitionId(processDefinitionId)   //通过id查询
				.singleResult();
		System.out.println("ID_"+pd.getId());
		System.out.println("NAME_"+pd.getName());
		System.out.println("KEY_"+pd.getKey());
		System.out.println("VERSION_"+pd.getVersion());
		
	}
	
	
  //	/Users/xiang/Desktop/helloWorld.png
	/**
	 * 根据流程部署id和资源文件名称来查询流程图片
	 * @throws Exception
	 */
	@Test
	public void getImageById() throws Exception {
		InputStream inputStream = processEngine.getRepositoryService()  //获取service
				.getResourceAsStream("10001", "diagrams/HelloWorld.png");
		FileUtils.copyInputStreamToFile(inputStream, new File("/Users/xiang/Desktop/helloWorld.png"));
	}
	
	/**
	 * 查询最新版本的流程定义
	 * @throws Exception
	 */
	@Test
	public void listLastVersion() throws Exception {
		List<ProcessDefinition> listAll = processEngine.getRepositoryService()   //获取service
				.createProcessDefinitionQuery()   //创建流程定义查询
				.orderByProcessDefinitionVersion().asc()   //根据流程定义版本升序
				.list();  //返回一个集合
		
		// 定义有序Map，相同的Key，假如添加map的值  后者的值会覆盖前面相同的key的值
		Map<String, ProcessDefinition> map = new LinkedHashMap<String, ProcessDefinition>();
		// 遍历集合，根据key来覆盖前面的值，来保证最新的key覆盖前面所有老的key的值
		for(ProcessDefinition pd:listAll) {
			map.put(pd.getKey(), pd);
		}
		
		List<ProcessDefinition> pdList = new LinkedList<ProcessDefinition>(map.values());
		
		for(ProcessDefinition pd:pdList) {
			System.out.println("ID_"+pd.getId());
			System.out.println("NAME_"+pd.getName());
			System.out.println("KEY_"+pd.getKey());
			System.out.println("VERSION_"+pd.getVersion());
			System.out.println("=========");
		}
		
	}
	
	/**
	 * 删除所有key相同的流程定义
	 * @throws Exception
	 */
	@Test
	public void deleteByKey()throws Exception{
		String processDefinitionKey="helloWorld2";
		List<ProcessDefinition> pdList=processEngine.getRepositoryService() // 获取service
				.createProcessDefinitionQuery() // 创建流程定义查询
				.processDefinitionKey(processDefinitionKey) // 根据key查询
				.list();  // 返回一个集合
		for(ProcessDefinition pd:pdList){
			processEngine.getRepositoryService()
				.deleteDeployment(pd.getDeploymentId(),true);
		}
	}

}
































