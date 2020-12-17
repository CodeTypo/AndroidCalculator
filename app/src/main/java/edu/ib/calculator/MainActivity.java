package edu.ib.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public TextView display;

    private boolean     dotPressed              = false;            //A flag monitoring the '.' sign usage
    private boolean     zeroPressed             = false;            //A flag monitoring the '0' input by user. inputs like: 00010 aren't allowed
    private final       Calculator  calculator  = new Calculator(); // A new instance of calculator Class object
    private boolean     operationPerformed      = false;            //A flag that notifies when '=' sign have been pressed
    private boolean     operationSelected       = false;            //A flag that notifies when an mathematical operation operator have been clicked
    private boolean     percentFlag             = false;            //A flag that notifies when a percent sign have been clicke


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        display = (TextView) findViewById(R.id.tv_userInput);
    }

    @SuppressLint("NonConstantResourceId")
    public void numeric_btn_clicked(View view) {

        if(operationPerformed)                      //checks if the number was clicked after '=' or '%' was clicked
        {                                           //If so, it means the user has finished his previous calculations
            clear();                                //So the calculator is being cleared
        }
        double current;
        //An enhanced if statement, checks if the displayed value is different than "" if so, it is being stored into 'current' var else, current = 0;
        if(!display.getText().toString().isEmpty()){
            current = Double.parseDouble(display.getText().toString());
        } else {current = 0.0;}

        if(current >0 || !zeroPressed|| dotPressed) {
            switch (view.getId()) {
                case R.id.one:
                    display.append("1");
                    break;
                case R.id.two:
                    display.append("2");
                    break;
                case R.id.three:
                    display.append("3");
                    break;
                case R.id.four:
                    display.append("4");
                    break;
                case R.id.five:
                    display.append("5");
                    break;
                case R.id.six:
                    display.append("6");
                    break;
                case R.id.seven:
                    display.append("7");
                    break;
                case R.id.eight:
                    display.append("8");
                    break;
                case R.id.nine:
                    display.append("9");
                    break;
                case R.id.zero:
                    display.append("0");zeroPressed = true;
                    break;

            }
        }//If the user presseed the dot '.' sign
        if(view.getId() == R.id.dot){
            //If the dot has not been pressed yet and the display is not empty
            if (!dotPressed && !(display.getText().equals(""))) {
                display.append("."); //Its being displayed
                dotPressed = true;       //A flag is being set
            }
        }
    }



    @SuppressLint("NonConstantResourceId")
    public void operation_btn_clicked(View view) {

        int id = view.getId();                 //Gets its id

        switch (id){
            case R.id.sum:      operatorClicked("sum");break;
            case R.id.subtract: operatorClicked("subtract");break;
            case R.id.multiply: operatorClicked("multiply");break;
            case R.id.divide:   operatorClicked("divide");break;

            case R.id.sign_swap:
                signSwap();
                break;

            case R.id.percent:
                if(operationSelected){
                    percentFlag = true; //When the user pressed the percent sign, the equals method changes a bit
                    performEquals();    //So the flag needs to be set before calling it
                }else {clear();}
                break;

            case R.id.clear:
                clear();
                break;

            case R.id.equals:
                performEquals();
                break;
        }
        dotPressed = false;             //After pressing an operation symbol, user should be able to type
        zeroPressed = false;            //Any number he/she wants, so the flags are being cleared

    }


    private void operatorClicked(String id) {
        calculatorMaintenance();        //calls calculatorMaintenance() - some basic repetitive tasks
        calculator.setOperation(id);    //calls Calculator class setOperation(id) method
        operationSelected = true;       //Sets the flag telling that the user selected some mathematical operation
        percentFlag = false;            //User did not pressed the % sign so the flag is being cleared
    }


    private void signSwap() {
        //A sign swap method, everything it does is just adding or removing the '-' sign in front of the number, of course it affects the number's value
        if(!display.getText().toString().isEmpty()){
            String number;
            if(!display.getText().toString().startsWith("-")){
                number =("-"+display.getText());
            } else {
                StringBuilder sb = new StringBuilder(display.getText());
                number = sb.deleteCharAt(0).toString();
            }
            calculator.reset();
            calculator.setNumber(Double.parseDouble(number));
            display.setText(number);
        }
    }

    public void clear(){
        calculator.reset(); //A Calculator object's reset() method is being called
        display.setText("");    //The display is being cleared
        dotPressed = zeroPressed = operationPerformed = percentFlag = operationSelected = false; //All bools set to FALSE
    }

    public void performEquals(){
        //If no mathematical operation has been performed yet
        if(!operationPerformed){
            //if the display isn't empty
            if(!display.getText().toString().isEmpty())
                //Calls the Calculator class setNumber(double x) method making the calculator to store the value that was displayed on the screen
                calculator.setNumber(Double.parseDouble(display.getText().toString()));}

        //Calling the calculator's performOperation(percentFlag) method and storing its return value in 'value' variable
        Double value = calculator.performOperation(percentFlag);
        //Prints the value on the display
        display.setText(String.valueOf(value));
        operationPerformed = true;
    }


    public void calculatorMaintenance(){
        //A method containing a group of statements that got called frequently in the code after the operational buttons were clicked
        //Assuming the user clicks an operational button after he/she input some number
        //Checking if there was an input, if there was, its being passed to the Calculator object via its setNumber(double x)method
        if(!display.getText().toString().isEmpty())
            calculator.setNumber(Double.parseDouble(display.getText().toString()));
        display.setText("");            //display is being cleared to be ready for the second nu.ber input
        operationPerformed = false; //An operation has not been performed yet, user input only one number
        zeroPressed = false;        //User is able to input zero at the beginning of the number again
    }
}