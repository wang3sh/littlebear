package com.yupi.wsyoj.judge.codeSandbox;

import com.yupi.wsyoj.judge.codeSandbox.Impl.ExampleSandBoxImpl;
import com.yupi.wsyoj.judge.codeSandbox.Impl.RemoteCodeSandBoxImpl;
import com.yupi.wsyoj.judge.codeSandbox.Impl.ThirdPartyCodeSandBoxImpl;
import com.yupi.wsyoj.judge.codeSandbox.TypeEnum.Types;

/**
 * 代码沙箱工厂，根据字符串参数创建指定代码实例。
 * 只需静态工厂即可，不用抽象工厂。
 */
public class CodeSandboxFactory {
    /**
     * 创建代码沙箱实例
     * @param type
     * @return
     */
    public static CodeSandBox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleSandBoxImpl();
            case "remote":
                return new RemoteCodeSandBoxImpl();
            case "thirdParty":
                return new ThirdPartyCodeSandBoxImpl();
            default:
                return new ExampleSandBoxImpl();
        }
    }
}
