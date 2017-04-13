package com.sist.mrh;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.rosuda.REngine.Rserve.RConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.mapreduce.JobRunner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @Autowired
    private Configuration conf;
    @Autowired
    private JobRunner jr;
    
    @RequestMapping("main/main.do")
    public String main_page(Model model) throws Exception
    {
    	// 데이터수집 => 파일 copyFromLocal()
    	//copyFromLocal();
    	// 분석 => JobRunner
    	jr.call();
    	// 결과파일을 읽어온다 copyToLocal()
    	//copyToLocal();
    	// R로 전송 rGraph()
    	//rGraph();
    	return "main";
    }
    public void copyFromLocal()
    {
    	try
    	{
    		FileSystem fs=FileSystem.get(conf);
    		// hadoop fs -rmr 
    		if(fs.exists(new Path("/input")))
    		{
    			fs.delete(new Path("/input"),true);
    			// rm -rf
    		}
    		if(fs.exists(new Path("/output")))
    		{
    			fs.delete(new Path("/output"),true);
    			// rm -rf
    		}
    		
    		
    		fs.copyFromLocalFile(new Path("/home/sist/app.log"), new Path("/input/app.log"));
    		fs.close();
    		
    	}catch(Exception ex)
    	{
    		System.out.println(ex.getMessage());
    	}
    }
    public void copyToLocal()
    {
    	try
    	{
    	  FileSystem fs=FileSystem.get(conf);
    	  fs.copyToLocalFile(new Path("/output/part-r-00000"), new Path("/home/sist/part-r-00000"));
    	  //fs.delete(new Path("/output"), true);
    	  fs.close();
    	}catch(Exception ex)
    	{
    		System.out.println(ex.getMessage());
    	}
    	
    }
    // /home/sist/bigdataDev/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/SpringMongoRHadoopProject/main.jsp
    public void rGraph()
    {
    	try
    	{
    	  RConnection rc;//=new RConnection("211.238.142.20");
      	  rc=new RConnection("127.0.0.1",6311 );
      	  rc.voidEval("data<-read.table(\"/home/sist/part-r-00000\")");
      	  rc.voidEval("png(\"/home/sist/bigdataDev/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/SpringMongoRHadoopProject/main/main.png\",width=800,height=600)");
      	  rc.voidEval("par(mfrow=c(2,2))");
      	  
      	  rc.voidEval("barplot(data$V2,names.arg=data$V1,col=rainbow(5))");
      	  rc.voidEval("pie(data$V2,labels=data$V1,col=rainbow(5))");
      	  rc.voidEval("hist(data$V2)");
      	  rc.voidEval("dev.off()");
      	  rc.close();
    	}catch(Exception ex)
    	{
    		System.out.println(ex.getMessage());
    	}
    }
}






