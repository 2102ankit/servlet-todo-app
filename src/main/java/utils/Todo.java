package utils;

import java.util.Date;

public class Todo{
	String title;
	String content;
	Date dateModified;
	boolean isCompleted = false;
	
	public Todo(String title, String content) {
		this.title = title;
		this.content = content;
		this.dateModified = new Date();
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void updateTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void updateContent(String content) {
		this.content = content;
	}
	
	public Date getDateModified() {
		return this.dateModified;
	}
	
	public void updateDateModified() {
		this.dateModified = new Date();
	}
	
	public void toggleCompleted() {
		this.isCompleted = !this.isCompleted;
	}
	
	@Override
	public String toString() {
		return "Todo {" + 
	            "\ntitle : " + title +
	            "\ncontent : " + content + 
	            "\nmodified : " + dateModified + 
	            "\ncompleted : " + (isCompleted ? "Yes" : "No");
	}
	
}