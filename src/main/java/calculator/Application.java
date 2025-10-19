package calculator;

import camp.nextstep.edu.missionutils.Console;

public class Application {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        
        System.out.println("덧셈할 문자열을 입력해 주세요.");
        String input;

        try {
            input = Console.readLine();
        } catch (java.util.NoSuchElementException e) {
            System.out.println("결과 : 0");
            return;
        }

        if (input == null || input.isBlank()) {
            System.out.println("결과 : 0");
            return;
        }

        //커스텀 구분자가 있는 경우 추가 입력 받기
        if (input.startsWith("//")) {
            //\n이 포함된 경우: "//ㄹㄹ\n1ㄹㄹ3ㄹㄹ4" 형태
            if (input.contains("\\n")) {
                //\n을 실제 줄바꿈으로 변환
                input = input.replace("\\n", "\n");
            } else if (!input.contains("\n")) {
                //\n이 없는 경우: "//ㄹㄹ" 형태 - 추가 입력 받기
                try {
                    String additionalInput = Console.readLine();
                    if (additionalInput != null && !additionalInput.isBlank()) {
                        input = input + "\n" + additionalInput;
                    }
                } catch (java.util.NoSuchElementException e) {
                    //추가 입력이 없으면 그대로 처리
                }
            }
        }

        int result = calculator.add(input);
        System.out.println("결과 : " + result);
    }
}
