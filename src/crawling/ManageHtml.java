package crawling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class ManageHtml {
	
	public String extractClassData(String Filename,String elem)throws IOException{
		
		File input = new File(Filename);
		Document doc = Jsoup.parse(input, "UTF-8", "http://www.studentadvisor.com/reviews/university-of-california-los-angeles-15838");
		
		Elements elementsByTag = doc.getElementsByClass("member-answer");
		Elements e2 = doc.getElementsByTag("div");
		
		for (Element ele : e2){
			Elements e2t = ele.getAllElements();
			
		}
		
		for (Element ele : elementsByTag) {
			
			if(ele.text().contains(elem)){
				return ele.text();
			}
		}
		
		return "";
	}
	
}
