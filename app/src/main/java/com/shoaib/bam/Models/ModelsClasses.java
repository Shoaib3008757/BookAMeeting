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

    public static class OfficeModel{
        public String officeId;
        public String name;
        public String address;
        public String lat;
        public String lng;
        public String bookingStatus;
        public String ownerName;
        public String companyName;
        public String openingTiming;
        public String closingTiming;
        public String OfficeNumber;

        public  OfficeModel(){}
        public OfficeModel(String officeId, String name, String address,
                 String lat, String lng, String bookingStatus,
                 String ownerName, String companyName, String openingTiming,
                            String closingTiming, String OfficeNumber)
        {
            this.officeId = officeId;
            this.name = name;
            this.address = address;
            this.lat = lat;
            this.lng = lng;
            this.bookingStatus = bookingStatus;
            this.ownerName = ownerName;
            this.companyName = companyName;
            this.openingTiming = openingTiming;
            this.closingTiming = closingTiming;
            this.OfficeNumber = OfficeNumber;


        }
    }

    public static class RoomModel
    {
        public String roomId;
        public String roomName;
        public String PhoneNumber;
        public String bookingStatus;
        public String roomNumber;
        public String bookedTime;
        public String openingTiming;
        public String clossingTiming;
        public String bookedById;
        public String BookedByName;
        public String BookFromTime;
        public String BookToTime;
        public String BookFromDate;
        public String BookToDate;
        public String officeName;
        public String officeId;


        public  RoomModel(){}

        public RoomModel(String roomId, String bookingStatus, String roomNumber,
                 String bookedTime, String openingTiming, String clossingTiming,
                 String bookedById, String BookedByName, String BookFromTime,
                 String BookToTime, String BookFromDate, String BookToDate,
                         String roomName, String PhoneNumber, String officeName, String officeId)
        {
            this.roomId = roomId;
            this.bookingStatus = bookingStatus;
            this.roomNumber = roomNumber;
            this.bookedTime = bookedTime;
            this.openingTiming = openingTiming;
            this.clossingTiming = clossingTiming;
            this.bookedById = bookedById;
            this.BookedByName = BookedByName;
            this.BookFromTime = BookFromTime;
            this.BookToTime = BookToTime;
            this.BookFromDate = BookFromDate;
            this.BookToDate = BookToDate;
            this.officeName = officeName;
            this.officeId = officeId;
            this.roomName = roomName;
            this.PhoneNumber = PhoneNumber;

        }
    }

    public static class ScheduledMeetings
    {
        public String meetingId;
        public String roomId;
        public String roomName;
        public String bookedtimeStemp;
        public String bookStartDate;
        public String bookStartTime;
        public String bookendEndDate;
        public String bookEndTime;
        public String bookingDurationTimeStemp;
        public String bookingDuration;
        public String bookByName;
        public String bookById;
        public String officeId;
        public String officeName;
        public String meetingStatus;
        public String meetingDescription;



        public ScheduledMeetings(){}

        public ScheduledMeetings(String meetingId, String roomId, String roomName,
                                 String bookedtimeStemp, String bookStartDate, String bookStartTime,
                                 String bookendEndDate, String bookEndTime, String bookingDurationTimeStemp,
                                 String bookingDuration, String bookByName, String bookById,
                                 String officeId, String officeName, String meetingStatus,
                                 String meetingDescription)
        {
            this.meetingId = meetingId;
            this.roomId = roomId;
            this.roomName = roomName;
            this.bookedtimeStemp = bookedtimeStemp;
            this.bookStartDate = bookStartDate;
            this.bookStartTime = bookStartTime;
            this.bookendEndDate = bookendEndDate;
            this.bookEndTime = bookEndTime;
            this.bookingDurationTimeStemp = bookingDurationTimeStemp;
            this.bookingDuration = bookingDuration;
            this.bookByName = bookByName;
            this.bookById = bookById;
            this.officeId = officeId;
            this.officeName = officeName;
            this.meetingStatus  = meetingStatus;
            this.meetingDescription  = meetingDescription;

        }

    }
}
