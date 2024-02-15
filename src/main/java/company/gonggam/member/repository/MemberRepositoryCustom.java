package company.gonggam.member.repository;

import company.gonggam.member.domain.Member;

import java.util.Optional;

public interface MemberRepositoryCustom {

    Optional<Member> findByEmail(String email);
}
