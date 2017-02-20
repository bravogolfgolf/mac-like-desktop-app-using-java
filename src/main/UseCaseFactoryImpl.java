package main;

import data.RepositoryInteractor;
import domain.*;
import ui.PresenterImpl;

public class UseCaseFactoryImpl implements ui.UseCaseFactory {

    private final RepositoryInteractor repository;
    private final ExportImport exportImport;
    private final PresenterImpl presenter;

    UseCaseFactoryImpl(RepositoryInteractor repository, ExportImport exportImport, PresenterImpl presenter) {

        this.repository = repository;
        this.exportImport = exportImport;
        this.presenter = presenter;
    }

    @Override
    public UseCase make(String useCase) {
        if (useCase.equals("RefreshUseCase"))
            return new RefreshUseCase(repository, presenter);
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
