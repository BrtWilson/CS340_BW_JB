package edu.byu.cs.client.presenter;

import java.io.IOException;
import edu.byu.cs.client.model.service.NewStatusService;

import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.response.NewStatusResponse;

public class NewStatusPresenter {

    private final  LoginPresenter.View view;
    private NewStatusService newStatusService;

    public interface View { }

    public NewStatusPresenter( LoginPresenter.View view) { this.view = view; }

    public NewStatusResponse newStatus(NewStatusRequest newStatusRequest) throws IOException, TweeterRemoteException {
        newStatusService = getNewStatusService();
        return newStatusService.postNewStatus(newStatusRequest);
    }

    public NewStatusService getNewStatusService() {
        if (newStatusService == null) {
            newStatusService = new NewStatusService();
        }
        return newStatusService;
    }
}