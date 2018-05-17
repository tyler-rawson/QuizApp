package com.example.android.quiz;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //final Strings are used for onSaveInstanceState keys
    public final String RG1_ISENABLED = "radioGroup1_isEnabled";
    public final String RG2_ISENABLED = "radioGroup2_isEnabled";
    public final String RG3_ISENABLED = "radioGroup3_isEnabled";
    public final String RG4_ISENABLED = "radioGroup4_isEnabled";
    public final String CB1_ISENABLED = "checkbox_Q6A1_isEnabled";
    public final String CB2_ISENABLED = "checkbox_Q6A2_isEnabled";
    public final String CB3_ISENABLED = "checkbox_Q6A3_isEnabled";
    public final String ET_ISENABLED = "editText_isEnabled";
    public final String RG1_SELECTED_ANSWER = "radioGroup1_selected_answer";
    public final String RG2_SELECTED_ANSWER = "radioGroup2_selected_answer";
    public final String RG3_SELECTED_ANSWER = "radioGroup3_selected_answer";
    public final String RG4_SELECTED_ANSWER = "radioGroup4_selected_answer";
    public final String SBVISIBILITY = "submitButtonVisibility";
    public final String RBVISIBILITY = "resetButtonVisibility";
    public final String QUESTION_ONE = "Q1";
    public final String QUESTION_TWO = "Q2";
    public final String QUESTION_THREE = "Q3";
    public final String QUESTION_FOUR = "Q4";
    public final String QUESTION_FIVE = "Q5";
    public final String QUESTION_INCORRECT_RADIO_BUTTONS = "incorrect_radio_buttons";
    public final String QUESTION_ALL = "all";
    public final String USER_ATTEMPTED_QUIZ = "userAttemptedQuiz";
    public RadioGroup radioGroupQ1;
    public RadioGroup radioGroupQ2;
    public RadioGroup radioGroupQ3;
    public RadioGroup radioGroupQ4;
    public RadioButton rbQ1A1;
    public RadioButton rbQ1A2;
    public RadioButton rbQ1A3;
    public RadioButton rbQ1A4;
    public RadioButton rbQ2A1;
    public RadioButton rbQ2A2;
    public RadioButton rbQ3A1;
    public RadioButton rbQ3A2;
    public RadioButton rbQ3A3;
    public RadioButton rbQ3A4;
    public RadioButton rbQ4A1;
    public RadioButton rbQ4A2;
    public EditText editTextPercent;
    public CheckBox cbQ6A1;
    public CheckBox cbQ6A2;
    public CheckBox cbQ6A3;
    public Button submitButton;
    public Button resetButton;
    public int userAttemptedQuiz;
    //used by onRestoreInstanceState to determine the selected RadioButtons the moment the user presses submit
    public int selectedRadioButtonID_Q1;
    public int selectedRadioButtonID_Q2;
    public int selectedRadioButtonID_Q3;
    public int selectedRadioButtonID_Q4;
    ScrollView scrollView;
    Drawable correctImg;
    Drawable incorrectImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollView = findViewById(R.id.scrollView); // define views and save for later use
        resetButton = findViewById((R.id.resetButton));
        submitButton = findViewById((R.id.submitButton));
        rbQ1A1 = findViewById(R.id.Q1A1);
        rbQ1A2 = findViewById(R.id.Q1A2);
        rbQ1A3 = findViewById(R.id.Q1A3);
        rbQ1A4 = findViewById(R.id.Q1A4);
        rbQ2A1 = findViewById(R.id.Q2A1);
        rbQ2A2 = findViewById(R.id.Q2A2);
        rbQ3A1 = findViewById(R.id.Q3A1);
        rbQ3A2 = findViewById(R.id.Q3A2);
        rbQ3A3 = findViewById(R.id.Q3A3);
        rbQ3A4 = findViewById(R.id.Q3A4);
        rbQ4A1 = findViewById(R.id.Q4A1);
        rbQ4A2 = findViewById(R.id.Q4A2);
        editTextPercent = findViewById(R.id.editTextPercent);
        cbQ6A1 = findViewById(R.id.Q6A1);
        cbQ6A2 = findViewById(R.id.Q6A2);
        cbQ6A3 = findViewById(R.id.Q6A3);
        radioGroupQ1 = findViewById(R.id.radioGroupQ1);
        radioGroupQ2 = findViewById(R.id.radioGroupQ2);
        radioGroupQ3 = findViewById(R.id.radioGroupQ3);
        radioGroupQ4 = findViewById(R.id.radioGroupQ4);
        correctImg = ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_correct);
        incorrectImg = ContextCompat.getDrawable(getApplicationContext(), R.drawable.red_x);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); // hides the soft keyboard for the editText so it doesn't show up when rotating phone
        editTextPercent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) { // setup listener for enter key to be pressed on soft keyboard so the keyboard will close when a user presses enter
                if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(editTextPercent.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });
        if (savedInstanceState == null) { // first time the app has run
            //do nothing
        } else { // the app is being restored
            super.onRestoreInstanceState(savedInstanceState);
            selectedRadioButtonID_Q1 = savedInstanceState.getInt(RG1_SELECTED_ANSWER); // reassign the global variables back to the radioButton ID, otherwise app will crash while attempting to draw an image to a null radioButton ID
            selectedRadioButtonID_Q2 = savedInstanceState.getInt(RG2_SELECTED_ANSWER);
            selectedRadioButtonID_Q3 = savedInstanceState.getInt(RG3_SELECTED_ANSWER);
            selectedRadioButtonID_Q4 = savedInstanceState.getInt(RG4_SELECTED_ANSWER);
            setEnabledRadioGroup(radioGroupQ1, savedInstanceState.getBoolean(RG1_ISENABLED)); // enables or disables the radiogroups depending on how the user answered them previously
            setEnabledRadioGroup(radioGroupQ2, savedInstanceState.getBoolean(RG2_ISENABLED));
            setEnabledRadioGroup(radioGroupQ3, savedInstanceState.getBoolean(RG3_ISENABLED));
            setEnabledRadioGroup(radioGroupQ4, savedInstanceState.getBoolean(RG4_ISENABLED));
            editTextPercent.setEnabled(savedInstanceState.getBoolean(ET_ISENABLED));
            cbQ6A1.setEnabled(savedInstanceState.getBoolean(CB1_ISENABLED)); // enables or disables the checkboxes depending on how the user answered them previously
            cbQ6A2.setEnabled(savedInstanceState.getBoolean(CB2_ISENABLED));
            cbQ6A3.setEnabled(savedInstanceState.getBoolean(CB3_ISENABLED));
            submitButton.setVisibility(savedInstanceState.getInt(SBVISIBILITY)); // retains visibility of submit/reset buttons
            resetButton.setVisibility(savedInstanceState.getInt(RBVISIBILITY));
            userAttemptedQuiz = savedInstanceState.getInt(USER_ATTEMPTED_QUIZ); // restore userAttemptedQuiz value so we'll remember the user has attempted the quiz
            try {
                if (checkForBlankQuestions() == 0 && (this.userAttemptedQuiz > 0)) { // ensure no blank questions with a prior attempt to prevent images from being drawn when user rotates screen before user even presses submit.
                    if (savedInstanceState.getBoolean(RG1_ISENABLED)) {
                        drawImage(incorrectImg, (RadioButton) findViewById((savedInstanceState.getInt(RG1_SELECTED_ANSWER))));
                    } else {
                        drawImage(correctImg, rbQ1A4);
                    }
                    if (savedInstanceState.getBoolean(RG2_ISENABLED)) {
                        drawImage(incorrectImg, (RadioButton) findViewById((savedInstanceState.getInt(RG2_SELECTED_ANSWER))));
                    } else {
                        drawImage(correctImg, rbQ2A1);
                    }
                    if (savedInstanceState.getBoolean(RG3_ISENABLED)) {
                        drawImage(incorrectImg, (RadioButton) findViewById((savedInstanceState.getInt(RG3_SELECTED_ANSWER))));
                    } else {
                        drawImage(correctImg, rbQ3A4);
                    }
                    if (savedInstanceState.getBoolean(RG4_ISENABLED)) {
                        drawImage(incorrectImg, (RadioButton) findViewById((savedInstanceState.getInt(RG4_SELECTED_ANSWER))));
                    } else {
                        drawImage(correctImg, rbQ4A1);
                    }
                    checkIfEditTextIsCorrect();
                    if (!savedInstanceState.getBoolean(CB1_ISENABLED)) {
                        drawCheckBox(correctImg, cbQ6A1);
                    } else {
                        drawCheckBox(incorrectImg, cbQ6A1);
                    }
                    if (!savedInstanceState.getBoolean(CB2_ISENABLED)) {
                        drawCheckBox(correctImg, cbQ6A2);
                    } else {
                        drawCheckBox(incorrectImg, cbQ6A2);
                    }
                    if (!savedInstanceState.getBoolean(CB3_ISENABLED)) {
                        drawCheckBox(correctImg, cbQ6A3);
                    } else {
                        drawCheckBox(incorrectImg, cbQ6A3);
                    }

                } else {
                    //do nothing if there are blank questions
                }

            } catch (Exception ex) {
                Log.e("ActivityMain onRestore", "" + ex.toString());
            }
        }

    }

    /**
     * Save the UI state changes to outState to retain information during orientation changes
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(RG1_ISENABLED, rbQ1A1.isEnabled()); // check an item in the radioGroup to see if it is enabled
        outState.putBoolean(RG2_ISENABLED, rbQ2A1.isEnabled());
        outState.putBoolean(RG3_ISENABLED, rbQ3A1.isEnabled());
        outState.putBoolean(RG4_ISENABLED, rbQ4A1.isEnabled());
        outState.putBoolean(ET_ISENABLED, editTextPercent.isEnabled());
        outState.putBoolean(CB1_ISENABLED, cbQ6A1.isEnabled());
        outState.putBoolean(CB2_ISENABLED, cbQ6A2.isEnabled());
        outState.putBoolean(CB3_ISENABLED, cbQ6A3.isEnabled());
        outState.putInt(RG1_SELECTED_ANSWER, selectedRadioButtonID_Q1); // store the selected radioButtonID to selectedRadioButtonID_Q1
        outState.putInt(RG2_SELECTED_ANSWER, selectedRadioButtonID_Q2);
        outState.putInt(RG3_SELECTED_ANSWER, selectedRadioButtonID_Q3);
        outState.putInt(RG4_SELECTED_ANSWER, selectedRadioButtonID_Q4);
        outState.putInt(SBVISIBILITY, submitButton.getVisibility()); // retain current visibility of the button
        outState.putInt(RBVISIBILITY, resetButton.getVisibility());
        outState.putInt(USER_ATTEMPTED_QUIZ, userAttemptedQuiz); // store the value of score so we know the user has at least made at least one attempt
    }

    /**
     * Returns the selected RadioButton within the selected RadioGroup.
     *
     * @param radioGroup the RadioGroup associated with selected question
     * @return the selected radio button within the selected RadioGroup
     */
    public RadioButton getSelectedRadioButton(RadioGroup radioGroup) {
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        return radioGroup.findViewById(radioButtonID);
    }

    /**
     * Draws an image to the right of the selected RadioGroup.
     *
     * @param correctOrIncorrect the image to draw -- either green checkmark or red X.
     * @param radioButton        the radioButton to draw the image next to.
     */
    public void drawImage(Drawable correctOrIncorrect, RadioButton radioButton) {
        correctOrIncorrect.setBounds(0, 0, 60, 60);
        radioButton.setCompoundDrawables(null, null, correctOrIncorrect, null);
    }

    /**
     * Draws an image to the right of the selected checkbox.
     *
     * @param correctOrIncorrect the image to draw -- either green checkmark or red X.
     * @param checkBox           the checkbox to draw the image next to.
     */
    public void drawCheckBox(Drawable correctOrIncorrect, CheckBox checkBox) {
        correctOrIncorrect.setBounds(0, 0, 60, 60);
        checkBox.setCompoundDrawables(null, null, correctOrIncorrect, null);
    }

    /**
     * Removes an image from the selected RadioGroup.
     *
     * @param correctOrIncorrect the image to remove.... the selected image doesn't really matter in this case
     *                           since we are redrawing it to null.
     * @param radioButton        the RadioButton to remove the image from.
     */
    public void eraseImage(Drawable correctOrIncorrect, RadioButton radioButton) {
        radioButton.setCompoundDrawables(null, null, null, null);
    }

    /**
     * Removes an image from the selected RadioGroup.
     *
     * @param correctOrIncorrect the image to remove... however, the selected image doesn't matter in this case since we are setting all parameters to null.
     *                           since we are redrawing it to null.
     * @param checkbox           the CheckBox to remove the image from.
     */
    public void eraseImage(Drawable correctOrIncorrect, CheckBox checkbox) {
        checkbox.setCompoundDrawables(null, null, null, null);
    }

    /**
     * Checks within each RadioGroup to determine which RadioButtons are selected.
     */
    public int checkForBlankQuestions() {
        if (radioGroupQ1.getCheckedRadioButtonId() == -1) { // the first question is blank
            return 1; // return 1 indicating the first question is blank
        } else if (radioGroupQ2.getCheckedRadioButtonId() == -1) {
            return 2;
        } else if (radioGroupQ3.getCheckedRadioButtonId() == -1) {
            return 3;
        } else if (radioGroupQ4.getCheckedRadioButtonId() == -1) {
            return 4;
        } else if (editTextPercent.getText().toString().isEmpty()) {
            return 5;
        }
        return 0; // return 0 indicating there are 0 blank questions
    }

    /**
     * Checks the editText field to see if the user entered the correct response. If so, it returns true, and if not it returns false. This function also draws appropriate images to indicate
     * whether a user clicked the correct button.
     *
     * @return score as double
     */
    public boolean checkIfEditTextIsCorrect() {
        double userGuess = Double.parseDouble(editTextPercent.getText().toString()); // since we're only use editText one time there is no need to make a custom method -- save userGuess as double.
        if (userGuess == 50.0d) { // if guess equals 50
            removeDrawables(QUESTION_FIVE); // remove all drawables from right of editText
            correctImg.setBounds(0, 0, 60, 60); // set bounds of green checkmark so it is correct size
            editTextPercent.setCompoundDrawables(null, null, correctImg, null); //draw green checkmark
            editTextPercent.setEnabled(false); //disable to allow for less distraction
            return true;
        } else {
            //since there aren't multiple editTexts we do not need to use a drawEditText() like we've done with radioButtons and checkBoxes.
            incorrectImg.setBounds(0, 0, 60, 60); // set bounds of the red X so when it is drawn it will be the correct size
            editTextPercent.setCompoundDrawables(null, null, incorrectImg, null); // draw red X.
            return false;
        }
    }

    /**
     * Calls the checkForBlankQuestions() function and then calculates the score assuming there are no blank questions. This function also draws appropriate images to indicate
     * whether a user clicked the correct button.
     *
     * @return score as double
     */
    public double calculateScore() {
        if (checkForBlankQuestions() == 1) {
            Toast.makeText(MainActivity.this, R.string.Blank_Q1, Toast.LENGTH_SHORT).show();
        } else if (checkForBlankQuestions() == 2) {
            Toast.makeText(MainActivity.this, R.string.Blank_Q2, Toast.LENGTH_SHORT).show();
        } else if (checkForBlankQuestions() == 3) {
            Toast.makeText(MainActivity.this, R.string.Blank_Q3, Toast.LENGTH_SHORT).show();
        } else if (checkForBlankQuestions() == 4) {
            Toast.makeText(MainActivity.this, R.string.Blank_Q4, Toast.LENGTH_SHORT).show();
        } else if (checkForBlankQuestions() == 5) {
            Toast.makeText(MainActivity.this, R.string.Blank_Q6, Toast.LENGTH_SHORT).show();
        } else if (checkForBlankQuestions() == 0) {
            double score = 0.0d;
            try {
                removeDrawables(QUESTION_INCORRECT_RADIO_BUTTONS); // remove drawables so we don't have an accumulation of several drawables under one RadioGroup with multiple attempts.
                if (rbQ1A4.isChecked()) { // if the correct answer is chosen
                    score += 1.0d; // add one to the user's overall score
                    drawImage(correctImg, rbQ1A4); // draw the correct image drawable to the right of the selected radio button
                    setEnabledRadioGroup(radioGroupQ1, false); // disable the radio button to remove distraction to allow for user to fix other incorrect questions.

                } else { // if the wrong answer is selected
                    drawImage(incorrectImg, getSelectedRadioButton(radioGroupQ1)); //draw the red X drawable)
                }
                if (rbQ2A1.isChecked()) {  //etc
                    score += 1.0d;
                    drawImage(correctImg, rbQ2A1);
                    setEnabledRadioGroup(radioGroupQ2, false);

                } else {
                    drawImage(incorrectImg, getSelectedRadioButton(radioGroupQ2));
                }
                if (rbQ3A4.isChecked()) {
                    score += 1.0d;
                    drawImage(correctImg, rbQ3A4);
                    setEnabledRadioGroup(radioGroupQ3, false);
                } else {
                    drawImage(incorrectImg, getSelectedRadioButton(radioGroupQ3));
                }
                if (rbQ4A1.isChecked()) {
                    score += 1.0d;
                    drawImage(correctImg, rbQ4A1);
                    setEnabledRadioGroup(radioGroupQ4, false);
                } else {
                    drawImage(incorrectImg, getSelectedRadioButton(radioGroupQ4));
                }
                if (checkIfEditTextIsCorrect()) {
                    score += 1;
                }
                if (cbQ6A1.isChecked()) {
                    score += (1.0d / 3.0d);
                    drawCheckBox(correctImg, cbQ6A1);
                    cbQ6A1.setEnabled(false);
                } else {
                    drawCheckBox(incorrectImg, cbQ6A1);
                }
                if (cbQ6A2.isChecked()) {
                    score += (1.0d / 3.0d);
                    drawCheckBox(correctImg, cbQ6A2);
                    cbQ6A2.setEnabled(false);
                } else {
                    drawCheckBox(incorrectImg, cbQ6A2);
                }
                if (cbQ6A3.isChecked()) {
                    score += (1.0d / 3.0d);
                    drawCheckBox(correctImg, cbQ6A3);
                    cbQ6A3.setEnabled(false);
                } else {
                    drawCheckBox(incorrectImg, cbQ6A3);
                }
                return score; // return score as double
            } catch (Exception ex) {
                Log.e("ActivityMain Calculate", "" + ex.toString());
            }
        }
        return -1.0d;
    }

    /**
     * This will save the selected radioButtons, call the calculateScore(), and display the score. Linked with submitButton onClick attribute.
     */

    public void submitButtonClick(View view) {
        selectedRadioButtonID_Q1 = radioGroupQ1.getCheckedRadioButtonId(); // selected radioButtons are stored as a global variable so they can be accessed with savedInstanceState.
        selectedRadioButtonID_Q2 = radioGroupQ2.getCheckedRadioButtonId();
        selectedRadioButtonID_Q3 = radioGroupQ3.getCheckedRadioButtonId();
        selectedRadioButtonID_Q4 = radioGroupQ4.getCheckedRadioButtonId();
        double userScore = calculateScore(); // calculate the score and store it within global variable
        userAttemptedQuiz = 1;
        if (checkForBlankQuestions() == 0) { // check to ensure no blank questions
            if (displayScore(userScore) == 6.0d) {
                submitButton.setVisibility(View.GONE);
                resetButton.setVisibility(View.VISIBLE);
            }
        } else {
            // do nothing -- blank questions present
        }
    }

    /**
     * Formats a double to one decimal point. That way toasts won't show values like "5.33333333333333333 correct out of 6".
     *
     * @param score is the score as double, calculated earlier by the calculateScore() method.
     * @return score as double
     */
    public double displayScore(double score) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US); // sets locale to US
        DecimalFormat decFormat = new DecimalFormat(getString(R.string.decimalFormatPattern), symbols); // sets pattern to #.# with Locale.US for decimal point instead of comma.
        double userScore = Double.parseDouble(decFormat.format(score)); // parse the double from the formatted string
        if (userScore == 6.0d) { //perfect score
            Toast.makeText(MainActivity.this, getString(R.string.strPerfectYouGot) + decFormat.format(score) + getString(R.string.strForwardSlash) + getString(R.string.str6) + getString(R.string.strCorrect),
                    Toast.LENGTH_SHORT).show();
        } else if (userScore >= 5.0d && userScore < 6.0d) { //greater than or equal to 5, but less than 6.
            Toast.makeText(MainActivity.this, getString(R.string.strGreatJobYouGot) + decFormat.format(score) + getString(R.string.strForwardSlash) + getString(R.string.str6) + getString(R.string.strCorrect),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.strYouGot) + decFormat.format(score) + getString(R.string.strForwardSlash) + getString(R.string.str6) + getString(R.string.strCorrect),
                    Toast.LENGTH_SHORT).show();
        }
        return userScore;

    }

    /**
     * This method cycles through the radio buttons in a given RadioGroup and uses the .setEnabled attribute to either true or false.
     *
     * @param radioGroup is the RadioGroup to cycle through
     * @param setEnabled sets the radio buttons with the group to either true or false.
     */
    public void setEnabledRadioGroup(RadioGroup radioGroup, boolean setEnabled) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(setEnabled);
        }
    }

    /**
     * This method resets everything back to the original freshly-opened state.
     */
    public void reset(View view) {
        submitButton.setVisibility(View.VISIBLE); // restore the submit button
        resetButton.setVisibility(View.GONE); // hide the reset button
        removeDrawables(QUESTION_ALL); // remove all drawables
        radioGroupQ1.clearCheck(); // clear all selected radio buttons
        radioGroupQ2.clearCheck();
        radioGroupQ3.clearCheck();
        radioGroupQ4.clearCheck();
        setEnabledRadioGroup(radioGroupQ1, true); // enable all radio buttons within this radio group
        setEnabledRadioGroup(radioGroupQ2, true);
        setEnabledRadioGroup(radioGroupQ3, true);
        setEnabledRadioGroup(radioGroupQ4, true);
        editTextPercent.setText(""); // reset the editText
        editTextPercent.clearFocus();
        editTextPercent.setEnabled(true);
        cbQ6A1.setEnabled(true); // enable checkboxes
        cbQ6A2.setEnabled(true);
        cbQ6A3.setEnabled(true);
        cbQ6A1.setChecked(false); // uncheck checkboxes
        cbQ6A2.setChecked(false);
        cbQ6A3.setChecked(false);
        this.userAttemptedQuiz = 0;
        scrollView.fullScroll(ScrollView.FOCUS_UP); // scroll to the top of the activity
    }

    /**
     * This method removes drawables by question, as a string in the format "Q1" or "Q2".
     *
     * @param drawablesToRemove a string in the format "QUESTION_ONE", "QUESTION_TWO", etc., "QUESTION_INCORRECT_RADIO_BUTTONS", or "QUESTION_ALL".
     */
    public void removeDrawables(String drawablesToRemove) {
        switch (drawablesToRemove.toUpperCase()) {
            case QUESTION_ONE:
                eraseImage(incorrectImg, rbQ1A1);
                eraseImage(incorrectImg, rbQ1A2);
                eraseImage(incorrectImg, rbQ1A3);
                eraseImage(incorrectImg, rbQ1A4);
                break;
            case QUESTION_TWO:
                eraseImage(incorrectImg, rbQ2A1);
                eraseImage(incorrectImg, rbQ2A2);
                break;
            case QUESTION_THREE:
                eraseImage(incorrectImg, rbQ3A1);
                eraseImage(incorrectImg, rbQ3A2);
                eraseImage(incorrectImg, rbQ3A3);
                eraseImage(incorrectImg, rbQ3A4);
                break;
            case QUESTION_FOUR:
                eraseImage(incorrectImg, rbQ4A1);
                eraseImage(incorrectImg, rbQ4A2);
                break;
            case QUESTION_FIVE:
                editTextPercent.setCompoundDrawables(null, null, null, null);
                break;
            case QUESTION_INCORRECT_RADIO_BUTTONS: // clears the radio buttons that are not correct answers
                eraseImage(incorrectImg, rbQ1A1);
                eraseImage(incorrectImg, rbQ1A2);
                eraseImage(incorrectImg, rbQ1A3);
                eraseImage(incorrectImg, rbQ2A2);
                eraseImage(incorrectImg, rbQ3A1);
                eraseImage(incorrectImg, rbQ3A2);
                eraseImage(incorrectImg, rbQ3A3);
                eraseImage(incorrectImg, rbQ4A2);
                break;
            default:
            case QUESTION_ALL: // clears all radio buttons
                eraseImage(incorrectImg, rbQ1A1);
                eraseImage(incorrectImg, rbQ1A2);
                eraseImage(incorrectImg, rbQ1A3);
                eraseImage(incorrectImg, rbQ1A4);
                eraseImage(incorrectImg, rbQ2A1);
                eraseImage(incorrectImg, rbQ2A2);
                eraseImage(incorrectImg, rbQ3A1);
                eraseImage(incorrectImg, rbQ3A2);
                eraseImage(incorrectImg, rbQ3A3);
                eraseImage(incorrectImg, rbQ3A4);
                eraseImage(incorrectImg, rbQ4A1);
                eraseImage(incorrectImg, rbQ4A2);
                editTextPercent.setCompoundDrawables(null, null, null, null);
                eraseImage(incorrectImg, cbQ6A1);
                eraseImage(incorrectImg, cbQ6A2);
                eraseImage(incorrectImg, cbQ6A3);
        }

    }

}
