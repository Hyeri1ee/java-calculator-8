package calculator;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {

    // 기능 1 테스트: 기본 문자열 분리 (쉽표 기준)
    @Test
    void 빈_문자열은_오류이다() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException(""))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 단일_숫자는_그대로_반환한다() {
        assertSimpleTest(() -> {
            run("1");
            assertThat(output()).contains("결과 : 1");
        });
    }

    @Test
    void 쉼표로_구분된_두_숫자의_합을_반환한다() {
        assertSimpleTest(() -> {
            run("1,2");
            assertThat(output()).contains("결과 : 3");
        });
    }

    @Test
    void 쉼표로_구분된_여러_숫자의_합을_반환한다() {
        assertSimpleTest(() -> {
            run("1,2,3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    // 기능 2-1 테스트: 콜론 구분자 추가
    @Test
    void 콜론으로_구분된_두_숫자의_합을_반환한다() {
        assertSimpleTest(() -> {
            run("1:2");
            assertThat(output()).contains("결과 : 3");
        });
    }

    @Test
    void 콜론으로_구분된_여러_숫자의_합을_반환한다() {
        assertSimpleTest(() -> {
            run("1:2:3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    void 쉼표와_콜론을_혼합하여_사용한다() {
        assertSimpleTest(() -> {
            run("1,2:3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    // 기능 2-2 테스트: 잘못된 값 입력 테스트 케이스 추가
    @Test
    void 쉼표_구분자_사이에_숫자가_아닌_값이_들어오면_예외가_발생한다() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,a,2"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 콜론_구분자_사이에_숫자가_아닌_값이_들어오면_예외가_발생한다() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1:b:2"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 혼합_구분자_사이에_숫자가_아닌_값이_들어오면_예외가_발생한다() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,a:2"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 특수문자가_들어오면_예외가_발생한다() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1@2"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 공백이_들어오면_예외가_발생한다() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1, 2"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    // 기능 2-3 테스트 : 구분자 위치 문제 테스트
    @Test
    void 시작이_구분자면_예외가_발생한다() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException(",1,2"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 끝이_구분자면_예외가_발생한다() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,2,"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 연속_구분자면_예외가_발생한다() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,,2"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 구분자만_있으면_예외가_발생한다() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException(",,"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    // 공백 문제 테스트
    @Test
    void 구분자_뒤_공백이_있으면_예외가_발생한다() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1, 2"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 시작_공백이_있으면_예외가_발생한다() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException(" 1,2"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 끝_공백이_있으면_예외가_발생한다() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,2 "))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 구분자_양쪽_공백이_있으면_예외가_발생한다() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1 , 2"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    // 빈 문자열 문제 테스트
    @Test
    void 구분자_사이_빈_문자열이_있으면_예외가_발생한다() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,,2"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 끝에_빈_문자열이_있으면_예외가_발생한다() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,2,"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    //기능 4-1 테스트: 커스텀 구분자 적용
    @Test
    void 커스텀_구분자_사용() {
        InputParser parser = new InputParser();
        ParsedInput result = parser.parse("//;\n1");

        assertThat(result.getNumbers()).containsExactly("1");
    }

    @Test
    void 커스텀_구분자_실제_줄바꿈_입력() {
        InputParser parser = new InputParser();
        ParsedInput result = parser.parse("//;");

        //실제 줄바꿈 입력의 경우 빈 배열 반환
        assertThat(result.getNumbers()).isEmpty();
        assertThat(result.getDelimiters()).containsExactly(";", ",", ":");
    }

    @Test
    void 커스텀_구분자_문자열_테스트() {
        Calculator calculator = new Calculator();

        //문자열에 \n 포함된 경우 (실제 줄바꿈)
        String input1 = "//;\n1;2;3";
        int result1 = calculator.add(input1);
        assertThat(result1).isEqualTo(6);
    }

    @Test
    void 커스텀_구분자_실제_줄바꿈_테스트() {
        Calculator calculator = new Calculator();

        //실제 줄바꿈 입력 시뮬레이션
        String input2 = "//;";
        int result2 = calculator.add(input2);
        assertThat(result2).isEqualTo(0);
    }

    @Test
    void 커스텀_구분자_문자열_백슬래시_테스트() {
        Calculator calculator = new Calculator();

        //문자열에 \n 포함된 경우 (백슬래시 + n)
        String input1 = "//ㄹㄹ\\n1ㄹㄹ3ㄹㄹ4";
        int result1 = calculator.add(input1);
        assertThat(result1).isEqualTo(8);
    }

    @Test
    void 커스텀_구분자_실제_줄바꿈_백슬래시_테스트() {
        Calculator calculator = new Calculator();

        //실제 줄바꿈 입력 시뮬레이션 (백슬래시 + n)
        String input2 = "//ㄹㄹ\\n";
        int result2 = calculator.add(input2);
        assertThat(result2).isEqualTo(0);
    }

    @Test
    void 커스텀_구분자_빈_줄_테스트() {
        InputParser parser = new InputParser();
        assertThatThrownBy(() -> parser.parse("//4\n\n"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("유효한 숫자가 없습니다");
    }

    @Test
    void 커스텀_구분자_실제_빈_줄_테스트() {
        //실제 엔터 두 번 누르는 경우 시뮬레이션
        assertSimpleTest(() -> {
            run("//4", "");  // //4 입력 후 빈 줄 입력
            assertThatThrownBy(() -> {
                // 실제로는 //4\n\n이 되어야 하므로 예외 발생해야 함
                InputParser parser = new InputParser();
                parser.parse("//4\n\n");
            }).isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("유효한 숫자가 없습니다");
        });
    }

    @Test
    void 커스텀_구분자_문자열_빈_줄_테스트() {
        //문자열에 \n\n이 포함된 경우 - 예외 발생해야 함
        //run() 메서드로는 \n 처리가 복잡하므로 직접 테스트
        InputParser parser = new InputParser();
        assertThatThrownBy(() -> parser.parse("//4\n\n"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("유효한 숫자가 없습니다");
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
