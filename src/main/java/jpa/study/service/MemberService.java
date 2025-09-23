package jpa.study.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpa.study.domain.Member;
import jpa.study.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	// 회원 가입
	public Long join(Member member) {
		validateDuplicateMember(member);
		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateMember(Member member) {
		List<Member> findMembers = memberRepository.findByName(member.getName());
		if (!findMembers.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}

	// 회원 전체 조회
	@Transactional(readOnly = true) // 읽기에는 readOnly = true 가 좋다.
	public List<Member> findAll(){
		return memberRepository.findAll();
	}

	// 회원 한명 조회
	@Transactional(readOnly = true)
	public Member findOne(Long memberId){
		return memberRepository.findOne(memberId);
	}

    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
