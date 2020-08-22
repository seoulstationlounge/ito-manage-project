package com.itosideproject.modules.study;

import com.itosideproject.modules.account.Account;
import com.itosideproject.modules.account.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudyTest {

    Study study;
    Account account;
    UserAccount userAccount;

    @BeforeEach
    void beforeEach() {
        study = new Study();
        account = new Account();
        account.setNickname("keesun");
        account.setPassword("123");
        userAccount = new UserAccount(account);

    }

    @DisplayName("AIA_JOB를 공개했고 인원 모집 중이고, 이미 멤버나 AIA_JOB 관리자가 아니라면 AIA_JOB 가입 가능")
    @Test
    void isJoinable() {
        study.setPublished(true);
        study.setRecruiting(true);

        assertTrue(study.isJoinable(userAccount));
    }

    @DisplayName("AIA_JOB를 공개했고 인원 모집 중이더라도, AIA_JOB 관리자는 AIA_JOB 가입이 불필요하다.")
    @Test
    void isJoinable_false_for_manager() {
        study.setPublished(true);
        study.setRecruiting(true);
        study.addManager(account);

        assertFalse(study.isJoinable(userAccount));
    }

    @DisplayName("AIA_JOB를 공개했고 인원 모집 중이더라도, AIA_JOB 멤버는 AIA_JOB 재가입이 불필요하다.")
    @Test
    void isJoinable_false_for_member() {
        study.setPublished(true);
        study.setRecruiting(true);
        study.addMemeber(account);

        assertFalse(study.isJoinable(userAccount));
    }

    @DisplayName("AIA_JOB가 비공개거나 인원 모집 중이 아니면 AIA_JOB 가입이 불가능하다.")
    @Test
    void isJoinable_false_for_non_recruiting_study() {
        study.setPublished(true);
        study.setRecruiting(false);

        assertFalse(study.isJoinable(userAccount));

        study.setPublished(false);
        study.setRecruiting(true);

        assertFalse(study.isJoinable(userAccount));
    }

    @DisplayName("AIA_JOB 관리자인지 확인")
    @Test
    void isManager() {
        study.addManager(account);
        assertTrue(study.isManager(userAccount));
    }

    @DisplayName("AIA_JOB 멤버인지 확인")
    @Test
    void isMember() {
        study.addMemeber(account);
        assertTrue(study.isMember(userAccount));
    }

}