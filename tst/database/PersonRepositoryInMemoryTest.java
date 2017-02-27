package database;

import databasegateway.PersonRepository;
import entity.PersonTemplate;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PersonRepositoryInMemoryTest {

    private final PersonRepository repository = new PersonRepositoryInMemory();
    private final PersonTemplate person1 = new Person(1, "Update Name", "Update", 1, 1, true,
            "Update ID", "Female");
    private List<PersonTemplate> people = new ArrayList<>();

    @Test
    public void newDatabaseShouldBeEmpty() {
        people = repository.findAll();
        assertEquals(0, people.size());
    }

    @Test
    public void newDatabaseShouldExceptPerson() {
        repository.addPerson("Full Name", "Occupation", 0, 0, false,
                "Tax ID", "Male");
        people = repository.findAll();
        assertEquals(1, people.size());
    }

    @Test
    public void shouldBeAbleToUpdatePeopleWithSameID() {
        repository.addPerson("Full Name", "Occupation", 0, 0, false,
                "Tax ID", "Male");
        repository.updatePerson(1, "Update Name", "Update", 1, 1, true,
                "Update ID", "Female");
        people = repository.findAll();
        assertEquals(1, people.size());
        for (PersonTemplate person : people) {
            assertEquals(person.getId(), person1.getId());
            assertEquals(person.getFullName(), person1.getFullName());
            assertEquals(person.getOccupation(), person1.getOccupation());
            assertEquals(person.getAgeCategory(), person1.getAgeCategory());
            assertEquals(person.getEmploymentStatus(), person1.getEmploymentStatus());
            assertTrue(person.isUsCitizen());
            assertEquals(person.getTaxId(), person1.getTaxId());
            assertEquals(person.getGender(), person1.getGender());
        }
    }

    @Test
    public void shouldBeAbleToDeletePerson() {
        int id = 2;
        repository.addPerson("Full Name", "Occupation", 0, 0, false,
                "Tax ID", "Male");
        repository.addPerson("Updated Name", "Occupation", 0, 2, true,
                "Tax ID", "Female");
        assertEquals(2, repository.findAll().size());
        repository.deletePerson(id);
        assertEquals(1, repository.findAll().size());
    }
}