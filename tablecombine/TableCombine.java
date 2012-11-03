package homework;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class TableCombine extends Configured implements Tool {

	enum Counter
	{
		LINESKIP,    //exception counter
	}
	
	public static class Map extends Mapper<LongWritable,Text,Text,Text>
	{
		public void map(LongWritable key,Text value,Context context)throws IOException
		{
			String line = value.toString();
			
			try
			{
				String[] temp = line.split(" ");
				
				if(line.charAt(0)>='0' && line.charAt(0)<='9')   // right table
				{
					context.write(new Text(temp[0]), new Text(temp[1]));
				}else{    //left table
					context.write(new Text(temp[2]), new Text(line));
				}
			}
			catch (java.lang.ArrayIndexOutOfBoundsException | InterruptedException e)
			{
				context.getCounter(Counter.LINESKIP).increment(1);
				return;
			}
		}
	}
	
	public static class Reduce extends Reducer<Text,Text,NullWritable,Text>
	{
		
		public void reduce(Text key,Iterable<Text> values,Context context)            //some values group by one key
		{
			try
			{
			String valueString;
			String outcity = "";
			String outname = "";
			
			for(Text value : values)
			{
				valueString = value.toString();
				if(valueString.charAt(valueString.length()-1) >= '0' && valueString.charAt(valueString.length()-1) <= '9' )   //left table
				{
					outcity = valueString;
				}else{         //right table
					outname = valueString;
				}
			}
			
			if(outcity.trim().length() == 0 || outname.trim().length() == 0)
				return;
			
			String out = outcity.trim() + " " + outname.trim();
			context.write(NullWritable.get(), new Text(out));                  // transfer to one key one value
			}
			catch (java.lang.ArrayIndexOutOfBoundsException | IOException | InterruptedException e)
			{
				context.getCounter(Counter.LINESKIP).increment(1);
				return;
			}
			
		}
	}
	
	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		
		Job job = new Job(conf,"sort");
		job.setJarByClass(TableCombine.class);
		
		FileInputFormat.addInputPath(job,new Path(args[0]));
		FileOutputFormat.setOutputPath(job,new Path(args[1]));
		
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);
		
		//job.setOutputFormatClass(TextOutputFormat.class);
		//job.setInputFormatClass(TextOutputFormat.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		job.waitForCompletion(true);
		
		return job.isSuccessful()?0:1;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ToolRunner.run(new Configuration(), new TableCombine(),args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
