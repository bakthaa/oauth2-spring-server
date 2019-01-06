/**
 * 
 */
package xyz.baktha.oaas.web.util;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Validator;
import org.owasp.esapi.errors.IntrusionException;
import org.springframework.stereotype.Component;

/**
 * @author power-team
 *
 */
@Component
final public class ValidationUtil {

	public Validator validator = ESAPI.validator();

	/**
	 * Calls isValidInput and returns true if no exceptions are thrown.
	 */
	public boolean isValidInput(final String context, final String input, final String type, final int minLength,
			final int maxLength, final boolean allowNull) throws IntrusionException {

		if (!allowNull && (null == input || input.length() < minLength)) {

			return false;
		}

		return (validator.isValidInput(context, input, type, maxLength, allowNull));
	}

}
