package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * 여러 빈을 등록 해 두고, 조회한 빈이 모두 필요한 상황
 */
public class AllBeanTest {

    @Test
    void findAllBean(){

        //given
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);
        //when
        int discountedPrice= discountService.discount(member, 10000, "fixDiscountPolicy");
        int discountedPrice2 = discountService.discount(member,20000,"rateDiscountPolicy");
        //then
        Assertions.assertThat(discountService).isInstanceOf(DiscountService.class);

        Assertions.assertThat(discountedPrice).isEqualTo(1000);
        Assertions.assertThat(discountedPrice2).isEqualTo(2000);
    }

    static class DiscountService{
        private final Map<String, DiscountPolicy> discountPolicyMap;
        private final List<DiscountPolicy> discountPolicyList;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> discountPolicyMap, List<DiscountPolicy> discountPolicyList) {
            this.discountPolicyMap = discountPolicyMap;
            this.discountPolicyList = discountPolicyList;
            System.out.println("discountPolicyMap = " + discountPolicyMap);
            System.out.println("discountPolicyList = " + discountPolicyList);
        }

        public int discount(Member member, int price, String discountCode) {

            DiscountPolicy discountPolicy = discountPolicyMap.get(discountCode);

            System.out.println("discountCode = " + discountCode);
            System.out.println("discountPolicy = " + discountPolicy);

            return discountPolicy.discount(member,price);
        }
    }
}
