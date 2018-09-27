package edu.up.cs371.soccer_application;

import android.util.Log;

import edu.up.cs371.soccer_application.soccerPlayer.SoccerPlayer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * Soccer player database -- presently, all dummied up
 * 
 * @author *** put your name here ***
 * @version *** put date of completion here ***
 *
 */
public class SoccerDatabase implements SoccerDB {

    HashMap<String, SoccerPlayer> players = new HashMap<>();

    /**
     * add a player
     *
     * @see SoccerDB#addPlayer(String, String, int, String)
     */
    @Override
	public boolean addPlayer(String firstName, String lastName,
			int uniformNumber, String teamName) {
            String fullName = getKey(firstName, lastName);
            if(players.get(fullName) != null){
                return false;
            }

            SoccerPlayer newPlayer = new SoccerPlayer(firstName, lastName, uniformNumber, teamName);

            players.put(fullName, newPlayer);

            return true;
	}

    /**
     * remove a player
     *
     * @see SoccerDB#removePlayer(String, String)
     */
    @Override
    public boolean removePlayer(String firstName, String lastName) {
        String key = getKey(firstName, lastName);
        if (players.get(key) != null){
            players.remove(key);
            return true;
        }
        return false;
    }

    public String getKey(String first, String last){
        return first + " ## " + last;
    }

    /**
     * look up a player
     *
     * @see SoccerDB#getPlayer(String, String)
     */
    @Override
	public SoccerPlayer getPlayer(String firstName, String lastName) {
        String key = getKey(firstName, lastName);

        return players.get(key);
    }

    /**
     * increment a player's goals
     *
     * @see SoccerDB#bumpGoals(String, String)
     */
    @Override
    public boolean bumpGoals(String firstName, String lastName) {
        SoccerPlayer player = getPlayer(firstName, lastName);
        if(player != null) {
            player.bumpGoals();
            return true;
        }
        return false;
    }

    /**
     * increment a player's assists
     *
     * @see SoccerDB#bumpAssists(String, String)
     */
    @Override
    public boolean bumpAssists(String firstName, String lastName) {
        SoccerPlayer player = getPlayer(firstName, lastName);
        if(player != null) {
            player.bumpAssists();
            return true;
        }
        return false;
    }

    /**
     * increment a player's shots
     *
     * @see SoccerDB#bumpShots(String, String)
     */
    @Override
    public boolean bumpShots(String firstName, String lastName) {
        SoccerPlayer player = getPlayer(firstName, lastName);
        if(player != null) {
            player.bumpShots();
            return true;
        }
        return false;
    }

    /**
     * increment a player's saves
     *
     * @see SoccerDB#bumpSaves(String, String)
     */
    @Override
    public boolean bumpSaves(String firstName, String lastName) {
        SoccerPlayer player = getPlayer(firstName, lastName);
        if(player != null) {
            player.bumpSaves();
            return true;
        }
        return false;
    }

    /**
     * increment a player's fouls
     *
     * @see SoccerDB#bumpFouls(String, String)
     */
    @Override
    public boolean bumpFouls(String firstName, String lastName) {
        SoccerPlayer player = getPlayer(firstName, lastName);
        if(player != null) {
            player.bumpFouls();
            return true;
        }
        return false;
    }

    /**
     * increment a player's yellow cards
     *
     * @see SoccerDB#bumpYellowCards(String, String)
     */
    @Override
    public boolean bumpYellowCards(String firstName, String lastName) {
        SoccerPlayer player = getPlayer(firstName, lastName);
        if(player != null) {
            player.bumpYellowCards();
            return true;
        }
        return false;
    }

    /**
     * increment a player's red cards
     *
     * @see SoccerDB#bumpRedCards(String, String)
     */
    @Override
    public boolean bumpRedCards(String firstName, String lastName) {
        SoccerPlayer player = getPlayer(firstName, lastName);
        if(player != null) {
            player.bumpRedCards();
            return true;
        }
        return false;
    }

