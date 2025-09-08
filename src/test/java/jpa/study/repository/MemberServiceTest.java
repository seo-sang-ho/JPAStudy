package jpa.study.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jpa.study.domain.Member;
import jpa.study.service.MemberService;

@SpringBootTest
@Transactional
class MemberServiceTest {

	@Autowired
	MemberService memberService;
	@Autowired MemberRepository memberRepository;

	@Test
	void 회원가입() throws Exception{
		Member member = new Member();
		member.setName("member1");

		Long memberId = memberService.join(member);
		Member joinMember = memberRepository.findOne(memberId);

		Assertions.assertThat(joinMember).isEqualTo(member);

	}

	@Test
	void 중복_회원가입() throws Exception{
		Member member1 = new Member();
		member1.setName("member1");

		Member member2 = new Member();
		member2.setName("member1");

		memberService.join(member1);

		org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(member2));
	}
}
