package calculator;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {
    
    // 기능 1 테스트: 기본 문자열 분리 (쉽표 기준)
    @Test
    void 빈_문자열은_0을_반환한다() {
        assertSimpleTest(() -> {
            run("");
            assertThat(output()).contains("결과 : 0");
        });
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
/*
    @Test
    void 커스텀_구분자_사용() {
        assertSimpleTest(() -> {
            run("//;\\n1");
            assertThat(output()).contains("결과 : 1");
        });
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("-1,2,3"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }
*/
    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
