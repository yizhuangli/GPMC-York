package model;

import java.util.ArrayList;

public class PagesCollection {
	
	private ArrayList<Page> pages = new ArrayList<Page>();
	
	public PagesCollection(ArrayList<Page> pages) {
		this.pages = pages;
	}
	public PagesCollection() {
		
	}
	public void addPage(Page p){
		pages.add(p);
	}
	
	public void removePage(String pageNum){
		int num = Integer.parseInt(pageNum)-1;
		pages.remove(num);
	}

	public ArrayList<Page> getPages() {
		return pages;
	}

	public void setPages(ArrayList<Page> pages) {
		this.pages = pages;
	}

	
}
