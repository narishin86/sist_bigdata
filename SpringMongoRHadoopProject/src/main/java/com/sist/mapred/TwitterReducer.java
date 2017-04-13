package com.sist.mapred;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
/*
 *    문재인 [1,1,1,1,1] => Iterable => 5
 *    안철수 [1,1,1,1,1]
 *    심상정 [1,1]
 *    홍준표 [1,1,1]
 *    유승민 [1,1]
 *    
 *    a 1
 *    a 1
 *    a 1
 *    a 1
 *    a 1   => a [1,1,1,1,1]
 *    
 *    ㅋㅋㅋ  ㅇㅇㅇ ㅎㅎㅎ 노잼 꿀잼 
 */
public class TwitterReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
    private IntWritable res=new IntWritable();

	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
		int sum=0;
		for(IntWritable i:values)
		{
			sum+=i.get();
		}
		
		res.set(sum);
		context.write(key, res);
	}
    
}






