package com.example.ui;

import java.util.Random;

import com.example.constants.Constants;
import com.example.sps.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SPSHome extends Activity {

	public static int userScore;
	public static int computerScore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		userScore = computerScore = 0;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// Computer Move
	public String getComputerMove() {
		Random rand = new Random(System.currentTimeMillis());
		int random = rand.nextInt((3 + 1) - 1) + 1;
		String computerChoice = null;
		switch (random) {
		case 1:
			computerChoice = getResources().getString(R.string.STONE);
			break;
		case 2:
			computerChoice = getResources().getString(R.string.PAPER);
			break;
		case 3:
			computerChoice = getResources().getString(R.string.SCISSORS);
			break;
		}
		return computerChoice;
	}

	// Computer Result. Returns 0 tie, 1 user, -1 computer
	public int computeResults(String userChoice, String computerChoice) {
		int result = 0;
		if (userChoice != null && computerChoice != null) {
			if (userChoice.equalsIgnoreCase(computerChoice)) {
				result = 0;
			} else if (userChoice.equalsIgnoreCase(getResources().getString(R.string.SCISSORS))) {
				if (computerChoice.equalsIgnoreCase(getResources().getString(R.string.STONE))) {
					result = -1;
				} else if (computerChoice.equalsIgnoreCase(getResources().getString(R.string.PAPER))) {
					result = 1;
				}
			} else if (userChoice.equalsIgnoreCase(getResources().getString(R.string.STONE))) {
				if (computerChoice.equalsIgnoreCase(getResources().getString(R.string.PAPER))) {
					result = -1;
				} else if (computerChoice.equalsIgnoreCase(getResources().getString(R.string.SCISSORS))) {
					result = 1;
				}
			} else if (userChoice.equalsIgnoreCase(getResources().getString(R.string.PAPER))) {
				if (computerChoice.equalsIgnoreCase(getResources().getString(R.string.SCISSORS))) {
					result = -1;
				} else if (computerChoice.equalsIgnoreCase(getResources().getString(R.string.STONE))) {
					result = 1;
				}
			}
		}
		return result;
	}

	// Display the results.
	public void displayResults(View view, int result, String computerMove) {
		EditText compMoveText = (EditText) findViewById(R.id.compMoveText);
		EditText resultText = (EditText) findViewById(R.id.resultText);
		EditText scoreText = (EditText) findViewById(R.id.scoreText);
		compMoveText.setText("COMPUTER : " + computerMove);
		switch (result) {
		case 0:
			resultText.setText("TIED.");
			break;
		case 1:
			resultText.setText("You WON.");
			userScore++;
			break;
		case -1:
			resultText.setText("You LOST.");
			computerScore++;
			break;
		}
		scoreText.setText("P " + userScore + " - " + computerScore + " C");
		if (userScore == 5 || computerScore == 5) {
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle(Constants.RESULT_TEXT);
			alert.setMessage((userScore == 5 ? "You " : "Computer") + " won the match.");

			alert.setNegativeButton(Constants.OK, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.dismiss();
					Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(
							getBaseContext().getPackageName());
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			});
			alert.show();			
		}
	}

	// Button Click
	public void goButtonOnClick(View view) {
		RadioGroup rGroup = (RadioGroup) findViewById(R.id.radioGroup);
		RadioButton checkedRadioButton = (RadioButton) rGroup.findViewById(rGroup.getCheckedRadioButtonId());
		String userChoice = null;
		if (checkedRadioButton != null) {
			userChoice = (String) checkedRadioButton.getText();
		}
		if (userChoice == null) {
			return;
		}
		String computerMove = getComputerMove();
		if (computerMove == null) {
			return;
		}
		int result = computeResults(userChoice, computerMove);
		displayResults(view, result, computerMove);
	}

	// Exit Button
	public void exitButtonOnClick(View view) {
		finish();
	}
}
