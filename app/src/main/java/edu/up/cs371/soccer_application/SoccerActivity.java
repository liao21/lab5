package edu.up.cs371.soccer_application;
/**
 * The activity class for working with a soccer database.
 */

// imports
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import edu.up.cs371.soccer_application.soccerPlayer.SoccerPlayer;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;


public class SoccerActivity extends ActionBarActivity {

    // the database
    private SoccerDB database;

    // our top view
    private View topView;

    // the editable text fields
    private EditText firstNameField;
    private EditText lastNameField;
    private EditText teamNameField;
    private EditText uniformNumberField;
    private EditText fileNameField;

    // the non-editable text fields
    private EditText goalsField;
    private EditText assistsField;
    private EditText shotsField;
    private EditText savesField;
    private EditText foulsField;
    private EditText yellowsField;
    private EditText redsField;
    private EditText countField;

    // buttons
    private Button newButton;
    private Button findButton;
    private Button removeButton;
    private Button incrGoalButton;
    private Button incrAssistButton;
    private Button incrShotButton;
    private Button incrSaveButton;
    private Button incrFoulButton;
    private Button incrYellowCardButton;
    private Button incrRedCardButton;
    private Button countPlayersButton;
    private Button firstPlayerButton;
    private Button nextPlayerButton;
    private Button readButton;
    private Button writeButton;

    // spinner
    private ArrayList<String> teamVec; // array of teams
    ArrayAdapter<String> adapter; // spinner's adapter
    Spinner spinner; // spinner object

    // colors
    private int backgroundColor; // background color
    private int flashColor; // flash color

    // strings
    private String allTeamsString; // the " ### ALL ### " string

    // player currently being displayed, if any
    private SoccerPlayer currentPlayer;

    // helper variables for iterating through a sequence of players
    private String currentIteratorTeam = "    ";
    private int currentIteratorIndex = 0;

