package com.rent.service.user;

import com.rent.common.dto.user.UserEmergencyContactDto;

import java.util.List;

public interface UserEmergencyContactService {
    List<UserEmergencyContactDto> getUserEmergencyContacts(String uid,Integer checked);

    Boolean checkUserEmergencyContactCompleted(String uid);

    Boolean addUserEmergencyContact(UserEmergencyContactDto dto, String uid);
}
