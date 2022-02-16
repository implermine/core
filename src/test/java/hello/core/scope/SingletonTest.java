package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class SingletonTest {

    @Test
    public void singletonBeanFind(){

        //given
        System.out.println("register BeanDefinitions...");
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);

        //when
        System.out.println("finding singletonBean1...");
        SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
        System.out.println("finding singletonBean2...");
        SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);
        System.out.println("singletonBean1 = " + singletonBean1);
        System.out.println("singletonBean2 = " + singletonBean2);

        //then
        Assertions.assertThat(singletonBean1).isSameAs(singletonBean2);

        ac.close();
    }

    @Scope("singleton")
    static class SingletonBean{

        public SingletonBean() {
            System.out.println("constructor");
        }

        @PostConstruct
        public void init(){
            System.out.println("init");
        }

        @PreDestroy
        public void close(){
            System.out.println("close");
        }
    }
}
