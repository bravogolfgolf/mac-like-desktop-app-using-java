package ui;

import data.PersonRepository;
import data.PersonRepositoryInMemory;
import domain.InputBoundary;
import domain.PersonTableModelRecord;
import domain.Presenter;
import domain.Request;
import domain.importfile.ImportRequest;
import main.RequestBuilderImpl;
import main.UseCaseFactoryImpl;
import org.junit.Before;
import org.junit.Test;
import ui.contoller.Controller;
import ui.contoller.ImportController;
import ui.contoller.UseCaseFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ImportControllerTest implements InputBoundary, View {

    private ImportRequest r;

    @Override
    public void execute(Request request) {
        this.r = (ImportRequest) request;
    }

    private PersonTableModelRecord[] records;

    @Override
    public String generateView(PersonTableModelRecord[] records) {
        this.records = records;
        return null;
    }

    private final RequestBuilder requestBuilder = new RequestBuilderImpl();
    private final Map<Integer, Object> args = new HashMap<>();
    private final Presenter presenter = new PersonTablePanelPresenter();
    private final View view = this;

    private final File file = new File("ImportTest.per");

    @Before
    public void setUp() throws Exception {
        args.put(1, file);
    }

    @Test
    public void shouldSendRequestToUseCase() {
        UseCaseFactory factory = (useCase, presenter) -> ImportControllerTest.this;
        Controller controller = new ImportController(requestBuilder, args, factory, presenter, view);

        controller.execute();

        assertEquals(file, r.file);
    }

    @Test
    public void shouldReturnRecords() {
        PersonRepository repository = new PersonRepositoryInMemory();
        UseCaseFactory factory = new UseCaseFactoryImpl(repository);
        Controller controller = new ImportController(requestBuilder, args, factory, presenter, view);

        controller.execute();

        assertEquals(1, records.length);
    }
}


