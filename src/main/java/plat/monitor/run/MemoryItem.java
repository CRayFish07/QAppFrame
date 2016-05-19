package plat.monitor.run;

import java.lang.management.MemoryUsage;

public class MemoryItem
{
	private String IP;
	private String type;
	private String name;
	
	private MemoryUsage collection;
	private MemoryUsage peak;
	private MemoryUsage now;
	
	private String date;
	
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public MemoryUsage getCollection() {
		return collection;
	}
	public void setCollection(MemoryUsage collection) {
		this.collection = collection;
	}
	public MemoryUsage getPeak() {
		return peak;
	}
	public void setPeak(MemoryUsage peak) {
		this.peak = peak;
	}
	public MemoryUsage getNow() {
		return now;
	}
	public void setNow(MemoryUsage now) {
		this.now = now;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