    /**
     * Activity's method for initializing itself
     *
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // perform superclass operations; create layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soccer);

        // initialize the GUI objects from resources
        topView = (View) findViewById(R.id.top_view);
        spinner = (Spinner) findViewById(R.id.team_spinner);
        firstNameField = (EditText)findViewById(R.id.first_name_field);
        lastNameField = (EditText)findViewById(R.id.last_name_field);
        teamNameField = (EditText)findViewById(R.id.team_field);
        uniformNumberField = (EditText)findViewById(R.id.uniform_num_field);
        fileNameField = (EditText)findViewById(R.id.file_name_field);
        goalsField = (EditText)findViewById(R.id.goals_field);
        assistsField = (EditText)findViewById(R.id.assists_field);
        shotsField = (EditText)findViewById(R.id.shots_field);
        savesField = (EditText)findViewById(R.id.saves_field);
        foulsField = (EditText)findViewById(R.id.fouls_field);
        yellowsField = (EditText)findViewById(R.id.yellow_cards_field);
        redsField = (EditText)findViewById(R.id.red_cards_field);
        countField = (EditText)findViewById(R.id.player_count_field);
        newButton = (Button)findViewById(R.id.new_player_button_widget);
        findButton = (Button)findViewById(R.id.find_player_button_widget);
        removeButton = (Button)findViewById(R.id.remove_player_button_widget);
        incrGoalButton = (Button)findViewById(R.id.incr_goal_button_widget);
        incrAssistButton = (Button)findViewById(R.id.incr_assist_button_widget);
        incrShotButton = (Button)findViewById(R.id.incr_shot_button_widget);
        incrSaveButton = (Button)findViewById(R.id.incr_save_button_widget);
        incrFoulButton = (Button)findViewById(R.id.incr_foul_button_widget);
        incrYellowCardButton = (Button)findViewById(R.id.incr_yellow_card_button_widget);
        incrRedCardButton = (Button)findViewById(R.id.incr_red_card_button_widget);
        countPlayersButton = (Button)findViewById(R.id.count_players_button_widget);
        firstPlayerButton = (Button)findViewById(R.id.first_player_button_widget);
        nextPlayerButton = (Button)findViewById(R.id.next_player_button_widget);
        readButton = (Button)findViewById(R.id.read_button_widget);
        writeButton = (Button)findViewById(R.id.write_button_widget);

        // initialize the colors
        backgroundColor = getResources().getColor(R.color.background_color);
        flashColor = getResources().getColor(R.color.flash_color);

        // initialize the strings
        allTeamsString = getResources().getString(R.string.all_teams);

        // Create the database object
        database = new SoccerDatabase();

        // Create an ArrayAdapter using the string array and a default spinner layout; also
        // create an ArrayList to hold its elements; connect the spinner to its adapter
        teamVec = new ArrayList<String>();
        teamVec.add(allTeamsString);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, teamVec.toArray(new String[0]));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // define listeners for all the buttons
        newButton.setOnClickListener(new NewButtonListener());
        findButton.setOnClickListener(new FindButtonListener());
        removeButton.setOnClickListener(new RemoveButtonListener());
        incrGoalButton.setOnClickListener(new PlusGoalsButtonListener());
        incrAssistButton.setOnClickListener(new PlusAssistsButtonListener());
        incrShotButton.setOnClickListener(new PlusShotsButtonListener());
        incrSaveButton.setOnClickListener(new PlusSavesButtonListener());
        incrFoulButton.setOnClickListener(new PlusFoulsButtonListener());
        incrYellowCardButton.setOnClickListener(new PlusYellowsButtonListener());
        incrRedCardButton.setOnClickListener(new PlusRedsButtonListener());
        countPlayersButton.setOnClickListener(new CountButtonListener());
        firstPlayerButton.setOnClickListener(new FirstPlayerButtonListener());
        nextPlayerButton.setOnClickListener(new NextPlayerButtonListener());
        readButton.setOnClickListener(new ReadButtonListener());
        writeButton.setOnClickListener(new WriteButtonListener());
    }

    /**
     * menu initializer
     *
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_soccer, menu);
        return true;
    }

    /**
     * update all EditTexts related to the current player with relevant informatino
     *
     * @param showAll whether to show items other than first and last name
     */
    private void updateAll(boolean showAll) {
        if (currentPlayer != null && showAll) {
            // player exists and we want to show everything: update each field
            String firstName = currentPlayer.getFirstName();
            String lastName = currentPlayer.getLastName();
            String teamName = currentPlayer.getTeamName();
            int uniformNumber = currentPlayer.getUniform();
            firstNameField.setText(firstName);
            lastNameField.setText(lastName);
            teamNameField.setText(teamName);
            uniformNumberField.setText(uniformNumber + "");
            goalsField.setText(currentPlayer.getGoals() + "");
            assistsField.setText(currentPlayer.getAssists() + "");
            shotsField.setText(currentPlayer.getShots() + "");
            savesField.setText(currentPlayer.getSaves() + "");
            foulsField.setText(currentPlayer.getFouls() + "");
            yellowsField.setText(currentPlayer.getYellowCards() + "");
            redsField.setText(currentPlayer.getRedCards() + "");
        }
        else if (currentPlayer != null) {
            // player exists, but we want to clear everything except the
            // first and last name
            teamNameField.setText("");
            uniformNumberField.setText("");
            goalsField.setText("");
            assistsField.setText("");
            shotsField.setText("");
            savesField.setText("");
            foulsField.setText("");
            yellowsField.setText("");
            redsField.setText("");
        }
        else {
            // player does not exist: clear everything
            firstNameField.setText("");
            lastNameField.setText("");
            teamNameField.setText("");
            uniformNumberField.setText("");
            goalsField.setText("");
            assistsField.setText("");
            shotsField.setText("");
            savesField.setText("");
            foulsField.setText("");
            yellowsField.setText("");
            redsField.setText("");
        }
    }

    /**
     * convert the uniform number field to an integer
     *
     * @return the converted number, or -100 if the field was not a valid integer
     */
    // converts text in "uniform number" field to integer
    private int getUniformNumber() {
        try {
            return Integer.parseInt(uniformNumberField.getText().toString().trim());
        } catch (NumberFormatException nfx) {
            return -100;
        }
    }

    /** get the name of the team selected in the menu
     *
     * @return the name of the team selected in the menu, or null if no
     * single team is selected
     */
    private String getSelectedTeamName() {
        Object obj = spinner.getSelectedItem();
        if (obj != null && obj instanceof String) {
            return obj.equals(allTeamsString) ? null : (String) obj;
        } else {
            return null;
        }
    }

    /**
     * add a team to the spinner
     *
     * @param name the name of the team to add
     */
    private void addTeam(String name) {
        // if player is already there, leave things alone
        if (teamVec.indexOf(name) >= 0) return;

        // add the name; resort
        teamVec.add(name);
        Collections.sort(teamVec);

        // create new adapter, add the updated elements and connect to the spinner
        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                        android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.addAll(teamVec);
        spinnerAdapter.notifyDataSetChanged();
    }

    /**
     * flashes the screen
     */
    private void flash() {
        // display the new background color, the "flash" color
        topView.setBackgroundColor(flashColor);
        topView.invalidate();

        // set up a timer that waits 1/20 of a second, quits after
        // the first tick, and resets the background to normal
        CountDownTimer timer = new CountDownTimer(50,100000) {
            @Override
            public void onFinish() {
                topView.setBackgroundColor(backgroundColor);
                topView.invalidate();
            }
            @Override
            public void onTick(long millis) {
                cancel();
            }
        };

        // start the timer
        timer.start();
    }

