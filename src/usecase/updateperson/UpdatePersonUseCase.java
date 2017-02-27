package usecase.updateperson;

import databasegateway.UpdatePersonGateway;
import requestor.Request;
import requestor.UseCase;

public class UpdatePersonUseCase extends UseCase {
    private final UpdatePersonGateway repository;

    public UpdatePersonUseCase(UpdatePersonGateway repository) {
        this.repository = repository;
    }

    public void execute(Request request) {
        UpdatePersonRequest r = (UpdatePersonRequest) request;
        repository.updatePerson(r.id, r.fullName, r.occupation, r.ageCategory, r.employmentStatus, r.uSCitizen, r.taxId, r.gender);
    }
}
