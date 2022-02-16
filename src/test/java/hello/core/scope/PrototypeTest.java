package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class PrototypeTest {

    @Test
    public void prototypeBeanFind(){

        //given
        System.out.println("register BeanDefinitions...");
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        //when
        System.out.println("finding prototypeBean1...");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("finding prototypeBean2...");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);
        //then
        Assertions.assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        ac.close();
    }

    @Scope("prototype")
    static class PrototypeBean{

        public PrototypeBean() {
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
