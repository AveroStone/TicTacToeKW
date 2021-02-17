package tugas.lab.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneScore, playerTwoScore, winningStatus;
    private final Button [] buttons = new Button[9];
    private Button btn_reset;

    private int scoreP1, scoreP2, rount;
    boolean playerTurn;

    //Empty     >> 0
    //Player 1  >> 1
    //Player 2  >> 2
    int [] gameState = {0,0,0,0,0,0,0,0,0};

    int [][] winningPositions = {
            {0,1,2}, {3,4,5}, {6,7,8},  //rows
            {0,3,6}, {1,4,7}, {2,5,8},  //column
            {0,4,8}, {2,4,6},           //cross
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        winningStatus = (TextView) findViewById(R.id.winningStatus);

        btn_reset = (Button) findViewById(R.id.btn_reset);

        //iteration that will automaticly Create identifier(findViewById) for all 9 button.
		//so we don't have to create or code it manually one by one.
		for(int i=0; i < buttons.length; i++){
            String buttonID = "btn_" + i;
            int resourceID  = getResources().getIdentifier(buttonID,"id", getPackageName());
            buttons[i] = (Button)findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        rount = 0;
        scoreP1 = 0;
        scoreP2 = 0;
        playerTurn = true;

    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

        if (playerTurn) {	//if boolean(playerTurn) is TRUE : that means Player One turn to play
            ((Button) v).setText("X");	//text on any button that Player One touch or press will be set to "X".
            ((Button) v).setTextColor(Color.parseColor("#FFFFFF")); //and set the button color to WHITE.
            gameState[gameStatePointer] = 1;	//value of any particular element of array(gameState) will be set 1 when Player One touch the empty button.
        } else {			//if boolean(playerTurn) is FALSE : that means Player TWO turn to play
            ((Button) v).setText("O");	//text on any button that Player Two touch or press will be set to "O".
            ((Button) v).setTextColor(Color.parseColor("#870B0B"));	//and set the button color to BLOOD RED.
            gameState[gameStatePointer] = 2;	// value of any particular element of array(gameState) will be set 2 when Player Two touch the empty button.
        }
        rount++;	//increase value of variabel round by one (+ 1).
					//to keep track of how many round the game have been play.
		
		//this IF statement will call the method "checkWinner"
        if(checkWinner()){				//if method "checkWinner" return TRUE, than any code inside IF statement will be execute
            if(playerTurn){				//check who if the active player?, if it is Player One than:
                scoreP1++;				//increase value of variabel "scoreP1" by one (+1).
                updatePlayerScore();	//call method "updatePlayerScore".
                Toast.makeText(this, "Player One Won!", Toast.LENGTH_SHORT).show();	//show a toast text dialog that will say who win the turn.
                playAgain();			//call method "playAgain" to reset all the button.
            }else{						//else if Player Two is the Active player than:
                scoreP2++;				//increase value of variabel "scoreP2" by one (+1).
                updatePlayerScore();	//call method "updatePlayerScore".
                Toast.makeText(this, "Player Two Won!", Toast.LENGTH_SHORT).show();	//show a toast text dialog that will say who win the turn.
                playAgain();			//call method "playAgain" to reset all the button.
            }
		//if method "checkWinner" return FALSE, than check again
        }else if(rount == 9){	// if the game's turn is all ready 9, that is means no winner, than execute any code inside this second IF.
            playAgain();		//call method "playAgain."
            Toast.makeText(this, "No Winner!", Toast.LENGTH_SHORT).show();	////show a toast text dialog that will say no body win the turn.
		// if there is no button at winning positon yet & the turn is still under 9 turn, than swap the Actice Player.
        }else{
            playerTurn = !playerTurn;	//swap the Actice Player.
        }

        if(scoreP1 > scoreP2){
            winningStatus.setText("Player One is Winning!");
        }else if(scoreP2 > scoreP1){
            winningStatus.setText("Player Two is Winning!");
        }else{
            winningStatus.setText("");
        }

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                scoreP1 = 0;
                scoreP2 = 0;
                winningStatus.setText("");
                updatePlayerScore();
            }
        });
    }
	
	//defines checkWinner method
    public boolean checkWinner(){
        boolean winnerResult = false;	//set boolean variable "winnerResult" to false.

        //iteration that will check the winning positon
		//if the value of any array(gameState) is meet the winning positon that is define in array 2D array (winningPositions)
		//than the boolean variable(winnerResult) will be set to TRUE.
		//----IF NOT------
		//than the boolean variable(winnerResult) will remain false, that means NO WINNER.
		for(int[] winningPosition : winningPositions){
            if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] !=0){
                winnerResult = true;
            }
        }
        return winnerResult;
    }

	//defines updatePlayerScore method
    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(scoreP1));	//retrieve integer value from variabel scoreP1 and parse it to String for [playerOneScore] text.
        playerTwoScore.setText(Integer.toString(scoreP2));	//retrieve integer value from variabel scoreP1 and parse it to String for [playerOneScore] text.
    }

    //defines playAgain method
	public void playAgain(){
        rount  = 0;         //reset value of round vaariable to 0.
        playerTurn = true;  //reset player turn to player one.

        for (int i = 0; i < buttons.length; i++){
            gameState[i] = 0;       // reset all button to state to 0, button color to default before press or touch.
            buttons[i].setText(""); // reset button text to empty => "_".
        }
    }
}