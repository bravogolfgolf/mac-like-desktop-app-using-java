package domain;

import data.PersonRepository;
import data.PersonRepositoryInMemory;
import domain.refresh.RefreshRequest;
import domain.refresh.RefreshResponse;
import domain.refresh.RefreshResponseRecord;
import domain.refresh.RefreshUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RefreshUseCaseTest implements Presenter {

    private Response response;

    @Override
    public void present(Response response) {
        this.response = response;
    }

    @Override
    public PersonRecord[] getViewModel() {
        return null;
    }

    private final PersonRepository repository = new PersonRepositoryInMemory();
    private final InputBoundary useCase = new RefreshUseCase(repository, this);
    private final Request request = new RefreshRequest();
    private final int ageCategory = 0, employmentStatus = 1;
    private final String fullName = "New Full Name", occupation = "New Occupation", taxId = "New Tax ID", gender = "Female";

    @Before
    public void setUp() {
        repository.addPerson(fullName, occupation, ageCategory,employmentStatus,true,taxId,gender);
    }

    @Test
    public void shouldProcessUpdateRequestIntoUpdateResponse() {
        useCase.execute(request);

        RefreshResponse r = (RefreshResponse) response;
        assertEquals(1, r.records.size());

        for (RefreshResponseRecord expected : r.records) {
            assertEquals(1, expected.id);
            assertEquals(fullName, expected.fullName);
            assertEquals(occupation, expected.occupation);
            assertEquals(ageCategory, expected.ageCategory);
            assertEquals(employmentStatus, expected.employmentStatus);
            assertTrue(expected.uSCitizen);
            assertEquals(taxId, expected.taxId);
            assertEquals(gender, expected.gender);
        }
    }
}
