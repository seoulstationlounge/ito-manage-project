package com.itosideproject.modules.event.event;


import com.itosideproject.modules.event.Enrollment;

public class EnrollmentAcceptedEvent extends EnrollmentEvent{

    public EnrollmentAcceptedEvent(Enrollment enrollment) {
        super(enrollment, "처리 참가 신청을 확인했습니다. 처리에 참석하세요.");
    }

}
