package jpa.study.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jpa.study.domain.Member;

@SpringBootTest
@Transactional
class MemberServiceTest {

	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;

	@Test
	public void 회원가입() throws Exception{
		Member member = new Member();
		member.setName("member1");

		Long memberId = memberService.join(member);
		Member joinMember = memberRepository.findOne(memberId);

		Assertions.assertThat(joinMember).isEqualTo(member);

	}

	@Test
	public void 중복_회원가입() throws Exception{
		
	}
}
