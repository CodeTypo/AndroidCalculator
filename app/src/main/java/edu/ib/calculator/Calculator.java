package edu.ib.calculator;

public class Calculator {
    //A calculator class responsible for handling all the mathematical operations on two numbers
    private double oldNumber = Double.NaN;  //The first number (initially set to NaN for the purpose of one if statement)
    private double newNumber = 0;           //The second number
    private String operation="";            //A string holding the name of the mathematical operator selected by user

    public Calculator() {}

    public void setOperation(String operation) {this.operation = operation;}

    public void setNumber(double x){ //A method responsible for setting the values of OldNumber and newNumber fields
        if(Double.isNaN(oldNumber))  //If old number has not been set yet
            oldNumber = x;           //Then its being set
        else{
            newNumber = x;           //After the first one, the second is being set
        }
    }

    //A method that gets called after the user input two numbers and pressed the equals (=) or percent(%) sign
    public double performOperation(boolean percents){
        double result = 0;
        if(!percents){          //if % has not been clicked, basic math is performed as follows:
            switch(operation){
                case "sum"      : result = oldNumber + newNumber;break;
                case "subtract" : result = oldNumber - newNumber;break;
                case "multiply" : result = oldNumber * newNumber;break;
                case "divide"   : result = oldNumber / newNumber;break;
            }
        } else {
            switch(operation){  //if the % has been clicked operations are being modified a bit

                //assuming 10 + 20 % should give the result 10 + ( 20/100 * 10 ) = 10.2
                case "sum"      : result = oldNumber + (newNumber/100 * oldNumber);break;

                //assuming 10 - 20 % should give the result 10 - ( 20/100 * 10 ) = 9.8
                case "subtract" : result = oldNumber - newNumber/100 *oldNumber;break;

                //assuming 10 * 20 % should give the result 10 * (20/100) = 0.2
                case "multiply" : result = oldNumber * newNumber/100;break;

                //assuming 10 / 20 % should give the result 10 / (20/100) = 50 !!! NIE DZIAłA ZWRACA 500*E^-5
                case "divide"   : result = oldNumber / newNumber/100;break;
            }
        }
        oldNumber = result;
        return result;
    }


    public void reset() {
        oldNumber = Double.NaN;
        newNumber = 0;
        operation="";
    }
}
