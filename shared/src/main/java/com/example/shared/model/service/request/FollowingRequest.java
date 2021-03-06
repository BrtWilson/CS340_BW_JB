package com.example.shared.model.service.request;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followees for a specified user.
 */
public class FollowingRequest {
    
    private  String userAlias;
    private  Integer limit;
    private  String lastFolloweeAlias;
    private  boolean isNumFollowingRequest;

    /**
     * Creates an instance.
     *
     * @param userAlias the alias of the user whose followees are to be returned.
     * @param limit the maximum number of followees to return.
     * @param lastFolloweeAlias the alias of the last followee that was returned in the previous request (null if
     *                     there was no previous request or if no followees were returned in the
     *                     previous request).
     */

    public FollowingRequest(String userAlias, int limit, String lastFolloweeAlias) {
        this.userAlias = userAlias;
        this.limit = limit;
        this.lastFolloweeAlias = lastFolloweeAlias;
        this.isNumFollowingRequest = false;
    }

    public FollowingRequest() {

    }


    public FollowingRequest(String userAlias, boolean isNumFollowingRequest) {
        this.userAlias = userAlias;
        this.isNumFollowingRequest = isNumFollowingRequest;
    }

    /**
     * Returns the user whose followees are to be returned by this request.
     *
     * @return the user.
     */
    public String getFollowingAlias() {
        return userAlias;
    }

    /**
     * Returns the number representing the maximum number of followees to be returned by this request.
     *
     * @return the limit.
     */
    public int getLimit() {
        return this.limit;
    }

    /**
     * Returns the last followee that was returned in the previous request or null if there was no
     * previous request or if no followees were returned in the previous request.
     *
     * @return the last followee.
     */
    public String getLastFolloweeAlias() {
        return lastFolloweeAlias;
    }

    public boolean isNumFollowingRequest() { return isNumFollowingRequest; }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setLastFolloweeAlias(String lastFolloweeAlias) {
        this.lastFolloweeAlias = lastFolloweeAlias;
    }

    public void setNumFollowingRequest(boolean numFollowingRequest) {
        isNumFollowingRequest = numFollowingRequest;
    }
}
