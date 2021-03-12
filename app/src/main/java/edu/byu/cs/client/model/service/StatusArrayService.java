package edu.byu.cs.client.model.service;

import java.io.IOException;

import com.example.shared.model.domain.User;
import com.example.shared.model.domain.Status;
import edu.byu.cs.client.model.net.ServerFacade;

import com.example.shared.model.service.IStatusArrayService;
import com.example.shared.model.service.request.IListRequest;
import com.example.shared.model.service.request.StatusArrayRequest;
import com.example.shared.model.service.response.IListResponse;
import com.example.shared.model.service.response.StatusArrayResponse;
import com.example.shared.model.service.IStatuses_Observer;
import edu.byu.cs.client.util.ByteArrayUtils;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class StatusArrayService implements IStatusArrayService {


    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link ServerFacade} to
     * get the followees from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public StatusArrayResponse requestStatusArray(IListRequest request, IStatuses_Observer statuses_observer) {
        StatusArrayResponse response = new StatusArrayResponse("Statuses missing");
        try {
            if (request.getClass() != StatusArrayRequest.class) {
                return response;
            }
            StatusArrayRequest req = (StatusArrayRequest) request;
            response = getServerFacade().getStatusArray(req, statuses_observer);

            if (response.isSuccess()) {
                loadImages(response);
            }
        } catch (IOException e) {
            response = new StatusArrayResponse("Statuses missing error");
            return response;
        }

        return response;
    }

    @Override
    public IListResponse getList(IListRequest listRequest, IStatuses_Observer statuses_observer) {
        return requestStatusArray(listRequest, statuses_observer);
    }

    /**
     * Loads the profile image data for each followee included in the response.
     *
     * @param response the response from the followee request.
     */
    private void loadImages(StatusArrayResponse response) throws IOException {
        for(Status status : response.getStatuses()) {
            User user = status.getCorrespondingUser();
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }
    }

    /**
     * Returns an instance of {@link ServerFacade}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    ServerFacade getServerFacade() {
        return new ServerFacade();
    }

}
