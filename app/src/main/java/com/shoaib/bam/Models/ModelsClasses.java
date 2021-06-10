package com.shoaib.bam.Models;

public class ModelsClasses {

    public static class UserModel{
        public String key;
        public String companyId;
        public String userId;
        public String roomId;
        public String officeId;
        public String createdById;
        public String userFullName;
        public String username;
        public String userPhone;
        public String userLocationTrackingId;
        public String userAddress;
        public String createdAt;
        public String userEmail;
        public String userDesignation;
        public String userProfileImageUrl;
        public String meetingStatus;
        public String password;
        public String userType;
        public String onlineStatus;
        public String currentLat;
        public String currentLng;
        public String FID;
        public String userStatus;

        public UserModel(){}

        public UserModel(String key,String companyId,String userId, String roomId,
                         String username,String userEmail, String password, String userType,
                         String userPhone, String createdById,String officeId,
                         String userFullName, String userLocationTrackingId,String userAddress,
                         String createdAt,String userDesignation,String userProfileImageUrl,
                         String onlineStatus,String currentLat,
                         String currentLng,String FID,
                         String userStatus) {

            this.key = key;
            this.companyId = companyId;
            this.userId = userId;
            this.roomId = roomId;
            this.userPhone = userPhone;
            this.createdById = createdById;
            this.officeId = officeId;
            this.userFullName = userFullName;
            this.username = username;
            this.userLocationTrackingId = userLocationTrackingId;
            this.userAddress = userAddress;
            this.createdAt = createdAt;
            this.userEmail = userEmail;
            this.userDesignation = userDesignation;
            this.userProfileImageUrl = userProfileImageUrl;
            this.meetingStatus = meetingStatus;
            this.password = password;
            this.userType = userType;
            this.onlineStatus = onlineStatus;
            this.currentLat = currentLat;
            this.currentLng = currentLng;
            this.FID = FID;
            this.userStatus = userStatus;


        }
    }
}
