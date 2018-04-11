package com.example.android.quiz;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
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
    public RadioGroup radioGroupQ1;
    public RadioGroup radioGroupQ2;
    public RadioGroup radioGroupQ3;
    public RadioGroup radioGroupQ4;
    ScrollView scrollView;
    Drawable correctImg;
    Drawable incorrectImg;

    public final String rg1_enabled = "radioGroup1_enabled";
    public final String rg2_enabled = "radioGroup2_enabled";
    public final String rg3_enabled = "radioGroup3_enabled";
    public final String rg4_enabled = "radioGroup4_enabled";
    public final String cb1_enabled = "checkbox_Q6A1_isEnabled";
    public final String cb2_enabled = "checkbox_Q6A2_isEnabled";
    public final String cb3_enabled = "checkbox_Q6A3_isEnabled";
    public final String et_enabled = "editText_enabled";
    public final String rg1_selected = "getRadioGroup1_selected";
    public final String rg2_selected = "getRadioGroup2_selected";
    public final String rg3_selected = "getRadioGroup3_selected";
    public final String rg4_selected = "getRadioGroup4_selected";
    public final String sbVisibility = "submitButtonVisibility";
    public final String rbVisibility = "resetButtonVisibility";
    public final String question_one = "Q1";
    public final String question_two = "Q2";
    public final String question_three = "Q3";
    public final String question_four = "Q4";
    public final String question_five = "Q5";
    public final String question_six = "Q6";
    public final String question_incorrect_radio_buttons = "incorrect_radio_buttons";
    public final String question_all = "all";
    public final String strGlblDoubleScore = "glblDoubleScore";
    public double glblDoubleScore = 0.0d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollView = findViewById(R.id.scrollView);
        resetButton = findViewById((R.id.resetButton));
        submitButton = findViewById((R.id.submitButton));

        rbQ1A1 = findViewById(R.id.Q1A1);
        rbQ1A2 = findViewById(R.id.Q1A2);
        rbQ1A3 = findViewById(R.id.Q1A3);
        rbQ1A4 = findViewById(R.id.Q1A4); //correct

        rbQ2A1 = findViewById(R.id.Q2A1); //correct
        rbQ2A2 = findViewById(R.id.Q2A2);

        rbQ3A1 = findViewById(R.id.Q3A1);
        rbQ3A2 = findViewById(R.id.Q3A2);
        rbQ3A3 = findViewById(R.id.Q3A3);
        rbQ3A4 = findViewById(R.id.Q3A4);//correct

        rbQ4A1 = findViewById(R.id.Q4A1);  //correct
        rbQ4A2 = findViewById(R.id.Q4A2);

        editTextPercent = findViewById(R.id.editTextPercent); //correct answer is 50%

        cbQ6A1 = findViewById(R.id.Q6A1); //correct
        cbQ6A2 = findViewById(R.id.Q6A2); //correct
        cbQ6A3 = findViewById(R.id.Q6A3); //correct

        radioGroupQ1 = findViewById(R.id.radioGroupQ1);
        radioGroupQ2 = findViewById(R.id.radioGroupQ2);
        radioGroupQ3 = findViewById(R.id.radioGroupQ3);
        radioGroupQ4 = findViewById(R.id.radioGroupQ4);

        correctImg = ContextCompat.getDrawable(getApplicationContext(), R.drawable.img_correct);
        incorrectImg = ContextCompat.getDrawable(getApplicationContext(), R.drawable.red_x);


        editTextPercent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(editTextPercent.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to savedInstanceState.

        savedInstanceState.putBoolean(rg1_enabled, rbQ1A1.isEnabled());
        savedInstanceState.putBoolean(rg2_enabled, rbQ2A1.isEnabled());
        savedInstanceState.putBoolean(rg3_enabled, rbQ3A1.isEnabled());
        savedInstanceState.putBoolean(rg4_enabled, rbQ4A1.isEnabled());
        savedInstanceState.putBoolean(et_enabled, editTextPercent.isEnabled());
        savedInstanceState.putBoolean(cb1_enabled, cbQ6A1.isEnabled());
        savedInstanceState.putBoolean(cb2_enabled, cbQ6A2.isEnabled());
        savedInstanceState.putBoolean(cb3_enabled, cbQ6A3.isEnabled());

        savedInstanceState.putInt(rg1_selected, radioGroupQ1.getCheckedRadioButtonId());
        savedInstanceState.putInt(rg2_selected, radioGroupQ2.getCheckedRadioButtonId());
        savedInstanceState.putInt(rg3_selected, radioGroupQ3.getCheckedRadioButtonId());
        savedInstanceState.putInt(rg4_selected, radioGroupQ4.getCheckedRadioButtonId());

        savedInstanceState.putInt(sbVisibility, submitButton.getVisibility());
        savedInstanceState.putInt(rbVisibility, resetButton.getVisibility());

        savedInstanceState.putDouble(strGlblDoubleScore, glblDoubleScore);
        // etc.
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState. This bundle is also passed to onCreate.

        setEnabledRadioGroup(radioGroupQ1, savedInstanceState.getBoolean(rg1_enabled));
        setEnabledRadioGroup(radioGroupQ2, savedInstanceState.getBoolean(rg2_enabled));
        setEnabledRadioGroup(radioGroupQ3, savedInstanceState.getBoolean(rg3_enabled));
        setEnabledRadioGroup(radioGroupQ4, savedInstanceState.getBoolean(rg4_enabled));
        editTextPercent.setEnabled(savedInstanceState.getBoolean(et_enabled));
        cbQ6A1.setEnabled(savedInstanceState.getBoolean(cb1_enabled));
        cbQ6A2.setEnabled(savedInstanceState.getBoolean(cb2_enabled));
        cbQ6A3.setEnabled(savedInstanceState.getBoolean(cb3_enabled));

        radioGroupQ1.check(savedInstanceState.getInt(rg1_selected));
        radioGroupQ2.check(savedInstanceState.getInt(rg2_selected));
        radioGroupQ3.check(savedInstanceState.getInt(rg3_selected));
        radioGroupQ4.check(savedInstanceState.getInt(rg4_selected));

        submitButton.setVisibility(savedInstanceState.getInt(sbVisibility));
        resetButton.setVisibility(savedInstanceState.getInt(rbVisibility));
        glblDoubleScore = savedInstanceState.getDouble(strGlblDoubleScore);

        if (checkForBlankQuestions() == 0 && (this.glblDoubleScore > 0)) { // no blank questions with a prior attempt
            calculateScore();
        } else {
            Log.e("", this.glblDoubleScore + " is: " + glblDoubleScore);

            //do nothing if there are blank questions... we don't want a toast saying there's a missed question every time we rotate phone
        }
    }

    /**
     * Returns the selected radio button within the selected RadioGroup.
     *
     * @param radioGroup the RadioGroup associated with selected question
     * @return the selected radio button within the selected RadioGroup
     * An if statement evaluates each boolean value to determine if that item should be added to the receipt.
     */
    public RadioButton getSelectedRadioButton(RadioGroup radioGroup) {
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = radioGroup.findViewById(radioButtonID);

        return radioButton;
    }

    /**
     * Draws an image to the right of the selected radiogroup.
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
     * Removes an image from the selected radiogroup.
     *
     * @param correctOrIncorrect the image to remove.... the selected image doesn't really matter in this case
     *                           since we are redrawing it to null.
     * @param radioButton        the radiobutton to remove the image from.
     */
    public void eraseImage(Drawable correctOrIncorrect, RadioButton radioButton) {
        radioButton.setCompoundDrawables(null, null, null, null);
    }

    /**
     * Removes an image from the selected radiogroup.
     *
     * @param correctOrIncorrect the image to remove.... the selected image doesn't really matter in this case
     *                           since we are redrawing it to null.
     * @param checkbox           the radiobutton to remove the image from.
     */
    public void eraseImage(Drawable correctOrIncorrect, CheckBox checkbox) {
        checkbox.setCompoundDrawables(null, null, null, null);
    }

    /**
     * Checks each radiogroup to determine if there is a radio button selection within each grouping.
     */
    public int checkForBlankQuestions() {
        if (radioGroupQ1.getCheckedRadioButtonId() == -1) {
            return 1; // first question is blank
        } else if (radioGroupQ2.getCheckedRadioButtonId() == -1) {
            return 2; // second question is blank
        } else if (radioGroupQ3.getCheckedRadioButtonId() == -1) {
            return 3; // etc
        } else if (radioGroupQ4.getCheckedRadioButtonId() == -1) {
            return 4;
        } else if (editTextPercent.getText().toString().isEmpty()) {
            return 5;
        }
        return 0; // no blank questions
    }

    /**
     * Calls the checkForBlankQuestions() function and then calculates the score. This function also draws appropriate images to indicate
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

            removeDrawables(question_incorrect_radio_buttons); // remove drawables so we don't have accumulation of several drawables under one radiogroup.

            if (rbQ1A4.isChecked()) { // if the correct answer is chosen
                score += 1.0d; // add one to the score
                removeDrawables(question_one); //remove drawable red X if it is present.
                drawImage(correctImg, rbQ1A4); // draw the correct image to the right of the correct radio button
                setEnabledRadioGroup(radioGroupQ1, false); // disable the radio button so the user focuses on fixing any potential incorrect questions.

            } else { // if wrong answer is selected
                drawImage(incorrectImg, getSelectedRadioButton(radioGroupQ1)); //draw red X
            }

            if (rbQ2A1.isChecked()) {  //etc
                score += 1.0d;
                removeDrawables(question_two);
                drawImage(correctImg, rbQ2A1);
                setEnabledRadioGroup(radioGroupQ2, false);

            } else {
                drawImage(incorrectImg, getSelectedRadioButton(radioGroupQ2));
            }

            if (rbQ3A4.isChecked()) {
                score += 1.0d;
                removeDrawables(question_three);
                drawImage(correctImg, rbQ3A4);
                setEnabledRadioGroup(radioGroupQ3, false);
            } else {
                drawImage(incorrectImg, getSelectedRadioButton(radioGroupQ3));
            }

            if (rbQ4A1.isChecked()) {
                score += 1.0d;
                removeDrawables(question_four);
                drawImage(correctImg, rbQ4A1);
                setEnabledRadioGroup(radioGroupQ4, false);
            } else {
                drawImage(incorrectImg, getSelectedRadioButton(radioGroupQ4));
            }

            double userGuess = Double.parseDouble(editTextPercent.getText().toString()); // we only use editText once so there's no need to make custom method -- save userGuess as double.

            if (userGuess == 50.0d) { // if guess == 50
                score += 1.0d; // add to score
                removeDrawables(question_five); // remove drawables from right of editText
                correctImg.setBounds(0, 0, 60, 60); // set bounds of green checkmark so it is correct size
                editTextPercent.setCompoundDrawables(null, null, correctImg, null); //draw green checkmark
                editTextPercent.setEnabled(false); //disable again
            } else {
                incorrectImg.setBounds(0, 0, 60, 60); // again, no method for editText. Setting bounds so the red X is the correct size.
                editTextPercent.setCompoundDrawables(null, null, incorrectImg, null); // draw red X.
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
        }
        return -1.0d; // something went wrong
    }

    /**
     * Many components of the original calculate method had to be broken up to allow for savedInstanceState implementation. Instead of checking for blank questions,
     * calculating the score, and displaying the score all in one button -- these were broken up and then called individually with this method. This method is linked with submitButton's onClick attribute.
     *
     * @return score as double
     */
    public void submitButtonClick(View view) {
        double score = calculateScore();

        if (checkForBlankQuestions() == 0) { // check to ensure no blank questions
            if (displayScore(score) == 6.0d) {
                submitButton.setVisibility(View.GONE);
                resetButton.setVisibility(View.VISIBLE);
            }
        } else {
            //blank questions are present
        }
    }

    /**
     * Formats the double score to a double score with one decimal point. That way we don't get values like 5.33333333333333333 correct out of 6 total questions.
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
        glblDoubleScore = userScore;
        Log.e("displayScore()", "userScore is: " + userScore + " -- " + "this.glblDoubleScore is: " + glblDoubleScore);
        return userScore;

    }

    /**
     * This method cycles through the radio buttons in a given RadioGroup and uses the .setEnabled attribute to either true or false.
     *
     * @param radioGroup the RadioGroup to cycle through
     * @param setEnabled sets the radio buttons with the group to either true or false.
     */
    public void setEnabledRadioGroup(RadioGroup radioGroup, boolean setEnabled) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(setEnabled);
        }
    }

    /**
     * This method resets everything back to it's original state.
     */
    public void reset(View view) {
        submitButton.setVisibility(View.VISIBLE); // restore the submit button
        resetButton.setVisibility(View.GONE); // hide the reset button

        removeDrawables(question_all); // remove all drawables

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

        this.glblDoubleScore = 0.0d;

        scrollView.fullScroll(ScrollView.FOCUS_UP); // scroll to the top of the activity
    }

    /**
     * This method removes drawables by question, as a string in the format "Q1" or "Q2".
     *
     * @param drawablesToRemove a string in the format "Q1", "Q2", etc., "incorrect_radio_buttons", or "all".
     */
    public void removeDrawables(String drawablesToRemove) {
        switch (drawablesToRemove.toUpperCase()) {
            case question_one:
                eraseImage(incorrectImg, rbQ1A1);
                eraseImage(incorrectImg, rbQ1A2);
                eraseImage(incorrectImg, rbQ1A3);
                eraseImage(incorrectImg, rbQ1A4);
                break;
            case question_two:
                eraseImage(incorrectImg, rbQ2A1);
                eraseImage(incorrectImg, rbQ2A2);
                break;
            case question_three:
                eraseImage(incorrectImg, rbQ3A1);
                eraseImage(incorrectImg, rbQ3A2);
                eraseImage(incorrectImg, rbQ3A3);
                eraseImage(incorrectImg, rbQ3A4);
                break;
            case question_four:
                eraseImage(incorrectImg, rbQ4A1);
                eraseImage(incorrectImg, rbQ4A2);
                break;
            case question_five:
                editTextPercent.setCompoundDrawables(null, null, null, null);
                break;
            case question_six:
                eraseImage(incorrectImg, cbQ6A1);
                eraseImage(incorrectImg, cbQ6A2);
                eraseImage(incorrectImg, cbQ6A3);
                break;

            case question_incorrect_radio_buttons: // clears the radio buttons that are not correct answers
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
            case question_all: // clears all radio buttons
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
