package com.example.Birthday_JobAnniversary_WisherBackend.Models.Enums;

public enum EventSubject {
    BIRTHDAY_WISHES {
        @Override
        public String toString() {
            return "BIRTHDAY_WISHES";
        }
    },
    JOB_ANNIVERSARY_WISHES {
        @Override
        public String toString() {
            return "JOB_ANNIVERSARY_WISHES";
        }
    }
}
