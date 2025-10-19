package calculator;

public class Calculator {
    private final InputParser parser;
    
    public Calculator() {
        this.parser = new InputParser();
    }
    
    public int add(String input) {
        //\n 변환 처리
        if (input.startsWith("//") && input.contains("\\n")) {
            input = input.replace("\\n", "\n");
        }
        
        ParsedInput parsedInput = parser.parse(input);
        return calculateSum(parsedInput.getNumbers());
    }
    
    private int calculateSum(String[] numbers) {
        int sum = 0;
        
        for (String number : numbers) {
            sum += Integer.parseInt(number.trim());
        }
        
        return sum;
    }
}
