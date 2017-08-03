package com.java1234.activiti.flow;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class StudentLeaveProcess {
	
	/**
	 * 获取默认流程引擎实例，会自动读取activiti.cfg.xml文件
	 */
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 部署流程定义
	 * @param args
	 */
	@Test
	public void deploy() {
	  Deployment deployment = processEngine.getRepositoryService()  //获取部署相关Service
		.createDeployment()   //创建部署
		.addClasspathResource("diagrams/StudentLeaveProcess.bpmn")  //加载资源文件
		.addClasspathResource("diagrams/StudentLeaveProcess.png")  //加载资源文件
		.name("学生请假流程")   //流程名称
		.deploy();   //部署
	  System.out.println("流程部署ID:"+deployment.getId());
	  System.out.println("流程部署Name:"+deployment.getName());
	}
	
	/**
	 * 启动流程实例
	 */
	@Test
	public void start(){
		ProcessInstance pi = processEngine.getRuntimeService()  //运行时Service
		.startProcessInstanceByKey("studentLeaveProcess");  //流程定义表的KEY字段值
		
		System.out.println("流程实例ID:"+pi.getId());
		System.out.println("流程定义ID:"+pi.getProcessDefinitionId());
		
 	}
	
	/**
	 * 查看任务
	 */
	@Test
	public void findTask() {
		List<Task> taskList = processEngine.getTaskService()  //任务相关Service
		   .createTaskQuery()    //创建任务查询
		   .taskAssignee("王五")  //指定某个人
		   .list();
		for(Task task:taskList) {
			System.out.println("任务ID:"+task.getId());
			System.out.println("任务名称:"+task.getName());
			System.out.println("任务创建时间:"+task.getCreateTime());
			System.out.println("任务委派人:"+task.getAssignee());
			System.out.println("流程实例ID:"+task.getProcessInstanceId());
		}
	}
	
	
	/**
	 * 完成任务
	 */
	@Test
	public void completeTask(){
		processEngine.getTaskService()  //任务相关Service
		.complete("35002");
	}
	
	/**
	 * 查询流程实例状态(正在执行 or 已经执行结束)
	 */
	@Test
	public void processState(){
		ProcessInstance pi = processEngine.getRuntimeService()  //获取运行时service
				.createProcessInstanceQuery()  //创建流程实例查询
				.processInstanceId("30001")  //用流程实例id查询
				.singleResult();
		if (pi!=null) {
			System.out.println("流程正在执行!");
		}else{
			System.out.println("流程已经执行结束！");
		}
	}
	
	/**
	 * 历史任务查询
	 */
	@Test
	public void historyTaskList() {
	 List<HistoricTaskInstance> list = processEngine.getHistoryService()  //历史相关service
		.createHistoricTaskInstanceQuery()  //创建历史任务实例查询
		.processInstanceId("30001")   //用流程实例id查询
		.finished()  //查询已经完成的任务
		.list();
	 for(HistoricTaskInstance hti:list) {
		 System.out.println("任务ID:"+hti.getId());
		 System.out.println("流程实例ID:"+hti.getProcessInstanceId());
		 System.out.println("任务名称:"+hti.getName());
		 System.out.println("办理人:"+hti.getAssignee());
		 System.out.println("开始时间:"+hti.getStartTime());
		 System.out.println("结束时间:"+hti.getEndTime());
		 System.out.println("===================");
	 }
	}
	
	
	/**
	 * 历史活动查询
	 */
	@Test
	public void historyActInstanceList() {
		List<HistoricActivityInstance> list = processEngine.getHistoryService()  //历史相关service
		.createHistoricActivityInstanceQuery()  //创建历史活动实例查询
		.processInstanceId("30001")   //执行流程实例id
		.finished()
		.list();
		for(HistoricActivityInstance hai:list) {
			 System.out.println("活动ID:"+hai.getId());
			 System.out.println("流程实例ID:"+hai.getProcessInstanceId());
			 System.out.println("活动名称:"+hai.getActivityName());
			 System.out.println("办理人:"+hai.getAssignee());
			 System.out.println("开始时间:"+hai.getStartTime());
			 System.out.println("结束时间:"+hai.getEndTime());
			 System.out.println("===================");
		 }
	}

}

































