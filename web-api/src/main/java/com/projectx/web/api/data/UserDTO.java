package com.projectx.web.api.data;

import com.projectx.logic.api.data.User;
import com.projectx.sdk.user.impl.BasicUser;

import java.util.List;

/**
 * Created by amoldavsky on 9/29/16.
 */
public class UserDTO extends BasicUser implements com.projectx.sdk.user.User {

    public UserDTO() { super(); }

    public UserDTO( User apiUser ) {

        setId( apiUser.getId() );
        setProfileImageUrl( apiUser.getProfileImageUrl() );
        setUsername( apiUser.getUsername() );
        setFirstName( apiUser.getFirstName() );
        setLastName( apiUser.getLastName() );
        //setEmail( apiUser.getEmail() );
        setEmail( null );
        setDateCreated( apiUser.getDateCreated() );

    }

    final String email = null;

//    List<PlaybookDTO> playbooks;
//
//    public List<PlaybookDTO> getPlaybooks() {
//        return playbooks;
//    }
//
//    public void setPlaybooks( List<PlaybookDTO> playbooks ) {
//        this.playbooks = playbooks;
//    }
}
