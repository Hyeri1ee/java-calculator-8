package calculator;

public class InputParser {
    
    public ParsedInput parse(String input) {
        if (input == null || input.isEmpty()) {
            return new ParsedInput(new String[0], new String[0]);
        }
        
        //공백이 포함된 경우 예외 발생
        if (input.contains(" ")) {
            throw new IllegalArgumentException("공백이 포함된 입력은 허용되지 않습니다.");
        }
        
        //커스텀 구분자가 있는지 확인 (두 갈래)
        if (hasCustomDelimiter(input)) {
            return parseWithCustomDelimiter(input);
        }
        return parseWithBasicDelimiters(input);
    }
    
    private boolean hasCustomDelimiter(String input) {
        return input.startsWith("//") && (input.contains("\n") || input.length() > 2);
    }
    
    private ParsedInput parseWithCustomDelimiter(String input) {
        int delimiterEndIndex = input.indexOf("\n");
        String customDelimiter;
        String numbersPart;
        
        
        if (delimiterEndIndex == -1) {
            // \n이 없는 경우: "//;" 형태로 입력된 경우 (실제 줄바꿈 입력)
            if (input.length() <= 2) {
                throw new IllegalArgumentException("커스텀 구분자가 비어있습니다.");
            }
            customDelimiter = input.substring(2);
            // 실제 줄바꿈 입력의 경우 빈 배열 반환
            return new ParsedInput(new String[0], new String[]{customDelimiter, ",", ":"});
        } else {
            // \n이 있는 경우: "//;\n1" 형태로 입력된 경우
            customDelimiter = input.substring(2, delimiterEndIndex);
            if (customDelimiter.isEmpty()) {
                throw new IllegalArgumentException("커스텀 구분자가 비어있습니다.");
            }
            numbersPart = input.substring(delimiterEndIndex + 1);
        }
        
        //numbersPart가 빈 문자열인 경우 (커스텀 구분자만 있는 경우)
        if (numbersPart.isEmpty()) {
            return new ParsedInput(new String[0], new String[]{customDelimiter, ",", ":"});
        }
        
        //입력 검증: 시작이나 끝이 구분자인지 확인
        if (numbersPart.startsWith(customDelimiter) || numbersPart.endsWith(customDelimiter)) {
            throw new IllegalArgumentException("구분자로 시작하거나 끝날 수 없습니다.");
        }

        String[] numbers = numbersPart.split(escapeRegex(customDelimiter));
        
        //빈 문자열이나 구분자만 있는 경우 검증
        if (numbers.length == 0 || (numbers.length == 1 && numbers[0].trim().isEmpty())) {
            throw new IllegalArgumentException("유효한 숫자가 없습니다.");
        }
        
        //각 숫자 검증
        for (String number : numbers) {
            if (number.trim().isEmpty()) {
                throw new IllegalArgumentException("구분자 사이에 빈 문자열이 있습니다.");
            }
            
            try {
                Integer.parseInt(number.trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("숫자가 아닌 값이 입력되었습니다: " + number);
            }
        }
        
        return new ParsedInput(numbers);
    }
    
    private ParsedInput parseWithBasicDelimiters(String input) {
        //입력 검증: 시작이나 끝이 구분자인지 확인
        if (input.startsWith(",") || input.startsWith(":") || 
            input.endsWith(",") || input.endsWith(":")) {
            throw new IllegalArgumentException("구분자로 시작하거나 끝날 수 없습니다.");
        }

        String[] numbers = input.split("[,:]");
        
        //빈 문자열이나 구분자만 있는 경우 검증
        if (numbers.length == 0 || (numbers.length == 1 && numbers[0].trim().isEmpty())) {
            throw new IllegalArgumentException("유효한 숫자가 없습니다.");
        }
        
        //각 숫자 검증
        for (String number : numbers) {
            if (number.trim().isEmpty()) {
                throw new IllegalArgumentException("구분자 사이에 빈 문자열이 있습니다.");
            }
            
            try {
                Integer.parseInt(number.trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("숫자가 아닌 값이 입력되었습니다: " + number);
            }
        }
        
        return new ParsedInput(numbers);
    }
    
    private String escapeRegex(String delimiter) {
        //정규식 특수문자 이스케이프
        return delimiter.replaceAll("[\\[\\]{}()*+?.\\\\^$|]", "\\\\$0");
    }
}
