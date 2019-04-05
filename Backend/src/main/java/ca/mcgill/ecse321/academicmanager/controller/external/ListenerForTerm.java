package ca.mcgill.ecse321.academicmanager.controller.external;

import ca.mcgill.ecse321.academicmanager.model.Term;
import ca.mcgill.ecse321.academicmanager.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashSet;

abstract class ListenerForTerm extends Listener {
    /** Contains a Set of all terms retrieved from the external database. */
    protected HashSet<ExternalTermDto> terms = new HashSet<>();

    @Autowired
    TermService termService;

    @Override
    protected abstract String trigger();

    @Override
    protected abstract void interpretRequest(String jsonString) throws RuntimeException;

    @Override
    protected final void handleDependencies() {
        // no dependencies for term, the method will be made final to
        // avoid further misleading overriding.
    }

    @Override
    protected void removeObsolete() {
        for (Term internalTerm : termService.getAll()) {
            if (!terms.contains(internalTerm.getTermID())) {
                termService.delete(internalTerm);
            }
        }
    }

    @Override
    protected void postData() {
        for (ExternalTermDto externalTerm : terms) {
            if (!termService.exists(externalTerm.getTermID()))
            {
                termService.create(
                        externalTerm.getTermID(),
                        externalTerm.getTermName(),
                        externalTerm.getDate1(),
                        externalTerm.getDate2()
                );}
        }
    }
}
