package com.example.reto2androidclient.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Contains a Clients evaluation of an Event.
 *
 * @see User
 * @see Client
 * @see Event
 * @author Aitor Fidalgo
 */
@Root(name="rating")
public class Rating implements Serializable {

    /**
     * Compound id used to identify the rating.
     */
    @Element(name="id")
    private RatingId ratingId;
    /**
     * User that rated the Event.
     *
     * The attribute is of the superclass type User because the subclass Client
     * does not have an id annotation and so is not a valid mapping and an
     * exception is thrown when deploying.
     *
     * See more
     * <a href="https://discourse.hibernate.org/t/embededid-containing-a-foreign-key-of-an-entity-with-inheritance/2334">here</a>
     */
    @Element(name="client", required=false)
    private User client;
    /**
     * Event that has been rated.
     */
    @Element(name="event", required=false)
    private Event event;
    /**
     * Brief commentary about an Event made by a User.
     */
    @Element(name="comment")
    private String comment;
    /**
     * Numeric evaluation of an Event made by a User.
     */
    @Element(name="rating")
    private Integer rating;

    /**
     * @return id of the event.
     */
    public RatingId getId() {
        return ratingId;
    }

    /**
     * Sets the id of the rating.
     *
     * @param ratingId value to be set.
     */
    public void setId(RatingId ratingId) {
        this.ratingId = ratingId;
    }

    /**
     * @return The User that made the rating.
     */
    public User getClient() {
        return client;
    }

    /**
     * Sets the User that made the rating.
     *
     * @param client User that rated the Event.
     */
    public void setClient(User client) {
        this.client = client;
    }

    /**
     * @return The Event that has been rated.
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Sets the Event that has been rated.
     *
     * @param event Event that has been rated.
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * @return The comment made by the User.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment.
     *
     * @param comment The comment made by the User.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return The numeric evaluation made by the User.
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * Sets the value of the numeric rating.
     *
     * @param rating Numeric rating made by the User.
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ratingId != null ? ratingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the ratingId fields are not set
        if (!(object instanceof Rating)) {
            return false;
        }
        Rating other = (Rating) object;
        if ((this.ratingId == null && other.ratingId != null) || (this.ratingId != null && !this.ratingId.equals(other.ratingId))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "entity.Rating[ id=" + ratingId + " ]";
    }

}
