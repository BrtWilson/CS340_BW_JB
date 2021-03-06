package server.dao;

import com.example.server.dao.StoryTableDAO;
import com.example.server.dao.dbstrategies.DynamoDBStrategy;
import com.example.server.dao.dbstrategies.ResultsPage;
import com.example.server.service.StatusArrayService;
import com.example.shared.model.domain.Status;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.request.StatusArrayRequest;
import com.example.shared.model.service.response.NewStatusResponse;
import com.example.shared.model.service.response.StatusArrayResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;


public class StoryDaoTest {

    private NewStatusRequest validRequest1;
    private NewStatusRequest validRequest2;

    private NewStatusResponse successResponse1;
    private NewStatusResponse successResponse2;

    private StatusArrayRequest validArrayRequest;
    private StatusArrayRequest invalidArrayRequest;

    private StatusArrayResponse successArrayResponse;
    private StatusArrayResponse failureArrayResponse;

    private StoryTableDAO statusesDAO;
    private DynamoDBStrategy mockDatabaseInteractor;

    @BeforeEach
    public void setup() {
        User user1 = new User("First", "Last", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user1.setPassword("password");

        User user2 = new User("User", "Name", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        user1.setPassword("password");
        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        Status resultStatus1 = new Status("Message 1", "TimeStamp1", user1.getAlias());
        Status resultStatus2 = new Status("Message 2", "TimeStamp2", user2.getAlias());
        Status resultStatus3 = new Status("Message 3", "TimeStamp3", resultUser3.getAlias());

        validRequest1 = new NewStatusRequest( user1.getAlias(), "Message 1", "TimeStamp1");
        validRequest2 = new NewStatusRequest( user2.getAlias(), "Message 2", "TimeStamp2");

        successResponse1 = new NewStatusResponse(resultStatus1);
        successResponse2 = new NewStatusResponse(resultStatus2);
        StoryTableDAO mockDao = Mockito.mock(StoryTableDAO.class);
//        mockDatabaseInteractor = Mockito.mock(DynamoDBStrategy.class);
//        Mockito.when(mockDao.getDatabaseInteractor()).thenReturn(mockDatabaseInteractor);
      //  Mockito.when(mockDao.postNewStatus(validRequest1)).thenReturn(successResponse1);
      //  Mockito.when(mockDao.postNewStatus(validRequest2)).thenReturn(successResponse2);

        // Setup request objects to use in the tests
        validArrayRequest = new StatusArrayRequest("@HarryPotter", 3, null);
        invalidArrayRequest = new StatusArrayRequest(null, 0, null);

        // Setup a mock ServerFacade that will return known responses
        successArrayResponse = new StatusArrayResponse(Arrays.asList(resultStatus1, resultStatus2, resultStatus3), true, null);
        //Mockito.when(mockDao.getStatusArray(validArrayRequest)).thenReturn(successArrayResponse);

        failureArrayResponse = new StatusArrayResponse("An exception occurred");
        //Mockito.when(mockDao.getStatusArray(invalidArrayRequest)).thenReturn(failureArrayResponse);

        statusesDAO = Mockito.spy(new StoryTableDAO());

        //ResultsPage mockResultsPage
    }

    // STATUS POST Tests
    @Test
    public void testPostStatus_validRequest_correctResponse() throws IOException {
        NewStatusResponse response = statusesDAO.postNewStatus(validRequest1);
        System.out.println(response.getNewStatus());
        Assertions.assertEquals(successResponse1.isSuccess(), response.isSuccess());
        Assertions.assertEquals(successResponse1.getNewStatus().getMessage(), response.getNewStatus().getMessage());
    }

    // STATUS ARRAY GET Tests
    /**
     * Verify that for successful requests the {@link StatusArrayService #getStatusArray(StatusArrayRequest)}
     * method returns the same result as the {@link StoryTableDAO}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetStatusArray_validRequest_correctResponse() throws IOException {
        StatusArrayResponse response = statusesDAO.getStatusArray(validArrayRequest);
        Assertions.assertEquals(successArrayResponse.getStatuses().size(), response.getStatuses().size());
        Assertions.assertEquals(successArrayResponse.getHasMorePages(), response.getHasMorePages());
    }

    /**
     * Verify that the {@link StatusArrayService #StoryTableDAO(StatusArrayRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetStatusArray_validRequest_loadsProfileImages() throws IOException {
        //Mockito.when(mockDatabaseInteractor.getListByString(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        StatusArrayResponse response = statusesDAO.getStatusArray(validArrayRequest);

        for(Status status : response.getStatuses()) {
            Assertions.assertNotNull(status.getCorrespondingUserAlias());
        }
    }

    /**
     * Verify that for failed requests the {@link StatusArrayService #requestStatusArray(StatusArrayRequest)}
     * method returns the same result as the {@link StoryTableDAO}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetStatusArray_invalidRequest_returnsNoFollowers() throws IOException {
        // Should throw error:
        try {
            StatusArrayResponse response = statusesDAO.getStatusArray(invalidArrayRequest);
        } catch (AssertionError e) {
            Assertions.assertEquals(e.getMessage(), new AssertionError().getMessage());
        }
        //Assertions.assertThrows(AssertionError.class, statusesDAO.getStatusArray(invalidArrayRequest));
        //Assertions.assertEquals(failureArrayResponse.getStatuses(), response.getStatuses());
    }
}
