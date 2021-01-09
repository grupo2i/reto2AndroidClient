package com.example.reto2androidclient.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Superclass of all type of users, contains common attributes.
 *
 * @author Aitor Fidalgo
 */
public class User implements Serializable {

    /**
     * Used to identify Users.
     */
    protected Integer id;
    /**
     * Unique name of the User in the system.
     */
    private String login;
    /**
     * Email of the User.
     */
    private String email;
    /**
     * Full and real name of the User.
     */
    private String fullName;
    /**
     * Brief description the Users writes about themselves.
     */
    private String biography;
    /**
     * Two possible value enum that defines the Users status.
     */
    private UserStatus userStatus;
    /**
     * Enum that defines the type of User.
     */
    private UserPrivilege userPrivilege;
    /**
     * Credential of the User.
     */
    private String password;
    /**
     * Specifies the last time the User loged in into the system.
     */
    private Date lastAccess;
    /**
     * Specifies the last time the User chaged their password.
     */
    private Date lastPasswordChange;
    /**
     * Name of the profile image of the User.
     */
    private String profileImage;

    /**
     * Ratings of an event made by a User.
     *
     * The relation was supposed to be between Client and Rating but due to an
     * Hibernate bug it can't be done. See more <a href="https://discourse.hibernate.org/t/embededid-containing-a-foreign-key-of-an-entity-with-inheritance/2334">here</a>
     */
    private Set<Rating> ratings;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public UserPrivilege getUserPrivilege() {
        return userPrivilege;
    }

    public void setUserPrivilege(UserPrivilege userPrivilege) {
        this.userPrivilege = userPrivilege;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    public Date getLastPasswordChange() {
        return lastPasswordChange;
    }

    public void setLastPasswordChange(Date lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.User[ id=" + id + " ]";
    }

}
