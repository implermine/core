package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Configuration이 붙은 beanDefinition spec 객체가, A -> B / C -> B,D 관계 일 때,
 * B가 Singleton으로 Loading 되는지 테스트
 */
public class ConfigurationSingletonTest {
    @Test
    void configurationTest(){
        //given
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        //when
        MemberServiceImpl memberServiceImpl = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderServiceImpl = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);


        MemberRepository memberRepository1 = memberServiceImpl.getMemberRepository();
        MemberRepository memberRepository2 = orderServiceImpl.getMemberRepository();

        System.out.println("memberService -> memberRepository = " + memberRepository1);
        System.out.println("orderService -> memberRepository = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        //then




    }
}
