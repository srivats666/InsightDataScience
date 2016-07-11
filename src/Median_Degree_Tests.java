import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class Median_Degree_Tests {

	class Info 
	{
	    private String target;
	    private String actor; 
	    private String timeStamp;
	    
	    public Info(String target, String actor, String time)
		{
			this.target = target;
			this.actor = actor;
			this.timeStamp = time;
		}
	}
	
	private List<Info> baseTestData()
	{
		List<Info> list = new ArrayList<Info>();
		
		String target = "Jamie-Korn";
	    String actor = "Jordan-Gruber";
	    String timeStamp = "2016-04-07T03:33:19Z";
	    list.add(new Info(target, actor, timeStamp));
	    
	    target = "Jamie-Korn";
	    actor = "Maryann-Berry";
	    timeStamp = "2016-04-07T03:33:19Z";
	    list.add(new Info(target, actor, timeStamp));
	    
	    target = "Maryann-Berry";
	    actor = "Ying-Mo";
	    timeStamp = "2016-04-07T03:33:19Z";
	    list.add(new Info(target, actor, timeStamp));
	    
	    target = "Ying-Mo";
	    actor = "Jamie-Korn";
	    timeStamp = "2016-04-07T03:34:18Z";
	    list.add(new Info(target, actor, timeStamp));
	    
	    target = "Maddie-Franklin";
	    actor = "Maryann-Berry";
	    timeStamp = "2016-04-07T03:34:58Z";
	    list.add(new Info(target, actor, timeStamp));
	    
	    target = "Ying-Mo";
	    actor = "Maryann-Berry";
	    timeStamp = "2016-04-07T03:34:00Z";
	    list.add(new Info(target, actor, timeStamp));
	    
	    target = "Rebecca-Waychunas";
	    actor = "Natalie-Piserchio";
	    timeStamp = "2016-04-07T03:31:18Z";
	    list.add(new Info(target, actor, timeStamp));
	    
	    target = "Connor-Liebman";
	    actor = "Nick-Shirreffs";
	    timeStamp = "2016-04-07T03:35:02Z";
	    list.add(new Info(target, actor, timeStamp));
	    
	   /* Graph at the end
	      {"created_time": "2016-04-07T03:34:18Z", "target": "Ying-Mo", "actor": "Jamie-Korn"}
		  {"created_time": "2016-04-07T03:34:58Z", "target": "Maddie-Franklin", "actor": "Maryann-Berry"}
		  {"created_time": "2016-04-07T03:35:02Z", "target": "Connor-Liebman", "actor": "Nick-Shirreffs"}
       */
	    
		return list;
	}
	
	
	@Test
	public void testBaseData() throws Exception
	{
		Median_Degree m = new Median_Degree();
	    List<Info> l = baseTestData();
		double[] res = new double[] {1.00, 1.00, 1.50, 2.00, 1.00, 1.50, 1.50, 1.00};
	    
		for(int i=0; i<l.size(); i++)
		{
			m.generateMedian(l.get(i).target, l.get(i).actor, l.get(i).timeStamp, true);
			assertEquals(res[i], m.getMedian(), 0);
		}
	}
	
	@Test
	public void testBeforeWindow() throws Exception
	{
		Median_Degree m = new Median_Degree();
	    List<Info> l = baseTestData();
	    
	    String target = "Esra";
	    String actor = "Srivats";
	    String timeStamp = "2016-04-07T03:34:02Z";
	    l.add(new Info(target, actor, timeStamp));
	    
		double[] res = new double[] {1.00, 1.00, 1.50, 2.00, 1.00, 1.50, 1.50, 1.00, 1.00};
	    
		for(int i=0; i<l.size(); i++)
		{
			m.generateMedian(l.get(i).target, l.get(i).actor, l.get(i).timeStamp, true);
			assertEquals(res[i], m.getMedian(), 0);
		}
	}
	
	@Test
	public void testNewUsersAfterWindow() throws Exception
	{
		Median_Degree m = new Median_Degree();
	    List<Info> l = baseTestData();
	    
	    String target = "Esra";
	    String actor = "Srivats";
	    String timeStamp = "2016-04-07T03:35:12Z";
	    l.add(new Info(target, actor, timeStamp));
	    
		double[] res = new double[] {1.00, 1.00, 1.50, 2.00, 1.00, 1.50, 1.50, 1.00, 1.00};
	    
		for(int i=0; i<l.size(); i++)
		{
			m.generateMedian(l.get(i).target, l.get(i).actor, l.get(i).timeStamp, true);
			assertEquals(res[i], m.getMedian(), 0);
		}
	}
	
	@Test
	public void testNewPaymentAfterWindow() throws Exception
	{
		Median_Degree m = new Median_Degree();
	    List<Info> l = baseTestData();
	    
	    String target = "Maddie-Franklin";
	    String actor = "Nick-Shirreffs";
	    String timeStamp = "2016-04-07T03:35:12Z";
	    l.add(new Info(target, actor, timeStamp));

	    target = "Ying-Mo";
	    actor = "Maryann-Berry";
	    timeStamp = "2016-04-07T03:35:18Z";
	    l.add(new Info(target, actor, timeStamp));
	    
		double[] res = new double[] {1.00, 1.00, 1.50, 2.00, 1.00, 1.50, 1.50, 1.00, 1.00, 2.00};
	    
		for(int i=0; i<l.size(); i++)
		{
			m.generateMedian(l.get(i).target, l.get(i).actor, l.get(i).timeStamp, true);
			assertEquals(res[i], m.getMedian(), 0);
		}
	}
	
	@Test
	public void testExistingPaymentAfterWindow() throws Exception
	{
		Median_Degree m = new Median_Degree();
	    List<Info> l = baseTestData();
	    
	    String target = "Ying-Mo";
	    String actor = "Jamie-Korn";
	    String timeStamp = "2016-04-07T03:35:12Z";
	    l.add(new Info(target, actor, timeStamp));
	    
		double[] res = new double[] {1.00, 1.00, 1.50, 2.00, 1.00, 1.50, 1.50, 1.00, 1.00};
	    
		for(int i=0; i<l.size(); i++)
		{
			m.generateMedian(l.get(i).target, l.get(i).actor, l.get(i).timeStamp, true);
			assertEquals(res[i], m.getMedian(), 0);
		}
	}
	
	@Test
	public void testOutOfOrderWithinWindow() throws Exception
	{
		Median_Degree m = new Median_Degree();
	    List<Info> l = baseTestData();
	    
	    String target = "Srivats";
	    String actor = "Esra";
	    String timeStamp = "2016-04-07T03:34:38Z";
	    l.add(new Info(target, actor, timeStamp));
	    
	    target = "Ying-Mo";
	    actor = "Maryann-Berry";
	    timeStamp = "2016-04-07T03:34:18Z";
	    l.add(new Info(target, actor, timeStamp));
	    
	    target = "Ying-Mo";
	    actor = "Nick-Shirreffs";
	    timeStamp = "2016-04-07T03:34:18Z";
	    l.add(new Info(target, actor, timeStamp));
	    
	    target = "Ying-Mo";
	    actor = "Jamie-Korn";
	    timeStamp = "2016-04-07T03:34:28Z";
	    l.add(new Info(target, actor, timeStamp));
	    	    
		double[] res = new double[] {1.00, 1.00, 1.50, 2.00, 1.00, 1.50, 1.50, 1.00, 1.00, 1.00, 1.00, 1.00};
	    
		for(int i=0; i<l.size(); i++)
		{
			m.generateMedian(l.get(i).target, l.get(i).actor, l.get(i).timeStamp, true);
			assertEquals(res[i], m.getMedian(), 0);
		}
	}
	
	@Test
	public void testRemoveOldEntries() throws Exception
	{
		Median_Degree m = new Median_Degree();
	    List<Info> l = baseTestData();
	    
	    String target = "Maddie-Franklin";
	    String actor = "Nick-Shirreffs";
	    String timeStamp = "2016-04-07T03:35:12Z";
	    l.add(new Info(target, actor, timeStamp));

	    target = "Ying-Mo";
	    actor = "Maryann-Berry";
	    timeStamp = "2016-04-07T03:35:18Z";
	    l.add(new Info(target, actor, timeStamp));
	    
	    target = "Ying-Mo";
	    actor = "Maryann-Berry";
	    timeStamp = "2016-04-07T03:37:18Z";
	    l.add(new Info(target, actor, timeStamp));
	    
		double[] res = new double[] {1.00, 1.00, 1.50, 2.00, 1.00, 1.50, 1.50, 1.00, 1.00, 2.00, 1.00};
	    
		for(int i=0; i<l.size(); i++)
		{
			m.generateMedian(l.get(i).target, l.get(i).actor, l.get(i).timeStamp, true);
			assertEquals(res[i], m.getMedian(), 0);
		}
	}
	
	@Test
	public void testBruteTime() throws Exception
	{
		long startTime = System.currentTimeMillis();
		Median_Degree m = new Median_Degree();
		 
		m.parseInput("../venmo_input//test-all-venmo-trans/venmo-trans.txt" , "../venmo_output/outputT1.txt", false);
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
		
		startTime = System.currentTimeMillis();
		Median_Degree m1 = new Median_Degree();
		m1.parseInput("../venmo_input/test-pt1-venmo-trans/venmo-trans.txt" , "../venmo_output/outputT11.txt", false);
		endTime   = System.currentTimeMillis();
		totalTime = endTime - startTime;
		System.out.println(totalTime);
		
		startTime = System.currentTimeMillis();
		Median_Degree m2 = new Median_Degree();
		m2.parseInput("../venmo_input/test-pt2-venmo-trans/venmo-trans.txt" , "../venmo_output/outputT12.txt", false);
		endTime   = System.currentTimeMillis();
		totalTime = endTime - startTime;
		System.out.println(totalTime);
		
		startTime = System.currentTimeMillis();
		Median_Degree m3 = new Median_Degree();
		m3.parseInput("../venmo_input/test-pt3-venmo-trans/venmo-trans.txt" , "../venmo_output/outputT13.txt", false);
		endTime   = System.currentTimeMillis();
		totalTime = endTime - startTime;
		System.out.println(totalTime);
		
		System.out.println("------------------------------------------- brute");
	}
	
	@Test
	public void testEfficientTime() throws Exception
	{
		long startTime = System.currentTimeMillis();
		Median_Degree m = new Median_Degree();
		m.parseInput("../venmo_input/test-all-venmo-trans/venmo-trans.txt" , "../venmo_output/outputT2.txt", true);
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
		
		startTime = System.currentTimeMillis();
		Median_Degree m1 = new Median_Degree();
		m1.parseInput("../venmo_input/test-pt1-venmo-trans/venmo-trans.txt" , "../venmo_output//outputT21.txt", true);
		endTime   = System.currentTimeMillis();
		totalTime = endTime - startTime;
		System.out.println(totalTime);
		
		startTime = System.currentTimeMillis();
		Median_Degree m2 = new Median_Degree();
		m2.parseInput("../venmo_input/test-pt2-venmo-trans/venmo-trans.txt" , "../venmo_output/outputT22.txt", true);
		endTime   = System.currentTimeMillis();
		totalTime = endTime - startTime;
		System.out.println(totalTime);//227
		
		startTime = System.currentTimeMillis();
		Median_Degree m3 = new Median_Degree();
		m3.parseInput("../venmo_input/test-pt3-venmo-trans/venmo-trans.txt" , "../venmo_output/outputT23.txt", true);
		endTime   = System.currentTimeMillis();
		totalTime = endTime - startTime;
		System.out.println(totalTime);
		
		System.out.println("------------------------------------------- Efficient");
	}

}
