package edu.up.cs371.soccer_application.soccerPlayer;

/**
 * Models a soccer player, with statistics
 * 
 * @author vegdahl
 * @version 20 September 2005
 *
 */
public class SoccerPlayer {
	
	// instance variables
	private String firstName; // first name
	private String lastName; // last name
	private int uniformNum; // uniform number
	private int goalsScored; // goals scored
	private int assists; // assists
	private int shots; // shots
	private int fouls; // fouls committed
	private int saves; // saves made
	private int yellowCards; // yellow cards shown
	private int redCards; // red cards shown
	private String teamName; // name of team
	
	/**
	 * SoccerPlayer constructor.  The parameters are copied in. Remaining fields
	 * are set to zero.
	 * 
	 * @param first first name
	 * @param last last name
	 * @param uniform uniform number
	 * @param team name of team
	 */
	public SoccerPlayer(String first, String last, int uniform, String team) {
		firstName = first;
		lastName = last;
		uniformNum = uniform;
		teamName = team;
		goalsScored = 0;
		assists = 0;
		shots = 0;
		fouls = 0;
		saves = 0;
		yellowCards = 0;
		redCards = 0;
	}

	// "get methods"
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public int getUniform() {
		return uniformNum;
	}
	public int getGoals() {
		return goalsScored;
	}
	public int getAssists() {
		return assists;
	}
	public int getShots() {
		return shots;
	}
	public int getFouls() {
		return fouls;
	}
	public int getSaves() {
		return saves;
	}
	public int getYellowCards() {
		return yellowCards;
	}
	public int getRedCards() {
		return redCards;
	}
	public String getTeamName() {
		return teamName;
	}
	
	// change uniform number to new one
	public void changeUniform(int newNumber) {
		uniformNum = newNumber;
	}
	
	// "bump" methods, which increment the various stat fields
	public void bumpGoals() {
		goalsScored++;
	}
	public void bumpAssists() {
		assists++;
	}
	public void bumpShots() {
		shots++;
	}
	public void bumpFouls() {
		fouls++;
	}
	public void bumpSaves() {
		saves++;
	}
	public void bumpYellowCards() {
		yellowCards++;
	}
	public void bumpRedCards() {
		redCards++;
	}
}
