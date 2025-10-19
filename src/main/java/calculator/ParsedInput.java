package calculator;

public class ParsedInput {
    private final String[] numbers;
    private final String[] delimiters;
    
    public ParsedInput(String[] numbers) {
        this.numbers = numbers;
        this.delimiters = new String[0];
    }
    
    public ParsedInput(String[] numbers, String[] delimiters) {
        this.numbers = numbers;
        this.delimiters = delimiters;
    }
    
    public String[] getNumbers() {
        return numbers;
    }
    
    public String[] getDelimiters() {
        return delimiters;
    }
}
