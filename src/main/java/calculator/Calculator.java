package calculator;

public class Calculator {
    
    public int add(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        //공백이 포함된 경우 예외 발생
        if (input.contains(" ")) {
            throw new IllegalArgumentException("공백이 포함된 입력은 허용되지 않습니다.");
        }

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
        
        int sum = 0;
        
        for (String number : numbers) {
            if (number.trim().isEmpty()) {
                throw new IllegalArgumentException("구분자 사이에 빈 문자열이 있습니다.");
            }
            
            try {
                sum += Integer.parseInt(number.trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("숫자가 아닌 값이 입력되었습니다: " + number);
            }
        }
        
        return sum;
    }
}
