package edu.byu.cs.client.presenter;

import java.io.IOException;

import edu.byu.cs.client.model.service.IStatusArrayService;
import edu.byu.cs.client.model.service.StatusArrayService;
import edu.byu.cs.client.model.service.UserService;
import com.example.shared.model.service.request.IListRequest;
import com.example.shared.model.service.request.StatusArrayRequest;
import com.example.shared.model.service.request.UserRequest;
import com.example.shared.model.service.response.IListResponse;
import com.example.shared.model.service.response.StatusArrayResponse;
import com.example.shared.model.service.response.UserResponse;

/**
 * The presenter for the "following" functionality of the application.
 */
public class StatusArrayPresenter implements IStatuses_Observer{
    protected final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
        void update();
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public IListResponse getDataList(IListRequest request, IStatuses_Observer statuses_observer) throws IOException {
        IStatusArrayService listService = getListService();
        return listService.getList(request, statuses_observer);
    }
    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public StatusArrayPresenter(View view) {
        this.view = view;
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public StatusArrayResponse getStatusArray(StatusArrayRequest request) throws IOException {
        StatusArrayService statusArrayService = getStatusArrayService();
        return statusArrayService.requestStatusArray(request, this);
    }

    /**
     * Returns an instance of {@link StatusArrayService}. Allows mocking of the FollowingService class
     * for testing purposes. All usages of FollowingService should get their FollowingService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    StatusArrayService getStatusArrayService() {
        return new StatusArrayService();
    }

    IStatusArrayService getListService() {
        return (IStatusArrayService) getStatusArrayService();
    }

    @Override
    public void Update() {
        view.update();
    }


    public UserResponse getUserByAlias(UserRequest request) throws IOException {
        UserService userService = getUserService();
        return userService.getUserByAlias(request);
    }

    private UserService getUserService() {
        return new UserService();
    }
}