    /**
     * listener class for the "New player" button
     */
    private class NewButtonListener implements OnClickListener {
        /**
         * respond to the press of the "New player" button
         *
         * @see android.view.View.OnClickListener#onClick(android.view.View)
         */
        @Override
        public void onClick(View view) {
            // get the data from the relevant text fields
            String first = firstNameField.getText().toString();
            String last = lastNameField.getText().toString();
            String teamName = teamNameField.getText().toString();
            int uniformNumber = getUniformNumber();

            // if any fields were empty or otherwise incorrect, flash and return
            if (first.isEmpty() || last.isEmpty() || teamName.isEmpty() || uniformNumber < 0) {
                flash();
                updateAll(true);
                return;
            }

            // attempt to add the player; if unsuccessful, flash and return
            if (!database.addPlayer(first, last, uniformNumber, teamName)) {
                flash();
                updateAll(true);
                return;
            }

            // add the team's name to the spinner
            addTeam(teamName);

            // set the current player
            currentPlayer = database.getPlayer(first, last);

            // update the relevant fields in the GUI
            if (currentPlayer != null) { // in case getPlayer is not yet implemented
                updateAll(true);
            }
        }

    }

    /**
     * listener class for the "Find player" button
     */
    private class FindButtonListener implements OnClickListener {
        /**
         * respond to the press of the "Find player" button
         *
         * @see android.view.View.OnClickListener#onClick(android.view.View)
         */
        @Override
        public void onClick(View view) {
            // get the data from the relevant text fields
            String first = firstNameField.getText().toString();
            String last = lastNameField.getText().toString();

            // if the data is not well-formed, flash and return
            if (first.isEmpty() || last.isEmpty()) {
                flash();
                updateAll(false);
                return;
            }

            // attempt to look the player up in the database
            SoccerPlayer temp = database.getPlayer(first, last);

            // depending on whether successful, either update things or flash and return
            if (temp == null) {
                flash();
                updateAll(false);
            } else {
                currentPlayer = temp;
                updateAll(true);
            }
        }
    }

    /**
     * listener class for the "Remove player" button
     */
    private class RemoveButtonListener implements OnClickListener {
        /**
         * respond to the press of the "Remove" button
         *
         * @see android.view.View.OnClickListener#onClick(android.view.View)
         */
        @Override
        public void onClick(View view) {
            // get the relevant data from the text fields
            String first = firstNameField.getText().toString();
            String last = lastNameField.getText().toString();

            // check that data is well formed
            if (first.isEmpty() || last.isEmpty()) {
                flash();
                updateAll(false);
                return;
            }

            // attempt the removal; depending on whether successful either flash or null out
            // current player (who has just been removed) and update GUI fields
            if (!database.removePlayer(first, last)) {
                flash();
            }
            else {
                currentPlayer = null;
                updateAll(false);
            }
        }
    }

    /**
     * abstract listener class for the "plus" buttons
     *
     * Defines the 'onClick' method; declares an abstract method, 'applyAction',
     * which should perform the appropriate action.
     */
    private abstract class PlusButtonListener implements OnClickListener {
        /**
         * respond to the press of the "New player" button
         *
         * @see android.view.View.OnClickListener#onClick(android.view.View)
         */
        @Override
        public void onClick(View view) {
            // get data from the relevant text fields in the GUI; check that
            // they are well formed
            String first = firstNameField.getText().toString();
            String last = lastNameField.getText().toString();
            if (first.isEmpty() || last.isEmpty()) {
                flash();
                updateAll(false);
                return;
            }

            // attempt to apply the action. If successful, set the current player to be
            // the player on which the action was applied; otherwise flash. Update the
            // GUI
            if (!applyAction(first, last)) {
                flash();
                updateAll(false);
            } else {
                currentPlayer = database.getPlayer(first, last);
                updateAll(true);
            }
        }

        /**
         * Performs an action on a player in the database
         *
         * @param first the first name of the player
         * @param last the last name of the player
         * @return whether the action was successful
         */
        public abstract boolean applyAction(String first, String last);
    }

    /**
     * listener class for the "+goal" button
     */
    private class PlusGoalsButtonListener extends PlusButtonListener {
        @Override
        public boolean applyAction(String first, String last) {
            return database.bumpGoals(first, last);
        }
    }

    /**
     * listener class for the "+assist" button
     */
    private class PlusAssistsButtonListener extends PlusButtonListener {
        @Override
        public boolean applyAction(String first, String last) {
            return database.bumpAssists(first, last);
        }
    }

    /**
     * listener class for the "+shot" button
     */
    private class PlusShotsButtonListener extends PlusButtonListener {
        @Override
        public boolean applyAction(String first, String last) {
            return database.bumpShots(first, last);
        }
    }

