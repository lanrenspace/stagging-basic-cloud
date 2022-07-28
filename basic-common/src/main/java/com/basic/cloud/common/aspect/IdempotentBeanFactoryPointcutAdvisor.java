package com.basic.cloud.common.aspect;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

/**
 * @Author lanrenspace@163.com
 * @Description:
 **/
public class IdempotentBeanFactoryPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    private StaticMethodMatcherPointcut pc;

    public void setPc(StaticMethodMatcherPointcut pc) {
        this.pc = pc;
    }

    /**
     * @return
     */
    @Override
    public Pointcut getPointcut() {
        return pc;
    }
}
