package com.yupi.wsyoj.judge.codeSandbox;

import com.yupi.wsyoj.judge.codeSandbox.Impl.ExampleSandBoxImpl;
import com.yupi.wsyoj.judge.codeSandbox.model.ExecuteCodeRequest;
import com.yupi.wsyoj.judge.codeSandbox.model.ExecuteCodeResponse;
import com.yupi.wsyoj.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CodeSandBoxTest {

    @Value("${codesandbox.type:example}")
    private String type;

    @Test
    void executeCode() {
        //该处即实际调用沙箱，但是发现写死了，如果要换一个沙箱，可能要改多处代码。
        //用工厂模式优化，传一个字符串，根据字符串返回一个实例即可。

//        CodeSandBox codeSandBox = new ExampleSandBoxImpl();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String type = sc.next();
            CodeSandBox codeSandBox = CodeSandboxFactory.newInstance(type);
            String code = "int main() {}";
            String language = QuestionSubmitLanguageEnum.JAVA.getValue();
            List<String> inputList = Arrays.asList("1 2", "3 4");

            //建造者模式
            ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                    .code(code)
                    .language(language)
                    .inputList(inputList)
                    .build();

            ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
            Assertions.assertNotNull(executeCodeResponse);
        }
    }

    @Test
    void executeCodeByValue() {
        //该处即实际调用沙箱，但是发现写死了，如果要换一个沙箱，可能要改多处代码。
        //用工厂模式优化，传一个字符串，根据字符串返回一个实例即可。
//        CodeSandBox codeSandBox = new ExampleSandBoxImpl();
        CodeSandBox codeSandBox = CodeSandboxFactory.newInstance(type);
        String code = "int main() {}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");

        //建造者模式
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();

        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);
    }

    @Test
    void executeCodeByValueWithProxy() {
        //该处即实际调用沙箱，但是发现写死了，如果要换一个沙箱，可能要改多处代码。
        //用工厂模式优化，传一个字符串，根据字符串返回一个实例即可。
//        CodeSandBox codeSandBox = new ExampleSandBoxImpl();
        CodeSandBox codeSandBox = CodeSandboxFactory.newInstance(type);
        CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(codeSandBox);
        String code = "int main() {}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");

        //建造者模式
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();

        ExecuteCodeResponse executeCodeResponse = codeSandboxProxy.executeCode(executeCodeRequest);
    }

}