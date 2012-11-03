package catchweb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CatchWeb {

	//抓取网页内容
	public static String getContent(String strUrl,String charset) {
		try {
			URL url = new URL(strUrl);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),charset));
			String s = "";
			StringBuffer sb = new StringBuffer("");
			while ((s = br.readLine()) != null) {
				sb.append(s + "\r\n");
			}
			br.close();
			return sb.toString();
		} catch (Exception e) {
			return "error open url:" + strUrl;
		}
	}

	//去掉<>标签，生成纯文本文件，没有去除js脚本
	public static String eraseTags(String html)
	{
		 String result = "";
		 int tag = 0;         //当tag==0时，追加当前字符到result字符串中
		 for (int i = 0; i < html.length(); i++) {
			 if (html.charAt(i) == '<') {
				tag = 1;  //从当前字符起，开始html标签，将tag置为1，不追加字符进result字符串
			}else if (html.charAt(i) == '>') {
				tag = -1;  //当前字符结束html标签，下一字符开始网页内容
			}
			 if (tag == 0) {
				result += html.charAt(i);
			}
			 if (tag == -1) {
				tag++;    //从下一次循环就恢复tag为0，开始追加字符
			}
		 }		
		return result;
	}
	
	
	//遍历去掉html标签后的文本，针对每个字符统计出现次数进行统计，并输出排名前10的字符
	public static void sort(String html)
	{
		int temp = 0;
		char tc = ' ';
		Map<String, Integer> rt = new HashMap<String, Integer>();
		int i = 0;
		for (; i < html.length(); i++) {
			tc = html.charAt(i);
			if (rt.get(String.valueOf(tc)) == null) {
				rt.put(String.valueOf(tc), 1);
			}else{
				temp = Integer.parseInt(String.valueOf(rt.get(String.valueOf(tc))));
				temp++;
				rt.put(String.valueOf(tc), temp);
			}
		}
		
		//遍历map
		Set<String> s = rt.keySet();
		Iterator<String> it = s.iterator();
		while(it.hasNext()){
			Object k = it.next();
			Object v = rt.get(k);
			System.out.println(" "+k+"-->"+v);
		}
		
		System.out.println("---------------------------------------------------");
		//使用Collections.sort根据Map的value进行排序
		List<Map.Entry<String, Integer>> sorted = new ArrayList<Map.Entry<String, Integer>>(rt.entrySet());
		Collections.sort(sorted, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> obj1, Map.Entry<String, Integer> obj2) {
                return obj2.getValue() - obj1.getValue();
            }
        });
		//输出排序后前10的map
		for (int j = 0; j<10;j++) {
	        System.out.println(sorted.get(j).getKey() + "-->" + sorted.get(j).getValue());
	    }
	}
	
	public static void main(String[] args) throws IOException {
		 String url = "http://home.baidu.com/contact/visitbaidu.html";
		 String charset = "gb2312";
		 String html = getContent(url,charset);
		 System.out.println(html);              //原版html
		 System.out.println("----------------------------------------------------------");
		 String result = eraseTags(html);
		 result.trim();
		 System.out.println(result);   //去掉所有tag的网页内容
		 System.out.println("----------------------------------------------------------");
		 sort(result);
		 
	}
}

