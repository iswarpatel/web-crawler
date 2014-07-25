web-crawler
===========

This is part of my big data analytics project.As part of this project, I am gathering students' reviews, comments and ratings of the schools where they have studied.
Web crawler has been implemented to Crawl a website based on a search parameter, download webpages and perform ETL ( Extraction, Transform and Store) on data.

A web crawler is a program used to search through pages on the World Wide Web for documents containing a specific word, phrase, or topic. The purpose of implementing a web crawler in this program is the same. I want the crawler to search the website for the pages on the particular university that I am looking for. I derived the source code of web crawler from: https://code.google.com/p/crawler4j/
Crawler4j is an open source Java crawler which provides a simple interface to crawl a particular website. The program takes two input parameters: the root folder and the number of crawlers which are described below:
1)	Root folder: This is where the intermediate data is stored while the website is being crawled.
2)	Number of crawlers: Crawler4j supports multiple threads for crawling. This feature increases efficiency and time.

The program has two main pieces: the “crawler” and the “crawl controller”:
1)	CrawlController: This program contains the main method. The crawl controller is responsible for setting up the configuration of the web crawler and creating an instance of a CrawlController that has configuration info. It also adds the seed to the CrawlController object to tell it where to start. When everything is setup, the CrawlController.start() method is called to start the crawling process.
Here I set the controller parameters. Following is the list of those parameters:
a)	Politeness delay: This is the delay between each request sent to the website. This is done to avoid large volumes of requests to the server, which might lead to server crash or even blocking. The ideal value is 1 second.
b)	Maximum crawl depth: The default value is -1, which denotes unlimited depth.
c)	Maximum pages to fetch: The program can be set to restrict the number of pages to be fetched. Default value is -1, which denotes unlimited pages.
d)	Resume crawling: This option is used to set your crawl to be restarted (meaning that you can resume the crawl from a previously interrupted/crashed crawl). 
e)	Seed URL: This is the URL from which the program should start crawling.
2)	Crawler: This is the program that crawl the website and fetches the data that I want. It has two main components: 
a)	shouldVisit: This function decides whether the given URL should be crawled or not. In this function I can write the conditions for that.
b)	Visit: This function is called after the content of a URL is downloaded successfully. I can easily get the url, text, links, html, and unique id of the downloaded page. 
 (The above parameter descriptions were adapted from the comments present in the program.)

Following is the detailed description of logic of the program. It starts with the execution of the shouldVisit method. This method takes URL as input. It checks whether the url starts with “"http://www.studentadvisor.com/reviews/"+university_name”. Here university_name is replaced by the actual name of the university to be searched for. 
Since the review pages on this website have this kind of URL, I filter out other URLs and crawl only these kinds of URLs. This method returns a Boolean value (True/False).If it returns False, this means that the webpage does not meet our conditions, so it should not be crawled. In this case I proceed to the next webpage.If it returns true, it means that the webpage meets all our conditions. Therefore the program crawls this page and extracts data. In this case the visit () method is called.

Following I briefly describe the logic for the visit () method. This is the method which extracts the data from the webpages. All the data to be stored in the output CSV file is stored in a list named “data”. I also keep a counter “ctr” to track the number of webpages visited. Following are the steps in that process:
1)	The first check is of the URL. The URL should contain the university name and the website domain name. If the webpage passes this validation, it scans through the page and searches for the website name. If the university name is not present, it returns to the main program and checks another webpage.
2)	After this, the parameters (like URL, time, etc.) are added. This is done if ctr=0, i.e. it is the 1st page to be visited. This data will be written in the 1st line of the CSV file. The time is extracted from the page save it in the data variable.
3)	The star rating present in the page, is extracted. This is done by counting the number of “Stars” present for each rating. Results are stored in the values variable.
4)	The project folder contains a file named Comments.txt. Comments file lists out what type of comments are to be extracted. The method ‘extractClassData’ defined in manageHtml.java file takes the html file name and the comment name as input and returns the comment as a string variable.
5)	All the values that are collected in the ‘value’ list are then written into a string. Finally the string is written into the output file.
