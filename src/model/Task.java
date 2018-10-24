package model;

public class Task {

	public int id;
	public int typeId;
	public String title;
	public String detail;
	public int todayTime;
	public int allTime;
	public int isToday;
	
	
	
	

	public Task(int id, int typeId, String title, String detail, int todayTime,
			int allTime,int isToday) {
		super();
		this.id = id;
		this.typeId = typeId;
		this.title = title;
		this.detail = detail;
		this.todayTime = todayTime;
		this.allTime = allTime;
		this.isToday=isToday;
	}
	
	public Task(int typeId, String title, String detail, int todayTime,
			int allTime,int isToday) {
		super();
		this.typeId = typeId;
		this.title = title;
		this.detail = detail;
		this.todayTime = todayTime;
		this.allTime = allTime;
		this.isToday=isToday;
	}
//	public Task(int id, String title, String detail, int todayTime, int allTime) {
//		super();
//		this.id = id;
//		this.title = title;
//		this.detail = detail;
//		this.todayTime = todayTime;
//		this.allTime = allTime;
//	}

	
	
//	public Task(String title, String detail, int todayTime, int allTime) {
//		super();
//		this.title = title;
//		this.detail = detail;
//		this.todayTime = todayTime;
//		this.allTime = allTime;
//	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public int getTodayTime() {
		return todayTime;
	}
	public void setTodayTime(int todayTime) {
		this.todayTime = todayTime;
	}
	public int getAllTime() {
		return allTime;
	}
	public void setAllTime(int allTime) {
		this.allTime = allTime;
	}
	
	
	
	
}
