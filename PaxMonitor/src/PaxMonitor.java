import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PaxMonitor {
	private Timer myTimer;
	private int frequency = 1;
	 private int count = 0;
	
	private String match1 = "Sold Out";

	public static void main(String[]args)
	{
		PaxMonitor pm = new PaxMonitor();
		//pm.paxChecker();
	}
	
	public PaxMonitor(){
		myTimer = new Timer();
		myTimer.schedule(new timerTaskStuff(), 1, 60000 * frequency);
	}
	
	class timerTaskStuff extends TimerTask{
		public void run(){
			paxChecker();
		}
	}
	
	public void paxChecker(){
		Document document;
		Document document2;
		try{
			String url = "http://prime.paxsite.com/registration";
			String url2 ="http://prime.paxsite.com/";
			
			String inputLine;
			String inputLine2;
			
			document = Jsoup.connect(url).get();
			document2 = Jsoup.connect(url2).get();
			
			Element badges = document.getElementById("badges");
			inputLine = badges.text();
	
			Elements registration = document2.select("div.left.registration");
			inputLine2 = registration.text();
			
			System.out.println("This is check number: "+count);
			count++;
			System.out.println(inputLine);
			System.out.println(inputLine2);
			
			paxVerify(inputLine, inputLine2);
		}
		catch (IOException e){
			//sendEmail("Site down", "Keep your eyes on me");
			e.printStackTrace();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void paxVerify(String inputLine, String inputLine2){
		boolean sendStuff = false;
		if(inputLine.equals(match1) == false)
		{
			match1 = inputLine;
			sendStuff = true;
			System.out.println(match1);
		}
		/*if(inputLine2.equals(match2) == false);
		{
			match2 = inputLine2;
			sendStuff = true;
			System.out.println("--");
			System.out.println(inputLine2);
			System.out.println(match2);
		}*/
		if(sendStuff)
		{
			sendEmail(inputLine, inputLine2);
		}
	}
	
	public void sendEmail(String inputLine, String inputLine2){
		System.out.println("I sent an email!");
		MyEmail myEmailObject = new MyEmail();
		try {
			myEmailObject.email(inputLine, inputLine2);
		} catch (AddressException e) {
			System.out.println("Address failed");
			e.printStackTrace();
		} catch (MessagingException e) {
			System.out.println("Message failed");
			e.printStackTrace();
		}
	}
}