    /**
     * listener class for the "+save" button
     */
    private class PlusSavesButtonListener extends PlusButtonListener {
        @Override
        public boolean applyAction(String first, String last) {
            return database.bumpSaves(first, last);
        }
    }

    /**
     * listener class for the "+foul" button
     */
    private class PlusFoulsButtonListener extends PlusButtonListener {
        @Override
        public boolean applyAction(String first, String last) {
            return database.bumpFouls(first, last);
        }
    }

    /**
     * listener class for the "+yellow card" button
     */
    private class PlusYellowsButtonListener extends PlusButtonListener {
        @Override
        public boolean applyAction(String first, String last) {
            return database.bumpYellowCards(first, last);
        }
    }

    /**
     * listener class for the "+red card" button
     */
    private class PlusRedsButtonListener extends PlusButtonListener {
        @Override
        public boolean applyAction(String first, String last) {
            return database.bumpRedCards(first, last);
        }
    }

    /**
     * listener class for the "count players" button
     */
    //
    private class CountButtonListener implements OnClickListener {
        /**
         * respond to the press of the "Count players" button
         *
         * @see android.view.View.OnClickListener#onClick(android.view.View)
         */
        @Override
        public void onClick(View view) {
            // get the team name that is selected in the spinner
            String teamName = getSelectedTeamName();

            // invoke the "numPlayers" database operation
            int count = database.numPlayers(teamName);

            // flash if there was an error (indicated by a negative result)
            if (count < 0) {
                flash();
                return;
            }

            // update the text in the count-field with the result
            countField.setText("" + count);
        }
    }

    /**
     * listener class for the "first player" button
     */
    private class FirstPlayerButtonListener implements OnClickListener {
        /**
         * respond to the press of the "First player" button
         *
         * @see android.view.View.OnClickListener#onClick(android.view.View)
         */
        @Override
        public void onClick(View view) {
            // get the team name that is selected in the spinner
            currentIteratorTeam = getSelectedTeamName();

            // set the index to 0; then display the given player
            currentIteratorIndex = 0;
            doIterativePlayer();
        }
    }

    /**
     * listener class for the "Next player" button
     */
    private class NextPlayerButtonListener implements OnClickListener {
        /**
         * respond to the press of the "Next player" button
         *
         * @see android.view.View.OnClickListener#onClick(android.view.View)
         */
        @Override
        public void onClick(View view) {
            // get the team name that is selected in the spinner
            String teamName = getSelectedTeamName();

            // If the spinner selection is different than the team being iterated,
            // flash and return
            if (teamName != null && !teamName.equals(currentIteratorTeam)) {
                flash();
                return;
            }

            // display the next player's information
            doIterativePlayer();
        }
    }

    /**
     * listener class for the "Read" button
     */
    private class ReadButtonListener implements OnClickListener {
        /**
         * respond to the press of the "read" button
         *
         * @see android.view.View.OnClickListener#onClick(android.view.View)
         */
        @Override
        public void onClick(View view) {
            // get the file name from the text field; check that it's non-empty
            String fileName = fileNameField.getText().toString();

            // if the fileName is empty flash and return
            if (fileName.isEmpty()) {
                flash();
                return;
            }

            // attempt the database operation; if unsuccessful, flash and return
            if (!database.readData(new File(getFilesDir(),fileName))) {
                flash();
                return;
            }

            // add all the database teams to the spinner
            for (String team : database.getTeams()) {
                addTeam(team);
            }

            // reset the first/next information
            currentPlayer = null;
            currentIteratorIndex = -1;

            // update the GUI
            updateAll(false);
        }
    }

    /**
     * listener class for the "count players" button
     */
    private class WriteButtonListener implements OnClickListener {
        /**
         * respond to the press of the "New player" button
         *
         * @see android.view.View.OnClickListener#onClick(android.view.View)
         */
        @Override
        public void onClick(View view) {

            // get the file name from the text field
            String fileName = fileNameField.getText().toString();

            // if the file name is empty, flash and nreturn
            if (fileName.isEmpty()) {
                flash();
                return;
            }

            // attempt the operation; if unsuccessful, flash
            if (!database.writeData(new File(getFilesDir(),fileName))) {
                flash();
                return;
            }
        }
    }

    /**
     * performs an iteration step in "next"-ing through players
     */
    private void doIterativePlayer() {
        // ask the database for nth player
        SoccerPlayer player = database.playerNum(currentIteratorIndex,
                currentIteratorTeam);

        // if there was an nth player, update the player and bump the index;
        // if there was not an nth player, flash and return
        if (player != null) {
            currentPlayer = player;
            currentIteratorIndex++;
        }
        else {
            flash();
            return;
        }

        // update the GUI with the player's full information
        updateAll(true);
    }

}
