package com.calculator.lab_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class CalculatorController {
    @Autowired
    private final CalculatorService calInatance;
	public CalculatorController(CalculatorService calInstance) {
		this.calInatance=calInstance;
    }
	@GetMapping(path = "{firstOperand}/{operator}/{secondOperand}")
	public String ExecuteBinaryOperation (@PathVariable("firstOperand") double firstOperand,@PathVariable("operator") String operator,@PathVariable("secondOperand") double secondOperand ) {
		return calInatance.ExecuteBinaryOperation(firstOperand, operator, secondOperand);
	}
	@GetMapping(path = "{operand}/{operator}")
	public String ExecuteBinaryOperation (@PathVariable("operand") double operand,@PathVariable("operator") String operator ) {
		return calInatance.ExecuteUniaryOperation(operand, operator);
	}
}
