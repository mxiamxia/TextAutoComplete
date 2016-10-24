package com.synonym.model;

public class Term implements Comparable<Term>{

	private String id;
	private String synonym;
	private String pos;
	private String xpath;
	private int dis;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSynonym() {
		return synonym;
	}
	public void setSynonym(String synonym) {
		this.synonym = synonym;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getXpath() {
		return xpath;
	}
	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
	
	
	public int getDis() {
		return dis;
	}
	public void setDis(int dis) {
		this.dis = dis;
	}
	@Override
	public int compareTo(Term o) {
		int d = this.dis - ((Term) o).dis;
		//do not override the same distance TERM in treemap
		if (d==0)  
		{
			d = -1;
		}
		return d ;
	}
	
}
