package database;

import databasegateway.PersonRepository;
import entity.PersonTemplate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PersonRepositoryMySQLTest {

    private final PersonRepository repository = new PersonRepositoryMySQL();

    private Map<Integer, PersonTemplate> people = new HashMap<>();

    @Before
    public void setUp() {
        clearRepository();
    }

    @After
    public void tearDown() {
        clearRepository();
    }

    private void clearRepository() {
        String url = "jdbc:mysql://localhost:3306/people?useSSL=true";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, "briangibson", "sKzuP3RMF");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PreparedStatement statement = null;
        try {
            statement = connection != null ? connection.prepareStatement("delete from person") : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (statement != null) statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void newDatabaseShouldBeEmpty() {
        people = repository.getPeople();
        assertEquals(0, people.size());
    }

    @Test
    public void newDatabaseShouldExceptPerson() {
        repository.addPerson("Full Name", "Occupation", 0, 0, false,
                "Tax ID", "Male");
        people = repository.getPeople();
        assertEquals(1, people.size());
    }

    @Test
    public void shouldBeAbleToUpdatePeopleWithSameID() {
        repository.addPerson("Full Name", "Occupation", 0, 0, false,
                "Tax ID", "Male");
        repository.updatePerson(1, "Update Name", "Update", 1, 1, true,
                "Update ID", "Female");
        people = repository.getPeople();
        assertEquals(1, people.size());
        for (PersonTemplate person : people.values()) {
            assertEquals(1, person.getId());
            assertEquals("Update Name", person.getFullName());
            assertEquals("Update", person.getOccupation());
            assertEquals(1, person.getAgeCategory());
            assertEquals(1, person.getEmploymentStatus());
            assertTrue(person.isUsCitizen());
            assertEquals("Update ID", person.getTaxId());
            assertEquals("Female", person.getGender());
        }
    }

    @Test
    public void shouldBeAbleToDeletePerson() {
        int id = 2;
        repository.addPerson("Full Name", "Occupation", 0, 0, false,
                "Tax ID", "Male");
        repository.addPerson("Full Name", "Occupation", 0, 2, true,
                "Tax ID", "Female");
        assertEquals(2, repository.getPeople().size());
        repository.deletePerson(id);
        assertEquals(1, repository.getPeople().size());
    }

    @Test
    public void shouldBeAbleToReplaceRepository() {
        repository.addPerson("Full Name", "Occupation", 0, 0, false,
                "Tax ID", "Male");
        Map<Integer, PersonTemplate> expected = new HashMap<>(repository.getPeople());
        assertEquals(1, expected.size());

        repository.addPerson("Full Name", "Occupation", 0, 2, true,
                "Tax ID", "Female");
        Map<Integer, PersonTemplate> updated = repository.getPeople();
        assertEquals(2, updated.size());

        repository.setPeople(expected);
        Map<Integer, PersonTemplate> actual = repository.getPeople();
        assertEquals(1, actual.size());
    }
}
