package contoller;

import contollerfactory.Controller;
import requestor.Request;
import requestor.RequestBuilder;
import requestor.UseCase;
import requestor.UseCaseFactory;
import responder.Presenter;
import responder.View;

import java.util.Map;

public class ImportController extends Controller {
    private final Request request;
    private final UseCase useCase;
    private final Presenter presenter;
    private final View view;

    public ImportController(RequestBuilder request, Map<Integer, Object> args, UseCaseFactory useCase, Presenter presenter, View view) {
        this.request = request.make("ImportRequest", args);
        this.useCase = useCase.make("ImportUseCase", presenter);
        this.presenter = presenter;
        this.view = view;
    }

    @Override
    public Object execute() {
        useCase.execute(request);
        return null;
    }
}
