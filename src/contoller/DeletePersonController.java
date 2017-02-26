package contoller;

import other.Controller;
import requestor.InputBoundary;
import requestor.Request;
import requestor.RequestBuilder;
import responder.Presenter;
import usecase.UseCaseFactoryImpl;
import view.View;

import java.util.ArrayList;
import java.util.Map;

public class DeletePersonController implements Controller {
    private final Request request;
    private final InputBoundary useCase;
    private final Presenter presenter;
    private final View view;

    DeletePersonController(RequestBuilder builder, Map<Integer, Object> args, UseCaseFactoryImpl factory, Presenter presenter, View view) {
        this.request = builder.make("DeletePersonRequest", args);
        this.useCase = factory.make("DeletePersonUseCase", presenter);
        this.presenter = presenter;
        this.view = view;
    }

    @Override
    public Object execute() {
        useCase.execute(request);
        return new ArrayList<>();
    }
}
