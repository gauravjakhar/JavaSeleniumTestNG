package com.TestAutomation.Framework.Initialization;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class WebDriverInitialize {
	
	WebDriverInitialize _webDriverInitialize;
	
	public WebDriver InitiateDriver(){
		String [] xmldata = _webDriverInitialize.ReadXML();
		String url = xmldata[0];
		String browser = xmldata[1];
		if(browser.equals("chrome"))
		{
		System.setProperty("webdriver."+browser+".driver", "../DataFiles/"+browser+"driver");
		WebDriver driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
		return driver;
		}
		else
			if(browser.equals("firefox"))
			{
			WebDriver driver = new FirefoxDriver();
			driver.get(url);
			driver.manage().window().maximize();
			return driver;
			}
		else
		{
			return null;
		}

	}
	private String[] ReadXML(){
		try
		{
		String [] xmldata = new String[2];
		File fXmlFile = new File("../DataFiles/Setting.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("name");
		
		for (int temp = 0; temp < nList.getLength(); temp++)
		{
			Node nNode = nList.item(temp);					
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				String active = eElement.getElementsByTagName("active").item(0).getTextContent();
				if(active.trim().equals("1"))
				{
					String url = eElement.getElementsByTagName("url").item(0).getTextContent();
					String browser = eElement.getElementsByTagName("browser").item(0).getTextContent();
					xmldata[0] = url;
					xmldata[1] = browser;
					break;
				}
			}
		}
		return xmldata;
		}
		catch(Exception e)
		{e.printStackTrace();			
			return null;
		}
	}

}
