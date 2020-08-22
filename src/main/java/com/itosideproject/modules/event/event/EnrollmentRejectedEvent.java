package com.itosideproject.modules.event.event;


import com.itosideproject.modules.event.Enrollment;

public class EnrollmentRejectedEvent extends EnrollmentEvent {

    public EnrollmentRejectedEvent(Enrollment enrollment) {
        super(enrollment, "처리 참가 신청을 거절했습니다.");
    }
}
