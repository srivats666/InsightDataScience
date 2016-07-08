import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;

public class Median_Degree {

	class Node
	{
		String target;
		String actor;
		Date time;
		String key;
		
		public Node(String target, String actor, Date time)
		{
			this.target = target;
			this.actor = actor;
			this.time = time;
			
			if(target.compareTo(actor) > 1)
				this.key = target + actor;
			else
				this.key = actor + target;
		}
		
		@Override
		public boolean equals(Object obj) {
			return this.key.equals(((Node)obj).key);
		}
		
		@Override
		public String toString() {
			return target + " -actor- " + actor + " " + time;
		}
	}
	
	//Date Utilities
	private Date changeTime(Date obj, int sec)
	{
		Calendar cl = Calendar.getInstance();
	    cl.setTime(obj);
	    cl.add(Calendar.SECOND, sec);
	    return cl.getTime();
	}
	
	private Date getMinTimeWindow()
	{
		return changeTime(new Date(prevTime.getTime()), -window);
	}
	
	private Date convertToDate(String created_time)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	    Date dateObject = null;

	    try 
	    {
	    	sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		    dateObject = sdf.parse(created_time);

	    } catch (ParseException e) {
	    	System.out.println( e );
	    }
	    
	    return dateObject;
	}
	
	private String convertToString(Date created_time)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    	sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    	String dateObject = sdf.format(created_time);
	    return dateObject;
	}
	//Date Utilities
	
	Map<String, Integer> map;
	Map<String, Set<Node>> timeMap;
	Map<Node, String> edgeMap;
	Date prevTime;
	double prevMedian;
	final int window = 59;
	
	@SuppressWarnings("deprecation")
	public Median_Degree()
	{
		prevTime = new Date(90, 0, 1);
		map = new HashMap<String, Integer>();
		timeMap = new HashMap<String, Set<Node>>();
		edgeMap = new HashMap<Node, String>();
	}
	
	private void updateEdgeMap(Node input, String time)
	{
		String mapTime = edgeMap.get(input);
		Date mapTimeObj = convertToDate(mapTime);
		
		//update the timeStamp of existing edge if older than current
		if(mapTimeObj.before(input.time))
		{
			Set<Node> set = timeMap.get(mapTime);
			Node n = new Node(input.target, input.actor, mapTimeObj); 
			set.remove(n);
			n.time = input.time;
			edgeMap.put(input, time);
			Set<Node> val = timeMap.get(time);
			if(val == null)
			{
				val = new HashSet<Node>();
				timeMap.put(time, val);
			}
			
			val.add(n);
			
		}
	}
	
	private void addEdgeMap(Node input, String time)
	{
		Set<Node> val = timeMap.get(time);
		if(val == null)
		{
			val = new HashSet<Node>();
			timeMap.put(time, val);
		}
		
		val.add(input);
		edgeMap.put(input, time);
		
		if(map.containsKey(input.target))
		{
			map.put(input.target, map.get(input.target) + 1);
		}
		else
		{
			map.put(input.target, 1);
		}
		
		if(map.containsKey(input.actor))
		{
			map.put(input.actor, map.get(input.actor) + 1);
		}
		else
		{
			map.put(input.actor, 1);
		}
	}
	
	private void removeOldEntries(Date currTime)
	{
		Date startPeriod = getMinTimeWindow();
		Date currMinus59 = changeTime(new Date(currTime.getTime()), -window);
		Date prevPlusOne = changeTime(new Date(prevTime.getTime()), 1);
		Date endPeriod = prevTime.before(currMinus59) ? prevPlusOne : currMinus59;
		
		while(startPeriod.before(endPeriod))
		{
			String key = convertToString(startPeriod);
			Set<Node> set = timeMap.get(key);
			
			if(set != null)
			{
				for(Node e  : set)
				{
					map.put(e.actor, map.get(e.actor) -1);
					map.put(e.target, map.get(e.target) -1);
					
					if(map.get(e.target) == 0)
						map.remove(e.target);
					
					if(map.get(e.actor) == 0)
						map.remove(e.actor);
					
					edgeMap.remove(e);
				}
				timeMap.remove(key);
			}
			startPeriod = changeTime(startPeriod, 1);
		}
	}
	
	private void calcMedian(Node input)
	{
		List<Integer> l = new ArrayList<Integer>();
		for(Entry<String, Integer> e : map.entrySet())
		{
			l.add(e.getValue());
		}
		
		Collections.sort(l);
		//System.out.println("list " + l);
		int idx = l.size();
		
		if(idx == 0)
		{
			prevMedian = 0; 
		}
		else if(idx % 2 != 0)
		{
			prevMedian = (double)l.get(idx/2);
		}
		else
		{
			prevMedian = ((double)l.get(idx/2) + l.get((idx - 1)/2))/2; 
		}
		
	}
	
	public void getMedian(String to, String from, String time)
	{
		Date timeObj = convertToDate(time);
		Date prevMinus59 = getMinTimeWindow();
		
		if(timeObj.before(prevMinus59))
		{
			return;
		}

	    Node input = new Node(to, from, timeObj);
		
		//Check if this edge already exists
		if(edgeMap.containsKey(input))
		{
			updateEdgeMap(input, time);
			if(timeObj.before(prevTime))
			{
				return;
			}
		}
		else
			addEdgeMap(input, time);
		
		if(prevTime.before(timeObj))
			removeOldEntries(timeObj);
		
		calcMedian(input);
		
		if(timeObj.after(prevTime))
			prevTime = timeObj;

	}
	
	public static void main(String[] args)
	{
		Median_Degree m = new Median_Degree();

		File file = new File(args[1]);
		if (!file.exists()) 
		{
		    try 
		    {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try (BufferedReader br = new BufferedReader(new FileReader(args[0])))
		{
			String sCurrentLine;
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);

			while ((sCurrentLine = br.readLine()) != null) {
				
				String[] parts = sCurrentLine.split(",");
				String created_time = parts[0].split(":")[1] + ":" + parts[0].split(":")[2] + ":" + parts[0].split(":")[3]; 
			    String target = parts[1].split(":")[1].replaceAll("\"", "");
			    String actor = parts[2].split(":")[1].replaceAll("\"", "").replaceAll("}", "");
				created_time = created_time.replaceAll("\"", "").replaceAll(" ", "");
				m.getMedian(target, actor, created_time);
				bw.write("" + m.prevMedian);
				bw.newLine();
			}
			
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//System.out.println("Median Finished");
	}
}
