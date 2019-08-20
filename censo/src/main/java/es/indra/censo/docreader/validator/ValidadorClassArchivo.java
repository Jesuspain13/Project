package es.indra.censo.docreader.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;


public class ValidadorClassArchivo implements ConstraintValidator<ValidadorArchivo, MultipartFile> {

		@Override
	    public void initialize(ValidadorArchivo contactNumber) {
	    }
	 
		@Override
		public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
			// TODO Auto-generated method stub
			if (value.isEmpty() || value.getSize() == 0) {
				return false;
			} else {
				return true;
			}
		}


	
}
