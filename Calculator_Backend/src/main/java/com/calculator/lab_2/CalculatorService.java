package com.calculator.lab_2;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
    public String ExecuteBinaryOperation(double firstOperand , String operator , double secondOperand) {
        switch (operator){
        case "+":  return String.valueOf(firstOperand + secondOperand);
        case "-":  return String.valueOf(firstOperand - secondOperand);
        case "*":  return String.valueOf(firstOperand * secondOperand);
        case "÷":   if(secondOperand==0)
                        return "E";
                        return String.valueOf(firstOperand / secondOperand);
        default: return "0";
        }
    }
    public String ExecuteUniaryOperation(double operand , String operator) {
        switch (operator){
        // ^ is %
        case "^":    return String.valueOf(operand/100);
        case "x^2":  return String.valueOf(Math.pow(operand,2));
        // $ is 1/x
        case "$":  if(operand==0)
                        return"E";
                        return String.valueOf(1/operand);
        case "sqr":  if(operand<0)
                        return "E";
                        return String.valueOf(Math.sqrt(operand));
        // ++ is +/- */
        case "++": if(operand==0)
                        return "0";
                        return String.valueOf(0 - operand);
        default: return "0";
        }
    }
}
