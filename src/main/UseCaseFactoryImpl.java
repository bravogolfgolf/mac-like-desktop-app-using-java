package main;

import data.RepositoryInteractor;
import domain.*;
import ui.PresenterImpl;

public class UseCaseFactoryImpl implements ui.UseCaseFactory {

    private final RepositoryInteractor repository;
    private final ExportImport exportImport;
    private final PresenterImpl presenter;
    private final ResponseBuilder builder;

    UseCaseFactoryImpl(RepositoryInteractor repository, ExportImport exportImport, ResponseBuilder builder, PresenterImpl presenter) {

        this.repository = repository;
        this.exportImport = exportImport;
        this.builder = builder;
        this.presenter = presenter;
    }

    @Override
    public UseCase make(String useCase) {
        if (useCase.equals("RefreshUseCase"))
            return new RefreshUseCase(repository, builder, presenter);
        if (useCase.equals("AddPersonUseCase"))
            return new AddPersonUseCase(repository);
        if (useCase.equals("DeletePersonUseCase"))
            return new DeletePersonUseCase(repository);
        if (useCase.equals("ExportUseCase"))
            return new ExportUseCase(repository, exportImport);
        if (useCase.equals("ImportUseCase"))
            return new ImportUseCase(exportImport, repository);
        return null;
    }
}
