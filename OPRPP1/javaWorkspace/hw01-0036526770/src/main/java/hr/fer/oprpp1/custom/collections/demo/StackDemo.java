package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {
	public static void main(String[] args) {
		String[] input = args[0].split(" ");
		ObjectStack stack = new ObjectStack();
		for(var e : input) {
			if(e.matches("-?[0-9]+")) {
				stack.push(Integer.valueOf(e));
			}
			else {
				Integer arg1;
				Integer arg2;
				switch (e) {
				case "*":
					arg1 = (Integer)stack.pop();
					arg2 = (Integer)stack.pop();
					stack.push(arg1*arg2);
					break;
				case "+":
					arg1 = (Integer)stack.pop();
					arg2 = (Integer)stack.pop();
					stack.push(arg1+arg2);
					break;
				case "-":
					arg1 = (Integer)stack.pop();
					arg2 = (Integer)stack.pop();
					stack.push(arg2-arg1);
					break;
				case "/":
					arg1 = (Integer)stack.pop();
					arg2 = (Integer)stack.pop();
					if(arg1==0)
						throw new IllegalArgumentException("Illegal to divide by zero.");
					stack.push(arg2/arg1);
					break;
				case "%":
					arg1 = (Integer)stack.pop();
					arg2 = (Integer)stack.pop();
					stack.push(arg2%arg1);
					break;
				default:
					throw new IllegalArgumentException("Illegal argument.");
				}
			}
		}
		if (stack.size()!=1) {
			throw new IllegalArgumentException("Illegal operations.");
		}
		else {
			
			System.out.println("Expression evaluates to "+Integer.toString((Integer)stack.pop())+".");
		}
	};
}
