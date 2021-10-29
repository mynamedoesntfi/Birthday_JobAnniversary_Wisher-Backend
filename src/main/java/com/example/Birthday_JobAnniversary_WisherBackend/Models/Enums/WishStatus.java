package com.example.Birthday_JobAnniversary_WisherBackend.Models.Enums;

public enum WishStatus {
    PENDING {
        @Override
        public String toString() {
            return "PENDING";
        }
    },
    SENT {
        @Override
        public String toString() {
            return "SENT";
        }
    }
}