    /**
     * tells the number of players on a given team
     *
     * @see SoccerDB#numPlayers(String)
     */
    @Override
    // report number of players on a given team (or all players, if null)
	public int numPlayers(String teamName) {
        if(teamName == null){
            return players.size();
        }
        int count = 0;

        for (SoccerPlayer player: players.values()) {
            if(player.getTeamName().equals(teamName)){
                count++;
            }
        }
        return count;
	}

    /**
     * gives the nth player on a the given team
     *
     * @see SoccerDB#playerNum(int, String)
     */
	// get the nTH player
	@Override
    public SoccerPlayer playerNum(int idx, String teamName) {
	    int count = 0;
	    for(SoccerPlayer player : players.values()){
	        if (teamName == null){
                if (count == idx) return player;
	            count++;
            } else if (player.getTeamName().equals(teamName)){
                if (count == idx) return player;
	            count++;
            }
        }

        return null;
    }

    /**
     * reads database data from a file
     *
     * @see SoccerDB#readData(java.io.File)
     */
	// read data from file
    @Override
	public boolean readData(File file) {
        try {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter(Pattern.compile("[,$\\x0A]"));

            while(scanner.hasNextLine()){
                String first = scanner.next();
                String last = scanner.next();
                String team = scanner.next();
                int uniform = scanner.nextInt();
                int goals = scanner.nextInt();
                int assists = scanner.nextInt();
                int shots = scanner.nextInt();
                int saves = scanner.nextInt();
                int fouls = scanner.nextInt();
                int yellow = scanner.nextInt();
                int red = scanner.nextInt();
                scanner.nextLine();
                Log.i("Read data", first + " " + last);

                SoccerPlayer player = new SoccerPlayer(first, last, uniform, team);

                for (int i = 0; i < goals; i++){
                    player.bumpGoals();
                }
                for (int i = 0; i < assists; i++){
                    player.bumpAssists();
                }
                for (int i = 0; i < shots; i++){
                    player.bumpShots();
                }
                for (int i = 0; i < saves; i++){
                    player.bumpSaves();
                }
                for (int i = 0; i < fouls; i++){
                    player.bumpFouls();
                }
                for (int i = 0; i < yellow; i++){
                    player.bumpYellowCards();
                }
                for (int i = 0; i < red; i++){
                    player.bumpRedCards();
                }

                players.put(getKey(first, last), player);
            }

            scanner.close();
            return true;
        } catch (Exception e){
            //Do nothing
            Log.e("Error", "An expection occured: " + e.getMessage());
            return false;
        }
	}


    /**
     * write database data to a file
     *
     * @see SoccerDB#writeData(java.io.File)
     */
	// write data to file
    @Override
	public boolean writeData(File file) {
        try {
            PrintWriter newFile = new PrintWriter(file);
            for(SoccerPlayer player: players.values()) {
                String playerStats = String.format("%s,%s,%s,%d,%d,%d,%d,%d,%d,%d,%d", player.getFirstName(),
                        player.getLastName(), player.getTeamName(), player.getUniform(),
                        player.getGoals(), player.getAssists(), player.getShots(),
                        player.getSaves(), player.getFouls(), player.getYellowCards(),
                        player.getRedCards());

                newFile.println(logString(playerStats));
            }
            newFile.close();
            return true;
        } catch (Exception e){
            Log.e("Write file", e.getMessage());
            return false;
        }
	}

    /**
     * helper method that logcat-logs a string, and then returns the string.
     * @param s the string to log
     * @return the string s, unchanged
     */
    private String logString(String s) {
        //Log.i("write string", s);
        return s;
    }

    /**
     * returns the list of team names in the database
     *
     * @see edu.up.cs371.soccer_application.SoccerDB#getTeams()
     */
	// return list of teams
    @Override
	public HashSet<String> getTeams() {
        HashSet<String> result = new HashSet<>();
        for (SoccerPlayer player : players.values()){
            result.add(player.getTeamName());
        }
        return result;
	}

}
