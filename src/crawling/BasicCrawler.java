package crawling;

import edu.uci.ics.crawler4j.crawler.Page;

import org.apache.commons.lang.StringUtils;

import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.io.File;
import org.apache.http.Header;


public class BasicCrawler extends WebCrawler {



	ArrayList<String> values = new ArrayList<>();
	int ctr = 0;
	String univ_name = "clark-university";

	/**
	 * Specifies whether the given url should be crawled or not (based on  crawling logic).
	 */
	@Override
	public boolean shouldVisit(WebURL url) {
		boolean tr = false;
		String href = url.getURL();
		URL url1;


		String s = "http://www.studentadvisor.com/reviews/"+univ_name;

		if (href.startsWith(s)){
			//System.out.println("inside shouldVisit Url="+href);

			return true;
		}

		return tr;
	}

	/**
	 * This function is called when a page is fetched and ready to be processed
	 * by your program.
	 */
	@Override
	public void visit(Page page) {
		ManageHtml hh = new ManageHtml();
		String b = "univ2.html";
		String url1 = page.getWebURL().getURL();
		String s = "http://www.studentadvisor.com/reviews/"+univ_name;
		if(!url1.contains(s)){
			return;
		}


		URL url;
		String line;
		int count;

		try {
			boolean chk = true;
			url = new URL(url1);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			BufferedWriter writer = new BufferedWriter (new FileWriter(b));
			while ((line = reader.readLine()) != null) {

				if(line.toLowerCase().contains(univ_name)){
					chk = false;
				}
			}
			if(chk){

				return;   // if webpage has no mention of the university then don't crawl that page.
			}

			if(ctr==0){

				values.add("URL");
				values.add("time");
				values.add("Overall");
				values.add("Academics");
				values.add("Campus Facilities");
				values.add("Sports");
				values.add("Student Life");
				values.add("Surrounding Area");
				values.add("Worth the Money");
				values.add("TheGood");
				values.add("TheBad");
				values.add("WouldIdoitagain");
				values.add("\n");
			}
			ctr++;

			values.add(url1);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));

			while ((line = reader.readLine()) != null) {
				if(line.contains("Posted:")&& line.contains("time-created")){

					values.add(line.substring(line.indexOf("Posted:")+8, line.lastIndexOf("<")).replaceAll(",", "-"));  // Values are added for ratings present in Ratings.txt
				}
			}

			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			while ((line = reader.readLine()) != null) {

				if(line.contains("alt=\"Star\"")){

					int count1 = StringUtils.countMatches(line, "alt=\"Star\"");

					values.add(Integer.toString(count1));  // Values are added for ratings present in Ratings.txt
				}


				writer.write(line);
				writer.newLine();
			}
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		FileInputStream fstream;
		try {
			fstream = new FileInputStream("Comments.txt"); // Comments file lists out what type of comments to be extracted.
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;


			while ((strLine = br.readLine()) != null)   {

				String data = hh.extractClassData(b,strLine);
				values.add(data.replaceAll(",", "&"));
			}
			File f = new File(b);
			boolean success = f.delete(); // Delete the temporary file so that it can be recreated in the next visit.
			String fname = univ_name+".csv";   // File name created is named as universityname.csv, for example, stanford-university.csv
			BufferedWriter out = new BufferedWriter( new FileWriter(fname));

			for(int i=0;i<values.size();i++){
				String str = "";         // Create a String with all the values to be written into the file.
				if(values.get(i)=="\n"){
					str+=values.get(i);
				}
				else
					str+=values.get(i)+",";
				out.write(str);         // Write the String to the file.

			}
			out.close();
			values.add("\n");
		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}


		System.out.println("=============");
	}
}

