package com.example.reto2androidclient.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Set;

/**
 * Superclass of all type of users, contains common attributes.
 *
 * @author Aitor Fidalgo
 */
@Root(name="user")
public class User implements Serializable {

    /**
     * Used to identify Users.
     */
    @Element(name="id", required=false)
    protected Integer id;
    /**
     * Unique name of the User in the system.
     */
    @Element(name="login", required=false)
    private String login;
    /**
     * Email of the User.
     */
    @Element(name="email", required=false)
    private String email;
    /**
     * Full and real name of the User.
     */
    @Element(name="fullName", required=false)
    private String fullName;
    /**
     * Brief description the Users writes about themselves.
     */
    @Element(name="biography", required=false)
    private String biography;
    /**
     * Two possible value enum that defines the Users status.
     */
    @Element(name="userStatus", required=false)
    private UserStatus userStatus;
    /**
     * Enum that defines the type of User.
     */
    @Element(name="userPrivilege", required=false)
    private UserPrivilege userPrivilege;
    /**
     * Credential of the User.
     */
    @Element(name="password", required=false)
    private String password;
    /**
     * Specifies the last time the User loged in into the system.
     */
    @Element(name="lastAccess", required=false)
    private String lastAccess;
    /**
     * Specifies the last time the User chaged their password.
     */
    @Element(name="lastPasswordChange", required=false)
    private String lastPasswordChange;
    /**
     * Name of the profile image of the User.
     */
    @Element(name="profileImage", required=false)
    private String profileImage;

    /**
     * Ratings of an event made by a User.
     *
     * The relation was supposed to be between Client and Rating but due to an
     * Hibernate bug it can't be done. See more <a href="https://discourse.hibernate.org/t/embededid-containing-a-foreign-key-of-an-entity-with-inheritance/2334">here</a>
     */
    @ElementList(entry="ratings", required=false, inline=true)
    private Set<Rating> ratings;


    /**
     * @return Id of the User.
     */
    public Integer getId() {
        return id;
    }
    /**
     * Sets the value of the id.
     * @param id The value.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * @return The login of the User.
     */
    public String getLogin() {
        return login;
    }
    /**
     * Sets the value of the login.
     * @param login The value.
     */
    public void setLogin(String login) {
        this.login = login;
    }
    /**
     * @return The email of he User.
     */
    public String getEmail() {
        return email;
    }
    /**
     * Sets the value of the email.
     * @param email The value.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return The full name of the User.
     */
    public String getFullName() {
        return fullName;
    }
    /**
     * Sets the value of the full name.
     * @param fullName The value.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    /**
     * @return The biography of the User.
     */
    public String getBiography() {
        return biography;
    }
    /**
     * Sets the value of the biography.
     * @param biography The value.
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }
    /**
     * @return The status of the User.
     */
    public UserStatus getUserStatus() {
        return userStatus;
    }
    /**
     * Sets the value of the user status.
     * @param userStatus The value.
     */
    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
    /**
     * @return The user privilege.
     */
    public UserPrivilege getUserPrivilege() {
        return userPrivilege;
    }
    /**
     * Sets the value of the user privilege.
     * @param userPrivilege The value.
     */
    public void setUserPrivilege(UserPrivilege userPrivilege) {
        this.userPrivilege = userPrivilege;
    }
    /**
     * @return The password of the User.
     */
    public String getPassword() {
        return password;
    }
    /**
     * Sets the value of the password.
     * @param password The value.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return The last access of the User.
     */
    public String getLastAccess() {
        return lastAccess;
    }
    /**
     * Sets the value of the last access.
     * @param lastAccess The value.
     */
    public void setLastAccess(String lastAccess) {
        this.lastAccess = lastAccess;
    }
    /**
     * @return The last password change of the User.
     */
    public String getLastPasswordChange() {
        return lastPasswordChange;
    }
    /**
     * Sets the value of the last password change.
     * @param lastPasswordChange The value.
     */
    public void setLastPasswordChange(String lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }
    /**
     * @return The profile image name of the User.
     */
    public String getProfileImage() {
        return profileImage;
    }
    /**
     * Sets the value of the profile image name.
     * @param profileImage The value.
     */
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
    /**
     * @return The ratings made by the User.
     */
    public Set<Rating> getRatings() {
        return ratings;
    }
    /**
     * Sets the value of the ratings.
     * @param ratings The value.
     */
    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

}
