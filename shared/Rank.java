package wwwordz.shared;

public class Rank {
	
	private String nick;
	private int points;
	private int accumulated;
	
	public Rank() {} 
	
	public Rank(String nick, int points, int accumulated) {
		super();
		this.nick = nick;
		this.points = points;
		this.accumulated = accumulated;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public int getAccumulated() {
		return accumulated;
	}
	
	public void setAccumulated(int accumulated) {
		this.accumulated = accumulated;
	}
		
}