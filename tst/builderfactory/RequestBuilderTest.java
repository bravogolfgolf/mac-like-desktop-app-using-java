package builderfactory;

import org.junit.Before;
import org.junit.Test;
import usecase.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RequestBuilderTest {
    private final Map<String, Class> requests = new HashMap<>();
    private final HashMap<Integer, Object> args = new HashMap<>();
    private RequestBuilder builder;

    @Before
    public void setUp() {
        requests.put("Refresh", RefreshRequest.class);
        requests.put("AddPerson", AddPersonRequest.class);
        requests.put("DeletePerson", DeletePersonRequest.class);
        requests.put("Export", ExportRequest.class);
        requests.put("Import", ImportRequest.class);
        requests.put("UpdatePerson", UpdatePersonRequest.class);
        builder = new RequestBuilder(requests);
    }

    @Test
    public void makeMethodShouldReturnRefreshRequest() {
        RefreshRequest request = (RefreshRequest) builder.make("Refresh", args);
        assertNotNull(request);
    }

    @Test
    public void makeMethodShouldReturnAddPersonRequest() {
        args.put(0, "Full Name");
        args.put(1, "Occupation");
        args.put(2, 0);
        args.put(3, 0);
        args.put(4, true);
        args.put(5, "Tax ID");
        args.put(6, "Gender");

        AddPersonRequest request = (AddPersonRequest) builder.make("AddPerson", args);
        assertEquals(args.get(0), request.fullName);
        assertEquals(args.get(1), request.occupation);
        assertEquals(args.get(2), request.ageCategory);
        assertEquals(args.get(3), request.employmentStatus);
        assertTrue(request.uSCitizen);
        assertEquals(args.get(5), request.taxId);
        assertEquals(args.get(6), request.gender);
    }

    @Test
    public void makeMethodShouldReturnDeletePersonRequest() {
        int idToBeDeleted = 1;
        args.put(0, idToBeDeleted);
        DeletePersonRequest request = (DeletePersonRequest) builder.make("DeletePerson", args);
        assertEquals(idToBeDeleted, request.id);
    }

    @Test
    public void makeMethodShouldReturnExportRequest() {
        File file = new File("Export.per");
        args.put(0, file);
        ExportRequest request = (ExportRequest) builder.make("Export", args);
        assertEquals(file, request.file);
    }

    @Test
    public void makeMethodShouldReturnImportRequest() {
        File file = new File("Import.per");
        args.put(0, file);
        ImportRequest request = (ImportRequest) builder.make("Import", args);
        assertEquals(file, request.file);
    }

    @Test
    public void makeMethodShouldReturnUpdatePersonRequest() {
        args.put(0, 1);
        args.put(1, "Full Name");
        args.put(2, "Occupation");
        args.put(3, 0);
        args.put(4, 0);
        args.put(5, true);
        args.put(6, "Tax ID");
        args.put(7, "Gender");

        UpdatePersonRequest request = (UpdatePersonRequest) builder.make("UpdatePerson", args);
        assertEquals(args.get(0), request.id);
        assertEquals(args.get(1), request.fullName);
        assertEquals(args.get(2), request.occupation);
        assertEquals(args.get(3), request.ageCategory);
        assertEquals(args.get(4), request.employmentStatus);
        assertTrue(request.uSCitizen);
        assertEquals(args.get(6), request.taxId);
        assertEquals(args.get(7), request.gender);
    }
}