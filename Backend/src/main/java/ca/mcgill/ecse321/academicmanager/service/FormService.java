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

@Service
public class FormService {
	@Autowired
	FormRepository formRepository;
	@Autowired
	CoopTermRegistrationService coopTermRegistrationService;

	// ---CREATE---
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
	@Transactional
	Form updateName(Form form, String name) {
		if (!Helper.checkArg(form)) {
			throw new IllegalArgumentException("form is null");
		}
		if (Helper.checkArg(name)) {
			form.setName(name);
		}

		return formRepository.save(form);
	}

	@Transactional
	Form updateLink(Form form, String pdflink) {
		if (!Helper.checkArg(form)) {
			throw new IllegalArgumentException("form is null");
		}
		if (Helper.checkArg(pdflink)) {
			form.setPdfLink(pdflink);
		}

		return formRepository.save(form);
	}

	@Transactional
	Form updateType(Form form, FormType formtype) {
		if (!Helper.checkArg(form)) {
			throw new IllegalArgumentException("form is null");
		}
		if (Helper.checkArg(formtype)) {
			form.setFormType(formtype);
		}

		return formRepository.save(form);
	}

	// ---GET---
	@Transactional
	public Form get(String formId) {
		return formRepository.findByFormID(formId);
	}

	@Transactional
	public Set<Form> getAll() {
		return Helper.toSet(formRepository.findAll());
	}

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
	@Transactional
	public void delete(Form form) {
		formRepository.delete(form);
	}
}
