package com.sist.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sist.mrh.MainController;

@Aspect
@Component
public class MyAspect {
  @Autowired
  private MainController mc;
  @Before("execution(* com.sist.mrh.MainController.main*(..))")
  public void before()
  {
	  mc.copyFromLocal();
  }
  @After("execution(* com.sist.mrh.MainController.main*(..))")
  public void after()
  {
	  mc.copyFromLocal();
	  mc.rGraph();
  }
 }
