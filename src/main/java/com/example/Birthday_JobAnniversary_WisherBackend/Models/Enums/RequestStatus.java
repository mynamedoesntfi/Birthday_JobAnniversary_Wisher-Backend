package com.example.Birthday_JobAnniversary_WisherBackend.Models.Enums;

public enum RequestStatus {
    PENDING {
        @Override
        public String toString() {
            return "PENDING";
        }
    },
    APPROVED {
        @Override
        public String toString() {
            return "APPROVED";
        }
    },
    DECLINED {
        @Override
        public String toString() {
            return "DECLINED";
        }
    }
}
