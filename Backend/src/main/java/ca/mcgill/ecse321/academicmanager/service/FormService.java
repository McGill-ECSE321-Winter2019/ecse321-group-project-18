package ca.mcgill.ecse321.academicmanager.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.academicmanager.dao.FormRepository;
import ca.mcgill.ecse321.academicmanager.exceptions.NullArgumentException;
import ca.mcgill.ecse321.academicmanager.model.CoopTermRegistration;
import ca.mcgill.ecse321.academicmanager.model.Form;
import ca.mcgill.ecse321.academicmanager.model.FormType;

/**
 * form service class to interface with the database
 * 
 * @author Group-18
 * @version 2.0
 *
 */
@Service
public class FormService {
	@Autowired
	FormRepository formRepository;
	@Autowired
	CoopTermRegistrationService coopTermRegistrationService;

	// ---CREATE---
	/**
	 * Creates a new Form instance
	 * 
	 * @param formID   id of the Form
	 * @param name     name of the Form
	 * @param pdflink  link to the Form
	 * @param formtype Type of the Form
	 * @param ctr      CoopTermRegistration instance associated with Form
	 * @return Form instance
	 */
	@Transactional
	public Form create(String formID, String name, String pdflink, FormType formtype, CoopTermRegistration ctr) {
		if (!Helper.checkArg(name) || !Helper.checkArg(pdflink) || !Helper.checkArg(formtype)
				|| !Helper.checkArg(ctr)) {
			throw new NullArgumentException();
		}

		Form form = new Form();
		form.setFormID(formID);
		form.setName(name);
		form.setPdfLink(pdflink);
		form.setFormType(formtype);
		form.setCoopTermRegistration(ctr);

		return formRepository.save(form);
	}

	// ---UPDATE---
	/**
	 * Updated name of form
	 * 
	 * @param form Form instance
	 * @param name new name of form to be updated
	 * @return updated Form instance
	 */
	@Transactional
	public Form updateName(Form form, String name) {
		if (!Helper.checkArg(form)) {
			throw new IllegalArgumentException("form is null");
		}
		if (Helper.checkArg(name)) {
			form.setName(name);
		}

		return formRepository.save(form);
	}

	/**
	 * Updated form link
	 * 
	 * @param form    Form instance
	 * @param pdflink new link of form to be updated
	 * @return updated Form instance
	 */
	@Transactional
	public Form updateLink(Form form, String pdflink) {
		if (!Helper.checkArg(form)) {
			throw new IllegalArgumentException("form is null");
		}
		if (Helper.checkArg(pdflink)) {
			form.setPdfLink(pdflink);
		}

		return formRepository.save(form);
	}

	/**
	 * Updated form type
	 * 
	 * @param form     Form instance
	 * @param formtype new form type to be updated
	 * @return updated Form instance
	 */
	@Transactional
	public Form updateType(Form form, FormType formtype) {
		if (!Helper.checkArg(form)) {
			throw new IllegalArgumentException("form is null");
		}
		if (Helper.checkArg(formtype)) {
			form.setFormType(formtype);
		}

		return formRepository.save(form);
	}

	// ---GET---
	/**
	 * Gets Form instance based on formId
	 * 
	 * @param formId id of the Form
	 * @return Form instance
	 */
	@Transactional
	public Form get(String formId) {
		return formRepository.findByFormID(formId);
	}

	/**
	 * Gets all Form instances
	 * 
	 * @return Set of Form instances
	 */
	@Transactional
	public Set<Form> getAll() {
		return Helper.toSet(formRepository.findAll());
	}

	/**
	 * Gets employer evaluation Form instances
	 * 
	 * @return Set of Form instances
	 */
	@Transactional
	public Set<Form> getAllEmployerEvalForms() {
		Set<CoopTermRegistration> ctrs = coopTermRegistrationService.getAll();

		Set<Form> forms = new HashSet<Form>();
		Set<Form> employerForms = new HashSet<Form>();

		for (CoopTermRegistration ctr : ctrs) {
			forms = ctr.getForm();
			for (Form form : forms) {
				if (form.getFormType() == FormType.COOPEVALUATION) {
					employerForms.add(form);
				}
			}
		}
		return employerForms;
	}

	/**
	 * Gets student evaluation Form instances
	 * 
	 * @return Set of Form instances
	 */
	@Transactional
	public Set<Form> getAllStudentEvalForms() {
		Set<CoopTermRegistration> ctrs = coopTermRegistrationService.getAll();

		Set<Form> forms = new HashSet<Form>();
		Set<Form> studentForms = new HashSet<Form>();

		for (CoopTermRegistration ctr : ctrs) {
			forms = ctr.getForm();
			for (Form form : forms) {
				if (form.getFormType() == FormType.STUDENTEVALUATION) {
					studentForms.add(form);
				}
			}
		}
		return studentForms;
	}

	// ---DELETE---
	/**
	 * Deletes a From instance
	 * 
	 * @param form Form instance to be deleted
	 */
	@Transactional
	public void delete(Form form) {
		formRepository.delete(form);
	}
}
