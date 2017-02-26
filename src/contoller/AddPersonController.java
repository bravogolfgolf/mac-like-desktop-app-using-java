package contoller;

import other.Controller;
import requestor.InputBoundary;
import requestor.Request;
import requestor.RequestBuilder;
import responder.Presenter;
import usecase.UseCaseFactoryImpl;
import view.View;

import java.util.Map;

public class AddPersonController implements Controller {
    private final Request request;
    private final InputBoundary useCase;
    private final Presenter presenter;
    private final View view;

    AddPersonController(RequestBuilder request, Map<Integer, Object> args, UseCaseFactoryImpl useCase, Presenter presenter, View view) {
        this.request = request.make("AddPersonRequest", args);
        this.useCase = useCase.make("AddPersonUseCase", presenter);
        this.presenter = presenter;
        this.view = view;
    }

    @Override
    public Object execute() {
        useCase.execute(request);
        return "AddPersonUseCase";
    }
}
