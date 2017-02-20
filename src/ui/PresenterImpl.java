package ui;

import domain.AddPersonRequest;
import domain.MainFramePresenter;
import domain.Person;
import domain.Presenter;

import java.util.Map;

public class PresenterImpl implements Presenter {

    private final MainFramePresenter mainFrame;

    public PresenterImpl(MainFramePresenter mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void present(Map<Integer, Person> result) {
        AddPersonRequest[] response = new AddPersonRequest[result.size()];
        int i = 0;
        for (Person person : result.values()) {
            AddPersonRequest message = new AddPersonRequest();
            message.id = person.getId();
            message.fullName = person.getFullName();
            message.occupation = person.getOccupation();
            message.ageCategory = person.getAgeCategory();
            message.employmentStatus = person.getEmploymentStatus();
            message.uSCitizen = person.isUsCitizen();
            message.taxId = person.getTaxId();
            message.gender = person.getGender();
            response[i++] = message;
        }
        mainFrame.update(response);
    }
}


